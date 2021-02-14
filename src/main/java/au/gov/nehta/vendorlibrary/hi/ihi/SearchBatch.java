package au.gov.nehta.vendorlibrary.hi.ihi;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.net.electronichealth.ns.hi.consumermessages.searchihi._3.SearchIHI;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchsyncrequest._3.SearchIHIBatchSync;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianPostalAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianStreetAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.InternationalAddressType;
import au.net.electronichealth.ns.hi.xsd.consumermessages.searchihibatch._3.SearchIHIRequestType;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

/**
 * Helper inner class to create a batch of validated IHI searches.
 */
public class SearchBatch {

    /**
     * Constant for Request Identifier Length.
     */
    private static final short REQUEST_IDENTIFIER_LENGTH = 36;

    /**
     * Container for validated searches
     */
    private SearchIHIBatchSync searchIhiBatchSync = new SearchIHIBatchSync();

    /**
     * Container for validated searches
     */
    private List<SearchIHIRequestType> searches = searchIhiBatchSync.getSearchIHIBatchRequest();

    /**
     * Argument Validator
     */
    private ArgumentValidator argumentValidator = new ArgumentValidator();


    /**
     * Validates a Basic search and adds it to the batch if successful.
     *
     * @param search the search object containing the following mandatory fields:
     *               IHI Number Family Name Date of Birth Sex and the following
     *               optional fields Given Name
     */
    public final void addBasicSearch(SearchIHIRequestType search) {
        this.argumentValidator.basicSearchCheck(search.getSearchIHI());
        this.validateRequestIdentifier(search.getRequestIdentifier());
        this.searches.add(search);
    }

    /**
     * Validates a Basic Medicare IHI search and adds it to the batch if
     * successful.
     *
     * @param search the search object containing the following mandatory fields:
     *               Medicare Card Number Family Name Date of Birth Sex and the
     *               following optional fields Given Name Medicare IRN
     */
    public final void addBasicMedicareSearch(SearchIHIRequestType search) {
        this.argumentValidator.basicMedicareSearchCheck(search.getSearchIHI());
        this.validateRequestIdentifier(search.getRequestIdentifier());
        this.searches.add(search);
    }


    /**
     * Validates a Basic DVA search and adds it to the batch if successful.
     *
     * @param search the search object containing the following mandatory fields:
     *               DVA File Number Family Name Date of Birth Sex and the
     *               following optional fields Given Name
     */
    public final void addBasicDvaSearch(SearchIHIRequestType search) {
        this.argumentValidator.basicDvaSearchCheck(search.getSearchIHI());
        this.validateRequestIdentifier(search.getRequestIdentifier());
        this.searches.add(search);
    }

    /**
     * Validates a Detailed search and adds it to the batch if successful.
     *
     * @param search the search object containing the following mandatory fields:
     *               Family Name Date of Birth Sex and the following optional
     *               fields Given Name
     */
    public final void addDetailedSearch(SearchIHIRequestType search) {
        this.argumentValidator.detailedSearchCheck(search.getSearchIHI());
        this.validateRequestIdentifier(search.getRequestIdentifier());
        this.searches.add(search);
    }

    /**
     * Validates a Australian Postal Address search and adds it to the batch if
     * successful.
     *
     * @param search the search object containing the following mandatory fields:
     *               Family Name Date of Birth Sex Australian Street Address:
     *               Postal Delivery Group Australian Street Address: Postal
     *               Delivery Type Australian Street Address: State Australian
     *               Street Address: Post Code Australian Street Address: Suburb
     *               and the following optional fields Given Name
     */
    public final void addAustralianPostalAddressSearch(SearchIHIRequestType search) {
        this.argumentValidator.australianPostalAddressSearchCheck(search.getSearchIHI());
        this.validateRequestIdentifier(search.getRequestIdentifier());
        this.searches.add(search);
    }

    /**
     * Validates a Australian Street Address search and adds it to the batch if
     * successful.
     *
     * @param search the search object containing the following mandatory fields:
     *               Family Name Date of Birth Sex Australian Street Address:
     *               Street Name Australian Street Address: Suburb Australian
     *               Street Address: State Australian Street Address: Post Code and
     *               the following optional fields Given Name
     */
    public final void addAustralianStreetAddressSearch(SearchIHIRequestType search) {
        this.argumentValidator.australianStreetAddressSearchCheck(search.getSearchIHI());
        this.validateRequestIdentifier(search.getRequestIdentifier());
        this.searches.add(search);
    }

    /**
     * Validates a Australian Street Address search and adds it to the batch if
     * successful.
     *
     * @param search the search object containing the following mandatory fields:
     *               Family Name Date of Birth Sex International Address: Line
     *               International Address: Post Code International Address:
     *               State/Province International Address: Country and the
     *               following optional fields Given Name
     */
    public final void addInternationalAddressSearch(SearchIHIRequestType search) {
        this.argumentValidator.internationalAddressSearchCheck(search.getSearchIHI());
        this.validateRequestIdentifier(search.getRequestIdentifier());
        this.searches.add(search);
    }

    // Helper methods

    private void validateRequestIdentifier(String requestIdentifier) {
        if (requestIdentifier == null) {
            throw new IllegalArgumentException("request Identifier may not be null");
        } else {
            if (requestIdentifier.length() != REQUEST_IDENTIFIER_LENGTH) {
                throw new IllegalArgumentException("request Identifier must have a length of " + REQUEST_IDENTIFIER_LENGTH);
            }
        }
    }

    public final SearchIHIBatchSync getBatch() {
        return this.searchIhiBatchSync;
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