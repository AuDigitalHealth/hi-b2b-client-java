package au.gov.nehta.vendorlibrary.hi.hpii;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.hi.client.ClientBase;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderindividual._5_1.SubmitSearchForProviderIndividual;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._5_0.SearchAustralianAddressType;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._5_0.SearchInternationalAddressType;
import au.net.electronichealth.ns.hi.xsd.providercore.providertype._5_1.BatchSearchForProviderIndividualCriteriaType;
import au.net.electronichealth.ns.hi.xsd.providermessages.searchindividual._5_0.SearchForProviderIndividual;

import java.util.List;

/**
 * Helper inner class to create a batch of validated HPII searches.
 */
public class ProviderIndividualBatchSearch {


    /**
     * Constant for Request Identifier Length.
     */
    private static final short REQUEST_IDENTIFIER_LENGTH = 36;

    /**
     * Container for validated searches
     */
    private SubmitSearchForProviderIndividual searchRequest = new SubmitSearchForProviderIndividual();

    /**
     * Container for validated searches
     */
    private List<BatchSearchForProviderIndividualCriteriaType> searches = getSearchRequest().getBatchSearchForProviderIndividualCriteria();

    /**
     * Argument Validator
     */
    private ArgumentValidator validator = new ArgumentValidator();

    /**
     * Constructs a provider individual demographic search and adds it to a
     * batch list.
     *
     * @param request a BatchSearchForProviderIndividualCriteriaType with: the
     *                SearchForProviderIndividual request object containing the
     *                following mandatory fields: FamilyName
     */
    public final void demographicSearch(BatchSearchForProviderIndividualCriteriaType request) {
        this.validator.validateRequestIdentifier(request.getRequestIdentifier());
        this.validator.demographicSearchCheck(request.getSearchForProviderIndividual());
        searches.add(request);
    }

    /**
     * Constructs a provider individual  Identifier search and adds it to the batch list.
     *
     * @param request a BatchSearchForProviderIndividualCriteriaType with:
     *                <p>
     *                the SearchHIProviderDirectoryForIndividual request object containing the following mandatory fields:
     *                HPII or Registration ID
     *                FamilyName
     *                <p>
     *                and a request identifier
     */
    public final void identifierSearch(BatchSearchForProviderIndividualCriteriaType request) {
        this.validator.validateRequestIdentifier(request.getRequestIdentifier());
        this.validator.identifierSearchCheck(request.getSearchForProviderIndividual());
        searches.add(request);
    }

    public SubmitSearchForProviderIndividual getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(SubmitSearchForProviderIndividual searchIhiBatchSync) {
        this.searchRequest = searchIhiBatchSync;
    }

    public List<BatchSearchForProviderIndividualCriteriaType> getSearches() {
        return searches;
    }

    public void setSearches(List<BatchSearchForProviderIndividualCriteriaType> searches) {
        this.searches = searches;
    }

    private class ArgumentValidator {

        private void validateRequestIdentifier(String requestIdentifier) {
            if (requestIdentifier == null) {
                throw new IllegalArgumentException("request Identifier may not be null");
            } else {
                if (requestIdentifier.length() != REQUEST_IDENTIFIER_LENGTH) {
                    throw new IllegalArgumentException("request Identifier must have a length of " + REQUEST_IDENTIFIER_LENGTH);
                }
            }
        }

        /**
         * The length of an Australian postcode.
         */
        public static final int AUSTRALIAN_POSTCODE_LENGTH = 4;

        /**
         * Checks that only the correct parameters for an identifier search are
         * set.
         *
         * @param request the search request object containing the parameters to be
         *                checked.
         */
        public final void identifierSearchCheck(SearchForProviderIndividual request) {
            ArgumentUtils.checkNotNull(request, "Request must be supplied");
            ArgumentUtils.checkNotNull(request.getFamilyName(), "Family name must be supplied");
            ArgumentUtils.checkMaxLength(request.getFamilyName(), 40, "FamilyName");

            boolean isHPIIEmpty = ClientBase.isEmpty(request.getHpiiNumber());
            boolean isRegistrationIDEmpty = ClientBase.isEmpty(request.getRegistrationId());

            if (!isRegistrationIDEmpty && !isHPIIEmpty) {
                throw new IllegalArgumentException("You must not supply either HPII or RegistrationId");
            }

        }

        /**
         * Checks that only the correct parameters for a demographic search are
         * set.
         *
         * @param request the search request object containing the parameters to be
         *                checked.
         */
        public final void demographicSearchCheck(SearchForProviderIndividual request) {
            ArgumentUtils.checkNotNull(request, "Request must be supplied");
            ArgumentUtils.checkNotNull(request.getFamilyName(), "Family name must be supplied");
            ArgumentUtils.checkMaxLength(request.getFamilyName(), 40, "FamilyName");
            ensureNull(request.getHpiiNumber(), "HPII Number");
            ensureEitherOneOrBothAreNull(request.getSearchAustralianAddress(), "Australian Address Criteria", request.getSearchInternationalAddress(), "International Address Criteria");

            if (ClientBase.isEmpty(request.getFamilyName()))

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
         * @param obj1Name the name of the first object variable (for use in
         *                 Exception messages) (Mandatory)
         * @param obj2     the second object being checked (Mandatory)
         * @param obj2Name the name of the second object variable (for use in
         *                 Exception messages) (Mandatory)
         */
        private void ensureEitherOneOrBothAreNull(Object obj1, String obj1Name, SearchInternationalAddressType obj2, String obj2Name) {
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
         * @param objectName the name of the Object variable (for use in Exception
         *                   messages) (Mandatory)
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
         * @param stringName the name of the String variable (for use in Exception
         *                   messages) (Mandatory)
         */
        private void ensureExactStringLength(String theString, Integer length, String stringName) {
            if (theString.length() != length) {
                throw new IllegalArgumentException(stringName + " must have a length of " + length);
            }
        }
    }
}
