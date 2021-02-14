package au.gov.nehta.vendorlibrary.hi.handler.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.gov.nehta.common.utils.ArgumentUtils;

public class HIHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    /**
     * CSP HPI-O header name.
     */
    public static final String HPIO_CSP_HEADER_ELEMENT_NAME = "hpio";

    /**
     * FaultTo header name. Medicare cannot handle
     *
     * <pre>
     * <wsa:FaultTo>Anything</wsa:FaultTo>
     * </pre>
     * <p>
     * so we strip it from the outbound messages before signing.
     */
    public static final String FAULT_TO_HEADER_ELEMENT_NAME = "FaultTo";

    /**
     * Header node name(s) to remove from the SOAP message.
     */
    private final List<String> headerNames;

    /**
     * Default constructor.
     *
     * @param headerNames Headers to remove from message.
     */
    public HIHeaderHandler(List<String> headerNames) {
        ArgumentUtils.checkNotNull(headerNames, "headerNames");

        this.headerNames = headerNames;
    }

    /**
     * Updates the SOAP headers in outgoing SOAP requests as required.
     *
     * @param context the incoming / outgoing soap message context
     * @return true Always returns true.
     * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.MessageContext)
     */
    public final boolean handleMessage(final SOAPMessageContext context) {
        Boolean isOutgoing = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (isOutgoing) {
            removeSoapHeaders(context);
        } else {
            // No action required.
        }
        return true;
    }

    /**
     * Remove all unnecessary headers from the message.
     *
     * @param context outgoing soap message context
     */
    private void removeSoapHeaders(SOAPMessageContext context) {

        // Retrieve SOAP header from SOAP message.
        SOAPMessage message = context.getMessage();
        SOAPHeader header;
        try {
            header = message.getSOAPHeader();

            // Process each header that needs to be removed.
            for (String name : headerNames) {

                // Get child nodes of SOAP header.
                NodeList nodeList = header.getChildNodes();
                List<Node> removeNodesList = new ArrayList<>();

                // Build up a list of nodes that need to be deleted.
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeName().endsWith(name)) {
                        removeNodesList.add(nodeList.item(i));
                    }
                }

                // Remove node(s).
                for (Node node : removeNodesList) {
                    node.getParentNode().removeChild(node);
                }
            }
        } catch (SOAPException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Does nothing returns false.<br>
     *
     * @param context the incoming / outgoing soap message context
     * @return true if the handle signature check is successful.
     * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext)
     */
    public final boolean handleFault(final SOAPMessageContext context) {
        return false;
    }

    /**
     * Does nothing returns null.<br>
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
     * @param context the message context
     * @see javax.xml.ws.handler.Handler close() javax.xml.ws.handler .
     * MessageContext)
     */
    public void close(final MessageContext context) {
        // Do nothing
    }
}
