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

import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.consumermessages.searchihi._3.SearchIHIResult;
import au.net.electronichealth.ns.hi.svc.consumersearchihi._3.SearchIHI;
import au.net.electronichealth.ns.hi.svc.consumersearchihi._3.SearchIHIResponse;
import au.net.electronichealth.ns.hi.svc.consumersearchihi._3.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._3.PostalDeliveryGroupType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.CountryType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.StateType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.StreetType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ServiceMessageType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianPostalAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianStreetAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.InternationalAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.consumercoredatatypes._3.IHIRecordStatusType;
import au.net.electronichealth.ns.hi.xsd.consumercore.consumercoredatatypes._3.IHIStatusType;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.*;

public class ConsumerSearchIHIClientTest {

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        ConsumerSearchIHIClient tc = getMedicareTestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getDrpTestClient();

        ReflectionTestUtils.setField(testClient, "loggingHandler", null);

        String lastSoapRequest = testClient.getLastSoapRequest();
        Assert.assertEquals(lastSoapRequest, LoggingHandler.EMPTY);

        String lastSoapResponse = testClient.getLastSoapResponse();
        Assert.assertEquals(lastSoapResponse, LoggingHandler.EMPTY);
    }

    // Basic Search Integration Tests
    @Test
    public void basicSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getBasicSearchForMedicare();
        SearchIHIResponse basicSearchResponse = testClient.basicSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(basicSearchResponse, searchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(basicSearchResponse, searchIHI);

    }

    @Test
    public void basicSearchAgainstMedicareForCSIRO() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getBasicSearchForMedicare();
        searchIHI.setFamilyName("ALSTON");
        searchIHI.setGivenName("GRANT");
        searchIHI.setSex(SexType.M);
        searchIHI.setIhiNumber("http://ns.electronichealth.net.au/id/hi/ihi/1.0/8003608666670997");

        searchIHI.setDateOfBirth(TimeUtility.getXMLGregorianDate("19760802"));
        SearchIHIResponse basicSearchResponse = testClient.basicSearch(searchIHI);
        verifySoapMessages(testClient);
        // verifyMandatoryResponseParameters( basicSearchResponse, searchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER );
        // verifyGivenName( basicSearchResponse, searchIHI );

    }

//    <ns6:ihiNumber>http://ns.electronichealth.net.au/id/hi/ihi/1.0/8003608666670997</ns6:ihiNumber>
//    <ns2:dateOfBirth>1976-08-02</ns2:dateOfBirth>
//    <ns2:sex>M</ns2:sex>
//    <ns7:familyName>ALSTON</ns7:familyName>
//    <ns7:givenName>GRANT</ns7:givenName>


    // defect found by vendor, cannot reuse client for a second call
    // this was to do with reusing the Holder<ProductType>
    @Test
    public void reusedSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getBasicSearchForMedicare();
        SearchIHIResponse basicSearchResponse = testClient.basicSearch(searchIHI);
        basicSearchResponse = testClient.basicSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(basicSearchResponse, searchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(basicSearchResponse, searchIHI);

    }

    // Basic Search Integration Tests
    @Test
    public void basicSearchAgainstMedicare_VicHealthTest() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = new SearchIHI();

        // <ns6:medicareCardNumber>2950190661</ns6:medicareCardNumber>
        // <ns3:dateOfBirth>1942-08-19</ns3:dateOfBirth>
        // <ns3:sex>F</ns3:sex>
        // <ns7:familyName>Schmidt</ns7:familyName>
        // <ns7:givenName>Helga</ns7:givenName>

        XMLGregorianCalendar cal = new XMLGregorianCalendarImpl();
        cal.setDay(19);
        cal.setMonth(8);
        cal.setYear(1942);

        searchIHI.setMedicareCardNumber("2950190661");
        searchIHI.setFamilyName("Schmidt");
        searchIHI.setGivenName("Helga");
        searchIHI.setDateOfBirth(cal);
        searchIHI.setSex(SexType.F);

        SearchIHIResponse basicSearchResponse = testClient.basicMedicareSearch(searchIHI);
        verifySoapMessages(testClient);
        // verifyMandatoryResponseParameters(basicSearchResponse, searchIHI,
        // MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(basicSearchResponse, searchIHI);

    }

    @Ignore
    public void basicSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getDrpTestClient();
        SearchIHI searchIHI = getBasicSearchForDRP();
        SearchIHIResponse basicSearchResponse = testClient.basicSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(basicSearchResponse, searchIHI, DRP_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(basicSearchResponse, searchIHI);
    }

    // Basic Medicare Search Integration Tests
    @Test
    public void basicMedicareSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getBasicMedicareSearchForMedicare();
        SearchIHIResponse basicMedicareSearchResponse = testClient.basicMedicareSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(basicMedicareSearchResponse, searchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(basicMedicareSearchResponse, searchIHI);
        verifyMedicareCardNumber(basicMedicareSearchResponse, searchIHI);
        verifyMedicareIRN(basicMedicareSearchResponse, searchIHI);
    }

    @Ignore
    public void basicMedicareSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getDrpTestClient();
        SearchIHI searchIHI = getBasicMedicareSearchForDRP();
        SearchIHIResponse basicMedicareSearchResponse = testClient.basicMedicareSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(basicMedicareSearchResponse, searchIHI, DRP_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(basicMedicareSearchResponse, searchIHI);
        verifyMedicareCardNumber(basicMedicareSearchResponse, searchIHI);
        verifyMedicareIRN(basicMedicareSearchResponse, searchIHI);
    }

    // Basic DVA File Number Integration Tests
    @Test
    public void basicDvaFileNumberSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getBasicDvaSearchForMedicare();
        SearchIHIResponse basicDvaFileNumberSearchResponse = testClient.basicDvaSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(basicDvaFileNumberSearchResponse, searchIHI, MEDICARE_DVA_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(basicDvaFileNumberSearchResponse, searchIHI);
        verifyDvaFileNumber(basicDvaFileNumberSearchResponse, searchIHI);
    }

    @Ignore
    public void basicDvaFileNumberSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getDrpTestClient();
        SearchIHI searchIHI = getBasicDvaSearchForDRP();
        SearchIHIResponse basicDvaFileNumberSearchResponse = testClient.basicDvaSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(basicDvaFileNumberSearchResponse, searchIHI, DRP_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(basicDvaFileNumberSearchResponse, searchIHI);
        verifyDvaFileNumber(basicDvaFileNumberSearchResponse, searchIHI);
    }

    // Detailed Search Integration Tests
    @Test
    public void detailedSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getDetailedSearchForMedicare();
        SearchIHIResponse detailedSearchResponse = testClient.detailedSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(detailedSearchResponse, searchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(detailedSearchResponse, searchIHI);
    }

    @Ignore
    public void detailedSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getDrpTestClient();
        SearchIHI searchIHI = getDetailedSearchForDRP();
        SearchIHIResponse detailedSearchResponse = testClient.detailedSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(detailedSearchResponse, searchIHI, DRP_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(detailedSearchResponse, searchIHI);
    }

    // Australian Postal Address Search Integration Tests
    @Test
    public void australianPostalAddressSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getAustralianPostalAddressSearchForMedicare();
        SearchIHIResponse addressSearchResponse = testClient.australianPostalAddressSearch(searchIHI);
        verifySoapMessages(testClient);

        // Unhappy as medicare do not currently have individuals with postal
        // addresses amongst their test data
        Assert.assertNull(addressSearchResponse.getSearchIHIResult().getIhiNumber());
    }

    @Ignore
    public void australianPostalAddressSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getDrpTestClient();
        SearchIHI searchIHI = getAustralianPostalAddressSearchForDRP();
        SearchIHIResponse addressSearchResponse = testClient.australianPostalAddressSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(addressSearchResponse, searchIHI, DRP_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(addressSearchResponse, searchIHI);
    }

    // Australian Street Address Search Integration Tests
    @Test
    public void australianStreetAddressSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getAustralianStreetAddressSearchForMedicare();
        SearchIHIResponse addressSearchResponse = testClient.australianStreetAddressSearch(searchIHI);

        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(addressSearchResponse, searchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(addressSearchResponse, searchIHI);
    }

    @Ignore
    public void australianStreetAddressSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getDrpTestClient();
        SearchIHI searchIHI = getAustralianStreetAddressSearchForDRP();
        SearchIHIResponse addressSearchResponse = testClient.australianStreetAddressSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(addressSearchResponse, searchIHI, DRP_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(addressSearchResponse, searchIHI);
    }

    // International Address Search Integration Tests
    @Test
    public void internationalAddressSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = getInternationalAddressSearchForMedicare();
        searchIHI.getInternationalAddress().setCountry(CountryType.fromValue("1101"));
        SearchIHIResponse addressSearchResponse = testClient.internationalAddressSearch(searchIHI);
        verifySoapMessages(testClient);

        // Unhappy as medicare do not currently have individuals with postal
        // addresses amongst their test data
        Assert.assertNull(addressSearchResponse.getSearchIHIResult().getIhiNumber());
    }

    // International Address Search Integration Tests
    @Test
    public void precedentHealthSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getMedicareTestClient();
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName("barefoot");
        searchIHI.setGivenName("jock");
        searchIHI.setDateOfBirth(TimeUtility.getXMLGregorianDate("19801108"));
        searchIHI.setSex(SexType.M);

        AustralianStreetAddressType address = new AustralianStreetAddressType();
        address.setStreetNumber("582");
        address.setStreetName("elise");
        address.setStreetType(StreetType.CCT);
        address.setSuburb("macquarie centre");
        address.setState(StateType.NSW);
        address.setPostcode("2113");
        searchIHI.setAustralianStreetAddress(address);


        try {
            SearchIHIResponse addressSearchResponse = testClient.australianStreetAddressSearch(searchIHI);
            verifySoapMessages(testClient);

            for (ServiceMessageType t : addressSearchResponse.getSearchIHIResult().getServiceMessages().getServiceMessage()) {
                System.out.println(t.getReason());
            }
            //addressSearchResponse.getSearchIHIResult().getGivenName().toString();
            //  Assert.assertNotNull( addressSearchResponse.getSearchIHIResult().getIhiNumber() );
        } catch (StandardErrorMsg e) {
            for (ServiceMessageType t : e.getFaultInfo().getServiceMessage()) {
                System.out.println(t.getReason());
            }
            e.printStackTrace();
        }

    }

    @Ignore
    public void internationalAddressSearchAgainstDRP() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIClient testClient = getDrpTestClient();
        SearchIHI searchIHI = getInternationalAddressSearchForDRP();
        SearchIHIResponse addressSearchResponse = testClient.internationalAddressSearch(searchIHI);
        verifySoapMessages(testClient);
        verifyMandatoryResponseParameters(addressSearchResponse, searchIHI, DRP_TEST_INDIVIDUAL_IHI_NUMBER);
        verifyGivenName(addressSearchResponse, searchIHI);
    }


    @Test
    public void wrappedClientSearchAgainstMedicare() throws Exception {
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

        ConsumerSearchIHIClient testClient = new ConsumerSearchIHIClient(
                MEDICARE_ENDPOINT_URL, productHeader,
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());


        //don't really care about the response so long as  no exceptions are thrown
        SearchIHI searchIHI = getBasicMedicareSearchForMedicare();
        testClient.basicMedicareSearch(searchIHI, userId);

        searchIHI = getInternationalAddressSearchForMedicare();
        testClient.internationalAddressSearch(searchIHI, userId);

        searchIHI = getAustralianStreetAddressSearchForMedicare();
        testClient.australianStreetAddressSearch(searchIHI, userId);

        searchIHI = getDetailedSearchForMedicare();
        testClient.detailedSearch(searchIHI, userId);
    }


    // Helper methods
    private ConsumerSearchIHIClient getMedicareTestClient() throws GeneralSecurityException, IOException {
        ConsumerSearchIHIClient testClient = new ConsumerSearchIHIClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(),
                getSigningPrivateKeyForMedicare(), getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ConsumerSearchIHIClient getDrpTestClient() throws GeneralSecurityException, IOException {
        ConsumerSearchIHIClient testClient = new ConsumerSearchIHIClient(DRP_IHI_SEARCH_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(),
                getSigningPrivateKeyForRefPlatform(), getSigningCertificateKeyForRefPlatform(), getSslSocketFactoryForRefPlatform());
        return testClient;
    }

    private SearchIHI getBasicSearchForMedicare() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        searchIHI.setFamilyName(MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX);

        return searchIHI;
    }

    private SearchIHI getBasicSearchForDRP() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setIhiNumber(DRP_TEST_INDIVIDUAL_IHI_NUMBER);
        searchIHI.setFamilyName(DRP_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(DRP_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(DRP_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(DRP_TEST_INDIVIDUAL_SEX);

        return searchIHI;
    }

    private SearchIHI getBasicMedicareSearchForMedicare() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setMedicareCardNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_MEDICARE_CARD_NUMBER);
        searchIHI.setMedicareIRN(MEDICARE_GENERIC_TEST_INDIVIDUAL_MEDICARE_IRN);
        searchIHI.setFamilyName(MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX);

        return searchIHI;
    }

    private SearchIHI getBasicMedicareSearchForDRP() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setMedicareCardNumber(DRP_TEST_INDIVIDUAL_MEDICARE_CARD_NUMBER);
        searchIHI.setMedicareIRN(DRP_TEST_INDIVIDUAL_MEDICARE_IRN);
        searchIHI.setFamilyName(DRP_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(DRP_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(DRP_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(DRP_TEST_INDIVIDUAL_SEX);

        return searchIHI;
    }

    private SearchIHI getBasicDvaSearchForMedicare() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setDvaFileNumber(MEDICARE_DVA_TEST_INDIVIDUAL_DVA_FILE_NUMBER);
        searchIHI.setFamilyName(MEDICARE_DVA_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_DVA_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_DVA_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_DVA_TEST_INDIVIDUAL_SEX);

        return searchIHI;
    }

    private SearchIHI getBasicDvaSearchForDRP() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setDvaFileNumber(DRP_TEST_INDIVIDUAL_DVA_FILE_NUMBER);
        searchIHI.setFamilyName(DRP_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(DRP_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(DRP_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(DRP_TEST_INDIVIDUAL_SEX);

        return searchIHI;
    }

    private SearchIHI getDetailedSearchForMedicare() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX);

        return searchIHI;
    }

    private SearchIHI getDetailedSearchForDRP() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(DRP_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(DRP_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(DRP_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(DRP_TEST_INDIVIDUAL_SEX);

        return searchIHI;
    }

    private SearchIHI getAustralianPostalAddressSearchForMedicare() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX);

        PostalDeliveryGroupType postalDeliveryGroup = new PostalDeliveryGroupType();
        postalDeliveryGroup.setPostalDeliveryNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_NUMBER);
        postalDeliveryGroup.setPostalDeliveryType(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_TYPE_CODE);
        AustralianPostalAddressType address = new AustralianPostalAddressType();
        address.setPostalDeliveryGroup(postalDeliveryGroup);
        address.setSuburb(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_SUBURB);
        address.setPostcode(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTCODE);
        address.setState(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_STATE);

        searchIHI.setAustralianPostalAddress(address);

        return searchIHI;
    }

    private SearchIHI getAustralianPostalAddressSearchForDRP() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(DRP_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(DRP_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(DRP_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(DRP_TEST_INDIVIDUAL_SEX);

        PostalDeliveryGroupType postalDeliveryGroup = new PostalDeliveryGroupType();
        postalDeliveryGroup.setPostalDeliveryNumber(DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_NUMBER);
        postalDeliveryGroup.setPostalDeliveryType(DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_TYPE_CODE);
        AustralianPostalAddressType address = new AustralianPostalAddressType();
        address.setPostalDeliveryGroup(postalDeliveryGroup);
        address.setSuburb(DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_SUBURB);
        address.setPostcode(DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTCODE);
        address.setState(DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_STATE);

        searchIHI.setAustralianPostalAddress(address);

        return searchIHI;
    }

    private SearchIHI getAustralianStreetAddressSearchForMedicare() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX);

        AustralianStreetAddressType address = new AustralianStreetAddressType();
        address.setStreetNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NUMBER);
        address.setStreetName(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NAME);
        address.setStreetType(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_TYPE);
        address.setSuburb(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_SUBURB);
        address.setState(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STATE);
        address.setPostcode(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_POST_CODE);
        searchIHI.setAustralianStreetAddress(address);

        return searchIHI;
    }

    private SearchIHI getAustralianStreetAddressSearchForDRP() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(DRP_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(DRP_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(DRP_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(DRP_TEST_INDIVIDUAL_SEX);

        AustralianStreetAddressType address = new AustralianStreetAddressType();
        address.setStreetNumber(DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NUMBER);
        address.setStreetName(DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NAME);
        address.setStreetType(DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_TYPE);
        address.setSuburb(DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_SUBURB);
        address.setState(DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STATE);
        address.setPostcode(DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_POST_CODE);
        searchIHI.setAustralianStreetAddress(address);

        return searchIHI;
    }

    private SearchIHI getInternationalAddressSearchForMedicare() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX);

        InternationalAddressType address = new InternationalAddressType();
        address.setInternationalAddressLine(MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_LINE);
        address.setInternationalStateProvince(MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_STATE_PROVINCE);
        address.setInternationalPostcode(MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_POSTCODE);
        address.setCountry(MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_COUNTRY);
        searchIHI.setInternationalAddress(address);

        return searchIHI;
    }

    private SearchIHI getInternationalAddressSearchForDRP() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(DRP_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(DRP_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(DRP_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(DRP_TEST_INDIVIDUAL_SEX);

        InternationalAddressType address = new InternationalAddressType();
        address.setInternationalAddressLine(DRP_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_LINE);
        address.setInternationalStateProvince(DRP_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_STATE_PROVINCE);
        address.setInternationalPostcode(DRP_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_POSTCODE);
        address.setCountry(DRP_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_COUNTRY);
        searchIHI.setInternationalAddress(address);

        return searchIHI;
    }

    private void verifyMandatoryResponseParameters(SearchIHIResponse basicSearchResponse, SearchIHI searchIHI, String IHI) {
        Assert.assertNotNull(basicSearchResponse);
        final SearchIHIResult basicSearchResult = basicSearchResponse.getSearchIHIResult();
        Assert.assertNotNull(basicSearchResult);

        final String ihiNumber = basicSearchResult.getIhiNumber();
        Assert.assertNotNull(ihiNumber);
        Assert.assertTrue(ihiNumber.length() > 0);
        Assert.assertEquals(ihiNumber, IHI);

        final IHIStatusType ihiStatus = basicSearchResult.getIhiStatus();
        Assert.assertNotNull(ihiStatus);
        Assert.assertSame(ihiStatus, IHIStatusType.ACTIVE);

        final IHIRecordStatusType ihiRecordStatus = basicSearchResult.getIhiRecordStatus();
        Assert.assertNotNull(ihiRecordStatus);
        Assert.assertSame(ihiRecordStatus, IHIRecordStatusType.VERIFIED);

        final String familyName = basicSearchResult.getFamilyName();
        Assert.assertNotNull(familyName);
        Assert.assertTrue(familyName.length() > 0);
        Assert.assertTrue(familyName.equalsIgnoreCase(searchIHI.getFamilyName()));

        final XMLGregorianCalendar dateOfBirth = basicSearchResult.getDateOfBirth();
        Assert.assertNotNull(dateOfBirth);
        Assert.assertTrue(dateOfBirth.isValid());
        Assert.assertEquals(dateOfBirth.getDay(), searchIHI.getDateOfBirth().getDay());
        Assert.assertEquals(dateOfBirth.getMonth(), searchIHI.getDateOfBirth().getMonth());
        Assert.assertEquals(dateOfBirth.getYear(), searchIHI.getDateOfBirth().getYear());
        final SexType sex = basicSearchResult.getSex();
        Assert.assertNotNull(sex);
        Assert.assertSame(sex, searchIHI.getSex());
    }

    private void verifySoapMessages(ConsumerSearchIHIClient testClient) {
        Assert.assertNotNull(testClient.getLastSoapRequest());
        Assert.assertNotNull(testClient.getLastSoapResponse());
        Assert.assertNotEquals("", testClient.getLastSoapRequest());
        Assert.assertNotEquals("", testClient.getLastSoapResponse());
    }

    private void verifyGivenName(SearchIHIResponse basicSearchResponse, SearchIHI searchIHI) {
        SearchIHIResult basicSearchResult = basicSearchResponse.getSearchIHIResult();
        final String givenName = basicSearchResult.getGivenName();
        Assert.assertNotNull(givenName);
        Assert.assertTrue(givenName.length() > 0);
        Assert.assertTrue(givenName.equalsIgnoreCase(searchIHI.getGivenName()));
    }

    private void verifyMedicareCardNumber(SearchIHIResponse basicSearchResponse, SearchIHI searchIHI) {
        SearchIHIResult searchResult = basicSearchResponse.getSearchIHIResult();
        String medicareCardNumber = searchResult.getMedicareCardNumber();
        Assert.assertNotNull(medicareCardNumber);
        Assert.assertEquals(medicareCardNumber, searchIHI.getMedicareCardNumber());
    }

    private void verifyMedicareIRN(SearchIHIResponse basicSearchResponse, SearchIHI searchIHI) {
        SearchIHIResult searchResult = basicSearchResponse.getSearchIHIResult();
        Integer medicareIRN = searchResult.getMedicareIRN();
        Assert.assertNotNull(medicareIRN);
        Assert.assertEquals(medicareIRN, searchIHI.getMedicareIRN());
    }

    private void verifyDvaFileNumber(SearchIHIResponse basicSearchResponse, SearchIHI searchIHI) {
        SearchIHIResult searchResult = basicSearchResponse.getSearchIHIResult();
        String dvaFileNumber = searchResult.getDvaFileNumber();
        Assert.assertNotNull(dvaFileNumber);
        Assert.assertEquals(dvaFileNumber, searchIHI.getDvaFileNumber());
    }
}
