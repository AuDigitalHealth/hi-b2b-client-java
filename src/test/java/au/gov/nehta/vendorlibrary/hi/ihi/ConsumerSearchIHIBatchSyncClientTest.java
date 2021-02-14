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
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.consumermessages.searchihi._3.SearchIHI;
import au.net.electronichealth.ns.hi.consumermessages.searchihi._3.SearchIHIResult;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchsyncrequest._3.SearchIHIBatchResponse;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._3.PostalDeliveryGroupType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianPostalAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianStreetAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.InternationalAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.consumercoredatatypes._3.IHIRecordStatusType;
import au.net.electronichealth.ns.hi.xsd.consumercore.consumercoredatatypes._3.IHIStatusType;
import au.net.electronichealth.ns.hi.xsd.consumermessages.searchihibatch._3.SearchIHIRequestType;
import au.net.electronichealth.ns.hi.xsd.consumermessages.searchihibatch._3.SearchIHIResultType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.*;

public class ConsumerSearchIHIBatchSyncClientTest {

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        ConsumerSearchIHIBatchSyncClient tc = getMedicareTestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIBatchSyncClient testClient = getMedicareTestClient();

        ReflectionTestUtils.setField(testClient, "loggingHandler", null);

        String lastSoapRequest = testClient.getLastSoapRequest();
        Assert.assertEquals(lastSoapRequest, LoggingHandler.EMPTY);

        String lastSoapResponse = testClient.getLastSoapResponse();
        Assert.assertEquals(lastSoapResponse, LoggingHandler.EMPTY);
    }

    @Test
    public void batchSearchAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIBatchSyncClient testClient = getMedicareTestClient();
        SearchBatch searchBatch = new SearchBatch();

        // Add Basic Search
        SearchIHI basicSearchIHI = getBasicSearchForMedicare();
        String basicSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType basicSearchIhiRequestType = getSearchIhiRequestType(basicSearchIHI, basicSearchRequestIdentifier);
        searchBatch.addBasicSearch(basicSearchIhiRequestType);

        // Add Basic Medicare Search
        SearchIHI basicMedicareSearchIHI = getBasicMedicareSearchForMedicare();
        String basicMedicareSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType basicMedicareSearchIhiRequestType = getSearchIhiRequestType(basicMedicareSearchIHI, basicMedicareSearchRequestIdentifier);
        searchBatch.addBasicMedicareSearch(basicMedicareSearchIhiRequestType);

        // Add Basic DVA Search
        SearchIHI basicDvaSearchIHI = getBasicDvaSearchForMedicare();
        String basicDvaSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType basicDvaSearchIhiRequestType = getSearchIhiRequestType(basicDvaSearchIHI, basicDvaSearchRequestIdentifier);
        searchBatch.addBasicDvaSearch(basicDvaSearchIhiRequestType);

        // Add Detailed Search
        SearchIHI detailedSearchIHI = getDetailedSearchForMedicare();
        String detailedSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType detailedSearchIhiRequestType = getSearchIhiRequestType(detailedSearchIHI, detailedSearchRequestIdentifier);
        searchBatch.addDetailedSearch(detailedSearchIhiRequestType);

        // Add Australian Postal Address Search
        SearchIHI australianPostalAddressSearchIHI = getAustralianPostalAddressSearchForMedicare();
        String australianPostalAddressSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType australianPostalAddressSearchIhiRequestType = getSearchIhiRequestType(australianPostalAddressSearchIHI, australianPostalAddressSearchRequestIdentifier);
        searchBatch.addAustralianPostalAddressSearch(australianPostalAddressSearchIhiRequestType);

        // Add Australian Street Address Search
        SearchIHI australianStreetAddressSearchIHI = getAustralianStreetAddressSearchForMedicare();
        String australianStreetAddressSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType australianStreetAddressSearchIhiRequestType = getSearchIhiRequestType(australianStreetAddressSearchIHI, australianStreetAddressSearchRequestIdentifier);
        searchBatch.addAustralianStreetAddressSearch(australianStreetAddressSearchIhiRequestType);

        // Add International Address Search
        SearchIHI internationalAddressSearchIHI = getInternationalAddressSearchForMedicare();
        String internationalAddressSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType internationalAddressSearchIhiRequestType = getSearchIhiRequestType(internationalAddressSearchIHI, internationalAddressSearchRequestIdentifier);
        searchBatch.addInternationalAddressSearch(internationalAddressSearchIhiRequestType);

        // Execute batch search
        SearchIHIBatchResponse batchSearchResponse = testClient.batchSearch(searchBatch);

        verifySoapMessages(testClient);
        List<SearchIHIResultType> responseItems = batchSearchResponse.getSearchIHIBatchResult();
        Assert.assertNotNull(responseItems);
        Assert.assertTrue(responseItems.size() > 0);

        for (SearchIHIResultType responseItem : responseItems) {
            Assert.assertNotNull(responseItem.getRequestIdentifier());

            // Verify Basic Search
            if (responseItem.getRequestIdentifier().equals(basicSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, basicSearchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, basicSearchIHI);
            }

            // Verify Basic Medicare Search
            if (responseItem.getRequestIdentifier().equals(basicMedicareSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, basicMedicareSearchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, basicMedicareSearchIHI);
                verifyMedicareCardNumber(responseItem, basicMedicareSearchIHI);
                verifyMedicareIRN(responseItem, basicMedicareSearchIHI);
            }

            // Verify Basic DVA Search
            if (responseItem.getRequestIdentifier().equals(basicDvaSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, basicDvaSearchIHI, MEDICARE_DVA_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, basicDvaSearchIHI);
                verifyDvaFileNumber(responseItem, basicDvaSearchIHI);
            }

            // Verify Detailed Search
            if (responseItem.getRequestIdentifier().equals(detailedSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, detailedSearchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, detailedSearchIHI);
            }

            // Verify Australian Postal Address Search
            if (responseItem.getRequestIdentifier().equals(australianPostalAddressSearchRequestIdentifier)) {
                // Unhappy as medicare do not currently have individuals with
                // postal addresses amongst their test data
                Assert.assertNull(responseItem.getSearchIHIResult().getIhiNumber());
            }

            // Verify Australian Street Address Search
            if (responseItem.getRequestIdentifier().equals(australianStreetAddressSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, australianStreetAddressSearchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, australianStreetAddressSearchIHI);
            }

            // Verify International Address Search
            if (responseItem.getRequestIdentifier().equals(internationalAddressSearchRequestIdentifier)) {
                // Unhappy as medicare do not currently have individuals with
                // international addresses amongst their test data
                Assert.assertNull(responseItem.getSearchIHIResult().getIhiNumber());
            }
        }
    }

    @Test
    public void wrappedClientbatchSearchAgainstMedicare() throws Exception {
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


        ConsumerSearchIHIBatchSyncClient testClient =
                new ConsumerSearchIHIBatchSyncClient(
                        MEDICARE_ENDPOINT_URL,
                        productHeader,
                        getSigningPrivateKeyForMedicare(),
                        getSigningCertificateKeyForMedicare(),
                        getSslSocketFactoryForMedicare());

        SearchBatch searchBatch = new SearchBatch();

        // Add Basic Search
        SearchIHI basicSearchIHI = getBasicSearchForMedicare();
        String basicSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType basicSearchIhiRequestType = getSearchIhiRequestType(basicSearchIHI, basicSearchRequestIdentifier);
        searchBatch.addBasicSearch(basicSearchIhiRequestType);

        // Add Basic Medicare Search
        SearchIHI basicMedicareSearchIHI = getBasicMedicareSearchForMedicare();
        String basicMedicareSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType basicMedicareSearchIhiRequestType = getSearchIhiRequestType(basicMedicareSearchIHI, basicMedicareSearchRequestIdentifier);
        searchBatch.addBasicMedicareSearch(basicMedicareSearchIhiRequestType);

        // Add Basic DVA Search
        SearchIHI basicDvaSearchIHI = getBasicDvaSearchForMedicare();
        String basicDvaSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType basicDvaSearchIhiRequestType = getSearchIhiRequestType(basicDvaSearchIHI, basicDvaSearchRequestIdentifier);
        searchBatch.addBasicDvaSearch(basicDvaSearchIhiRequestType);

        // Add Detailed Search
        SearchIHI detailedSearchIHI = getDetailedSearchForMedicare();
        String detailedSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType detailedSearchIhiRequestType = getSearchIhiRequestType(detailedSearchIHI, detailedSearchRequestIdentifier);
        searchBatch.addDetailedSearch(detailedSearchIhiRequestType);

        // Add Australian Postal Address Search
        SearchIHI australianPostalAddressSearchIHI = getAustralianPostalAddressSearchForMedicare();
        String australianPostalAddressSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType australianPostalAddressSearchIhiRequestType = getSearchIhiRequestType(australianPostalAddressSearchIHI, australianPostalAddressSearchRequestIdentifier);
        searchBatch.addAustralianPostalAddressSearch(australianPostalAddressSearchIhiRequestType);

        // Add Australian Street Address Search
        SearchIHI australianStreetAddressSearchIHI = getAustralianStreetAddressSearchForMedicare();
        String australianStreetAddressSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType australianStreetAddressSearchIhiRequestType = getSearchIhiRequestType(australianStreetAddressSearchIHI, australianStreetAddressSearchRequestIdentifier);
        searchBatch.addAustralianStreetAddressSearch(australianStreetAddressSearchIhiRequestType);

        // Add International Address Search
        SearchIHI internationalAddressSearchIHI = getInternationalAddressSearchForMedicare();
        String internationalAddressSearchRequestIdentifier = UUID.randomUUID().toString();
        SearchIHIRequestType internationalAddressSearchIhiRequestType = getSearchIhiRequestType(internationalAddressSearchIHI, internationalAddressSearchRequestIdentifier);
        searchBatch.addInternationalAddressSearch(internationalAddressSearchIhiRequestType);

        // Execute batch search
        SearchIHIBatchResponse batchSearchResponse = testClient.batchSearch(searchBatch, userId);

        verifySoapMessages(testClient);
        List<SearchIHIResultType> responseItems = batchSearchResponse.getSearchIHIBatchResult();
        Assert.assertNotNull(responseItems);
        Assert.assertTrue(responseItems.size() > 0);

        for (SearchIHIResultType responseItem : responseItems) {
            Assert.assertNotNull(responseItem.getRequestIdentifier());

            // Verify Basic Search
            if (responseItem.getRequestIdentifier().equals(basicSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, basicSearchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, basicSearchIHI);
            }

            // Verify Basic Medicare Search
            if (responseItem.getRequestIdentifier().equals(basicMedicareSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, basicMedicareSearchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, basicMedicareSearchIHI);
                verifyMedicareCardNumber(responseItem, basicMedicareSearchIHI);
                verifyMedicareIRN(responseItem, basicMedicareSearchIHI);
            }

            // Verify Basic DVA Search
            if (responseItem.getRequestIdentifier().equals(basicDvaSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, basicDvaSearchIHI, MEDICARE_DVA_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, basicDvaSearchIHI);
                verifyDvaFileNumber(responseItem, basicDvaSearchIHI);
            }

            // Verify Detailed Search
            if (responseItem.getRequestIdentifier().equals(detailedSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, detailedSearchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, detailedSearchIHI);
            }

            // Verify Australian Postal Address Search
            if (responseItem.getRequestIdentifier().equals(australianPostalAddressSearchRequestIdentifier)) {
                // Unhappy as medicare do not currently have individuals with
                // postal addresses amongst their test data
                Assert.assertNull(responseItem.getSearchIHIResult().getIhiNumber());
            }

            // Verify Australian Street Address Search
            if (responseItem.getRequestIdentifier().equals(australianStreetAddressSearchRequestIdentifier)) {
                verifyMandatoryResponseParameters(responseItem, australianStreetAddressSearchIHI, MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
                verifyGivenName(responseItem, australianStreetAddressSearchIHI);
            }

            // Verify International Address Search
            if (responseItem.getRequestIdentifier().equals(internationalAddressSearchRequestIdentifier)) {
                // Unhappy as medicare do not currently have individuals with
                // international addresses amongst their test data
                Assert.assertNull(responseItem.getSearchIHIResult().getIhiNumber());
            }
        }
    }

    private SearchIHIRequestType getSearchIhiRequestType(SearchIHI searchIHI, String searchRequestIdentifier) {
        SearchIHIRequestType searchIhiRequestType = new SearchIHIRequestType();
        searchIhiRequestType.setSearchIHI(searchIHI);
        searchIhiRequestType.setRequestIdentifier(searchRequestIdentifier);
        return searchIhiRequestType;
    }

    // Helper Methods
    private ConsumerSearchIHIBatchSyncClient getMedicareTestClient() throws GeneralSecurityException, IOException {
        return new ConsumerSearchIHIBatchSyncClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(), getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
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

    private SearchIHI getBasicDvaSearchForMedicare() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setDvaFileNumber(MEDICARE_DVA_TEST_INDIVIDUAL_DVA_FILE_NUMBER);
        searchIHI.setFamilyName(MEDICARE_DVA_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_DVA_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_DVA_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_DVA_TEST_INDIVIDUAL_SEX);

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

    private void verifyMandatoryResponseParameters(SearchIHIResultType searchResponse, SearchIHI searchIHI, String IHI) {
        Assert.assertNotNull(searchResponse);
        final SearchIHIResult searchResult = searchResponse.getSearchIHIResult();
        Assert.assertNotNull(searchResult);

        final String ihiNumber = searchResult.getIhiNumber();
        Assert.assertNotNull(ihiNumber);
        Assert.assertTrue(ihiNumber.length() > 0);
        Assert.assertEquals(ihiNumber, IHI);

        final IHIStatusType ihiStatus = searchResult.getIhiStatus();
        Assert.assertNotNull(ihiStatus);
        Assert.assertSame(ihiStatus, IHIStatusType.ACTIVE);

        final IHIRecordStatusType ihiRecordStatus = searchResult.getIhiRecordStatus();
        Assert.assertNotNull(ihiRecordStatus);
        Assert.assertSame(ihiRecordStatus, IHIRecordStatusType.VERIFIED);

        final String familyName = searchResult.getFamilyName();
        Assert.assertNotNull(familyName);
        Assert.assertTrue(familyName.length() > 0);
        Assert.assertTrue(familyName.equalsIgnoreCase(searchIHI.getFamilyName()));

        final XMLGregorianCalendar dateOfBirth = searchResult.getDateOfBirth();
        Assert.assertNotNull(dateOfBirth);
        Assert.assertTrue(dateOfBirth.isValid());
        Assert.assertEquals(dateOfBirth.getDay(), searchIHI.getDateOfBirth().getDay());
        Assert.assertEquals(dateOfBirth.getMonth(), searchIHI.getDateOfBirth().getMonth());
        Assert.assertEquals(dateOfBirth.getYear(), searchIHI.getDateOfBirth().getYear());
        final SexType sex = searchResult.getSex();
        Assert.assertNotNull(sex);
        Assert.assertSame(sex, searchIHI.getSex());
    }

    private void verifySoapMessages(ConsumerSearchIHIBatchSyncClient testClient) {
        Assert.assertNotNull(testClient.getLastSoapRequest());
        Assert.assertNotNull(testClient.getLastSoapResponse());
        Assert.assertNotEquals("", testClient.getLastSoapRequest());
        Assert.assertNotEquals("", testClient.getLastSoapResponse());
    }

    private void verifyGivenName(SearchIHIResultType searchResponse, SearchIHI searchIHI) {
        SearchIHIResult basicSearchResult = searchResponse.getSearchIHIResult();
        final String givenName = basicSearchResult.getGivenName();
        Assert.assertNotNull(givenName);
        Assert.assertTrue(givenName.length() > 0);
        Assert.assertTrue(givenName.equalsIgnoreCase(searchIHI.getGivenName()));
    }

    private void verifyMedicareCardNumber(SearchIHIResultType searchResponse, SearchIHI searchIHI) {
        SearchIHIResult searchResult = searchResponse.getSearchIHIResult();
        String medicareCardNumber = searchResult.getMedicareCardNumber();
        Assert.assertNotNull(medicareCardNumber);
        Assert.assertEquals(medicareCardNumber, searchIHI.getMedicareCardNumber());
    }

    private void verifyMedicareIRN(SearchIHIResultType searchResponse, SearchIHI searchIHI) {
        SearchIHIResult searchResult = searchResponse.getSearchIHIResult();
        Integer medicareIRN = searchResult.getMedicareIRN();
        Assert.assertNotNull(medicareIRN);
        Assert.assertEquals(medicareIRN, searchIHI.getMedicareIRN());
    }

    private void verifyDvaFileNumber(SearchIHIResultType searchResponse, SearchIHI searchIHI) {
        SearchIHIResult searchResult = searchResponse.getSearchIHIResult();
        String dvaFileNumber = searchResult.getDvaFileNumber();
        Assert.assertNotNull(dvaFileNumber);
        Assert.assertEquals(dvaFileNumber, searchIHI.getDvaFileNumber());
    }
}
