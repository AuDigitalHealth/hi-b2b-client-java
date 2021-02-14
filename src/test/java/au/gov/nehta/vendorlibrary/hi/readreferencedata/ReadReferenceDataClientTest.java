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
package au.gov.nehta.vendorlibrary.hi.readreferencedata;

import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.providerreadreferencedata._3_2.ReadReferenceData;
import au.net.electronichealth.ns.hi.svc.providerreadreferencedata._3_2.ReadReferenceDataResponse;
import au.net.electronichealth.ns.hi.xsd.providermessages.readreferencedata._3_2.ElementReferenceValuesType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.setSystemVariablesForTest;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReadReferenceDataClientTest {

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        ReadReferenceDataClient tc = getMedicareTestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ReadReferenceDataClient testClient = getMedicareTestClient();

        ReflectionTestUtils.setField(testClient, "loggingHandler", null);

        String lastSoapRequest = testClient.getLastSoapRequest();
        assertEquals(lastSoapRequest, LoggingHandler.EMPTY);

        String lastSoapResponse = testClient.getLastSoapResponse();
        assertEquals(lastSoapResponse, LoggingHandler.EMPTY);
    }

    @Test
    public void readReferenceDataAgainstMedicare() throws Exception {
        setSystemVariablesForTest();
        ReadReferenceDataClient testClient = getMedicareTestClient();
        ReadReferenceData readReferenceData = new ReadReferenceData();
        readReferenceData.getElementNames().add("providerTypeCode");

        ReadReferenceDataResponse readReferenceDataResponse = testClient.readReferenceData(readReferenceData);

        verifyReadReferenceDataResponse(readReferenceDataResponse, readReferenceData);
        verifySoapMessages(testClient);

    }

    @Test
    public void readReferenceDataAgainstMedicareWithCsp() throws Exception {
        setSystemVariablesForTest();
        ReadReferenceDataClient testClient = getMedicareTestClientCsp();
        ReadReferenceData readReferenceData = new ReadReferenceData();
        readReferenceData.getElementNames().add("providerTypeCode");

        ReadReferenceDataResponse readReferenceDataResponse = testClient.readReferenceData(readReferenceData);

        verifyReadReferenceDataResponse(readReferenceDataResponse, readReferenceData);
        verifySoapMessages(testClient);

    }

    @Test
    public void testWrappedClientReadReferenceDataAgainstMedicare() throws Exception {
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

        ReadReferenceDataClient testClient = new ReadReferenceDataClient(MEDICARE_ENDPOINT_URL, productHeader,
                getSigningPrivateKeyForMedicare(), getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());


        ReadReferenceData readReferenceData = new ReadReferenceData();
        readReferenceData.getElementNames().add("providerTypeCode");

        ReadReferenceDataResponse readReferenceDataResponse = testClient.readReferenceData(readReferenceData, userId);

        verifyReadReferenceDataResponse(readReferenceDataResponse, readReferenceData);
        verifySoapMessages(testClient);

    }

    private ReadReferenceDataClient getMedicareTestClient() throws GeneralSecurityException, IOException {
        ReadReferenceDataClient testClient = new ReadReferenceDataClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(),
                getSigningPrivateKeyForMedicare(), getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ReadReferenceDataClient getMedicareTestClientCsp() throws GeneralSecurityException, IOException {
        ReadReferenceDataClient testClient = new ReadReferenceDataClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getCspHpioQualifiedId(), getProductHeader(),
                getCspSigningPrivateKeyForMedicare(), getCspSigningCertificateKeyForMedicare(), getSslSocketFactoryForCspMedicare(), null);
        return testClient;
    }

    // Invalid data / argument test cases
    @Test
    public void testNullReadReferenceData() throws Exception {
        boolean pass = false;
        ReadReferenceDataClient testClient = getMedicareTestClient();

        try {
            testClient.readReferenceData(null);
        } catch (IllegalArgumentException ex) {
            assertEquals("readReferenceData cannot be null.", ex.getMessage());
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testNullElementNameList() throws Exception {
        boolean pass = false;
        ReadReferenceDataClient testClient = getMedicareTestClient();

        try {
            testClient.readReferenceData(new ReadReferenceData());
        } catch (IllegalArgumentException ex) {
            assertEquals("The element names list must contain one or more elements", ex.getMessage());
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testBlankElementName() throws Exception {
        boolean pass = false;
        ReadReferenceDataClient testClient = getMedicareTestClient();

        ReadReferenceData readReferenceData = new ReadReferenceData();
        readReferenceData.getElementNames().add(""); // Blank element name
        try {
            testClient.readReferenceData(readReferenceData);
        } catch (IllegalArgumentException ex) {
            assertEquals("elementName cannot be a blank string.", ex.getMessage());
            pass = true;
        }
        assertTrue(pass);
    }

    // verify methods
    private void verifyReadReferenceDataResponse(ReadReferenceDataResponse readReferenceDataResponse, ReadReferenceData readReferenceData) {
        Assert.assertNotNull(readReferenceDataResponse);
        Assert.assertNotNull(readReferenceDataResponse.getReadReferenceDataResult());

        List<ElementReferenceValuesType> referenceElementValues = readReferenceDataResponse.getReadReferenceDataResult().getElementReferenceValues();

        Assert.assertNotNull(referenceElementValues);
        Assert.assertTrue(referenceElementValues.size() > 0);
        for (int i = 0; i < referenceElementValues.size(); i++) {
            Assert.assertNotNull(referenceElementValues.get(i));
            Assert.assertEquals(readReferenceData.getElementNames().get(i), referenceElementValues.get(i).getElementName());
            Assert.assertTrue(referenceElementValues.get(i).getReferenceSet().size() > 0);
        }
    }

    private void verifySoapMessages(ReadReferenceDataClient testClient) {
        Assert.assertNotNull(testClient.getLastSoapRequest());
        Assert.assertNotNull(testClient.getLastSoapResponse());
        Assert.assertNotEquals("", testClient.getLastSoapRequest());
        Assert.assertNotEquals("", testClient.getLastSoapResponse());
    }
}
