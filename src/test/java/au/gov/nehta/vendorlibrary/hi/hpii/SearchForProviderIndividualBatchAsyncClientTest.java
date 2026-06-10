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
package au.gov.nehta.vendorlibrary.hi.hpii;

import au.gov.nehta.vendorlibrary.hi.test.utils.TestReflect;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderindividual._5_1.RetrieveSearchForProviderIndividual;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderindividual._5_1.RetrieveSearchForProviderIndividualResponse;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderindividual._5_1.StandardErrorMsg;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderindividual._5_1.SubmitSearchForProviderIndividualResponse;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SeverityType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import au.net.electronichealth.ns.hi.xsd.providercore.providertype._5_1.BatchSearchForProviderIndividualCriteriaType;
import au.net.electronichealth.ns.hi.xsd.providercore.providertype._5_1.BatchSearchForProviderIndividualResultType;
import au.net.electronichealth.ns.hi.xsd.providermessages.searchindividual._5_0.SearchForProviderIndividual;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_FAMILY_NAME;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_HPII;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.setSystemVariablesForTest;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.*;

public class SearchForProviderIndividualBatchAsyncClientTest {

    private final SearchForProviderIndividualBatchAsyncClient client = getMedicareTestClient();
    private final SearchForProviderIndividualBatchAsyncClient clientNoID = getMedicareTestClientNoId();

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        SearchForProviderIndividualBatchAsyncClient tc = getMedicareTestClient();
        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        SearchForProviderIndividualBatchAsyncClient testClient = getMedicareTestClient();
        TestReflect.setField(testClient, "loggingHandler", null);
        Assert.assertEquals(LoggingHandler.EMPTY, testClient.getLastSoapRequest());
        Assert.assertEquals(LoggingHandler.EMPTY, testClient.getLastSoapResponse());
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitSearch_nullBatchRequest_clientUser() throws StandardErrorMsg {
        client.submitSearch(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitSearch_nullBatchRequest_perRequestUser() throws StandardErrorMsg {
        clientNoID.submitSearch(null, getWrappedUserQualifiedId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitSearch_nullIndividualId_throws() throws StandardErrorMsg {
        ProviderIndividualBatchSearch batch = new ProviderIndividualBatchSearch();
        clientNoID.submitSearch(batch, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveSearch_nullRequest_clientUser() throws StandardErrorMsg {
        client.retrieveSearch(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveSearch_nullRequest_perRequestUser() throws StandardErrorMsg {
        clientNoID.retrieveSearch(null, getWrappedUserQualifiedId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveSearch_nullIndividualId_throws() throws StandardErrorMsg {
        RetrieveSearchForProviderIndividual retrieve = new RetrieveSearchForProviderIndividual();
        retrieve.setBatchIdentifier("dummy");
        clientNoID.retrieveSearch(retrieve, null);
    }

    @Test
    public void submitSearchCheck_nullSubmitRequestThrows() {
        try {
            client.submitSearchCheck(null);
            Assert.fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("Request"));
        }
    }

    @Test
    public void retrieveSearchCheck_nullRetrieveRequestThrows() {
        try {
            client.retrieveSearchCheck(null);
            Assert.fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("Request"));
        }
    }

    @Test
    public void testIdentifierSearchSingle() throws StandardErrorMsg {
        ProviderIndividualBatchSearch batch = new ProviderIndividualBatchSearch();
        BatchSearchForProviderIndividualCriteriaType batchIdSearch = new BatchSearchForProviderIndividualCriteriaType();

        batchIdSearch.setRequestIdentifier(UUID.randomUUID().toString());
        SearchForProviderIndividual idSearch = getGenericIDSearch();
        batchIdSearch.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch);

        SubmitSearchForProviderIndividualResponse response = client.submitSearch(batch);
        Assert.assertEquals(response.getSubmitSearchForProviderIndividualResult().getServiceMessages().getHighestSeverity(), SeverityType.INFORMATIONAL);
    }

    @Test
    public void testIdentifierSearchSingleWithSuppliedUserID() throws StandardErrorMsg {
        ProviderIndividualBatchSearch batch = new ProviderIndividualBatchSearch();
        BatchSearchForProviderIndividualCriteriaType batchIdSearch = new BatchSearchForProviderIndividualCriteriaType();

        batchIdSearch.setRequestIdentifier(UUID.randomUUID().toString());
        SearchForProviderIndividual idSearch = getGenericIDSearch();
        batchIdSearch.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch);

        SubmitSearchForProviderIndividualResponse response = clientNoID.submitSearch(batch, getWrappedUserQualifiedId());
        Assert.assertEquals(response.getSubmitSearchForProviderIndividualResult().getServiceMessages().getHighestSeverity(), SeverityType.INFORMATIONAL);
    }

    @Test
    public void testIdentifierSearchMulti() throws StandardErrorMsg {
        ProviderIndividualBatchSearch batch = new ProviderIndividualBatchSearch();

        BatchSearchForProviderIndividualCriteriaType batchIdSearch1 = new BatchSearchForProviderIndividualCriteriaType();
        batchIdSearch1.setRequestIdentifier(UUID.randomUUID().toString());
        SearchForProviderIndividual idSearch = getGenericIDSearch();
        batchIdSearch1.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch1);

        BatchSearchForProviderIndividualCriteriaType batchIdSearch2 = new BatchSearchForProviderIndividualCriteriaType();
        batchIdSearch2.setRequestIdentifier(UUID.randomUUID().toString());
        batchIdSearch2.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch2);

        SubmitSearchForProviderIndividualResponse response = client.submitSearch(batch);
        Assert.assertEquals(response.getSubmitSearchForProviderIndividualResult().getServiceMessages().getHighestSeverity(), SeverityType.INFORMATIONAL);
    }

    @Test
    public void testIdentifierSearchMultiWithSuppliedUserID() throws StandardErrorMsg {
        ProviderIndividualBatchSearch batch = new ProviderIndividualBatchSearch();

        BatchSearchForProviderIndividualCriteriaType batchIdSearch1 = new BatchSearchForProviderIndividualCriteriaType();
        batchIdSearch1.setRequestIdentifier(UUID.randomUUID().toString());
        SearchForProviderIndividual idSearch = getGenericIDSearch();
        batchIdSearch1.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch1);

        BatchSearchForProviderIndividualCriteriaType batchIdSearch2 = new BatchSearchForProviderIndividualCriteriaType();
        batchIdSearch2.setRequestIdentifier(UUID.randomUUID().toString());
        batchIdSearch2.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch2);

        SubmitSearchForProviderIndividualResponse response = clientNoID.submitSearch(batch, getWrappedUserQualifiedId());
        Assert.assertEquals(response.getSubmitSearchForProviderIndividualResult().getServiceMessages().getHighestSeverity(), SeverityType.INFORMATIONAL);
    }

    @Test
    public void testRetrieveIdentifierSearchWithSuppliedId() throws StandardErrorMsg {
        ProviderIndividualBatchSearch batch = new ProviderIndividualBatchSearch();

        BatchSearchForProviderIndividualCriteriaType batchIdSearch1 = new BatchSearchForProviderIndividualCriteriaType();
        batchIdSearch1.setRequestIdentifier(UUID.randomUUID().toString());
        SearchForProviderIndividual idSearch = getGenericIDSearch();
        batchIdSearch1.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch1);

        BatchSearchForProviderIndividualCriteriaType batchIdSearch2 = new BatchSearchForProviderIndividualCriteriaType();
        batchIdSearch2.setRequestIdentifier(UUID.randomUUID().toString());
        batchIdSearch2.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch2);

        SubmitSearchForProviderIndividualResponse response = clientNoID.submitSearch(batch, getWrappedUserQualifiedId());
        Assert.assertEquals(response.getSubmitSearchForProviderIndividualResult().getServiceMessages().getHighestSeverity(), SeverityType.INFORMATIONAL);

        String batchIdentifier = response.getSubmitSearchForProviderIndividualResult().getBatchIdentifier();

        RetrieveSearchForProviderIndividual retrieveRequest = new RetrieveSearchForProviderIndividual();

        retrieveRequest.setBatchIdentifier(batchIdentifier);
        RetrieveSearchForProviderIndividualResponse retrieveResults = clientNoID.retrieveSearch(retrieveRequest, getWrappedUserQualifiedId());
        List<BatchSearchForProviderIndividualResultType> searchResults = retrieveResults.getRetrieveSearchForProviderIndividualResult().getBatchSearchForProviderIndividualResult();
        Assert.assertEquals(2, searchResults.size());
    }

    @Test
    public void testRetrieveSearch_clientScopedUser() throws StandardErrorMsg {
        ProviderIndividualBatchSearch batch = new ProviderIndividualBatchSearch();
        BatchSearchForProviderIndividualCriteriaType criteria = new BatchSearchForProviderIndividualCriteriaType();
        criteria.setRequestIdentifier(UUID.randomUUID().toString());
        criteria.setSearchForProviderIndividual(getGenericIDSearch());
        batch.identifierSearch(criteria);

        SubmitSearchForProviderIndividualResponse submitResponse = client.submitSearch(batch);
        Assert.assertEquals(SeverityType.INFORMATIONAL,
                submitResponse.getSubmitSearchForProviderIndividualResult().getServiceMessages().getHighestSeverity());

        RetrieveSearchForProviderIndividual retrieve = new RetrieveSearchForProviderIndividual();
        retrieve.setBatchIdentifier(submitResponse.getSubmitSearchForProviderIndividualResult().getBatchIdentifier());
        RetrieveSearchForProviderIndividualResponse retrieveResponse = client.retrieveSearch(retrieve);
        Assert.assertEquals(1, retrieveResponse.getRetrieveSearchForProviderIndividualResult().getBatchSearchForProviderIndividualResult().size());
    }

    private SearchForProviderIndividual getGenericIDSearch() {
        SearchForProviderIndividual idSearch = new SearchForProviderIndividual();
        idSearch.setHpiiNumber(MCA_HPII);
        idSearch.setFamilyName(MCA_FAMILY_NAME);
        idSearch.setSex(SexType.M);
        return idSearch;
    }

    private static SearchForProviderIndividualBatchAsyncClient getMedicareTestClient() {
        try {
            return new SearchForProviderIndividualBatchAsyncClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static SearchForProviderIndividualBatchAsyncClient getMedicareTestClientNoId() {
        try {
            return new SearchForProviderIndividualBatchAsyncClient(MEDICARE_ENDPOINT_URL, getWrappedProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
