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
package au.gov.nehta.vendorlibrary.hi.ihi;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.hi.client.BaseClient_3;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.hi.svc.consumersearchihi._3.*;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianPostalAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianStreetAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.InternationalAddressType;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Healthcare Identifiers (HI) - Individual Healthcare Identifier (IHI) search client. This
 * class may be used to connect to the Medicare HI Service to do Consumer Search IHI searches.
 */
public class ConsumerSearchIHIClient extends BaseClient_3<ConsumerSearchIHIPortType> {

    /**
     * Validates the arguments passed for the various operations
     */
    private ArgumentValidator argumentValidator = new ArgumentValidator();

    /**
     * Constructor which creates a new ConsumerSearchIHIClient with an endpoint and an SSL Socket Factory.
     *
     * @param searchIhiServiceEndpoint the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId    The qualified user ID for connecting to the ConsumerSearchIHI service (Mandatory)
     * @param productHeader            The product header data for connecting to the ConsumerSearchIHI service (Mandatory)
     * @param signingPrivateKey        The private key to be used for signing (Mandatory)
     * @param signingCertificate       The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory         the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ConsumerSearchIHIClient(final String searchIhiServiceEndpoint,
                                   final QualifiedId individualQualifiedId,
                                   final ProductType productHeader,
                                   final PrivateKey signingPrivateKey,
                                   final X509Certificate signingCertificate,
                                   final SSLSocketFactory sslSocketFactory) {
        this(
                searchIhiServiceEndpoint,
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
     * Constructor which creates a new ConsumerSearchIHIClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ConsumerSearchIHI service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ConsumerSearchIHI service (Optional)
     * @param productHeader           The product header data for connecting to the ConsumerSearchIHI service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ConsumerSearchIHIClient(final String serviceEndpoint,
                                   final QualifiedId individualQualifiedId,
                                   final QualifiedId organisationQualifiedId,
                                   final ProductType productHeader,
                                   final PrivateKey signingPrivateKey,
                                   final X509Certificate signingCertificate,
                                   final SSLSocketFactory sslSocketFactory,
                                   CertificateValidator certificateValidator) {

        super(ConsumerSearchIHIPortType.class,
                ConsumerSearchIHIService.class,
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
     * Constructor which creates a new ConsumerSearchIHIClient with an endpoint and an SSL Socket Factory, with
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
    public ConsumerSearchIHIClient(final String serviceEndpoint,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId organisationQualifiedId,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                   final PrivateKey signingPrivateKey,
                                   final X509Certificate signingCertificate,
                                   final SSLSocketFactory sslSocketFactory,
                                   CertificateValidator certificateValidator) {

        super(ConsumerSearchIHIPortType.class,
                ConsumerSearchIHIService.class,
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
     * This is an overloaded constructor for the generic qualified id and product types.
     *
     * @param serviceEndpoint    the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader      The product header data for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param signingPrivateKey  The private key to be used for signing (Mandatory)
     * @param signingCertificate The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory   the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ConsumerSearchIHIClient(final String serviceEndpoint,
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
     * Executes a basic ConsumerSearchIHI search.
     *
     * @param request the SearchIHI request object containing the following mandatory fields:
     *                IHI Number
     *                Family Name
     *                Date of Birth
     *                Sex
     *                and the following optional fields
     *                Given Name
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse basicSearch(SearchIHI request) throws StandardErrorMsg {
        checkUserID();
        argumentValidator.basicSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId);
    }

    /**
     * Executes a basic Medicare ConsumerSearchIHI search.
     *
     * @param request the SearchIHI request object containing the following mandatory fields:
     *                Medicare Card Number
     *                Family Name
     *                Date of Birth
     *                Sex
     *                and the following optional fields
     *                Given Name
     *                Medicare IRN
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse basicMedicareSearch(SearchIHI request) throws StandardErrorMsg {
        checkUserID();
        argumentValidator.basicMedicareSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a basic DVA ConsumerSearchIHI search.
     *
     * @param request the SearchIHI request object containing the following mandatory fields:
     *                DVA File Number
     *                Family Name
     *                Date of Birth
     *                Sex
     *                and the following optional fields
     *                Given Name
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse basicDvaSearch(SearchIHI request) throws StandardErrorMsg {
        checkUserID();
        argumentValidator.basicDvaSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a Detailed ConsumerSearchIHI search.
     *
     * @param request the SearchIHI request object containing the following mandatory fields:
     *                Family Name
     *                Date of Birth
     *                Sex
     *                and the following optional fields
     *                Given Name
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse detailedSearch(SearchIHI request) throws StandardErrorMsg {
        checkUserID();
        argumentValidator.detailedSearchCheck(request);

        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a Australian Postal Address ConsumerSearchIHI search.
     *
     * @param request the SearchIHI request object containing the following mandatory fields:
     *                Family Name
     *                Date of Birth
     *                Sex
     *                Australian Street Address: Postal Delivery Group
     *                Australian Street Address: Postal Delivery Type
     *                Australian Street Address: State
     *                Australian Street Address: Post Code
     *                Australian Street Address: Suburb
     *                and the following optional fields
     *                Given Name
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse australianPostalAddressSearch(SearchIHI request) throws StandardErrorMsg {
        checkUserID();
        argumentValidator.australianPostalAddressSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a Australian Street Address ConsumerSearchIHI search.
     *
     * @param request the SearchIHI request object containing the following mandatory fields:
     *                Family Name
     *                Date of Birth
     *                Sex
     *                Australian Street Address: Street Name
     *                Australian Street Address: Suburb
     *                Australian Street Address: State
     *                Australian Street Address: Post Code
     *                and the following optional fields
     *                Given Name
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse australianStreetAddressSearch(SearchIHI request) throws StandardErrorMsg {
        checkUserID();
        argumentValidator.australianStreetAddressSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes an International Address ConsumerSearchIHI search.
     *
     * @param request the SearchIHI request object containing the following mandatory fields:
     *                Family Name
     *                Date of Birth
     *                Sex
     *                International Address: Line
     *                International Address: Post Code
     *                International Address: State/Province
     *                International Address: Country
     *                and the following optional fields
     *                Given Name
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse internationalAddressSearch(SearchIHI request) throws StandardErrorMsg {
        checkUserID();
        argumentValidator.internationalAddressSearchCheck(request);
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        Holder<SignatureContainerType> signatureHeader = null;
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a basic ConsumerSearchIHI search.
     *
     * @param request      the SearchIHI request object containing the following mandatory fields:
     *                     IHI Number
     *                     Family Name
     *                     Date of Birth
     *                     Sex
     *                     and the following optional fields
     *                     Given Name
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse basicSearch(SearchIHI request,
                                               au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {


        argumentValidator.basicSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId);
    }

    /**
     * Executes a basic Medicare ConsumerSearchIHI search.
     *
     * @param request      the SearchIHI request object containing the following mandatory fields:
     *                     Medicare Card Number
     *                     Family Name
     *                     Date of Birth
     *                     Sex
     *                     and the following optional fields
     *                     Given Name
     *                     Medicare IRN
     * @param individualId the qualified user id of the user making the request            *
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse basicMedicareSearch(SearchIHI request,
                                                       au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {

        argumentValidator.basicMedicareSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a basic DVA ConsumerSearchIHI search.
     *
     * @param request      the SearchIHI request object containing the following mandatory fields:
     *                     DVA File Number
     *                     Family Name
     *                     Date of Birth
     *                     Sex
     *                     and the following optional fields
     *                     Given Name
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse basicDvaSearch(SearchIHI request,
                                                  au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {
        argumentValidator.basicDvaSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a Detailed ConsumerSearchIHI search.
     *
     * @param request      the SearchIHI request object containing the following mandatory fields:
     *                     Family Name
     *                     Date of Birth
     *                     Sex
     *                     and the following optional fields
     *                     Given Name
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse detailedSearch(SearchIHI request,
                                                  au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {
        argumentValidator.detailedSearchCheck(request);

        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a Australian Postal Address ConsumerSearchIHI search.
     *
     * @param request      the SearchIHI request object containing the following mandatory fields:
     *                     Family Name
     *                     Date of Birth
     *                     Sex
     *                     Australian Street Address: Postal Delivery Group
     *                     Australian Street Address: Postal Delivery Type
     *                     Australian Street Address: State
     *                     Australian Street Address: Post Code
     *                     Australian Street Address: Suburb
     *                     and the following optional fields
     *                     Given Name
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse australianPostalAddressSearch(SearchIHI request,
                                                                 au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {
        argumentValidator.australianPostalAddressSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a Australian Street Address ConsumerSearchIHI search.
     *
     * @param request      the SearchIHI request object containing the following mandatory fields:
     *                     Family Name
     *                     Date of Birth
     *                     Sex
     *                     Australian Street Address: Street Name
     *                     Australian Street Address: Suburb
     *                     Australian Street Address: State
     *                     Australian Street Address: Post Code
     *                     and the following optional fields
     *                     Given Name
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse australianStreetAddressSearch(SearchIHI request,
                                                                 au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId)
            throws StandardErrorMsg {

        argumentValidator.australianStreetAddressSearchCheck(request);
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHI(
                request,
                productHolder,
                getTimestampHeader(),
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId
        );
    }

    /**
     * Executes an International Address ConsumerSearchIHI search.
     *
     * @param request      the SearchIHI request object containing the following mandatory fields:
     *                     Family Name
     *                     Date of Birth
     *                     Sex
     *                     International Address: Line
     *                     International Address: Post Code
     *                     International Address: State/Province
     *                     International Address: Country
     *                     and the following optional fields
     *                     Given Name
     * @param individualId the qualified user id of the user making the request
     * @return the response from the ConsumerSearchIHI service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIResponse internationalAddressSearch(SearchIHI request,
                                                              au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId)
            throws StandardErrorMsg {

        argumentValidator.internationalAddressSearchCheck(request);
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        Holder<SignatureContainerType> signatureHeader = null;
        return getPort().searchIHI(
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
         * The maximum length of a postal delivery number.
         */
        public static final int POSTAL_DELIVERY_NUMBER_MAX_LENGTH = 11;

        /**
         * The length of an Australian postcode.
         */
        public static final int AUSTRALIAN_POSTCODE_LENGTH = 4;

        /**
         * Checks that only the correct parameters for a basic search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void basicSearchCheck(final SearchIHI request) {
            checkCommonMandatoryParameters(request);

            ArgumentUtils.checkNotNullNorBlank(request.getIhiNumber(), "IHI Number");

            ensureNull(request.getAustralianPostalAddress(), "Australian Postal Address");
            ensureNull(request.getDvaFileNumber(), "DVA File Number");
            ensureNull(request.getAustralianStreetAddress(), "Australian Street Address");
            ensureNull(request.getHistory(), "History");
            ensureNull(request.getInternationalAddress(), "International Address");
            ensureNull(request.getMedicareCardNumber(), "Medicare Card Number");
            ensureNull(request.getMedicareIRN(), "Medicare IRN");
        }

        /**
         * Checks that only the correct parameters for a basic Medicare search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void basicMedicareSearchCheck(final SearchIHI request) {
            checkCommonMandatoryParameters(request);

            ArgumentUtils.checkNotNullNorBlank(request.getMedicareCardNumber(), "Medicare Card Number");

            ensureNull(request.getAustralianPostalAddress(), "Australian Postal Address");
            ensureNull(request.getDvaFileNumber(), "DVA File Number");
            ensureNull(request.getAustralianStreetAddress(), "Australian Street Address");
            ensureNull(request.getHistory(), "History");
            ensureNull(request.getInternationalAddress(), "International Address");
        }

        /**
         * Checks that only the correct parameters for a basic DVA search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void basicDvaSearchCheck(final SearchIHI request) {
            checkCommonMandatoryParameters(request);

            ArgumentUtils.checkNotNullNorBlank(request.getDvaFileNumber(), "DVA File Name");

            ensureNull(request.getAustralianPostalAddress(), "Australian Postal Address");
            ensureNull(request.getMedicareCardNumber(), "Medicare Card Number");
            ensureNull(request.getMedicareIRN(), "Medicare IRN");
            ensureNull(request.getAustralianStreetAddress(), "Australian Street Address");
            ensureNull(request.getHistory(), "History");
            ensureNull(request.getInternationalAddress(), "International Address");
        }

        /**
         * Checks that only the correct parameters for a detailed search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void detailedSearchCheck(final SearchIHI request) {
            checkCommonMandatoryParameters(request);

            ensureNull(request.getAustralianPostalAddress(), "Australian Postal Address");
            ensureNull(request.getMedicareCardNumber(), "Medicare Card Number");
            ensureNull(request.getMedicareIRN(), "Medicare IRN");
            ensureNull(request.getDvaFileNumber(), "DVA File Name");
            ensureNull(request.getAustralianStreetAddress(), "Australian Street Address");
            ensureNull(request.getHistory(), "History");
            ensureNull(request.getInternationalAddress(), "International Address");
        }

        /**
         * Checks that only the correct parameters for an Australian Postal address search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void australianPostalAddressSearchCheck(final SearchIHI request) {
            checkCommonMandatoryParameters(request);

            // Duplicate type 'PostalDeliveryGroupType' - remove from JAR file.
            AustralianPostalAddressType australianPostalAddress = request.getAustralianPostalAddress();
            ArgumentUtils.checkNotNull(australianPostalAddress, "Australian Postal Address");
            ArgumentUtils.checkNotNull(australianPostalAddress.getPostalDeliveryGroup(), "Postal Delivery Group");
            ArgumentUtils.checkNotNull(australianPostalAddress.getPostalDeliveryGroup().getPostalDeliveryType(), "Postal Delivery Type");
            if (australianPostalAddress.getPostalDeliveryGroup().getPostalDeliveryNumber() != null) {
                ArgumentUtils.checkMaxLength(australianPostalAddress.getPostalDeliveryGroup().getPostalDeliveryNumber(),
                        POSTAL_DELIVERY_NUMBER_MAX_LENGTH,
                        "Postal Delivery Number");
            }
            ArgumentUtils.checkNotNull(australianPostalAddress.getState(), "State");
            ArgumentUtils.checkNotNullNorBlank(australianPostalAddress.getPostcode(), "Post Code");
            ensureExactStringLength(australianPostalAddress.getPostcode(), AUSTRALIAN_POSTCODE_LENGTH, "Post Code");
            ArgumentUtils.checkNotNullNorBlank(australianPostalAddress.getSuburb(), "Suburb");

            ensureNull(request.getAustralianStreetAddress(), "Australian Street Address");

            australianAddressNullChecks(request);
        }

        /**
         * Checks that only the correct parameters for an Australian Street address search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void australianStreetAddressSearchCheck(final SearchIHI request) {
            checkCommonMandatoryParameters(request);

            AustralianStreetAddressType australianStreetAddress = request.getAustralianStreetAddress();
            ArgumentUtils.checkNotNull(australianStreetAddress, "Australian Street Address");
            ArgumentUtils.checkNotNullNorBlank(request.getAustralianStreetAddress().getStreetName(), "Street Name");
            ArgumentUtils.checkNotNullNorBlank(request.getAustralianStreetAddress().getSuburb(), "Suburb Name");
            ArgumentUtils.checkNotNull(request.getAustralianStreetAddress().getState(), "State");
            ArgumentUtils.checkNotNullNorBlank(request.getAustralianStreetAddress().getPostcode(), "Post Code");
            ensureExactStringLength(australianStreetAddress.getPostcode(), AUSTRALIAN_POSTCODE_LENGTH, "Postcode");

            if (request.getAustralianStreetAddress().getUnitGroup() != null) {
                ArgumentUtils.checkNotNull(request.getAustralianStreetAddress().getUnitGroup().getUnitType(), "Unit Type");
            }

            if (request.getAustralianStreetAddress().getLevelGroup() != null) {
                ArgumentUtils.checkNotNull(request.getAustralianStreetAddress().getLevelGroup().getLevelType(), "Level Type");
            }

            ensureNull(request.getAustralianPostalAddress(), "Australian Postal Address");
            australianAddressNullChecks(request);
        }

        /**
         * Checks that only the correct parameters for an International address search are set.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        public final void internationalAddressSearchCheck(final SearchIHI request) {
            checkCommonMandatoryParameters(request);

            InternationalAddressType internationalAddress = request.getInternationalAddress();
            ArgumentUtils.checkNotNull(internationalAddress, "International Address");
            ArgumentUtils.checkNotNullNorBlank(internationalAddress.getInternationalAddressLine(), "International Address Line");
            ArgumentUtils.checkNotNullNorBlank(internationalAddress.getInternationalPostcode(), "International Post Code");
            ArgumentUtils.checkNotNullNorBlank(internationalAddress.getInternationalStateProvince(), "International State Province");
            ArgumentUtils.checkNotNull(internationalAddress.getCountry(), "Country");

            ensureNull(request.getMedicareCardNumber(), "Medicare Card Number");
            ensureNull(request.getMedicareIRN(), "Medicare IRN");
            ensureNull(request.getDvaFileNumber(), "DVA File Name");
            ensureNull(request.getAustralianStreetAddress(), "Australian Street Address");
            ensureNull(request.getAustralianPostalAddress(), "Australian Postal Address");
            ensureNull(request.getHistory(), "History");
        }

        /**
         * Verifies that various non-Australian Address fields are null.
         *
         * @param request the search request object containing the parameters to be checked.
         */
        private void australianAddressNullChecks(SearchIHI request) {
            ensureNull(request.getMedicareCardNumber(), "Medicare Card Number");
            ensureNull(request.getMedicareIRN(), "Medicare IRN");
            ensureNull(request.getDvaFileNumber(), "DVA File Name");
            ensureNull(request.getHistory(), "History");
            ensureNull(request.getInternationalAddress(), "International Address");
        }

        /**
         * Checks that the following mandatory parameters are set correctly:
         * Family Name
         * Date of birth
         * Sex
         *
         * @param request the search request object containing the parameters to be checked.
         */
        private void checkCommonMandatoryParameters(SearchIHI request) {
            if (request == null) {
                throw new IllegalArgumentException("The request may not be null");
            }
            ArgumentUtils.checkNotNullNorBlank(request.getFamilyName(), "Family Name");
            XMLGregorianCalendar dateOfBirth = request.getDateOfBirth();
            if (dateOfBirth == null) {
                throw new IllegalArgumentException("Date of birth may not be null");
            } else {
                if (!dateOfBirth.isValid()) {
                    throw new IllegalArgumentException("Date of birth is invalid");
                }
            }
            SexType sex = request.getSex();
            if (sex == null) {
                throw new IllegalArgumentException("Sex may not be null");
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
