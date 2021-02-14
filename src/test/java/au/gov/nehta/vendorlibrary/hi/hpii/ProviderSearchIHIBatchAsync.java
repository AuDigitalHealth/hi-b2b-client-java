package au.gov.nehta.vendorlibrary.hi.hpii;

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
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.*;

public class ProviderSearchIHIBatchAsync {
    SearchForProviderIndividualBatchAsyncClient client = getMedicareTestClient();
    SearchForProviderIndividualBatchAsyncClient clientNoID = getMedicareTestClientNoId();

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

        // This is the client where we have to supply the user ID with the search
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

        // add second batch (for the same hpii)
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

        // add second batch (for the same hpii)
        BatchSearchForProviderIndividualCriteriaType batchIdSearch2 = new BatchSearchForProviderIndividualCriteriaType();
        batchIdSearch2.setRequestIdentifier(UUID.randomUUID().toString());
        batchIdSearch2.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch2);

        // This is the client where we have to supply the user ID with the search
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

        // add second batch (for the same hpii)
        BatchSearchForProviderIndividualCriteriaType batchIdSearch2 = new BatchSearchForProviderIndividualCriteriaType();
        batchIdSearch2.setRequestIdentifier(UUID.randomUUID().toString());
        batchIdSearch2.setSearchForProviderIndividual(idSearch);
        batch.identifierSearch(batchIdSearch2);

        // This is the client where we have to supply the user ID with the search
        SubmitSearchForProviderIndividualResponse response = clientNoID.submitSearch(batch, getWrappedUserQualifiedId());
        Assert.assertEquals(response.getSubmitSearchForProviderIndividualResult().getServiceMessages().getHighestSeverity(), SeverityType.INFORMATIONAL);

        // the id of this search
        String batchIdentifier = response.getSubmitSearchForProviderIndividualResult().getBatchIdentifier();

        RetrieveSearchForProviderIndividual retrieveRequest = new RetrieveSearchForProviderIndividual();

        retrieveRequest.setBatchIdentifier(batchIdentifier);
        RetrieveSearchForProviderIndividualResponse retrieveResults = clientNoID.retrieveSearch(retrieveRequest, getWrappedUserQualifiedId());
        List<BatchSearchForProviderIndividualResultType> searchResults = retrieveResults.getRetrieveSearchForProviderIndividualResult().getBatchSearchForProviderIndividualResult();
        Assert.assertEquals(2, searchResults.size());
    }

    private SearchForProviderIndividual getGenericIDSearch() {
        SearchForProviderIndividual idSearch = new SearchForProviderIndividual();
        idSearch.setHpiiNumber(MCA_HPII);
        idSearch.setFamilyName(MCA_FAMILY_NAME);
        idSearch.setSex(SexType.M);
        return idSearch;
    }

    protected SearchForProviderIndividualBatchAsyncClient getMedicareTestClient() {
        try {
            return new SearchForProviderIndividualBatchAsyncClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected SearchForProviderIndividualBatchAsyncClient getMedicareTestClientNoId() {
        try {
            return new SearchForProviderIndividualBatchAsyncClient(MEDICARE_ENDPOINT_URL, getWrappedProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
