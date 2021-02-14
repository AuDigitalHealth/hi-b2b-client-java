package au.gov.nehta.vendorlibrary.hi.hpio;

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
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.*;

public class ProviderSearchOrganisationBatchAsync {

    SearchForProviderOrganisationBatchAsyncClient client = getMedicareTestClient();
    SearchForProviderOrganisationBatchAsyncClient clientNoID = getMedicareTestClientNoId();

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
        // do a batch search
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
        Assert.assertEquals(retrieveSearchResponse.getRetrieveSearchForProviderOrganisationResult().getBatchSearchForProviderOrganisationResult().get(0).getSearchForProviderOrganisationResult().getHpioNumber(), MCA_HPIO);
    }

    protected SearchForProviderOrganisationBatchAsyncClient getMedicareTestClient() {
        try {
            return new SearchForProviderOrganisationBatchAsyncClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected SearchForProviderOrganisationBatchAsyncClient getMedicareTestClientNoId() {
        try {
            return new SearchForProviderOrganisationBatchAsyncClient(MEDICARE_ENDPOINT_URL, getWrappedProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
