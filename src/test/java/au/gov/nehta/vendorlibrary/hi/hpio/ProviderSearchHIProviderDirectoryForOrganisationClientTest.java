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

package au.gov.nehta.vendorlibrary.hi.hpio;

import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryfororganisation._3_2.SearchHIProviderDirectoryForOrganisation;
import au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryfororganisation._3_2.SearchHIProviderDirectoryForOrganisationResponse;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.StateType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.StreetSuffixType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.StreetType;
import au.net.electronichealth.ns.hi.xsd.providercore.address._3_2.AustralianAddressType;
import au.net.electronichealth.ns.hi.xsd.providercore.organisationdetails._3_2.OrganisationDetails;
import au.net.electronichealth.ns.hi.xsd.providercore.organisationservice._3_2.OrganisationService;
import au.net.electronichealth.ns.hi.xsd.providermessages.searchorganisation._3_2.OrganisationProviderDirectoryEntryType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.*;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.setSystemVariablesForTest;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.*;
import static org.junit.Assert.*;

public class ProviderSearchHIProviderDirectoryForOrganisationClientTest {
    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient testClient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                        getProductHeader(), getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());

        ReflectionTestUtils.setField(testClient, "loggingHandler", null);

        String lastSoapRequest = testClient.getLastSoapRequest();
        assertEquals(lastSoapRequest, LoggingHandler.EMPTY);

        String lastSoapResponse = testClient.getLastSoapResponse();
        assertEquals(lastSoapResponse, LoggingHandler.EMPTY);
    }

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        ProviderSearchHIProviderDirectoryForOrganisationClient tc = new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testIdentifierSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                        getProductHeader(), getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());

        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setHpioNumber(MCA_HPIO);
        verifyIdentifierSearch(hpioclient.identifierSearch(request), MCA_HPIO);
        verifySoapMessages(hpioclient);
    }

    @Test
    public void testCspIdentifierSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(
                        MEDICARE_ENDPOINT_URL,
                        getUserQualifiedId(),
                        getCspHpioQualifiedId(),
                        getProductHeader(),
                        getCspSigningPrivateKeyForMedicare(),
                        getCspSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForCspMedicare(), null
                );

        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setHpioNumber(MCA_HPIO);
        verifyIdentifierSearch(hpioclient.identifierSearch(request), MCA_HPIO);
        verifySoapMessages(hpioclient);
    }

    @Test
    public void testDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                        getProductHeader(), getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());

        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setName(MCA_ORG_NAME);
        verifyNameDemographicSearch(hpioclient.demographicSearch(request), MCA_ORG_NAME);
        verifySoapMessages(hpioclient);
    }

    @Test
    public void testOrgTypeDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                        getProductHeader(), getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setOrganisationType(MCA_ORG_TYPE);
        assertOrgTypeDemographicSearch(hpioclient.demographicSearch(request), MCA_ORG_TYPE);
        verifySoapMessages(hpioclient);
    }

    @Test
    public void testServiceTypeDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                        getProductHeader(), getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setServiceType(MCA_SERVICE_TYPE);
        verifyServiceTypeDemographicSearch(hpioclient.demographicSearch(request), MCA_SERVICE_TYPE);
        verifySoapMessages(hpioclient);
    }

    @Test
    public void testOrgDetailsDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                        getProductHeader(), getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        OrganisationDetails orgDetails = new OrganisationDetails();
        orgDetails.setAustralianBusinessNumber(MCA_ORG_DETAILS_ABN);
        // TODO medicare returns null for the Australain company number.
        // orgDetails.setAustralianCompanyNumber(MCA_ORG_DETAILS_ACN);
        request.setOrganisationDetails(orgDetails);
//    verifyOrgDetailsDemographicSearch(hpioclient.demographicSearch(request), MCA_ORG_DETAILS_ABN,
//      MCA_ORG_DETAILS_ACN);
//    verifySoapMessages(hpioclient);
    }

    @Test
    public void testAustralianAddressDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                        getProductHeader(), getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForMedicare());
        verifyUnstructuredAustralianAddressDemographicSearch(hpioclient.demographicSearch(request),
                MCA_AUSADDR_UNSTRUCTUREDADDRESSLINE, MCA_AUSADDR_SUBURB, StateType.ACT, MCA_AUSADDR_POSTCODE);
        verifySoapMessages(hpioclient);
    }

    @Test
    public void testInternationalAddressDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                        getProductHeader(), getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setInternationalAddressCriteria(getInternationalAddressCriteriaTypeForMedicare());
        //TODO include valid test data for international address search
//         hpioclient.demographicSearch(request);
    }

    @Ignore
    public void testIdentifierSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setHpioNumber(DRP_HPIO);
        verifyIdentifierSearch(hpioclient.identifierSearch(request), DRP_HPIO);
        verifySoapMessages(hpioclient);
    }

    @Ignore
    public void testDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());

        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setName(DRP_ORG_NAME);
        verifyNameDemographicSearch(hpioclient.demographicSearch(request), DRP_ORG_NAME);
        verifySoapMessages(hpioclient);
    }

    @Ignore
    public void testOrgTypeDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());

        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setOrganisationType(DRP_ORG_TYPE);
        // TODO include this validation when DRP OrgType has been fixed
//        assertOrgTypeDemographicSearch(hpioclient.demographicSearch(request), DRP_ORG_TYPE);
    }

    @Ignore
    public void testServiceTypeDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());

        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setServiceType(DRP_ORG_TYPE);
        // TODO include this validation when DRP ServiceType has been fixed
//        verifyServiceTypeDemographicSearch(hpioclient.demographicSearch(request), DRP_ORG_TYPE);
    }

    @Ignore
    public void testAustralianAddressDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForDRP());
        verifyStructuredAustralianAddressDemographicSearch(hpioclient.demographicSearch(request),
                StreetType.ACCS, StreetSuffixType.CN, DRP_AUSADDR_STREETNAME, DRP_AUSADDR_SUBURB, DRP_AUSADDR_POSTCODE,
                StateType.VIC);
        verifySoapMessages(hpioclient);
    }

    @Ignore
    public void testInternationlAddressDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setInternationalAddressCriteria(getInternationalAddressCriteriaTypeForDRP());
        verifyInternationalAddressDemographicSearch(hpioclient.demographicSearch(request),
                DRP_AUSADDR_STREETNAME, DRP_AUSADDR_POSTCODE, StateType.VIC);
        verifySoapMessages(hpioclient);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAddressDemographicSearchWithAustraliaAndInternationAddressAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();

        request.setInternationalAddressCriteria(getInternationalAddressCriteriaTypeForDRP());
        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForDRP());

        hpioclient.demographicSearch(request);
        verifySoapMessages(hpioclient);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDemographicAndIdentifierSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();

        request.setInternationalAddressCriteria(getInternationalAddressCriteriaTypeForDRP());
        request.setHpioNumber(DRP_HPIO);
        hpioclient.demographicSearch(request);
        verifySoapMessages(hpioclient);
        fail();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPostCodeLengthDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();

        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForDRP());
        request.getAustralianAddressCriteria().setPostcode(DRP_AUSADDR_POSTCODE_INVALID);
        hpioclient.demographicSearch(request);
        fail();

    }

    @Test
    public void testBlankIdentifierSearchAgainstDRP() throws Exception {
        boolean pass = false;
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setHpioNumber(TestConstants.EMPTY);

        try {
            hpioclient.identifierSearch(request);
        } catch (IllegalArgumentException ex) {
            pass = true;
            assertEquals("HPI-O Number cannot be a blank string.", ex.getMessage());
        }
        assertTrue(pass);
    }

    @Test
    public void testNullIdentifierSearchAgainstDRP() throws Exception {
        boolean pass = false;
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForOrganisationClient hpioclient =
                new ProviderSearchHIProviderDirectoryForOrganisationClient(DRP_HPIO_SEARCH_ENDPOINT_URL,
                        getUserQualifiedId(), getProductHeader(),
                        getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(),
                        getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();

        try {
            hpioclient.identifierSearch(request);
            request.setHpioNumber(TestConstants.EMPTY);
        } catch (IllegalArgumentException ex) {
            pass = true;
            assertEquals("HPI-O Number cannot be null.", ex.getMessage());
        }
        assertTrue(pass);
    }

    @Test
    public void testWrappedAustralianAddressDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();

        au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId vendorQualifiedId =
                new au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId(
                        IHITestConstants.VENDOR_QUALIFIER, IHITestConstants.VENDOR_QUALIFIER_ID);

        au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader = new
                au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType(
                IHITestConstants.PRODUCT_PLATFORM,
                IHITestConstants.PRODUCT_NAME,
                IHITestConstants.PRODUCT_VERSION,
                vendorQualifiedId
        );

        au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId userId =
                new au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId(
                        IHITestConstants.USER_QUALIFIER, IHITestConstants.USER_QUALIFIED_ID);

        ProviderSearchHIProviderDirectoryForOrganisationClient client =

                new ProviderSearchHIProviderDirectoryForOrganisationClient(MEDICARE_ENDPOINT_URL,
                        productHeader, getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());

        SearchHIProviderDirectoryForOrganisation request = new SearchHIProviderDirectoryForOrganisation();
        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForMedicare());
        verifyUnstructuredAustralianAddressDemographicSearch(client.demographicSearch(request, userId),
                MCA_AUSADDR_UNSTRUCTUREDADDRESSLINE, MCA_AUSADDR_SUBURB, StateType.ACT, MCA_AUSADDR_POSTCODE);
        verifySoapMessages(client);

    }

    private void verifyIdentifierSearch(SearchHIProviderDirectoryForOrganisationResponse response, String hpioNumber)
            throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType.getHpioNumber());
            assertEquals(hpioNumber, directoryEntryType.getHpioNumber());
        }
    }

    private void verifyNameDemographicSearch(SearchHIProviderDirectoryForOrganisationResponse response, String orgName)
            throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType);
            assertNotNull(directoryEntryType.getOrganisationName());
            assertNotNull(directoryEntryType.getOrganisationName().getName());
            assertTrue(directoryEntryType.getOrganisationName().getName().startsWith(orgName));
        }
    }

    private void assertOrgTypeDemographicSearch(SearchHIProviderDirectoryForOrganisationResponse response,
                                                String orgType) throws Exception {

        verifyMandatoryOrganisationService(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType);
            for (OrganisationService service : directoryEntryType.getOrganisationService()) {
                assertNotNull(service);
                assertEquals(orgType, service.getOrganisationType());
            }
        }
    }

    private void verifyOrgDetailsDemographicSearch(SearchHIProviderDirectoryForOrganisationResponse response,
                                                   String australianBusinessBNumber, String australianCompanyNumber)
            throws Exception {
        boolean pass = false;
        verifyMandatoryProviderDirectoryEntries(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType);
            assertNotNull(directoryEntryType.getOrganisationDetails());
            if (directoryEntryType.getOrganisationDetails().getAustralianBusinessNumber().
                    equals(australianBusinessBNumber)) {
                pass = true;
            }
            assertTrue(pass);
        }
    }

    private void verifyUnstructuredAustralianAddressDemographicSearch(SearchHIProviderDirectoryForOrganisationResponse response,
                                                                      String unstructuredAddress, String suburb, StateType state,
                                                                      String postCode) throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            verifyAustralianAddressType(directoryEntryType);
            final AustralianAddressType address = directoryEntryType.getAddress().getAustralianAddress();
            assertEquals(unstructuredAddress.toLowerCase(), address.getUnstructuredAddressLine().toLowerCase());
            assertEquals(suburb, address.getSuburb());
            assertEquals(state, address.getState());
            assertEquals(postCode, address.getPostcode());
        }
    }

    private void verifyAustralianAddressType(OrganisationProviderDirectoryEntryType directoryEntryType) throws Exception {
        assertNotNull(directoryEntryType);
        assertNotNull(directoryEntryType.getAddress());
        assertNotNull(directoryEntryType.getAddress().getAustralianAddress());
    }

    private void verifyStructuredAustralianAddressDemographicSearch(SearchHIProviderDirectoryForOrganisationResponse response,
                                                                    StreetType streetType, StreetSuffixType streetSuffixType,
                                                                    String streetName, String suburb, String postCode,
                                                                    StateType stateType) throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            verifyAustralianAddressType(directoryEntryType);
            final AustralianAddressType address = directoryEntryType.getAddress().getAustralianAddress();
            // TODO These values are null in the search result. Need to identify the cause of this issue.
//            assertEquals(streetType, address.getStreetType());
//            assertEquals(streetSuffixType, address.getStreetSuffix());
            assertEquals(streetName, address.getStreetName());
            assertEquals(suburb, address.getSuburb());
            assertEquals(stateType, address.getState());
            assertEquals(postCode, address.getPostcode());
        }
    }

    private void verifyInternationalAddressDemographicSearch(SearchHIProviderDirectoryForOrganisationResponse response,
                                                             String streetName, String postCode, StateType stateType)
            throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType :
                response.getSearchHIProviderDirectoryForOrganisationResult().
                        getOrganisationProviderDirectoryEntries()) {
            verifyAustralianAddressType(directoryEntryType);
            final AustralianAddressType address = directoryEntryType.getAddress().getAustralianAddress();
            assertEquals(streetName, address.getStreetName());
            assertEquals(stateType, address.getState());
            assertEquals(postCode, address.getPostcode());
        }
    }

    private void verifyServiceTypeDemographicSearch(SearchHIProviderDirectoryForOrganisationResponse response,
                                                    String serviceType) throws Exception {
        verifyMandatoryOrganisationService(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType);
            for (OrganisationService service : directoryEntryType.getOrganisationService()) {
                assertNotNull(service);
                assertEquals(serviceType, service.getOrganisationServiceType());
            }
        }
    }

    private void verifyMandatoryOrganisationService(SearchHIProviderDirectoryForOrganisationResponse response) throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType);
            assertNotNull(directoryEntryType.getOrganisationService());
            assertTrue(directoryEntryType.getOrganisationService().size() > 0);
        }
    }

    private void verifyMandatoryProviderDirectoryEntries(SearchHIProviderDirectoryForOrganisationResponse response)
            throws Exception {
        assertNotNull(response);
        assertNotNull(response.getSearchHIProviderDirectoryForOrganisationResult());
        assertNotNull(response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries());
        // TODO: find results with > 0 entries
//    assertTrue(response.getSearchHIProviderDirectoryForOrganisationResult().
//      getOrganisationProviderDirectoryEntries().size() > 0);
        for (OrganisationProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForOrganisationResult().
                getOrganisationProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType);
            assertNotNull(directoryEntryType.getHpioNumber());
            assertNotNull(directoryEntryType.getOrganisationName());
            assertNotNull(directoryEntryType.getAddress());
        }
    }

    private void verifySoapMessages(ProviderSearchHIProviderDirectoryForOrganisationClient testClient) {
        Assert.assertNotNull(testClient.getLastSoapRequest());
        Assert.assertNotNull(testClient.getLastSoapResponse());
        assertNotEquals(testClient.getLastSoapRequest(), EMPTY);
        assertNotEquals(testClient.getLastSoapResponse(), EMPTY);
    }
}


