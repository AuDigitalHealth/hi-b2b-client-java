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
import au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.*;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._5_0.SearchAustralianAddressType;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._5_0.SearchInternationalAddressType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Healthcare Identifiers (HI) - Healthcare Provider Identifier Organisation (HPI-O) search client.
 * This class may be used to connect to the Medicare HI Service to do Provider Search HI Provider Directory For
 * Organisation searches.
 */
public class ProviderSearchForProviderIndividualClient extends BaseClient_3<ProviderSearchForProviderIndividualPortType> {

    private ArgumentValidator validator = new ArgumentValidator();

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
    public ProviderSearchForProviderIndividualClient(final String serviceEndpoint,
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
                sslSocketFactory
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
     */
    public ProviderSearchForProviderIndividualClient(final String serviceEndpoint,
                                                     final QualifiedId individualQualifiedId,
                                                     final QualifiedId organisationQualifiedId,
                                                     final ProductType productHeader,
                                                     final PrivateKey signingPrivateKey,
                                                     final X509Certificate signingCertificate,
                                                     final SSLSocketFactory sslSocketFactory) {

        super(ProviderSearchForProviderIndividualPortType.class,
                ProviderSearchForProviderIndividualService.class,
                individualQualifiedId,
                organisationQualifiedId,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                serviceEndpoint,
                null
        );
    }

    /**
     * Constructor which creates a new Client using the wrapped version of the product type with an endpoint and an SSL Socket Factory.
     * This type constructed client assumes the individual qualified user id will be supplied per request
     *
     * @param serviceEndpoint    the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader      The product header data for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param signingPrivateKey  The private key to be used for signing (Mandatory)
     * @param signingCertificate The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory   the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ProviderSearchForProviderIndividualClient(
            String serviceEndpoint,
            au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
            PrivateKey signingPrivateKey,
            X509Certificate signingCertificate,
            SSLSocketFactory sslSocketFactory) {

        super(ProviderSearchForProviderIndividualPortType.class,
                ProviderSearchForProviderIndividualService.class,
                null,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                serviceEndpoint,
                null
        );
    }

    /**
     * Executes an demographic search.
     *
     * @param request the SearchForProviderIndividual request object containing the
     *                following mandatory fields: FamilyName
     * @return the response from the SearchForProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchForProviderIndividualResponse demographicSearch(SearchForProviderIndividual request)
            throws StandardErrorMsg {
        checkUserID();
        this.validator.demographicSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.getPort().searchForProviderIndividual(request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId);
    }

    /**
     * Executes an demographic search.
     *
     * @param request      the SearchForProviderIndividual request object containing the
     *                     following mandatory fields: FamilyName
     * @param individualId the qualified user id of the user making the request
     * @return the response from the SearchForProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchForProviderIndividualResponse demographicSearch(SearchForProviderIndividual request,
                                                                       au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId)
            throws StandardErrorMsg {

        this.validator.demographicSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.getPort().searchForProviderIndividual(request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId);
    }

    /**
     * Executes an Identifier HPII search.
     *
     * @param request      the SearchHIProviderDirectoryForIndividual request object containing the following mandatory fields:
     *                     HPII or Registration ID
     *                     FamilyName
     * @param individualId the qualified user id of the user making the request
     * @return the response from the SearchForProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchForProviderIndividualResponse identifierSearch(SearchForProviderIndividual request,
                                                                      au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {

        this.validator.identifierSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.getPort().searchForProviderIndividual(request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId);
    }

    /**
     * Executes an Identifier HPII search.
     *
     * @param request the SearchHIProviderDirectoryForIndividual request object containing the following mandatory fields:
     *                HPII or Registration ID
     *                FamilyName
     * @return the response from the SearchForProviderIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchForProviderIndividualResponse identifierSearch(SearchForProviderIndividual request) throws StandardErrorMsg {
        checkUserID();
        this.validator.identifierSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.getPort().searchForProviderIndividual(request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId);
    }

    /**
     * Validates search parameters are correct for the various search types.
     */
    public static class ArgumentValidator {
        /**
         * The length of an Australian postcode.
         */
        public static final int AUSTRALIAN_POSTCODE_LENGTH = 4;

        /**
         * Checks that only the correct parameters for an identifier search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void identifierSearchCheck(SearchForProviderIndividual request) {
            ArgumentUtils.checkNotNull(request, "Request must be supplied");
            ArgumentUtils.checkNotNull(request.getFamilyName(), "Family name must be supplied");
            ArgumentUtils.checkMaxLength(request.getFamilyName(), 40, "FamilyName");

            boolean isHPIIEmpty = isEmpty(request.getHpiiNumber());
            boolean isRegistrationIDEmpty = isEmpty(request.getRegistrationId());

            if (!isRegistrationIDEmpty && !isHPIIEmpty) {
                throw new IllegalArgumentException("You must not supply either HPII or RegistrationId");
            }


        }

        /**
         * Checks that only the correct parameters for a demographic search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void demographicSearchCheck(SearchForProviderIndividual request) {
            ArgumentUtils.checkNotNull(request, "Request must be supplied");
            ArgumentUtils.checkNotNull(request.getFamilyName(), "Family name must be supplied");
            ArgumentUtils.checkMaxLength(request.getFamilyName(), 40, "FamilyName");
            ensureNull(request.getHpiiNumber(), "HPII Number");
            ensureEitherOneOrBothAreNull(request.getSearchAustralianAddress(), "Australian Address Criteria",
                    request.getSearchInternationalAddress(), "International Address Criteria");

            if (isEmpty(request.getFamilyName()))

                if (request.getSearchAustralianAddress() != null) {
                    checkAustralianAddressCriteria(request.getSearchAustralianAddress());
                } else {
                    if (request.getSearchInternationalAddress() != null) {
                        checkInternationalAddressCriteria(request.getSearchInternationalAddress());
                    }
                }
        }

        /**
         * Verifies that the Australian Address Criteria provided is valid.
         *
         * @param searchAustralianAddressType the Australian Address Criteria being verified
         */
        private void checkAustralianAddressCriteria(SearchAustralianAddressType searchAustralianAddressType) {
            ArgumentUtils.checkNotNullNorBlank(searchAustralianAddressType.getSuburb(), "Australian Address Criteria: Suburb");
            ArgumentUtils.checkNotNull(searchAustralianAddressType.getState(), "Australian Address Criteria: State");
            ArgumentUtils.checkNotNullNorBlank(searchAustralianAddressType.getPostcode(), "Australian Address Criteria: Postcode");
            ensureExactStringLength(searchAustralianAddressType.getPostcode(), AUSTRALIAN_POSTCODE_LENGTH, "Post Code");
        }

        /**
         * Verifies that the International Address Criteria provided is valid.
         *
         * @param searchInternationalAddressType the International Address Criteria being verified
         */
        private void checkInternationalAddressCriteria(SearchInternationalAddressType searchInternationalAddressType) {
            ArgumentUtils.checkNotNullNorBlank(searchInternationalAddressType.getInternationalAddressLine(), "International Address Criteria: Address Line");
            ArgumentUtils.checkNotNullNorBlank(searchInternationalAddressType.getInternationalStateProvince(), "International Address Criteria: State Province");
            ArgumentUtils.checkNotNullNorBlank(searchInternationalAddressType.getInternationalPostcode(), "International Address Criteria: Postcode");
            ArgumentUtils.checkNotNull(searchInternationalAddressType.getCountry(), "International Address Criteria: Country");
        }

        /**
         * Ensures
         *
         * @param obj1     the first object being checked (Mandatory)
         * @param obj1Name the name of the first object variable (for use in Exception messages) (Mandatory)
         * @param ob2      the second object being checked (Mandatory)
         * @param obj2Name the name of the second object variable (for use in Exception messages) (Mandatory)
         */
        private void ensureEitherOneOrBothAreNull(Object obj1, String obj1Name, Object ob2, String obj2Name) {
            if (obj1 != null) {
                if (ob2 != null) {
                    throw new IllegalArgumentException("Both " + obj1Name + " and " + obj2Name + " are set. Only one of them may be set");
                }
            }
        }

        /**
         * Ensures the passed object is null
         *
         * @param theObject  the object being checked (Mandatory)
         * @param objectName the name of the Object variable (for use in Exception messages) (Mandatory)
         */
        private void ensureNull(Object theObject, String objectName) {
            if (theObject != null) {
                throw new IllegalArgumentException(objectName + " may not be not-null");
            }
        }

        /**
         * Ensures the passed String has a specific length
         *
         * @param theString  the String being checked (Mandatory)
         * @param length     the required length (Mandatory)
         * @param stringName the name of the String variable (for use in Exception messages) (Mandatory)
         */
        private void ensureExactStringLength(String theString, Integer length, String stringName) {
            if (theString.length() != length) {
                throw new IllegalArgumentException(stringName + " must have a length of " + length);
            }
        }
    }
}
