package au.gov.nehta.vendorlibrary.hi.hpio;

import au.gov.nehta.vendorlibrary.hi.client.ClientBase;
import au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.net.electronichealth.ns.hi.svc.providermanageproviderorganisation._3_2.ManageProviderOrganisation;
import au.net.electronichealth.ns.hi.svc.providermanageproviderorganisation._3_2.ManageProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.svc.providermanageproviderorganisation._3_2.ProviderManageProviderOrganisationPortType;
import au.net.electronichealth.ns.hi.svc.providermanageproviderorganisation._3_2.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;
import au.net.electronichealth.ns.hi.xsd.providercore.organisationdetails._3_2.OrganisationDetails;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.*;

public class ProviderManageOrganisationClientTest {

    @Test
    public void bindingProviderIsNotNull() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderManageProviderOrganisationClient tc = getMCATestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void runProviderOrgClient() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderManageProviderOrganisationClient tc = getMCATestClient();

        ManageProviderOrganisation request = new ManageProviderOrganisation();
        request.setHpioNumber(HPIOHPIITestConstants.MCA_HPIO);


        OrganisationDetails orgDetails = new OrganisationDetails();
        orgDetails.setAustralianBusinessNumber("99 111 111 111");


        ManageProviderOrganisationResponse response = tc.manageProviderOrganisation(request);
        System.out.println(response.getManageProviderOrganisationResult().getStatus());
    }

    private ProviderManageProviderOrganisationClient getMCATestClient() throws GeneralSecurityException, IOException {
        ProviderManageProviderOrganisationClient testClient = new ProviderManageProviderOrganisationClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ProviderManageProviderOrganisationClient getMCATestIClient() throws GeneralSecurityException, IOException {
        ProviderManageProviderOrganisationClient testClient = new ProviderManageProviderOrganisationClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ClientBase<ProviderManageProviderOrganisationPortType> getGenericMCATestIClient() throws GeneralSecurityException, IOException {

        ClientBase<ProviderManageProviderOrganisationPortType> testClient = new ProviderManageProviderOrganisationClient(MEDICARE_ENDPOINT_URL,
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

        //matching ssl certificate
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
