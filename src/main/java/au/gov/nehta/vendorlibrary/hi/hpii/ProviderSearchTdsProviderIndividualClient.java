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
package au.gov.nehta.vendorlibrary.hi.hpii;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.hi.client.BaseClient_3;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.hi.svc.providersearchtdsproviderindividual._5_1.ProviderSearchTdsProviderIndividualPortType;
import au.net.electronichealth.ns.hi.svc.providersearchtdsproviderindividual._5_1.ProviderSearchTdsProviderIndividualService;
import au.net.electronichealth.ns.hi.svc.providersearchtdsproviderindividual._5_1.SearchTdsProviderIndividual;
import au.net.electronichealth.ns.hi.svc.providersearchtdsproviderindividual._5_1.SearchTdsProviderIndividualResponse;
import au.net.electronichealth.ns.hi.svc.providersearchtdsproviderindividual._5_1.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import jakarta.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Healthcare Identifiers (HI) Provider Search TDS Provider Individual client.
 * This class may be used to connect to the Medicare HI Service for Provider Search TDS Provider Individual (5.1.0) operations.
 */
public class ProviderSearchTdsProviderIndividualClient extends BaseClient_3<ProviderSearchTdsProviderIndividualPortType> {

    /**
     * Constructor which creates a new ProviderSearchTdsProviderIndividualClient with an endpoint and an SSL Socket Factory.
     *
     * @param serviceEndpoint       the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ProviderSearchTdsProviderIndividual service (Mandatory)
     * @param productHeader         The product header data for connecting to the ProviderSearchTdsProviderIndividual service (Mandatory)
     * @param signingPrivateKey     The private key to be used for signing (Mandatory)
     * @param signingCertificate    The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory      the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ProviderSearchTdsProviderIndividualClient(final String serviceEndpoint,
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
     * Constructor which creates a new ProviderSearchTdsProviderIndividualClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ProviderSearchTdsProviderIndividual service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ProviderSearchTdsProviderIndividual service (Optional)
     * @param productHeader           The product header data for connecting to the ProviderSearchTdsProviderIndividual service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ProviderSearchTdsProviderIndividualClient(final String serviceEndpoint,
                                                     final QualifiedId individualQualifiedId,
                                                     final QualifiedId organisationQualifiedId,
                                                     final ProductType productHeader,
                                                     final PrivateKey signingPrivateKey,
                                                     final X509Certificate signingCertificate,
                                                     final SSLSocketFactory sslSocketFactory,
                                                     CertificateValidator certificateValidator) {

        super(ProviderSearchTdsProviderIndividualPortType.class,
                ProviderSearchTdsProviderIndividualService.class,
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
     * Constructor which creates a new ProviderSearchTdsProviderIndividualClient using the wrapped version of the product type with an endpoint and an SSL Socket Factory.
     * This type constructed client assumes the individual qualified user id will be supplied per request
     *
     * @param serviceEndpoint      the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader        The product header data for connecting to the ProviderSearchTdsProviderIndividual service (Mandatory)
     * @param signingPrivateKey    The private key to be used for signing (Mandatory)
     * @param signingCertificate   The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory     the SSL Socket Factory to be used when connecting to the Web Service provider
     * @param certificateValidator (optional) a user supplied validator to authenticate the response certificate.
     */
    public ProviderSearchTdsProviderIndividualClient(
            String serviceEndpoint,
            au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
            PrivateKey signingPrivateKey,
            X509Certificate signingCertificate,
            SSLSocketFactory sslSocketFactory,
            CertificateValidator certificateValidator) {

        super(ProviderSearchTdsProviderIndividualPortType.class,
                ProviderSearchTdsProviderIndividualService.class,
                null,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                serviceEndpoint,
                certificateValidator
        );
    }

    /**
     * Executes a Search TDS Provider Individual request.
     *
     * @param request the SearchTdsProviderIndividual request body
     * @return the response from the ProviderSearchTdsProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchTdsProviderIndividualResponse searchTdsProviderIndividual(SearchTdsProviderIndividual request)
            throws StandardErrorMsg {
        ArgumentUtils.checkNotNull(request, "request");
        checkUserID();
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchTdsProviderIndividual(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId);
    }

    /**
     * Executes a Search TDS Provider Individual request.
     *
     * @param request      the SearchTdsProviderIndividual request body
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ProviderSearchTdsProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchTdsProviderIndividualResponse searchTdsProviderIndividual(
            SearchTdsProviderIndividual request,
            au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {
        ArgumentUtils.checkNotNull(request, "request");
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchTdsProviderIndividual(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type());
    }
}
