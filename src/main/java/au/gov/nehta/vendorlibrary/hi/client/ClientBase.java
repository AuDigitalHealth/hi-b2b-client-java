package au.gov.nehta.vendorlibrary.hi.client;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import au.gov.nehta.vendorlibrary.hi.handler.message.HIHeaderHandler;
import au.gov.nehta.vendorlibrary.hi.handler.security.HISecurityHandler;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.gov.nehta.xsp.CertificateValidator;

public abstract class ClientBase<T> {

    /**
     * The Metro/XML Webservice binding port for webservice operations
     */
    protected T port;
    /**
     * The SSL Socket Factory to be used for connecting to the Web Service provider
     */
    protected SSLSocketFactory sslSocketFactory;

    /**
     * The Private Key to be used for Signing
     */
    protected PrivateKey signingPrivateKey;

    /**
     * The Certificate to be used for Signing
     */
    protected X509Certificate signingCertificate;

    /**
     * SSL socket factory class name
     */
    protected static final String SSL_SOCKET_FACTORY_INTERNAL = "com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory";

    /**
     * SSL socket factory class name
     * Also added for non "internal" clients where the Metro libraries are supplied on the classpath
     */
    protected static final String SSL_SOCKET_FACTORY = "com.sun.xml.ws.transport.https.client.SSLSocketFactory";

    /**
     * Empty String variable.
     */
    protected static final String EMPTY = "";

    /**
     * a validator to check the certificate of incoming signed SOAP messages
     */
    protected CertificateValidator certificateValidator;

    /**
     * The Logging handler instance for logging SOAP request and Response messages.
     */
    protected LoggingHandler loggingHandler;

    /* error message for qualified user ID*/
    protected static final String QUALIFIED_ID_MISSING = "individualQualifiedId must be supplied, construct the client with a individualQualifiedId or use the alternate request method to supply the qualifiedUserID per request";

    /**
     * Configure the endpoint using the provided configuration information.
     *
     * @param servicePort the service definition. (Mandatory)
     * @param endpoint    the URL for the Web service endpoint. (Mandatory)
     */
    protected void configureEndpoint(final Object servicePort, final String endpoint) {
        final BindingProvider bindingProvider = (BindingProvider) servicePort;

        // Set details on request context
        final Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint.trim());

        requestContext.put(SSL_SOCKET_FACTORY, this.sslSocketFactory);
        requestContext.put(SSL_SOCKET_FACTORY_INTERNAL, this.sslSocketFactory);

        if (bindingProvider.getBinding() != null) {
            Binding binding = bindingProvider.getBinding();
            // Get handler chain
            @SuppressWarnings("rawtypes")
            List<Handler> handlerChain = binding.getHandlerChain();


            List<String> headerNames = new ArrayList<>();
            /*
             * HACK WARNING:
             *
             * Medicare cannot handle <pre>
             * <wsa:FaultTo>Anything</wsa:FaultTo>
             * </pre>
             * so we strip it from the outbound messages before signing.
             *
             * We should remove the following line (and hence not strip the element)
             * when Medicare allow FaultTo as per WSA Addressing 2005/08
             */
            headerNames.add(HIHeaderHandler.FAULT_TO_HEADER_ELEMENT_NAME);

            // Remove hpioHeader if value is not specified.
            if (hasNoOrganisationQualifiedId()) {
                headerNames.add(HIHeaderHandler.HPIO_CSP_HEADER_ELEMENT_NAME);
            }

            handlerChain.add(new HIHeaderHandler(headerNames));

            // Add HISecurityHandler to sign the Outgoing SOAP message and verify the incoming SOAP message.
            handlerChain.add(new HISecurityHandler(this.signingCertificate, this.signingPrivateKey, certificateValidator));

            // Add handler to capture inbound and outbound SOAP messages
            handlerChain.add(this.loggingHandler);
            binding.setHandlerChain(handlerChain);
        }
    }

    /**
     * @return if subclass has a null organisation qualified id
     */
    protected abstract boolean hasNoOrganisationQualifiedId();

    /**
     * Return access to the underlying webservice client object
     *
     * @return webservice port
     */
    public T getPort() {
        return port;
    }

    /**
     * Convenience method
     * Return access to the  underlying webservice client as a binding provider
     */
    public BindingProvider getBindingProvider() {
        return (BindingProvider) port;
    }

    /**
     * Getter for lastSoapResponse.
     *
     * @return lastSoapResponse the lastSoapResponse instance variable
     */
    public final String getLastSoapResponse() {
        if (loggingHandler != null) {
            return loggingHandler.getLastSoapResponse();
        } else {
            return EMPTY;
        }
    }

    /**
     * Getter for lastSoapRequest.
     *
     * @return lastSoapRequest the lastSoapRequest instance variable (Mandatory)
     */
    public final String getLastSoapRequest() {
        if (loggingHandler != null) {
            return loggingHandler.getLastSoapRequest();
        } else {
            return EMPTY;
        }
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
