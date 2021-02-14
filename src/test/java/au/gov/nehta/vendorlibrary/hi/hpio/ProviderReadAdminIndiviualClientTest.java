package au.gov.nehta.vendorlibrary.hi.hpio;

import au.gov.nehta.vendorlibrary.hi.client.ClientBase;
import au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.net.electronichealth.ns.hi.svc.providerreadprovideradministrativeindividual._3_2.ProviderReadProviderAdministrativeIndividualPortType;
import au.net.electronichealth.ns.hi.svc.providerreadprovideradministrativeindividual._3_2.ReadProviderAdministrativeIndividual;
import au.net.electronichealth.ns.hi.svc.providerreadprovideradministrativeindividual._3_2.ReadProviderAdministrativeIndividualResponse;
import au.net.electronichealth.ns.hi.svc.providerreadprovideradministrativeindividual._3_2.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.*;
import static org.junit.Assert.fail;

public class ProviderReadAdminIndiviualClientTest {

    @Test
    public void bindingProviderIsNotNull() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderReadAdministrativeIndividualClient tc = getMCATestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void runProviderOrgClient() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderReadAdministrativeIndividualClient tc = getMCATestClient();

        ReadProviderAdministrativeIndividual request = new ReadProviderAdministrativeIndividual();
        request.setQualifiedIdentifier(HPIOHPIITestConstants.MCA_HPIO);
        ReadProviderAdministrativeIndividualResponse response = tc.readProviderAdministrativeIndividual(request);

        System.out.println(response.getReadProviderAdministrativeIndividualResult().getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void runNoHPIO() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderReadAdministrativeIndividualClient tc = getMCATestClient();

        ReadProviderAdministrativeIndividual request = new ReadProviderAdministrativeIndividual();
        //No id

        tc.readProviderAdministrativeIndividual(request);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runNullRequest() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderReadAdministrativeIndividualClient tc = getMCATestClient();
        tc.readProviderAdministrativeIndividual(null);

        fail("Exception not thrown");
    }

    @Test
    public void runProviderReadClientLinkType() throws GeneralSecurityException, IOException, StandardErrorMsg {

        ProviderReadAdministrativeIndividualClient tic = getMCATestIClient();
        ReadProviderAdministrativeIndividual request = new ReadProviderAdministrativeIndividual();
        request.setQualifiedIdentifier(HPIOHPIITestConstants.MCA_HPIO);


        ReadProviderAdministrativeIndividualResponse identifierSearch = tic.readProviderAdministrativeIndividual(request);

        System.out.println(identifierSearch.getReadProviderAdministrativeIndividualResult().getStatus());
    }

    private ProviderReadAdministrativeIndividualClient getMCATestClient() throws GeneralSecurityException, IOException {
        ProviderReadAdministrativeIndividualClient testClient = new ProviderReadAdministrativeIndividualClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ProviderReadAdministrativeIndividualClient getMCATestIClient() throws GeneralSecurityException, IOException {
        ProviderReadAdministrativeIndividualClient testClient = new ProviderReadAdministrativeIndividualClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ClientBase<ProviderReadProviderAdministrativeIndividualPortType> getGenericMCATestIClient() throws GeneralSecurityException, IOException {

        ClientBase<ProviderReadProviderAdministrativeIndividualPortType> testClient = new ProviderReadAdministrativeIndividualClient(MEDICARE_ENDPOINT_URL,
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
        qualifiedId.setId("CN=Location 191 :4902022799,OU=Location 191,O=Location 191,L=TUGGERANONG,ST=ACT,C=AU");
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
