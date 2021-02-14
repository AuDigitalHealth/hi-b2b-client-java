package au.gov.nehta.vendorlibrary.hi.client;

/**
 * A Base implementation of the client wrapper that allows access to the
 * WS Port for the XXXXX._30 series of classes
 *
 * @author NeHTA
 * @deprecated
 */
public class BaseClient_30<T> extends ClientBase<T> {

    @Override
    protected boolean hasNoOrganisationQualifiedId() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     *//**
     * The user Qualified ID associated with this use of the ProviderSearchHIProviderDirectoryForOrganisation service
     *//*
    protected final QualifiedId individualQualifiedId;
  
    *//**
     * The organisation Qualified ID associated with this use of the ProviderSearchHIProviderDirectoryForOrganisation service
     *//*
    protected final QualifiedId organisationQualifiedId;
    
    
    *//**
     * The Product details associated with this use of the ProviderSearchHIProviderDirectoryForIndividual service
     *//*
    protected final ProductType productHeader;
  
    public BaseClient_30(
            final Class<T> portClass,
            final Class<? extends Service> serviceClass,
            final QualifiedId individualQualifiedId,
            final QualifiedId organisationQualifiedId, 
            final ProductType productHeader,
            final PrivateKey signingPrivateKey,
            final X509Certificate signingCertificate, 
            final SSLSocketFactory sslSocketFactory,
            final String serviceEndpoint
            ) {
        
        ArgumentUtils.checkNotNull(serviceClass, "serviceClass");
        ArgumentUtils.checkNotNull(portClass, "portClass");
        ArgumentUtils.checkNotNullNorBlank(serviceEndpoint, "serviceEndpoint");
        ArgumentUtils.checkNotNull(individualQualifiedId, "individualQualifiedId");
        ArgumentUtils.checkNotNull(productHeader, "productHeader");
        ArgumentUtils.checkNotNull(signingPrivateKey, "signingPrivateKey");
        ArgumentUtils.checkNotNull(signingCertificate, "signingPrivateKey");
        ArgumentUtils.checkNotNull(sslSocketFactory, "sslSocketFactory");
        
        this.productHeader = productHeader;
        this.organisationQualifiedId=organisationQualifiedId;
        this.individualQualifiedId=individualQualifiedId;
        this.signingPrivateKey = signingPrivateKey;
        this.signingCertificate = signingCertificate;
        this.sslSocketFactory = sslSocketFactory;
        this.loggingHandler = new LoggingHandler(true); //Set true to dump the SOAP message to the default logger.
        
        @SuppressWarnings("rawtypes")
        List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(loggingHandler);
        
        this.port =(T) WebServiceClientUtil.getPort(
                portClass,
                serviceClass,
                sslSocketFactory,
                handlerChain);
        
        configureEndpoint(this.port, serviceEndpoint);
    }
    
    *//**
     * An alternate base class that takes the wrapped version of XML bound objects
     *//*
    public BaseClient_30(
            final Class<T> portClass,
            final Class<? extends Service> serviceClass,
            final  au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
            final  au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId organisationQualifiedId, 
            final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
            final PrivateKey signingPrivateKey,
            final X509Certificate signingCertificate, 
            final SSLSocketFactory sslSocketFactory,
            final String serviceEndpoint
            ) {
        
        ArgumentUtils.checkNotNull(serviceClass, "serviceClass");
        ArgumentUtils.checkNotNull(portClass, "portClass");
        ArgumentUtils.checkNotNullNorBlank(serviceEndpoint, "serviceEndpoint");
        ArgumentUtils.checkNotNull(productHeader, "productHeader");
        ArgumentUtils.checkNotNull(signingPrivateKey, "signingPrivateKey");
        ArgumentUtils.checkNotNull(signingCertificate, "signingPrivateKey");
        ArgumentUtils.checkNotNull(sslSocketFactory, "sslSocketFactory");
        
        this.productHeader = productHeader.as30Type();
        
        if(organisationQualifiedId!= null){
            this.organisationQualifiedId=organisationQualifiedId.as30Type();
        }else{
            this.organisationQualifiedId=null;
        }
        
        if(individualQualifiedId != null){
            this.individualQualifiedId=individualQualifiedId.as30Type();
        }else{
            this.individualQualifiedId=null;
        }
        this.signingPrivateKey = signingPrivateKey;
        this.signingCertificate = signingCertificate;
        this.sslSocketFactory = sslSocketFactory;
        this.loggingHandler = new LoggingHandler(true); //Set true to dump the SOAP message to the default logger.
        
        @SuppressWarnings("rawtypes")
        List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(loggingHandler);
        
        this.port =(T) WebServiceClientUtil.getPort(
                portClass,
                serviceClass,
                sslSocketFactory,
                handlerChain);
        
        configureEndpoint(this.port, serviceEndpoint);
    }

    *//**
     * Returns the current
     * {@link au.net.electronichealth.ns.hi.common.commoncoreelements._3_0.TimestampType}
     *
     * @return {@link au.net.electronichealth.ns.hi.common.commoncoreelements._3_0.TimestampType}
     *         instance with current as created time.
     *//*
    protected TimestampType getTimestampHeader() {
        TimestampType timestampHeader = new TimestampType();
        timestampHeader.setCreated(TimeUtility.nowXMLGregorianCalendar());
        return timestampHeader;
    }

    @Override
    protected boolean hasNoOrganisationQualifiedId() {
        return organisationQualifiedId == null;
    }
    
    protected void checkUserID() {
        if(this.individualQualifiedId == null) throw new IllegalArgumentException(QUALIFIED_ID_MISSING);
    }*/
}
