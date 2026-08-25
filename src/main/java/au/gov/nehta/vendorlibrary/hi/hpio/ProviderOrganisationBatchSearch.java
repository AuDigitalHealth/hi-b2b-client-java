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
package au.gov.nehta.vendorlibrary.hi.hpio;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.SubmitSearchForProviderOrganisation;
import au.net.electronichealth.ns.hi.xsd.providercore.organisationdetails._5_1.BatchSearchForProviderOrganisationCriteriaType;
import au.net.electronichealth.ns.hi.xsd.providermessages.searchorganisation._5_0.SearchForProviderOrganisation;

import java.util.List;

/**
 * Helper inner class to create a batch of validated HPIO searches.
 */
public class ProviderOrganisationBatchSearch {

    /**
     * Constant for Request Identifier Length.
     */
    private static final short REQUEST_IDENTIFIER_LENGTH = 36;

    /**
     * Container for validated searches
     */
    private SubmitSearchForProviderOrganisation searchRequest = new SubmitSearchForProviderOrganisation();

    /**
     * Container for validated searches
     */
    private List<BatchSearchForProviderOrganisationCriteriaType> searches = searchRequest.getBatchSearchForProviderOrganisationCriteria();

    /**
     * Argument Validator
     */
    private ArgumentValidator validator = new ArgumentValidator();

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
    public final void identifierSearch(BatchSearchForProviderOrganisationCriteriaType request) {
        this.validator.validateRequestIdentifier(request.getRequestIdentifier());
        this.validator.identifierSearchCheck(request.getSearchForProviderOrganisation());
        searches.add(request);
    }

    public SubmitSearchForProviderOrganisation getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(SubmitSearchForProviderOrganisation searchIhiBatchSync) {
        this.searchRequest = searchIhiBatchSync;
    }

    public List<BatchSearchForProviderOrganisationCriteriaType> getSearches() {
        return searches;
    }

    public void setSearches(List<BatchSearchForProviderOrganisationCriteriaType> searches) {
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
         * Checks that only the correct parameters for an identifier search are
         * set.
         *
         * @param searchForProviderOrganisation the search request object containing the parameters to be
         *                                      checked.
         */
        public final void identifierSearchCheck(SearchForProviderOrganisation searchForProviderOrganisation) {
            ArgumentUtils.checkNotNull(searchForProviderOrganisation, "Request must be supplied");
            ArgumentUtils.checkNotNullNorBlank(searchForProviderOrganisation.getHpioNumber(), "HPIO number");

        }
    }
}
