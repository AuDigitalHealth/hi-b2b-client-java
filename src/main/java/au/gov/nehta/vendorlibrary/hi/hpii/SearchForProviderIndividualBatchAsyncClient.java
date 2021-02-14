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
package au.gov.nehta.vendorlibrary.hi.hpii;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.hi.client.BaseClient_3;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderindividual._5_1.*;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Healthcare Identifiers (HI) - Healthcare Provider Identifier Organisation (HPI-O) search client for Batch Asynchronous requests.
 * This class may be used to connect to the Medicare HI Service to do Provider Search HI Provider Directory For
 * Organisation searches.
 */
public class SearchForProviderIndividualBatchAsyncClient extends BaseClient_3<SearchForProviderIndividualBatchAsyncPortType> {

    /**
     * Constructor which creates a new Client with an endpoint and an SSL Socket Factory.
     *
     * @param serviceEndpoint       the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param productHeader         The product header data for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param signingPrivateKey     The private key to be used for signing (Mandatory)
     * @param signingCertificate    The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory      the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public SearchForProviderIndividualBatchAsyncClient(final String serviceEndpoint,
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
     * Constructor which creates a new ProviderSearchForProviderOrganisation Client with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ProviderSearchHIProviderDirectoryForOrganisation
     *                                service (Optional)
     * @param productHeader           The product header data for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public SearchForProviderIndividualBatchAsyncClient(final String serviceEndpoint,
                                                       final QualifiedId individualQualifiedId,
                                                       final QualifiedId organisationQualifiedId,
                                                       final ProductType productHeader,
                                                       final PrivateKey signingPrivateKey,
                                                       final X509Certificate signingCertificate,
                                                       final SSLSocketFactory sslSocketFactory,
                                                       CertificateValidator certificateValidator) {

        super(SearchForProviderIndividualBatchAsyncPortType.class,
                ProviderSearchForIndividualBatchAsyncService.class,
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
     * Constructor which creates a new Client using the wrapped version of the product type with an endpoint and an SSL Socket Factory.
     * This type constructed client assumes the individual qualified user id will be supplied per request
     *
     * @param serviceEndpoint      the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader        The product header data for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param signingPrivateKey    The private key to be used for signing (Mandatory)
     * @param signingCertificate   The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory     the SSL Socket Factory to be used when connecting to the Web Service provider
     * @param certificateValidator (optional) a user supplied validator to authenticate the response certificate.
     */
    public SearchForProviderIndividualBatchAsyncClient(
            String serviceEndpoint,
            au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
            PrivateKey signingPrivateKey,
            X509Certificate signingCertificate,
            SSLSocketFactory sslSocketFactory,
            CertificateValidator certificateValidator) {

        super(SearchForProviderIndividualBatchAsyncPortType.class,
                ProviderSearchForIndividualBatchAsyncService.class,
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
     * Submits provider asynchronous search.
     *
     * @param request the SearchForProviderIndividual request object containing the
     *                following mandatory fields: FamilyName
     * @return the response from the SearchForProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public SubmitSearchForProviderIndividualResponse submitSearch(ProviderIndividualBatchSearch request)
            throws StandardErrorMsg {
        checkUserID();
        submitSearchCheck(request.getSearchRequest());
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().submitSearchForProviderIndividual(
                request.getSearchRequest(),
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualQualifiedId,
                organisationQualifiedId);
    }

    /**
     * Submits provider asynchronous search.
     *
     * @param request      the SearchForProviderIndividual request object containing the
     *                     following mandatory fields: FamilyName
     * @param individualId the qualified user id of the user making the request
     * @return the response from the SearchForProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public SubmitSearchForProviderIndividualResponse submitSearch(ProviderIndividualBatchSearch request,
                                                                  au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId)
            throws StandardErrorMsg {

        submitSearchCheck(request.getSearchRequest());
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.getPort().submitSearchForProviderIndividual(
                request.getSearchRequest(),
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                organisationQualifiedId);
    }

    /**
     * Retrieves provider asynchronous search.
     *
     * @param request      the SearchForProviderIndividual request object containing the
     *                     following mandatory fields: FamilyName
     * @param individualId the qualified user id of the user making the request
     * @return the response from the SearchForProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public RetrieveSearchForProviderIndividualResponse retrieveSearch(RetrieveSearchForProviderIndividual request,
                                                                      au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId
    ) throws StandardErrorMsg {
        retrieveSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().retrieveSearchForProviderIndividual(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                organisationQualifiedId);
    }

    /**
     * Retrieves provider asynchronous search.
     *
     * @param request the SearchForProviderIndividual request object containing the
     *                following mandatory fields: FamilyName
     * @return the response from the SearchForProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public RetrieveSearchForProviderIndividualResponse retrieveSearch(RetrieveSearchForProviderIndividual request) throws StandardErrorMsg {
        checkUserID();
        retrieveSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().retrieveSearchForProviderIndividual(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualQualifiedId,
                organisationQualifiedId);
    }

    /**
     * Checks that only the correct parameters for a Retrieve search request are set.
     *
     * @param request the search request object containing the parameters to be checked.
     */

    public void retrieveSearchCheck(RetrieveSearchForProviderIndividual request) {
        ArgumentUtils.checkNotNull(request, "Request must be supplied");
        ArgumentUtils.checkNotNull(request.getBatchIdentifier(), "Request Batch Identifier must be supplied");
    }

    /**
     * Checks that only the correct parameters for a batch search request are set.
     *
     * @param request the search request object containing the parameters to be checked.
     */
    public void submitSearchCheck(SubmitSearchForProviderIndividual request) {
        ArgumentUtils.checkNotNull(request, "Request must be supplied");
        ArgumentUtils.checkNotNullNorEmpty(request.getBatchSearchForProviderIndividualCriteria(), "Request must supply batch searches");
    }
}
