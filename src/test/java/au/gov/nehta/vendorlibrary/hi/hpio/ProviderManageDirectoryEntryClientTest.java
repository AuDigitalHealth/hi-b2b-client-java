package au.gov.nehta.vendorlibrary.hi.hpio;

import au.gov.nehta.vendorlibrary.hi.client.ClientBase;
import au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.ManageProviderDirectoryEntry;
import au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.ManageProviderDirectoryEntryResponse;
import au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.ProviderManageProviderDirectoryEntryPortType;
import au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;
import au.net.electronichealth.ns.hi.xsd.providercore.organisationdetails._3_2.OrganisationDetails;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.*;

public class ProviderManageDirectoryEntryClientTest {

    @Test
    public void bindingProviderIsNotNull() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderManageProviderDirectoryEntryClient tc = getMCATestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void runProviderOrgClient() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderManageProviderDirectoryEntryClient tc = getMCATestClient();

        ManageProviderDirectoryEntry request = new ManageProviderDirectoryEntry();
        request.setQualifiedIdentifier(HPIOHPIITestConstants.MCA_HPIO);
        request.getIndividualDeleteEntry().add(1);

        OrganisationDetails orgDetails = new OrganisationDetails();
        orgDetails.setAustralianBusinessNumber("99 111 111 111");


        ManageProviderDirectoryEntryResponse response = tc.manageProviderDirectoryEntry(request);
        System.out.println(response.getManageProviderDirectoryEntryResult().getServiceMessages());
    }

    private ProviderManageProviderDirectoryEntryClient getMCATestClient() throws GeneralSecurityException, IOException {
        ProviderManageProviderDirectoryEntryClient testClient = new ProviderManageProviderDirectoryEntryClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ProviderManageProviderDirectoryEntryClient getMCATestIClient() throws GeneralSecurityException, IOException {
        ProviderManageProviderDirectoryEntryClient testClient = new ProviderManageProviderDirectoryEntryClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ClientBase<ProviderManageProviderDirectoryEntryPortType> getGenericMCATestIClient() throws GeneralSecurityException, IOException {

        ClientBase<ProviderManageProviderDirectoryEntryPortType> testClient = new ProviderManageProviderDirectoryEntryClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    public static QualifiedId getUserQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();

        /*
         * WR16.8. Each request should use organisation certificate and
         * incorporated individual DN details for qualifier.
         */

        // matching ssl certificate
        qualifiedId.setId("QID = CN=Sarah Franklin :8774668043, O=Health, S=ACT, C=AU");
        qualifiedId.setQualifier("http://ns.medicareaustralia.gov.au/id/hi/distinguishedname/1.0");
        return qualifiedId;
    }

    public static ProductType getProductHeader() {
        ProductType productHeader = new ProductType();
        productHeader.setPlatform(IHITestConstants.PRODUCT_PLATFORM);
        productHeader.setProductName(IHITestConstants.PRODUCT_NAME);
        productHeader.setProductVersion(IHITestConstants.PRODUCT_VERSION);
        productHeader.setVendor(getVendorQualifiedId());

        return productHeader;
    }

    public static QualifiedId getVendorQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();
        qualifiedId.setId(IHITestConstants.VENDOR_QUALIFIER_ID);
        qualifiedId.setQualifier(IHITestConstants.VENDOR_QUALIFIER);
        return qualifiedId;
    }
}
