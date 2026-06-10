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
package au.gov.nehta.vendorlibrary.hi.ihi;

import au.gov.nehta.vendorlibrary.hi.test.utils.TestReflect;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.DeleteSearchIHIBatch;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.DeleteSearchIHIBatchResponse;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.GetSearchIHIBatchStatus;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.GetSearchIHIBatchStatusResponse;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.RetrieveSearchIHIBatch;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.RetrieveSearchIHIBatchResponse;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.StandardErrorMsg;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.SubmitSearchIHIBatch;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.SubmitSearchIHIBatchResponse;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SeverityType;
import au.net.electronichealth.ns.hi.xsd.consumermessages.searchihi._3.SearchIHI;
import au.net.electronichealth.ns.hi.xsd.consumermessages.searchihibatch._3.BatchResponseStatusType;
import au.net.electronichealth.ns.hi.xsd.consumermessages.searchihibatch._3.SearchIHIRequestType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.UUID;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.setSystemVariablesForTest;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getProductHeader;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningCertificateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningPrivateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSslSocketFactoryForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getUserQualifiedId;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getWrappedProductHeader;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getWrappedUserQualifiedId;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.MEDICARE_ENDPOINT_URL;

public class ConsumerSearchIHIBatchAsyncClientTest {

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        ConsumerSearchIHIBatchAsyncClient tc = getMedicareTestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIBatchAsyncClient testClient = getMedicareTestClient();

        TestReflect.setField(testClient, "loggingHandler", null);

        String lastSoapRequest = testClient.getLastSoapRequest();
        Assert.assertEquals(lastSoapRequest, LoggingHandler.EMPTY);

        String lastSoapResponse = testClient.getLastSoapResponse();
        Assert.assertEquals(lastSoapResponse, LoggingHandler.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitSearchIHIBatch_nullRequest_throws() throws Exception {
        getMedicareTestClient().submitSearchIHIBatch(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveSearchIHIBatch_nullRequest_throws() throws Exception {
        getMedicareTestClient().retrieveSearchIHIBatch(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSearchIHIBatchStatus_nullRequest_throws() throws Exception {
        getMedicareTestClient().getSearchIHIBatchStatus(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteSearchIHIBatch_nullRequest_throws() throws Exception {
        getMedicareTestClient().deleteSearchIHIBatch(null);
    }

    @Test
    public void asyncBatchMedicare_roundTrip_clientUser() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIBatchAsyncClient testClient = getMedicareTestClient();
        String batchId = submitOneBasicSearch(testClient, null);
        waitForBatchCompleted(testClient, batchId, null);

        RetrieveSearchIHIBatch retrieveRequest = new RetrieveSearchIHIBatch();
        retrieveRequest.setBatchIdentifier(batchId);
        RetrieveSearchIHIBatchResponse retrieveResponse = testClient.retrieveSearchIHIBatch(retrieveRequest);
        verifySoapMessages(testClient);
        Assert.assertEquals(batchId, retrieveResponse.getBatchIdentifier());
        Assert.assertEquals(1, retrieveResponse.getSearchIHIBatchResult().size());
        Assert.assertEquals(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER,
                retrieveResponse.getSearchIHIBatchResult().get(0).getSearchIHIResult().getIhiNumber());

        GetSearchIHIBatchStatus statusRequest = new GetSearchIHIBatchStatus();
        statusRequest.setBatchIdentifier(batchId);
        GetSearchIHIBatchStatusResponse statusResponse = testClient.getSearchIHIBatchStatus(statusRequest);
        Assert.assertNotNull(statusResponse.getGetSearchIHIBatchStatusResult());
        Assert.assertEquals(batchId, statusResponse.getGetSearchIHIBatchStatusResult().getBatchIdentifier());

        DeleteSearchIHIBatch deleteRequest = new DeleteSearchIHIBatch();
        deleteRequest.setBatchIdentifier(batchId);
        DeleteSearchIHIBatchResponse deleteResponse = testClient.deleteSearchIHIBatch(deleteRequest);
        Assert.assertEquals(BatchResponseStatusType.DELETED, deleteResponse.getDeleteSearchIHIBatchResult().getStatus());
    }

    @Test
    public void asyncBatchMedicare_roundTrip_perRequestUser() throws Exception {
        setSystemVariablesForTest();
        ConsumerSearchIHIBatchAsyncClient testClient = getMedicareWrappedClient();
        au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId userId = getWrappedUserQualifiedId();
        String batchId = submitOneBasicSearch(testClient, userId);
        waitForBatchCompleted(testClient, batchId, userId);

        RetrieveSearchIHIBatch retrieveRequest = new RetrieveSearchIHIBatch();
        retrieveRequest.setBatchIdentifier(batchId);
        RetrieveSearchIHIBatchResponse retrieveResponse = testClient.retrieveSearchIHIBatch(retrieveRequest, userId);
        verifySoapMessages(testClient);
        Assert.assertEquals(batchId, retrieveResponse.getBatchIdentifier());
        Assert.assertEquals(1, retrieveResponse.getSearchIHIBatchResult().size());
        Assert.assertEquals(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER,
                retrieveResponse.getSearchIHIBatchResult().get(0).getSearchIHIResult().getIhiNumber());

        GetSearchIHIBatchStatus statusRequest = new GetSearchIHIBatchStatus();
        statusRequest.setBatchIdentifier(batchId);
        GetSearchIHIBatchStatusResponse statusResponse = testClient.getSearchIHIBatchStatus(statusRequest, userId);
        Assert.assertNotNull(statusResponse.getGetSearchIHIBatchStatusResult());

        DeleteSearchIHIBatch deleteRequest = new DeleteSearchIHIBatch();
        deleteRequest.setBatchIdentifier(batchId);
        DeleteSearchIHIBatchResponse deleteResponse = testClient.deleteSearchIHIBatch(deleteRequest, userId);
        Assert.assertEquals(BatchResponseStatusType.DELETED, deleteResponse.getDeleteSearchIHIBatchResult().getStatus());
    }

    private String submitOneBasicSearch(ConsumerSearchIHIBatchAsyncClient client,
                                       au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId perRequestUser) throws StandardErrorMsg {
        SearchBatch batch = buildSingleBasicSearchBatch();
        SubmitSearchIHIBatch submit = new SubmitSearchIHIBatch();
        submit.getSearchIHIBatchRequest().addAll(new ArrayList<>(batch.getBatch().getSearchIHIBatchRequest()));
        SubmitSearchIHIBatchResponse submitResponse = perRequestUser == null
                ? client.submitSearchIHIBatch(submit)
                : client.submitSearchIHIBatch(submit, perRequestUser);
        Assert.assertEquals(SeverityType.INFORMATIONAL, submitResponse.getServiceMessages().getHighestSeverity());
        Assert.assertNotNull(submitResponse.getBatchIdentifier());
        return submitResponse.getBatchIdentifier();
    }

    private void waitForBatchCompleted(ConsumerSearchIHIBatchAsyncClient client, String batchId,
                                      au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId perRequestUser) throws Exception {
        GetSearchIHIBatchStatus statusReq = new GetSearchIHIBatchStatus();
        statusReq.setBatchIdentifier(batchId);
        for (int i = 0; i < 90; i++) {
            GetSearchIHIBatchStatusResponse statusRes = perRequestUser == null
                    ? client.getSearchIHIBatchStatus(statusReq)
                    : client.getSearchIHIBatchStatus(statusReq, perRequestUser);
            BatchResponseStatusType s = statusRes.getGetSearchIHIBatchStatusResult().getStatus();
            if (s == BatchResponseStatusType.COMPLETED) {
                return;
            }
            if (s == BatchResponseStatusType.ERROR) {
                Assert.fail("batch error status for " + batchId);
            }
            Thread.sleep(1000L);
        }
        Assert.fail("batch did not reach COMPLETED: " + batchId);
    }

    private SearchBatch buildSingleBasicSearchBatch() {
        SearchBatch searchBatch = new SearchBatch();
        SearchIHI basicSearchIHI = new SearchIHI();
        basicSearchIHI.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        basicSearchIHI.setFamilyName(MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME);
        basicSearchIHI.setGivenName(MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME);
        basicSearchIHI.setDateOfBirth(MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        basicSearchIHI.setSex(MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX);
        SearchIHIRequestType basicSearchIhiRequestType = new SearchIHIRequestType();
        basicSearchIhiRequestType.setSearchIHI(basicSearchIHI);
        basicSearchIhiRequestType.setRequestIdentifier(UUID.randomUUID().toString());
        searchBatch.addBasicSearch(basicSearchIhiRequestType);
        return searchBatch;
    }

    private void verifySoapMessages(ConsumerSearchIHIBatchAsyncClient testClient) {
        Assert.assertNotNull(testClient.getLastSoapRequest());
        Assert.assertNotNull(testClient.getLastSoapResponse());
        Assert.assertNotEquals("", testClient.getLastSoapRequest());
        Assert.assertNotEquals("", testClient.getLastSoapResponse());
    }

    private ConsumerSearchIHIBatchAsyncClient getMedicareTestClient() throws GeneralSecurityException, IOException {
        return new ConsumerSearchIHIBatchAsyncClient(
                MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
    }

    private ConsumerSearchIHIBatchAsyncClient getMedicareWrappedClient() throws GeneralSecurityException, IOException {
        return new ConsumerSearchIHIBatchAsyncClient(
                MEDICARE_ENDPOINT_URL,
                getWrappedProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
    }
}
