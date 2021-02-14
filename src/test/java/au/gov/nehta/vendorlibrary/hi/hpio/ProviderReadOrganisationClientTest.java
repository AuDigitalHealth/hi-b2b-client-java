package au.gov.nehta.vendorlibrary.hi.hpio;

import au.gov.nehta.vendorlibrary.hi.client.ClientBase;
import au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.net.electronichealth.ns.hi.svc.providerreadproviderorganisation._3_2.ProviderReadProviderOrganisationPortType;
import au.net.electronichealth.ns.hi.svc.providerreadproviderorganisation._3_2.ReadProviderOrganisation;
import au.net.electronichealth.ns.hi.svc.providerreadproviderorganisation._3_2.ReadProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.svc.providerreadproviderorganisation._3_2.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.*;
import static org.junit.Assert.fail;

public class ProviderReadOrganisationClientTest {

    @Test
    public void bindingProviderIsNotNull() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderReadProviderOrganisationClient tc = getMCATestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void runProviderOrgClient() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderReadProviderOrganisationClient tc = getMCATestClient();

        ReadProviderOrganisation request = new ReadProviderOrganisation();
        request.setHpioNumber(HPIOHPIITestConstants.MCA_HPIO);
        ReadProviderOrganisationResponse response = tc.readProvider(request);
        System.out.println(response.getReadProviderOrganisationResult().getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void runNoHPIO() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderReadProviderOrganisationClient tc = getMCATestClient();

        ReadProviderOrganisation request = new ReadProviderOrganisation();
        // No hpio

        tc.readProvider(request);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runNullRequest() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderReadProviderOrganisationClient tc = getMCATestClient();
        tc.readProvider(null);

        fail("Exception not thrown");
    }

    @Test
    public void runProviderReadClientLinkType() throws GeneralSecurityException, IOException, StandardErrorMsg {

        ProviderReadProviderOrganisationClient tic = getMCATestIClient();
        ReadProviderOrganisation request = new ReadProviderOrganisation();
        request.setHpioNumber(HPIOHPIITestConstants.MCA_HPIO);
        request.setLinkSearchType("Children");

        ReadProviderOrganisationResponse identifierSearch = tic.readProvider(request);

        System.out.println(identifierSearch.getReadProviderOrganisationResult().getStatus());
    }

    private ProviderReadProviderOrganisationClient getMCATestClient() throws GeneralSecurityException, IOException {
        ProviderReadProviderOrganisationClient testClient = new ProviderReadProviderOrganisationClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ProviderReadProviderOrganisationClient getMCATestIClient() throws GeneralSecurityException, IOException {
        ProviderReadProviderOrganisationClient testClient = new ProviderReadProviderOrganisationClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ClientBase<ProviderReadProviderOrganisationPortType> getGenericMCATestIClient() throws GeneralSecurityException, IOException {

        ClientBase<ProviderReadProviderOrganisationPortType> testClient = new ProviderReadProviderOrganisationClient(MEDICARE_ENDPOINT_URL,
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
