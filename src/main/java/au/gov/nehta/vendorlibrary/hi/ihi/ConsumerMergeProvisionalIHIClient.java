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
import au.net.electronichealth.ns.hi.svc.consumermergeprovisionalihi._3.ConsumerMergeProvisionalIHIPortType;
import au.net.electronichealth.ns.hi.svc.consumermergeprovisionalihi._3.ConsumerMergeProvisionalIHIService;
import au.net.electronichealth.ns.hi.svc.consumermergeprovisionalihi._3.MergeProvisionalIHI;
import au.net.electronichealth.ns.hi.svc.consumermergeprovisionalihi._3.MergeProvisionalIHIResponse;
import au.net.electronichealth.ns.hi.svc.consumermergeprovisionalihi._3.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import jakarta.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Healthcare Identifiers (HI) Individual Healthcare Identifier (IHI) merge provisional client.
 * This class may be used to connect to the Medicare HI Service for Consumer Merge Provisional IHI (3.0) operations.
 */
public class ConsumerMergeProvisionalIHIClient extends BaseClient_3<ConsumerMergeProvisionalIHIPortType> {

    /**
     * Constructor which creates a new ConsumerMergeProvisionalIHIClient with an endpoint and an SSL Socket Factory.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param productHeader           The product header data for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ConsumerMergeProvisionalIHIClient(final String serviceEndpoint,
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
     * Constructor which creates a new ConsumerMergeProvisionalIHIClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ConsumerMergeProvisionalIHI service (Optional)
     * @param productHeader           The product header data for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ConsumerMergeProvisionalIHIClient(final String serviceEndpoint,
                                             final QualifiedId individualQualifiedId,
                                             final QualifiedId organisationQualifiedId,
                                             final ProductType productHeader,
                                             final PrivateKey signingPrivateKey,
                                             final X509Certificate signingCertificate,
                                             final SSLSocketFactory sslSocketFactory,
                                             CertificateValidator certificateValidator) {

        super(ConsumerMergeProvisionalIHIPortType.class,
                ConsumerMergeProvisionalIHIService.class,
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
     * Constructor which creates a new ConsumerMergeProvisionalIHIClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ConsumerMergeProvisionalIHI service (Optional)
     * @param productHeader           The product header data for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ConsumerMergeProvisionalIHIClient(final String serviceEndpoint,
                                             final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                             final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId organisationQualifiedId,
                                             final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                             final PrivateKey signingPrivateKey,
                                             final X509Certificate signingCertificate,
                                             final SSLSocketFactory sslSocketFactory,
                                             CertificateValidator certificateValidator) {

        super(ConsumerMergeProvisionalIHIPortType.class,
                ConsumerMergeProvisionalIHIService.class,
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
     * Constructor which creates a new ConsumerMergeProvisionalIHIClient with an endpoint and an SSL Socket Factory.
     * This is an overloaded constructor for the generic qualified id and product types.
     *
     * @param serviceEndpoint    the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader      The product header data for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param signingPrivateKey  The private key to be used for signing (Mandatory)
     * @param signingCertificate The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory   the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ConsumerMergeProvisionalIHIClient(final String serviceEndpoint,
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
     * Constructor which creates a new ConsumerMergeProvisionalIHIClient with an endpoint and an SSL Socket Factory.
     *
     * @param serviceEndpoint       the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param productHeader         The product header data for connecting to the ConsumerMergeProvisionalIHI service (Mandatory)
     * @param signingPrivateKey     The private key to be used for signing (Mandatory)
     * @param signingCertificate    The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory      the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ConsumerMergeProvisionalIHIClient(final String serviceEndpoint,
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
     * Executes a Merge Provisional IHI request.
     *
     * @param request the MergeProvisionalIHI request body
     * @return the response from the ConsumerMergeProvisionalIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final MergeProvisionalIHIResponse mergeProvisionalIHI(MergeProvisionalIHI request) throws StandardErrorMsg {
        ArgumentUtils.checkNotNull(request, "request");
        checkUserID();
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().mergeProvisionalIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId);
    }

    /**
     * Executes a Merge Provisional IHI request.
     *
     * @param request      the MergeProvisionalIHI request body
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ConsumerMergeProvisionalIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final MergeProvisionalIHIResponse mergeProvisionalIHI(
            MergeProvisionalIHI request,
            au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {
        ArgumentUtils.checkNotNull(request, "request");
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().mergeProvisionalIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId);
    }
}
