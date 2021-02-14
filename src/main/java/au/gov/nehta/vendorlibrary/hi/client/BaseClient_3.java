package au.gov.nehta.vendorlibrary.hi.client;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.common.security.SimpleCertificateValidator;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.vendorlibrary.ws.WebServiceClientUtil;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.TimestampType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


/**
 * A Base implementation of the client wrapper that allows access to the
 * WS Port for the XXXXX._3 series of classes
 *
 * @author NeHTA
 */
public class BaseClient_3<T> extends ClientBase<T> {


    /**
     * The user Qualified ID associated with this use of the ProviderSearchHIProviderDirectoryForOrganisation service
     */
    protected final QualifiedId individualQualifiedId;

    /**
     * The organisation Qualified ID associated with this use of the ProviderSearchHIProviderDirectoryForOrganisation service
     */
    protected final QualifiedId organisationQualifiedId;

    /**
     * The Product details associated with this use of the ProviderSearchHIProviderDirectoryForIndividual service
     */
    protected final ProductType productHeader;

    public BaseClient_3(
            final Class<T> portClass,
            final Class<? extends Service> serviceClass,
            final QualifiedId individualQualifiedId,
            final QualifiedId organisationQualifiedId,
            final ProductType productHeader,
            final PrivateKey signingPrivateKey,
            final X509Certificate signingCertificate,
            final SSLSocketFactory sslSocketFactory,
            final String serviceEndpoint,
            CertificateValidator certificateValidator
    ) {

        ArgumentUtils.checkNotNull(serviceClass, "serviceClass");
        ArgumentUtils.checkNotNull(portClass, "portClass");
        ArgumentUtils.checkNotNullNorBlank(serviceEndpoint, "serviceEndpoint");
        ArgumentUtils.checkNotNull(individualQualifiedId, "individualQualifiedId");
        ArgumentUtils.checkNotNull(productHeader, "productHeader");
        ArgumentUtils.checkNotNull(signingPrivateKey, "signingPrivateKey");
        ArgumentUtils.checkNotNull(signingCertificate, "signingPrivateKey");
        ArgumentUtils.checkNotNull(sslSocketFactory, "sslSocketFactory");

        // supply an optional certificate Validator
        // if not present use simple certificate validator that does cursory checks
        if (certificateValidator == null) {
            this.certificateValidator = new SimpleCertificateValidator(null);
        } else {
            this.certificateValidator = certificateValidator;
        }

        this.productHeader = productHeader;
        this.organisationQualifiedId = organisationQualifiedId;
        this.individualQualifiedId = individualQualifiedId;
        this.signingPrivateKey = signingPrivateKey;
        this.signingCertificate = signingCertificate;
        this.sslSocketFactory = sslSocketFactory;
        // Set true to dump the SOAP message to the default logger.
        this.loggingHandler = new LoggingHandler(true);


        @SuppressWarnings("rawtypes")
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(loggingHandler);

        this.port = (T) WebServiceClientUtil.getPort(
                portClass,
                serviceClass,
                sslSocketFactory,
                handlerChain);

        configureEndpoint(this.port, serviceEndpoint);
    }

    public BaseClient_3(
            final Class<T> portClass,
            final Class<? extends Service> serviceClass,
            final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
            final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId organisationQualifiedId,
            final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
            final PrivateKey signingPrivateKey,
            final X509Certificate signingCertificate,
            final SSLSocketFactory sslSocketFactory,
            final String serviceEndpoint,
            CertificateValidator certificateValidator
    ) {

        ArgumentUtils.checkNotNull(serviceClass, "serviceClass");
        ArgumentUtils.checkNotNull(portClass, "portClass");
        ArgumentUtils.checkNotNullNorBlank(serviceEndpoint, "serviceEndpoint");
        ArgumentUtils.checkNotNull(productHeader, "productHeader");
        ArgumentUtils.checkNotNull(signingPrivateKey, "signingPrivateKey");
        ArgumentUtils.checkNotNull(signingCertificate, "signingPrivateKey");
        ArgumentUtils.checkNotNull(sslSocketFactory, "sslSocketFactory");


        //supply an optional certificate Validator
        //if not present use simple certificate validator that does cursory checks
        if (certificateValidator == null) {
            this.certificateValidator = new SimpleCertificateValidator(null);
        } else {
            this.certificateValidator = certificateValidator;
        }

        this.productHeader = productHeader.as3Type();

        if (organisationQualifiedId != null) {
            this.organisationQualifiedId = organisationQualifiedId.as3Type();
        } else {
            this.organisationQualifiedId = null;
        }

        if (individualQualifiedId != null) {
            this.individualQualifiedId = individualQualifiedId.as3Type();
        } else {
            this.individualQualifiedId = null;
        }

        this.signingPrivateKey = signingPrivateKey;
        this.signingCertificate = signingCertificate;
        this.sslSocketFactory = sslSocketFactory;
        this.loggingHandler = new LoggingHandler(true); //Set true to dump the SOAP message to the default logger.


        @SuppressWarnings("rawtypes")
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(loggingHandler);

        this.port = (T) WebServiceClientUtil.getPort(
                portClass,
                serviceClass,
                sslSocketFactory,
                handlerChain);

        configureEndpoint(this.port, serviceEndpoint);
    }

    /**
     * Returns the current {@link au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.TimestampType}
     *
     * @return {@link au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.TimestampType} instance with current
     * as created time.
     */
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
        if (this.individualQualifiedId == null) throw new IllegalArgumentException(QUALIFIED_ID_MISSING);
    }
}
