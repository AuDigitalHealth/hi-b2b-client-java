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
import au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryforindividual._3_2.*;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;
import au.net.electronichealth.ns.hi.xsd.providercore.address._3_2.AustralianAddressCriteriaType;
import au.net.electronichealth.ns.hi.xsd.providercore.address._3_2.InternationalAddressCriteriaType;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Healthcare Identifiers (HI) - Healthcare Provider Identifier Individual (HPI-I) search client.
 * This class may be used to connect to the Medicare HI Service to do Provider Search HI Provider Directory For
 * Individual searches.
 */
public class ProviderSearchHIProviderDirectoryForIndividualClient extends BaseClient_3<ProviderSearchHIProviderDirectoryForIndividualPortType> {

    /**
     * Validates the arguments passed for the various operations
     */
    private ArgumentValidator argumentValidator = new ArgumentValidator();

    /**
     * Constructor which creates a new ProviderSearchHIProviderDirectoryForIndividual Client with an endpoint and an SSL Socket Factory.
     *
     * @param serviceEndpoint       the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param productHeader         The product header data for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param signingPrivateKey     The private key to be used for signing (Mandatory)
     * @param signingCertificate    The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory      the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ProviderSearchHIProviderDirectoryForIndividualClient(final String serviceEndpoint,
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
     * Constructor which creates a new ProviderSearchHIProviderDirectoryForIndividual Client with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param organisationQualifiedId The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Optional)
     * @param productHeader           The product header data for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ProviderSearchHIProviderDirectoryForIndividualClient(final String serviceEndpoint,
                                                                final QualifiedId individualQualifiedId,
                                                                final QualifiedId organisationQualifiedId,
                                                                final ProductType productHeader,
                                                                final PrivateKey signingPrivateKey,
                                                                final X509Certificate signingCertificate,
                                                                final SSLSocketFactory sslSocketFactory,
                                                                CertificateValidator certificateValidator) {

        super(ProviderSearchHIProviderDirectoryForIndividualPortType.class,
                ProviderSearchHIProviderDirectoryForIndividualService.class,
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
     * Constructor which creates a new ProviderSearchHIProviderDirectoryForIndividual Client with an endpoint and an SSL Socket Factory.
     * This is an overloaded constructor for the generic qualifiedId and product types.
     *
     * @param serviceEndpoint       the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param productHeader         The product header data for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param signingPrivateKey     The private key to be used for signing (Mandatory)
     * @param signingCertificate    The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory      the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ProviderSearchHIProviderDirectoryForIndividualClient(final String serviceEndpoint,
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
     * Constructor which creates a new ProviderSearchHIProviderDirectoryForIndividual Client with an endpoint and an SSL Socket Factory.
     * This is an overloaded constructor for the generic qualified id and product types.
     *
     * @param serviceEndpoint    the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader      The product header data for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param signingPrivateKey  The private key to be used for signing (Mandatory)
     * @param signingCertificate The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory   the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ProviderSearchHIProviderDirectoryForIndividualClient(final String serviceEndpoint,
                                                                final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                                                final PrivateKey signingPrivateKey,
                                                                final X509Certificate signingCertificate,
                                                                final SSLSocketFactory sslSocketFactory) {
        this(
                serviceEndpoint,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory
        );
    }

    /**
     * Constructor which creates a new ProviderSearchHIProviderDirectoryForIndividual Client with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     * This is an overloaded constructor for the generic qualified id and product types.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param organisationQualifiedId The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Optional)
     * @param productHeader           The product header data for connecting to the ProviderSearchHIProviderDirectoryForIndividual service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ProviderSearchHIProviderDirectoryForIndividualClient(final String serviceEndpoint,
                                                                final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                                                final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId organisationQualifiedId,
                                                                final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                                                final PrivateKey signingPrivateKey,
                                                                final X509Certificate signingCertificate,
                                                                final SSLSocketFactory sslSocketFactory,
                                                                CertificateValidator certificateValidator
    ) {

        super(ProviderSearchHIProviderDirectoryForIndividualPortType.class,
                ProviderSearchHIProviderDirectoryForIndividualService.class,
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
     * Executes an Identifier HPI-I search.
     *
     * @param request the SearchHIProviderDirectoryForIndividual request object containing the following mandatory fields:
     *                HPI-I
     * @return the response from the SearchHIProviderDirectoryForIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchHIProviderDirectoryForIndividualResponse identifierSearch(SearchHIProviderDirectoryForIndividual request)
            throws StandardErrorMsg {
        checkUserID();
        this.argumentValidator.identifierSearchCheck(request);

        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchHIProviderDirectoryForIndividual(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a Demographic HPI-I search.
     *
     * @param request the SearchHIProviderDirectoryForIndividual request object containing the following mandatory fields:
     *                Family Name
     *                and the following optional fields:
     *                Either "Australian Address Criteria" OR "International Address Criteria" but not both.
     * @return the response from the SearchHIProviderDirectoryForIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchHIProviderDirectoryForIndividualResponse demographicSearch(SearchHIProviderDirectoryForIndividual request)
            throws StandardErrorMsg {
        checkUserID();
        this.argumentValidator.demographicSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchHIProviderDirectoryForIndividual(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes an Identifier HPI-I search.
     *
     * @param request      the SearchHIProviderDirectoryForIndividual request object containing the following mandatory fields:
     *                     HPI-I
     * @param individualId the qualified user id of the user making the request
     * @return the response from the SearchHIProviderDirectoryForIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchHIProviderDirectoryForIndividualResponse identifierSearch(SearchHIProviderDirectoryForIndividual request,
                                                                                 au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId)

            throws StandardErrorMsg {
        this.argumentValidator.identifierSearchCheck(request);

        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchHIProviderDirectoryForIndividual(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a Demographic HPI-I search.
     *
     * @param request      the SearchHIProviderDirectoryForIndividual request object containing the following mandatory fields:
     *                     Family Name
     *                     and the following optional fields:
     *                     Either "Australian Address Criteria" OR "International Address Criteria" but not both.
     * @param individualId the qualified user id of the user making the request
     * @return the response from the SearchHIProviderDirectoryForIndividual service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchHIProviderDirectoryForIndividualResponse demographicSearch(SearchHIProviderDirectoryForIndividual request,
                                                                                  au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId)
            throws StandardErrorMsg {
        this.argumentValidator.demographicSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchHIProviderDirectoryForIndividual(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId
        );
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
        public final void identifierSearchCheck(SearchHIProviderDirectoryForIndividual request) {
            ArgumentUtils.checkNotNull(request, "request");
            ArgumentUtils.checkNotNullNorBlank(request.getHpiiNumber(), "HPI-I Number");
            ensureNull(request.getFamilyName(), "Family Name");
            ensureNull(request.getGivenName(), "Given Name");
            ensureNull(request.getSex(), "Sex");
            ensureNull(request.getProviderTypeCode(), "Provider Type Code");
            ensureNull(request.getProviderSpecialty(), "Provider Specialty");
            ensureNull(request.getProviderSpecialisation(), "Provider Specialisation");
            ensureNull(request.getAustralianAddressCriteria(), "Australian Address Criteria");
            ensureNull(request.getInternationalAddressCriteria(), "International Address Criteria");
        }

        /**
         * Checks that only the correct parameters for a demographic search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void demographicSearchCheck(SearchHIProviderDirectoryForIndividual request) {
            ArgumentUtils.checkNotNull(request, "request");
//      ArgumentUtils.checkNotNullNorBlank(request.getFamilyName(), "Family Name");


            ensureNull(request.getHpiiNumber(), "HPI-I Number");
            ensureEitherOneOrBothAreNull(request.getAustralianAddressCriteria(), "Australian Address Criteria",
                    request.getInternationalAddressCriteria(), "International Address Criteria");
            if (request.getAustralianAddressCriteria() != null) {
                checkAustralianAddressCriteria(request.getAustralianAddressCriteria());
            } else {
                if (request.getInternationalAddressCriteria() != null) {
                    checkInternationalAddressCriteria(request.getInternationalAddressCriteria());
                }
            }

        }

        /**
         * Verifies that the Australian Address Criteria provided is valid.
         *
         * @param address the Australian Address Criteria being verified
         */
        private void checkAustralianAddressCriteria(AustralianAddressCriteriaType address) {
            ArgumentUtils.checkNotNullNorBlank(address.getSuburb(), "Australian Address Criteria: Suburb");
            ArgumentUtils.checkNotNull(address.getState(), "Australian Address Criteria: State");
            ArgumentUtils.checkNotNullNorBlank(address.getPostcode(), "Australian Address Criteria: Postcode");
            ensureExactStringLength(address.getPostcode(), AUSTRALIAN_POSTCODE_LENGTH, "Post Code");

            if (isAddressUnstructured(address)) {
                ArgumentUtils.checkNotNullNorBlank(address.getUnstructuredAddressLine(), "Australian Address Criteria: Unstructured Address Line");
            }
        }

        private boolean isAddressUnstructured(final AustralianAddressCriteriaType address) {
            return (address.getLevelGroup() == null
                    && address.getUnitGroup() == null
                    && address.getAddressSiteName() == null
                    && address.getStreetNumber() == null
                    && address.getLotNumber() == null
                    && address.getStreetName() == null
                    && address.getStreetType() == null
                    && address.getStreetSuffix() == null
                    && address.getPostalDeliveryGroup() == null);
        }

        /**
         * Verifies that the International Address Criteria provided is valid.
         *
         * @param address the International Address Criteria being verified
         */
        private void checkInternationalAddressCriteria(InternationalAddressCriteriaType address) {
            ArgumentUtils.checkNotNullNorBlank(address.getInternationalAddressLine(), "International Address Criteria: Address Line");
            ArgumentUtils.checkNotNullNorBlank(address.getInternationalStateProvince(), "International Address Criteria: State Province");
            ArgumentUtils.checkNotNullNorBlank(address.getInternationalPostcode(), "International Address Criteria: Postcode");
            ArgumentUtils.checkNotNull(address.getCountry(), "International Address Criteria: Country");
        }

        /**
         * Ensures
         *
         * @param obj1     the first object being checked (Mandatory)
         * @param obj1Name the name of the first object variable (for use in Exception messages) (Mandatory)
         * @param obj2     the second object being checked (Mandatory)
         * @param obj2Name the name of the second object variable (for use in Exception messages) (Mandatory)
         */
        private void ensureEitherOneOrBothAreNull(Object obj1, String obj1Name, InternationalAddressCriteriaType obj2, String obj2Name) {
            if (obj1 != null) {
                if (obj2 != null) {
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
