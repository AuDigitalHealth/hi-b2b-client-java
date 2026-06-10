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
package au.gov.nehta.vendorlibrary.hi.ihi;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.hi.client.BaseClient_3;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.hi.svc.consumernotifyduplicateihi._3_2.ConsumerNotifyDuplicateIHIPortType;
import au.net.electronichealth.ns.hi.svc.consumernotifyduplicateihi._3_2.ConsumerNotifyDuplicateIHIService;
import au.net.electronichealth.ns.hi.svc.consumernotifyduplicateihi._3_2.NotifyDuplicateIHI;
import au.net.electronichealth.ns.hi.svc.consumernotifyduplicateihi._3_2.NotifyDuplicateIHIResponse;
import au.net.electronichealth.ns.hi.svc.consumernotifyduplicateihi._3_2.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import jakarta.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Healthcare Identifiers (HI) Individual Healthcare Identifier (IHI) notify duplicate client.
 * This class may be used to connect to the Medicare HI Service for Consumer Notify Duplicate IHI (3.2.0) operations.
 */
public class ConsumerNotifyDuplicateIHIClient extends BaseClient_3<ConsumerNotifyDuplicateIHIPortType> {

    /**
     * Constructor which creates a new ConsumerNotifyDuplicateIHIClient with an endpoint and an SSL Socket Factory.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param productHeader           The product header data for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ConsumerNotifyDuplicateIHIClient(final String serviceEndpoint,
                                            final QualifiedId individualQualifiedId,
                                            final ProductType productHeader,
                                            final PrivateKey signingPrivateKey,
                                            final X509Certificate signingCertificate,
                                            final SSLSocketFactory sslSocketFactory) {
        this(
                serviceEndpoint,
                individualQualifiedId,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                null
        );
    }

    /**
     * Constructor which creates a new ConsumerNotifyDuplicateIHIClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ConsumerNotifyDuplicateIHI service (Optional)
     * @param productHeader           The product header data for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ConsumerNotifyDuplicateIHIClient(final String serviceEndpoint,
                                            final QualifiedId individualQualifiedId,
                                            final QualifiedId organisationQualifiedId,
                                            final ProductType productHeader,
                                            final PrivateKey signingPrivateKey,
                                            final X509Certificate signingCertificate,
                                            final SSLSocketFactory sslSocketFactory,
                                            CertificateValidator certificateValidator) {

        super(ConsumerNotifyDuplicateIHIPortType.class,
                ConsumerNotifyDuplicateIHIService.class,
                individualQualifiedId,
                organisationQualifiedId,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                serviceEndpoint,
                certificateValidator
        );
    }

    /**
     * Constructor which creates a new ConsumerNotifyDuplicateIHIClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ConsumerNotifyDuplicateIHI service (Optional)
     * @param productHeader           The product header data for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ConsumerNotifyDuplicateIHIClient(final String serviceEndpoint,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId organisationQualifiedId,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                            final PrivateKey signingPrivateKey,
                                            final X509Certificate signingCertificate,
                                            final SSLSocketFactory sslSocketFactory,
                                            CertificateValidator certificateValidator) {

        super(ConsumerNotifyDuplicateIHIPortType.class,
                ConsumerNotifyDuplicateIHIService.class,
                individualQualifiedId,
                organisationQualifiedId,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                serviceEndpoint,
                certificateValidator
        );
    }

    /**
     * Constructor which creates a new ConsumerNotifyDuplicateIHIClient with an endpoint and an SSL Socket Factory.
     * This is an overloaded constructor for the generic qualified id and product types.
     *
     * @param serviceEndpoint    the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader      The product header data for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param signingPrivateKey  The private key to be used for signing (Mandatory)
     * @param signingCertificate The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory   the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ConsumerNotifyDuplicateIHIClient(final String serviceEndpoint,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                            final PrivateKey signingPrivateKey,
                                            final X509Certificate signingCertificate,
                                            final SSLSocketFactory sslSocketFactory) {
        this(
                serviceEndpoint,
                null,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                null
        );
    }

    /**
     * Constructor which creates a new ConsumerNotifyDuplicateIHIClient with an endpoint and an SSL Socket Factory.
     *
     * @param serviceEndpoint       the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param productHeader         The product header data for connecting to the ConsumerNotifyDuplicateIHI service (Mandatory)
     * @param signingPrivateKey     The private key to be used for signing (Mandatory)
     * @param signingCertificate    The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory      the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ConsumerNotifyDuplicateIHIClient(final String serviceEndpoint,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                            final PrivateKey signingPrivateKey,
                                            final X509Certificate signingCertificate,
                                            final SSLSocketFactory sslSocketFactory) {
        this(
                serviceEndpoint,
                individualQualifiedId,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                null
        );
    }

    /**
     * Executes a Notify Duplicate IHI request.
     *
     * @param request the NotifyDuplicateIHI request body
     * @return the response from the ConsumerNotifyDuplicateIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final NotifyDuplicateIHIResponse notifyDuplicateIHI(NotifyDuplicateIHI request) throws StandardErrorMsg {
        ArgumentUtils.checkNotNull(request, "request");
        checkUserID();
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().notifyDuplicateIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId);
    }

    /**
     * Executes a Notify Duplicate IHI request.
     *
     * @param request      the NotifyDuplicateIHI request body
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ConsumerNotifyDuplicateIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final NotifyDuplicateIHIResponse notifyDuplicateIHI(
            NotifyDuplicateIHI request,
            au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {
        ArgumentUtils.checkNotNull(request, "request");
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().notifyDuplicateIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId);
    }
}
