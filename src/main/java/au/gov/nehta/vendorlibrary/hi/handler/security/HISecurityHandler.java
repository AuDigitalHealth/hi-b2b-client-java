/*
 * Copyright 2011 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.hi.handler.security;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.xsp.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * This SOAP handler constructs the Medicare HI security header elements in the
 * outgoing SOAP requests and verifies incoming SOAP Response from HI service to
 * ensure conformance to HI guidelines. If a SOAP handler is specified in the
 * handler chain of a JAX-WS client or service, JAX-WS will call the SOAP handler
 * before a SOAP message is sent and after a SOAP message has been received. <br>
 */
public class HISecurityHandler implements SOAPHandler<SOAPMessageContext> {

    /**
     * Medicare HI header product XML element local name.
     */
    public static final String PRODUCT = "product";
    /**
     * Medicare HI header timestamp XML element local name.
     */
    public static final String TIMESTAMP = "timestamp";
    /**
     * Medicare HI header User XML element local name .
     */
    public static final String USER = "user";
    /**
     * Medicare HI header UHI audit XML element namespace.
     */
    public static final String COMMON_COREELEMENTS_NS = "http://ns.electronichealth.net.au/hi/xsd/common/CommonCoreElements/3.0";
    /**
     * Digital Signature namespace  .
     */
    public static final String DIG_NS = "http://www.w3.org/2000/09/xmldsig#";
    /**
     * Medicare HI header Signature XML element local name.
     */
    public static final String SIGNATURE_ELEM = "signature";

    /**
     * XMLS namespace attribute name
     */
    private static final String XMLNS = "xmlns:xml";

    /**
     * The signing certificate key
     */
    private X509Certificate x509Certificate;

    /**
     * The signing private key
     */
    private PrivateKey privateKey;

    /**
     * a validator to check the certificate of incoming signed SOAP messages
     */
    private CertificateValidator certificateValidator;

    /**
     * Default constructor.
     *
     * @param x509Certificate the certificate key to be used for signing (Mandatory)
     * @param privateKey      the private key to be used for signing (Mandatory)
     */
    public HISecurityHandler(X509Certificate x509Certificate, PrivateKey privateKey, CertificateValidator certificateValidator) {
        ArgumentUtils.checkNotNull(x509Certificate, "x509Certificate");
        ArgumentUtils.checkNotNull(privateKey, "privateKey");
        ArgumentUtils.checkNotNull(privateKey, "certificateValidator");
        this.x509Certificate = x509Certificate;
        this.privateKey = privateKey;
        this.certificateValidator = certificateValidator;
    }

    /**
     * Updates the SOAP headers in outgoing SOAP requests to ensure conformance
     * to NEHTA's guidelines.
     *
     * @param context the incoming / outgoing soap message context
     * @return true Always returns true.
     * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.MessageContext)
     */
    public final boolean handleMessage(final SOAPMessageContext context) {
        Boolean isOutgoing = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (isOutgoing) {
            signBodyAndSOAPHeaders(context);
        } else {
            // Verifies the inbound message signature.
            extractElementsAndVerifyingSignature(context);
        }
        return true;
    }

    /**
     * Ignore Fault and continues with processing logical handling of message.
     *
     * @param context the incoming / outgoing soap message context
     * @return true if the handle signature check is successful.
     * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext)
     */
    public final boolean handleFault(final SOAPMessageContext context) {
        if (!(Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            // Verifies the inbound fault signature.
            extractElementsAndVerifyingSignature(context);
        }
        return true;
    }

    /**
     * Does nothing returns null.<br>
     * Ignore processing of SOAP header as the primary intention is just to
     * 'Dump' the SOAP message
     *
     * @return @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
     */
    public final Set<QName> getHeaders() {
        return null;
    }

    /**
     * Does nothing <br>
     * Not utilised for dumping SOAP message.
     *
     * @param context @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
     */
    public void close(final MessageContext context) {
        //Do nothing
    }

    /**
     * Signs the SOAP message parts matching the Medicare UHI specification. <br>
     * Metro toolkit removes the SOAP body attribute Id. A work around as been
     * introduced to remove WSS Security Tube from the Tubeline Assembly for TLS
     * based service <br>
     *
     * @param context of type {@link javax.xml.ws.handler.soap.SOAPMessageContext} (Mandatory)
     *                returns {@link javax.xml.ws.handler.soap.SOAPMessageContext} with the signed SOAP element
     */
    private void signBodyAndSOAPHeaders(final SOAPMessageContext context) {
        ArgumentUtils.checkNotNull(context, "context");
        SOAPMessageContext localContext = context;
        try {
            // Local variables for signing the SOAP header and body elements.
            SOAPHeader header;
            SOAPBody body;

            SOAPPart soapPart = getSoapPartFromLocalContext(localContext);
            ArgumentUtils.checkNotNull(soapPart, "Outgoing message soapPart");
            ArgumentUtils.checkNotNull(soapPart.getEnvelope(), "Outgoing message soapPart.getEnvelope()");
            header = soapPart.getEnvelope().getHeader();
            body = soapPart.getEnvelope().getBody();

            extractAndSignSoapElements(header, body);

        } catch (SOAPException | XspException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    /**
     * Extract the SOAPPart from the provided SOAP message context
     *
     * @param localContext the incoming/outgoing SOAP message context
     * @return the extracted SOAPPart of the SOAPMessage context
     */
    private SOAPPart getSoapPartFromLocalContext(SOAPMessageContext localContext) {
        ArgumentUtils.checkNotNull(localContext, "SOAPMessageContext");
        ArgumentUtils.checkNotNull(localContext.getMessage(), "SOAPMessageContext.getMessage()");
        return localContext.getMessage().getSOAPPart();
    }

    /**
     * Extracts elements from SOAP header and SOAP body and signs them abiding the Medicare Australia's Signing specification.
     *
     * @param header the SOAP header to be signed
     * @param body   the SOAP body element to be signed.
     * @throws XspException in an event of signature failure.
     */
    private void extractAndSignSoapElements(SOAPHeader header, Element body) throws XspException {

        ArgumentUtils.checkNotNull(header, "Outgoing message SOAP header");
        ArgumentUtils.checkNotNull(body, "Outgoing message SOAP body");

        // Obtain the User, Product, SignatureElement and timestamp node as Element.
        // These elements are signed abiding Medicare Australia signing specification.
        // Sign the body and the SOAP header elements to include message integrity
        NodeList userNodeList = header.getElementsByTagNameNS(COMMON_COREELEMENTS_NS, USER);
        NodeList prodNodeList = header.getElementsByTagNameNS(COMMON_COREELEMENTS_NS, PRODUCT);
        NodeList timestampNodeList = header.getElementsByTagNameNS(COMMON_COREELEMENTS_NS, TIMESTAMP);
        NodeList sigNodeList = header.getElementsByTagNameNS(COMMON_COREELEMENTS_NS, SIGNATURE_ELEM);

        Element userElem = getFirstElementFromNodeList(userNodeList);
        Element prodElem = getFirstElementFromNodeList(prodNodeList);
        Element timestampElem = getFirstElementFromNodeList(timestampNodeList);
        Element sigElem = getFirstElementFromNodeList(sigNodeList);

        // Remove the xmlns"xml attribute if added by JAXB
        removeXMLNS(userElem);
        removeXMLNS(prodElem);
        removeXMLNS(timestampElem);
        removeXMLNS(sigElem);

        //XSP-API will add the correct ID elements
        //addSignatureReferenceId(body, timestampElem, userElem);
        signSoapElements(timestampElem, userElem, body, sigElem);
    }

    /**
     * Signs the provided SOAP elements.
     *
     * @param timestampElem the header timestamp element to be signed
     * @param userElem      the header user element to be signed
     * @param body          the SOAP body element ot be signed.
     * @param sigElem       the header signature element to be signed.
     * @throws XspException in an event of signature failure.
     */
    private void signSoapElements(Element timestampElem, Element userElem, Element body, Element sigElem) throws XspException {
        List<Element> elementsToSign = Arrays.asList(timestampElem, userElem,
                body);
        // Obtain the required public certificate and private key for
        // performing XML digital signing
        List<X500PrivateCredential> certificateKeyPairs = Collections.singletonList(new X500PrivateCredential(
                x509Certificate, privateKey));

        // Sign the Body , timestamp and user elements.
        // Add the signed value to signature element for remote
        // endpoint verification
        XmlSignatureProfileService xmlSignatureProfileService = XspFactory
                .getInstance().getXmlSignatureProfileService(
                        XspVersion.V_2010);
        xmlSignatureProfileService.sign(sigElem, elementsToSign,
                certificateKeyPairs);
    }

    /**
     * Returns the first element from the provided nodeList
     *
     * @param nodeList containing the SOAP header/body element.
     * @return the first element from the nodeList
     */
    private Element getFirstElementFromNodeList(NodeList nodeList) {
        Element element = null;
        if (nodeList.getLength() > 0) {
            ArgumentUtils.checkNotNull(nodeList.item(0), "nodeList.item(0)");
            element = (Element) nodeList.item(0);
        }
        return element;
    }

    /**
     * This method removes the unwanted XML namespace prefix from the provided
     * Element <br>
     * The xmlns:xml namespace prefix mapping breaks the WSIT interoperability. <br>
     * Need to report to JAXWS to avoid this step.
     *
     * @param elem {@link org.w3c.dom.Element} which contains xmlns:xml namespace prefix (Mandatory)
     */
    private static void removeXMLNS(final Element elem) {
        if (elem.hasAttribute(XMLNS)) {
            elem.removeAttribute(XMLNS);
        }
    }

    /**
     * Verifies the signature value of the provided {@link javax.xml.ws.handler.soap.SOAPMessageContext}.
     *
     * @param context the MCA Inbound {@link javax.xml.ws.handler.soap.SOAPMessageContext}
     */
    private void extractElementsAndVerifyingSignature(final SOAPMessageContext context) {
        SOAPMessageContext localContext = context;

        SOAPEnvelope envelope;
        SOAPPart soapPart = getSoapPartFromLocalContext(localContext);
        try {
            if (soapPart != null && soapPart.getEnvelope() != null) {
                soapPart = localContext.getMessage().getSOAPPart();
                envelope = soapPart.getEnvelope();
                verifySoapElements(envelope.getHeader());
            } else {
                throw new IllegalArgumentException("SOAP missing envelope");
            }
        } catch (XspException | SOAPException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    /**
     * Verifies the signature element for the provided SOAP header.
     *
     * @param header the SOAP header containing the signature element.
     * @throws XspException in an event of signature verification failure.
     */
    private void verifySoapElements(SOAPHeader header) throws XspException {
        try {

            ArgumentUtils.checkNotNull(header, "Failed to verify incoming signature. Invalid SOAP header");
            NodeList sigList = header.getElementsByTagNameNS(DIG_NS, "Signature");

            //Older Java XML Parsers will not recognise the special attribute xml:id
            //so we explicitly set the attributes where this id is known to be, to allow
            //the signature check to dereference the ID attribute
            registerIDAttributes(header.getOwnerDocument());


            // Sign the Body , timestamp and user elements.
            // Add the signed value to signature element for remote
            // endpoint verification
            XmlSignatureProfileService xmlSignatureProfile = XspFactory.getInstance().getXmlSignatureProfileService(XspVersion.V_2010);
            xmlSignatureProfile.check((Element) sigList.item(0), certificateValidator);

        } catch (Exception ex) {
            throw new XspException(ex.getMessage(), ex);

        }
    }

    private void registerIDAttributes(Document doc) {
        NodeList bodyTags = doc.getDocumentElement().getElementsByTagNameNS("http://www.w3.org/2003/05/soap-envelope", "Body");
        for (int i = 0; i < bodyTags.getLength(); i++) {
            Element element = (Element) bodyTags.item(i);
            Attr xmlid = element.getAttributeNode("xml:id");
            element.setIdAttributeNode(xmlid, true);
        }

    }
}
