/*
 * Copyright 2011 NEHTA
 * Copyright 2021-2026 ADHA (Australian Digital Health Agency)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
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
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.Handler;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


/**
 * Base client wrapper for HI SOAP ports whose generated types live in the {@code _3}
 * package namespace (Common Core Elements 3.0 and related WSDL bindings).
 *
 * @author NeHTA
 */
public class BaseClient_3<T> extends ClientBase<T> {


    /** Qualified identifier for the individual (user) on whose behalf requests are made. */
    protected final QualifiedId individualQualifiedId;

    /** Optional organisation qualified identifier when the call is scoped to an organisation. */
    protected final QualifiedId organisationQualifiedId;

    /** Product and version metadata sent on each HI request. */
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
        ArgumentUtils.checkNotNull(signingCertificate, "signingCertificate");
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
        ArgumentUtils.checkNotNull(signingCertificate, "signingCertificate");
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

    /** Ensures {@link #individualQualifiedId} was supplied; used by operations that require a user qualified ID. */
    protected void checkUserID() {
        if (this.individualQualifiedId == null) throw new IllegalArgumentException(QUALIFIED_ID_MISSING);
    }
}
