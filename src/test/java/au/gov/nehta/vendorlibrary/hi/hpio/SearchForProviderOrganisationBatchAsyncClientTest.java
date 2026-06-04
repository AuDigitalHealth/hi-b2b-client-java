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

import au.gov.nehta.vendorlibrary.hi.test.utils.TestReflect;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.RetrieveSearchForProviderOrganisation;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.RetrieveSearchForProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.StandardErrorMsg;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.SubmitSearchForProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SeverityType;
import au.net.electronichealth.ns.hi.xsd.providercore.organisationdetails._5_1.BatchSearchForProviderOrganisationCriteriaType;
import au.net.electronichealth.ns.hi.xsd.providermessages.searchorganisation._5_0.SearchForProviderOrganisation;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.MCA_HPIO;
import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.setSystemVariablesForTest;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.*;

public class SearchForProviderOrganisationBatchAsyncClientTest {

    private final SearchForProviderOrganisationBatchAsyncClient client = getMedicareTestClient();
    private final SearchForProviderOrganisationBatchAsyncClient clientNoID = getMedicareTestClientNoId();

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        SearchForProviderOrganisationBatchAsyncClient tc = getMedicareTestClient();
        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        SearchForProviderOrganisationBatchAsyncClient testClient = getMedicareTestClient();
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
        ProviderOrganisationBatchSearch batch = new ProviderOrganisationBatchSearch();
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
        RetrieveSearchForProviderOrganisation retrieve = new RetrieveSearchForProviderOrganisation();
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
    public void testHPIOBatchSingle() throws StandardErrorMsg {
        ProviderOrganisationBatchSearch request = new ProviderOrganisationBatchSearch();
        BatchSearchForProviderOrganisationCriteriaType idSearch = new BatchSearchForProviderOrganisationCriteriaType();
        idSearch.setRequestIdentifier(UUID.randomUUID().toString());
        SearchForProviderOrganisation hpioSearch = new SearchForProviderOrganisation();
        hpioSearch.setHpioNumber(MCA_HPIO);
        idSearch.setSearchForProviderOrganisation(hpioSearch);
        request.identifierSearch(idSearch);
        SubmitSearchForProviderOrganisationResponse response = client.submitSearch(request);
        Assert.assertEquals(response.getSubmitSearchForProviderOrganisationResult().getServiceMessages().getHighestSeverity(), SeverityType.INFORMATIONAL);
    }

    @Test
    public void testRetrieveHPIOBatchSingle() throws StandardErrorMsg {
        ProviderOrganisationBatchSearch request = new ProviderOrganisationBatchSearch();
        BatchSearchForProviderOrganisationCriteriaType idSearch = new BatchSearchForProviderOrganisationCriteriaType();
        idSearch.setRequestIdentifier(UUID.randomUUID().toString());
        SearchForProviderOrganisation hpioSearch = new SearchForProviderOrganisation();
        hpioSearch.setHpioNumber(MCA_HPIO);
        idSearch.setSearchForProviderOrganisation(hpioSearch);
        request.identifierSearch(idSearch);
        SubmitSearchForProviderOrganisationResponse response = client.submitSearch(request);
        Assert.assertEquals(response.getSubmitSearchForProviderOrganisationResult().getServiceMessages().getHighestSeverity(), SeverityType.INFORMATIONAL);

        RetrieveSearchForProviderOrganisation requestSearch = new RetrieveSearchForProviderOrganisation();
        requestSearch.setBatchIdentifier(response.getSubmitSearchForProviderOrganisationResult().getBatchIdentifier());
        RetrieveSearchForProviderOrganisationResponse retrieveSearchResponse = client.retrieveSearch(requestSearch);
        Assert.assertEquals(1, retrieveSearchResponse.getRetrieveSearchForProviderOrganisationResult().getBatchSearchForProviderOrganisationResult().size());
        Assert.assertEquals(MCA_HPIO,
                retrieveSearchResponse.getRetrieveSearchForProviderOrganisationResult().getBatchSearchForProviderOrganisationResult().get(0).getSearchForProviderOrganisationResult().getHpioNumber());
    }

    @Test
    public void testRetrieveSearch_clientScopedUser() throws StandardErrorMsg {
        ProviderOrganisationBatchSearch request = new ProviderOrganisationBatchSearch();
        BatchSearchForProviderOrganisationCriteriaType idSearch = new BatchSearchForProviderOrganisationCriteriaType();
        idSearch.setRequestIdentifier(UUID.randomUUID().toString());
        SearchForProviderOrganisation hpioSearch = new SearchForProviderOrganisation();
        hpioSearch.setHpioNumber(MCA_HPIO);
        idSearch.setSearchForProviderOrganisation(hpioSearch);
        request.identifierSearch(idSearch);
        SubmitSearchForProviderOrganisationResponse submitResponse = client.submitSearch(request);
        Assert.assertEquals(SeverityType.INFORMATIONAL,
                submitResponse.getSubmitSearchForProviderOrganisationResult().getServiceMessages().getHighestSeverity());

        RetrieveSearchForProviderOrganisation retrieve = new RetrieveSearchForProviderOrganisation();
        retrieve.setBatchIdentifier(submitResponse.getSubmitSearchForProviderOrganisationResult().getBatchIdentifier());
        RetrieveSearchForProviderOrganisationResponse retrieveResponse = client.retrieveSearch(retrieve);
        Assert.assertEquals(1, retrieveResponse.getRetrieveSearchForProviderOrganisationResult().getBatchSearchForProviderOrganisationResult().size());
    }

    private static SearchForProviderOrganisationBatchAsyncClient getMedicareTestClient() {
        try {
            return new SearchForProviderOrganisationBatchAsyncClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static SearchForProviderOrganisationBatchAsyncClient getMedicareTestClientNoId() {
        try {
            return new SearchForProviderOrganisationBatchAsyncClient(MEDICARE_ENDPOINT_URL, getWrappedProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
