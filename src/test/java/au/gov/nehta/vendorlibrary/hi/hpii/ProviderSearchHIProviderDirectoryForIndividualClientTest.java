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

import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.DRP_AUSADDR_POSTCODE;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.DRP_AUSADDR_POSTCODE_INVALID;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.DRP_AUSADDR_STREETNAME;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.DRP_AUSADDR_SUBURB;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.DRP_HPII;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.DRP_HPIO;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_AUSADDR_POSTCODE;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_AUSADDR_SUBURB;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_AUSADDR_UNSTRUCTUREDADDRESSLINE;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_FAMILY_NAME;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_GIVEN_NAME;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_HPII;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_HPIO;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_PROVIDER_SPECIALITY;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_PROVIDER_TYPECODE;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.getAustralianAddressCriteriaTypeForDRP;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.getAustralianAddressCriteriaTypeForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.getInternationalAddressCriteriaTypeForDRP;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.setSystemVariablesForTest;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.DRP_HPII_SEARCH_ENDPOINT_URL;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.EMPTY;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.MEDICARE_ENDPOINT_URL;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getCspHpioQualifiedId;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getCspSigningCertificateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getCspSigningPrivateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getProductHeader;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningCertificateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningCertificateKeyForRefPlatform;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningPrivateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningPrivateKeyForRefPlatform;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSslSocketFactoryForCspMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSslSocketFactoryForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSslSocketFactoryForRefPlatform;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getUserQualifiedId;
import static org.junit.Assert.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import au.gov.nehta.vendorlibrary.hi.client.BaseClient_3;
import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryforindividual._3_2.ProviderSearchHIProviderDirectoryForIndividualPortType;
import au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryforindividual._3_2.SearchHIProviderDirectoryForIndividual;
import au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryforindividual._3_2.SearchHIProviderDirectoryForIndividualResponse;
import au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryforindividual._3_2.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.StateType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.StreetSuffixType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.StreetType;
import au.net.electronichealth.ns.hi.xsd.providercore.address._3_2.AustralianAddressType;
import au.net.electronichealth.ns.hi.xsd.providermessages.searchindividual._3_2.IndividualProviderDirectoryEntryType;

public class ProviderSearchHIProviderDirectoryForIndividualClientTest {

    @Test
    public void testBindingProvider() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient tc = getMedicareTestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient testClient = getMedicareTestClient();

        ReflectionTestUtils.setField(testClient, "loggingHandler", null);

        String lastSoapRequest = testClient.getLastSoapRequest();
        assertEquals(lastSoapRequest, LoggingHandler.EMPTY);

        String lastSoapResponse = testClient.getLastSoapResponse();
        assertEquals(lastSoapResponse, LoggingHandler.EMPTY);
    }

    /**
     * testNoCspHpioIdentifierSearchAgainstMedicare test case to perform
     * webservice call against the Medicare endpoint. Specifically tests
     * optional hpioHeader functionality.
     *
     * @throws Exception Exception in the event of an error.
     */
    @Test
    public void testCspIdentifierSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();

        ProviderSearchHIProviderDirectoryForIndividualClient hpiiClient = new ProviderSearchHIProviderDirectoryForIndividualClient(
                MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getCspHpioQualifiedId(), getProductHeader(),
                getCspSigningPrivateKeyForMedicare(),
                getCspSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForCspMedicare(), null);
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setHpiiNumber(MCA_HPII);
        verifyIdentifierSearch(hpiiClient.identifierSearch(request), MCA_HPII);
        verifySoapMessages(hpiiClient);
    }

    /**
     * testIdentifierSearchAgainstMedicare test case to perform webservice call
     * against the Medicare endpoint
     *
     * @throws Exception in an event of error
     */
    @Test
    public void testIdentifierSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();

        ProviderSearchHIProviderDirectoryForIndividualClient hpiiClient = getMedicareTestClient();
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setHpiiNumber(MCA_HPII);
        verifyIdentifierSearch(hpiiClient.identifierSearch(request), MCA_HPII);
        verifySoapMessages(hpiiClient);
    }

    /**
     * testInvalidIdentifierSearchAgainstMedicare test case to perform
     * webservice call against the Medicare endpoint using HPIO (Invalid HPII)
     * as identifier
     *
     * @throws Exception in an event of error
     */
    @Test(expected = StandardErrorMsg.class)
    public void testInvalidIdentifierSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();

        ProviderSearchHIProviderDirectoryForIndividualClient hpiiClient = getMedicareTestClient();
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setHpiiNumber(MCA_HPIO);
        hpiiClient.identifierSearch(request);
        verifySoapMessages(hpiiClient);
    }

    /**
     * testFamilyAndGivenNameAndSexDemographicSearchAgainstMedicare test case to
     * perform webservice call against the Medicare endpoint using provider
     * familyname, givenname and Sex.
     *
     * @throws Exception in an event of error
     */
    @Test
    public void testFamilyAndGivenNameAndSexDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiClient = getMedicareTestClient();
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setGivenName(MCA_GIVEN_NAME);
        request.setFamilyName(MCA_FAMILY_NAME);
        request.setSex(SexType.M);
        verifyDemographicSearch(hpiiClient.demographicSearch(request), MCA_FAMILY_NAME, MCA_GIVEN_NAME, SexType.M);
        verifySoapMessages(hpiiClient);
    }

    /**
     * testFamilyAndGivenNameAndSexDemographicSearchAgainstMedicare test case to
     * perform webservice call against the Medicare endpoint using provider
     * familyname, givenname and Sex.
     *
     * @throws Exception in an event of error
     */
    @Test
    public void testProviderTypeDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiClient = getMedicareTestClient();
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setProviderTypeCode(MCA_PROVIDER_TYPECODE);
        request.setProviderSpecialty(MCA_PROVIDER_SPECIALITY);
        verifyProviderTypeDemographicSearch(hpiiClient.demographicSearch(request), MCA_PROVIDER_TYPECODE, MCA_PROVIDER_SPECIALITY);
        verifySoapMessages(hpiiClient);
    }

    /**
     * testAustralianAddressDemographicSearchAgainstMedicare test case to
     * perform webservice call against the Medicare endpoint using provider
     * Australian Address.
     *
     * @throws Exception in an event of error
     */
    @Test
    public void testAustralianAddressDemographicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiClient = getMedicareTestClient();
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForMedicare());
        verifyUnstructuredAustralianAdderssDemographicSearch(hpiiClient.demographicSearch(request), MCA_AUSADDR_UNSTRUCTUREDADDRESSLINE, MCA_AUSADDR_SUBURB, StateType.ACT, MCA_AUSADDR_POSTCODE);
        verifySoapMessages(hpiiClient);
    }

    /**
     * testIdentifierSearchAgainstDRP test case to perform webservice call
     * against the DRP endpoint
     *
     * @throws Exception in an event of error
     */
    @Ignore
    public void testIdentifierSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();

        ProviderSearchHIProviderDirectoryForIndividualClient hpiiClient = new ProviderSearchHIProviderDirectoryForIndividualClient(DRP_HPII_SEARCH_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setHpiiNumber(DRP_HPII);
        verifyIdentifierSearch(hpiiClient.identifierSearch(request), DRP_HPII);
        verifySoapMessages(hpiiClient);
    }

    /**
     * testAustralianAddressDemographicSearchAgainstDRP test case to perform
     * webservice call against the DRP endpoint using the provider
     * AustralianAddress
     *
     * @throws Exception in an event of error
     */
    @Ignore
    public void testAustralianAddressDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiclient = new ProviderSearchHIProviderDirectoryForIndividualClient(DRP_HPII_SEARCH_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForDRP());
        verifyStructuredAustralianAdderssDemographicSearch(hpiiclient.demographicSearch(request), StreetType.ACCS, StreetSuffixType.CN, DRP_AUSADDR_STREETNAME, DRP_AUSADDR_SUBURB,
                DRP_AUSADDR_POSTCODE, StateType.VIC);
        verifySoapMessages(hpiiclient);
    }

    /**
     * testInternationlAddressDemographicSearchAgainstDRP test case to perform
     * webservice call against the DRP endpoint using the provider
     * InternationalAddress
     *
     * @throws Exception in an event of error
     */
    @Ignore
    public void testInternationlAddressDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiclient = new ProviderSearchHIProviderDirectoryForIndividualClient(DRP_HPII_SEARCH_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setInternationalAddressCriteria(getInternationalAddressCriteriaTypeForDRP());
        verifyInternationalAdderssDemographicSearch(hpiiclient.demographicSearch(request), DRP_AUSADDR_STREETNAME, DRP_AUSADDR_POSTCODE, StateType.VIC);
        verifySoapMessages(hpiiclient);
    }

    /**
     * testBlankIdentifierSearchAgainstDRP test case to perform webservice call
     * against the DRP endpoint using the EMPTY String as an identifier
     *
     * @throws Exception in an event of error
     */
    @Ignore
    public void testBlankIdentifierSearchAgainstDRP() throws Exception {
        boolean pass = false;
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiclient = new ProviderSearchHIProviderDirectoryForIndividualClient(DRP_HPII_SEARCH_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setHpiiNumber(TestConstants.EMPTY);

        try {
            hpiiclient.identifierSearch(request);
        } catch (IllegalArgumentException ex) {
            pass = true;
            assertEquals("HPI-I Number cannot be a blank string.", ex.getMessage());
        }
        assertTrue(pass);
    }

    /**
     * testInvalidPostCodeLengthDemographicSearchAgainstDRP test case to perform
     * webservice call against the DRP endpoint using an invalid postcode.
     *
     * @throws Exception in an event of error
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPostCodeLengthDemographicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiclient = new ProviderSearchHIProviderDirectoryForIndividualClient(DRP_HPII_SEARCH_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();

        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForDRP());
        request.getAustralianAddressCriteria().setPostcode(DRP_AUSADDR_POSTCODE_INVALID);
        hpiiclient.demographicSearch(request);
        Assert.fail();

    }

    /**
     * testInvalidDemographicAndIdentifierSearchAgainstDRP test case to perform
     * webservice call against the DRP endpoint using an valid identifier and
     * international address as demographic data.
     *
     * @throws Exception in an event of error
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDemographicAndIdentifierSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiclient = new ProviderSearchHIProviderDirectoryForIndividualClient(DRP_HPII_SEARCH_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();

        request.setInternationalAddressCriteria(getInternationalAddressCriteriaTypeForDRP());
        request.setHpiiNumber(DRP_HPIO);
        hpiiclient.demographicSearch(request);
        verifySoapMessages(hpiiclient);
        fail();

    }

    /**
     * testInvalidAddressDemographicSearchWithAustraliaAndInternationAddressAgainstDRP
     * test case to perform webservice call against the DRP endpoint using an
     * valid Australian and international address.
     *
     * @throws Exception in an event of error
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAddressDemographicSearchWithAustraliaAndInternationAddressAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiclient = new ProviderSearchHIProviderDirectoryForIndividualClient(DRP_HPII_SEARCH_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();

        request.setInternationalAddressCriteria(getInternationalAddressCriteriaTypeForDRP());
        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForDRP());

        hpiiclient.demographicSearch(request);
        verifySoapMessages(hpiiclient);

        fail();
    }

    /**
     * testNullIdentifierSearchAgainstDRP test case to perform webservice call
     * against the DRP endpoint using null identifer.
     *
     * @throws Exception in an event of error
     */
    @Test
    public void testNullIdentifierSearchAgainstDRP() throws Exception {
        boolean pass = false;
        setSystemVariablesForTest();
        ProviderSearchHIProviderDirectoryForIndividualClient hpiiclient = new ProviderSearchHIProviderDirectoryForIndividualClient(DRP_HPII_SEARCH_ENDPOINT_URL, getUserQualifiedId(),
                getProductHeader(), getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();

        try {
            hpiiclient.identifierSearch(request);
            request.setHpiiNumber(TestConstants.EMPTY);
        } catch (IllegalArgumentException ex) {
            pass = true;
            assertEquals("HPI-I Number cannot be null.", ex.getMessage());
        }
        assertTrue(pass);
    }


    /**
     * testNullIdentifierSearchAgainstDRP test case to perform webservice call
     * against the DRP endpoint using null identifer.
     *
     * @throws Exception in an event of error
     */
    @Test
    public void testWrappedClientSearch() throws Exception {
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


        ProviderSearchHIProviderDirectoryForIndividualClient hpiiClient =
                new ProviderSearchHIProviderDirectoryForIndividualClient(
                        MEDICARE_ENDPOINT_URL,
                        productHeader,
                        getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());

        SearchHIProviderDirectoryForIndividual request = new SearchHIProviderDirectoryForIndividual();
        request.setHpiiNumber(MCA_HPII);
        verifyIdentifierSearch(hpiiClient.identifierSearch(request, userId), MCA_HPII);
        verifySoapMessages(hpiiClient);

        //reset for the second request
        request = new SearchHIProviderDirectoryForIndividual();
        request.setAustralianAddressCriteria(getAustralianAddressCriteriaTypeForMedicare());
        verifyUnstructuredAustralianAdderssDemographicSearch(hpiiClient.demographicSearch(request, userId), MCA_AUSADDR_UNSTRUCTUREDADDRESSLINE, MCA_AUSADDR_SUBURB, StateType.ACT, MCA_AUSADDR_POSTCODE);
        verifySoapMessages(hpiiClient);


    }

    private void verifyIdentifierSearch(SearchHIProviderDirectoryForIndividualResponse response, String hpioNumber) throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (IndividualProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType.getHpiiNumber());
            assertEquals(hpioNumber, directoryEntryType.getHpiiNumber());
        }
    }

    private void verifyDemographicSearch(SearchHIProviderDirectoryForIndividualResponse response, String familyName, String givenName, SexType sex) throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (IndividualProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType.getIndividualName());
            assertEquals(familyName, directoryEntryType.getIndividualName().getFamilyName());
            assertTrue(directoryEntryType.getIndividualName().getGivenName().size() > 0);
            assertEquals(givenName, directoryEntryType.getIndividualName().getGivenName().get(0));
            assertNotNull(directoryEntryType.getPersonalDetails());
            assertEquals(sex, directoryEntryType.getPersonalDetails().getSex());
        }
    }

    private void verifyProviderTypeDemographicSearch(SearchHIProviderDirectoryForIndividualResponse response, String providerCodeType, String providerSpeciality) throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (IndividualProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType);
            assertNotNull(directoryEntryType.getProviderType());
            assertTrue(providerCodeType, directoryEntryType.getProviderType().size() > 0);
            assertNotNull(directoryEntryType.getProviderType().get(0).getProviderTypeCode());
            assertEquals(providerCodeType, directoryEntryType.getProviderType().get(0).getProviderTypeCode());
            assertEquals(providerSpeciality, directoryEntryType.getProviderType().get(0).getProviderSpecialty());
        }
    }

    private void verifyMandatoryProviderDirectoryEntries(SearchHIProviderDirectoryForIndividualResponse response) throws Exception {
        assertNotNull(response);
        assertNotNull(response.getSearchHIProviderDirectoryForIndividualResult());
        assertNotNull(response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries());
        assertTrue(response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries().size() > 0);
        for (IndividualProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries()) {
            assertNotNull(directoryEntryType);
            assertNotNull(directoryEntryType.getHpiiNumber());
            assertNotNull(directoryEntryType.getIndividualName());
            assertNotNull(directoryEntryType.getAddress());
        }
    }

    private void verifySoapMessages(BaseClient_3<ProviderSearchHIProviderDirectoryForIndividualPortType> testClient) {
        Assert.assertNotNull(testClient.getLastSoapRequest());
        Assert.assertNotNull(testClient.getLastSoapResponse());
        assertNotEquals(testClient.getLastSoapRequest(), EMPTY);
        assertNotEquals(testClient.getLastSoapResponse(), EMPTY);
    }

    private void verifyUnstructuredAustralianAdderssDemographicSearch(SearchHIProviderDirectoryForIndividualResponse response,
                                                                      String unstructuredAddress,
                                                                      String suburb,
                                                                      StateType state,
                                                                      String postCode) throws Exception {

        verifyMandatoryProviderDirectoryEntries(response);
        for (IndividualProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries()) {
            verifyAustralianAddresstype(directoryEntryType);
            final AustralianAddressType address = directoryEntryType.getAddress().getAustralianAddress();
            assertEquals(unstructuredAddress.toLowerCase(), address.getUnstructuredAddressLine().toLowerCase());
            assertEquals(suburb, address.getSuburb());
            assertEquals(state, address.getState());
            assertEquals(postCode, address.getPostcode());
        }
    }

    private void verifyStructuredAustralianAdderssDemographicSearch(SearchHIProviderDirectoryForIndividualResponse response, StreetType streetType, StreetSuffixType streetSuffixType,
                                                                    String streetName, String suburb, String postCode, StateType stateType) throws Exception {
        verifyMandatoryProviderDirectoryEntries(response);
        for (IndividualProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries()) {
            verifyAustralianAddresstype(directoryEntryType);
            final AustralianAddressType address = directoryEntryType.getAddress().getAustralianAddress();
            // TODO These values are null in the search result. Need to identify
            // the cause of this issue.
            // assertEquals(streetType, address.getStreetType());
            // assertEquals(streetSuffixType, address.getStreetSuffix());
            assertEquals(streetName, address.getStreetName());
            assertEquals(suburb, address.getSuburb());
            assertEquals(stateType, address.getState());
            assertEquals(postCode, address.getPostcode());
        }
    }

    private void verifyInternationalAdderssDemographicSearch(SearchHIProviderDirectoryForIndividualResponse response,
                                                             String streetName,
                                                             String postCode,
                                                             StateType stateType) throws Exception {

        verifyMandatoryProviderDirectoryEntries(response);
        for (IndividualProviderDirectoryEntryType directoryEntryType : response.getSearchHIProviderDirectoryForIndividualResult().getIndividualProviderDirectoryEntries()) {
            verifyAustralianAddresstype(directoryEntryType);
            final AustralianAddressType address = directoryEntryType.getAddress().getAustralianAddress();
            assertEquals(streetName, address.getStreetName());
            assertEquals(stateType, address.getState());
            assertEquals(postCode, address.getPostcode());
        }
    }

    private void verifyAustralianAddresstype(IndividualProviderDirectoryEntryType directoryEntryType) throws Exception {
        assertNotNull(directoryEntryType);
        assertNotNull(directoryEntryType.getAddress());
        assertNotNull(directoryEntryType.getAddress().getAustralianAddress());
    }

    protected ProviderSearchHIProviderDirectoryForIndividualClient getMedicareTestClient()
            throws GeneralSecurityException, IOException {

        return new ProviderSearchHIProviderDirectoryForIndividualClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(), getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
    }
}
