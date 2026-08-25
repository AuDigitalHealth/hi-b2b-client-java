package au.gov.nehta.vendorlibrary.hi.test.unittests;

import au.gov.nehta.vendorlibrary.hi.client.ClientBase;
import jakarta.xml.ws.Service;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class HiFacadeCoverageTest {

    private static final Map<String, String> FACADE_TO_SERVICE = expectedFacadeServices();

    @Test
    public void allMcaFacadesAreOnClasspath() throws Exception {
        Assert.assertEquals(26, FACADE_TO_SERVICE.size());

        for (String facadeName : FACADE_TO_SERVICE.keySet()) {
            Class<?> facadeType = Class.forName(facadeName);
            Assert.assertTrue(facadeName + " must extend ClientBase",
                    ClientBase.class.isAssignableFrom(facadeType));
        }
    }

    @Test
    public void allMcaGeneratedServicesAreOnClasspath() throws Exception {
        Assert.assertEquals(26, FACADE_TO_SERVICE.size());

        for (Map.Entry<String, String> entry : FACADE_TO_SERVICE.entrySet()) {
            Class<?> serviceType = Class.forName(entry.getValue());
            Assert.assertTrue(entry.getValue() + " for " + entry.getKey()
                            + " must extend jakarta.xml.ws.Service",
                    Service.class.isAssignableFrom(serviceType));
        }
    }

    private static Map<String, String> expectedFacadeServices() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerSearchIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumersearchihi._3.ConsumerSearchIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerSearchIHIBatchSyncClient",
                "au.net.electronichealth.ns.hi.svc.consumersearchihibatchsyncrequest._3.ConsumerSearchIHIBatchSyncService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerSearchIHIBatchAsyncClient",
                "au.net.electronichealth.ns.hi.svc.consumersearchihibatchasync._3.ConsumerSearchIHIBatchAsyncService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerCreateProvisionalIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumercreateprovisionalihi._3.ConsumerCreateProvisionalIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerUpdateProvisionalIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumerupdateprovisionalihi._3.UpdateProvisionalIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerMergeProvisionalIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumermergeprovisionalihi._3.ConsumerMergeProvisionalIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerResolveProvisionalIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumerresolveprovisionalihi._3_0.ConsumerResolveProvisionalIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerCreateUnverifiedIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumercreateunverifiedihi._3_0.ConsumerCreateUnverifiedIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerCreateVerifiedIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumercreateverifiedihi._4_0.ConsumerCreateVerifiedIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerUpdateIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumerupdateihi._3_2.ConsumerUpdateIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerNotifyDuplicateIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumernotifyduplicateihi._3_2.ConsumerNotifyDuplicateIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.ihi.ConsumerNotifyReplicaIHIClient",
                "au.net.electronichealth.ns.hi.svc.consumernotifyreplicaihi._3_2.ConsumerNotifyReplicaIHIService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpii.ProviderSearchForProviderIndividualClient",
                "au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.ProviderSearchForProviderIndividualService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpii.ProviderSearchHIProviderDirectoryForIndividualClient",
                "au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryforindividual._3_2.ProviderSearchHIProviderDirectoryForIndividualService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpii.SearchForProviderIndividualBatchAsyncClient",
                "au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderindividual._5_1.ProviderSearchForIndividualBatchAsyncService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpii.ProviderSearchTdsProviderIndividualClient",
                "au.net.electronichealth.ns.hi.svc.providersearchtdsproviderindividual._5_1.ProviderSearchTdsProviderIndividualService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpii.ProviderManageTdsProviderIndividualClient",
                "au.net.electronichealth.ns.hi.svc.providermanagetdsproviderindividual._5_1.ProviderManageTdsProviderIndividualService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpio.ProviderSearchForProviderOrganisationClient",
                "au.net.electronichealth.ns.hi.svc.providersearchforproviderorganisation._5_0.ProviderSearchForProviderOrganisationService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpio.ProviderSearchHIProviderDirectoryForOrganisationClient",
                "au.net.electronichealth.ns.hi.svc.providersearchhiproviderdirectoryfororganisation._3_2.ProviderSearchHIProviderDirectoryForOrganisationService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpio.SearchForProviderOrganisationBatchAsyncClient",
                "au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.ProviderSearchForOrganisationBatchAsyncService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpio.ProviderReadProviderOrganisationClient",
                "au.net.electronichealth.ns.hi.svc.providerreadproviderorganisation._3_2.ProviderReadProviderOrganisationService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpio.ProviderReadAdministrativeIndividualClient",
                "au.net.electronichealth.ns.hi.svc.providerreadprovideradministrativeindividual._3_2.ProviderReadProviderAdministrativeIndividualService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpio.ProviderManageProviderOrganisationClient",
                "au.net.electronichealth.ns.hi.svc.providermanageproviderorganisation._3_2.ProviderManageProviderOrganisationService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpio.ProviderManageProviderDirectoryEntryClient",
                "au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.ProviderManageProviderDirectoryEntryService");
        map.put("au.gov.nehta.vendorlibrary.hi.hpio.ProviderManageProviderAdministrativeIndividualClient",
                "au.net.electronichealth.ns.hi.svc.providermanageprovideradministrativeindividual._3_2.ProviderManageProviderAdministrativeIndividualService");
        map.put("au.gov.nehta.vendorlibrary.hi.readreferencedata.ReadReferenceDataClient",
                "au.net.electronichealth.ns.hi.svc.providerreadreferencedata._3_2.ProviderReadReferenceDataService");
        return map;
    }
}
