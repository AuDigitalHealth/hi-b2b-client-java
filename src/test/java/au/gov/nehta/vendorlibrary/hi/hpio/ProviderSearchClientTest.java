package au.gov.nehta.vendorlibrary.hi.hpio;

import au.gov.nehta.vendorlibrary.hi.client.ClientBase;
import au.gov.nehta.vendorlibrary.hi.hpii.ProviderSearchForProviderIndividualClient;
import au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.ProviderSearchForProviderIndividualPortType;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.SearchForProviderIndividual;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.SearchForProviderIndividualResponse;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderorganisation._5_0.SearchForProviderOrganisation;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderorganisation._5_0.SearchForProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderorganisation._5_0.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._5_0.SearchAustralianAddressType;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._5_0.SearchInternationalAddressType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.CountryType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.*;
import static org.junit.Assert.fail;

public class ProviderSearchClientTest {

    @Test
    public void bindingProviderIsNotNull() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderSearchForProviderOrganisationClient tc = getMCATestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void runProviderOrgClient() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderSearchForProviderOrganisationClient tc = getMCATestClient();

        SearchForProviderOrganisation request = new SearchForProviderOrganisation();
        request.setHpioNumber(HPIOHPIITestConstants.MCA_HPIO);
        SearchForProviderOrganisationResponse response = null;

        response = tc.identifierSearch(request);
        System.out.println(response.getSearchForProviderOrganisationResult().getStatus());
    }


    @Test(expected = IllegalArgumentException.class)
    public void runProviderOrgClientNegativeTestNoHPIO() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderSearchForProviderOrganisationClient tc = getMCATestClient();

        SearchForProviderOrganisation request = new SearchForProviderOrganisation();
        //No hpio

        tc.identifierSearch(request);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderOrgClientNegativeTestNullRequest() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderSearchForProviderOrganisationClient tc = getMCATestClient();
        tc.identifierSearch(null);
        fail("Exception not thrown");
    }

    @Test
    public void runProviderIndividualClient() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {

        ProviderSearchForProviderIndividualClient tic = getMCATestIClient();
        SearchForProviderIndividual request = new SearchForProviderIndividual();
        request.setHpiiNumber(HPIOHPIITestConstants.MCA_HPII);
        request.setFamilyName("Smith");
        request.getGivenName().add("John");
        request.setOnlyNameIndicator(false);
        request.setPostcode("2900");
        request.setSex(SexType.M);


        SearchForProviderIndividualResponse identifierSearch = tic.identifierSearch(request);

        System.out.println(identifierSearch.getSearchForProviderIndividualResult().getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderIDClientWithBothIds() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tc = getMCATestIClient();
        SearchForProviderIndividual r = new SearchForProviderIndividual();
        r.setHpiiNumber("12345");
        r.setRegistrationId("12345");
        tc.identifierSearch(r);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderIDClientWithALongFamilyName() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tc = getMCATestIClient();
        SearchForProviderIndividual r = new SearchForProviderIndividual();
        r.setHpiiNumber("12345");
        r.setFamilyName("12345678901234567890123456789012345678901"); //41 chars
        tc.identifierSearch(r);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderIDClientWithALongFamilyNameDemographic() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tc = getMCATestIClient();
        SearchForProviderIndividual r = new SearchForProviderIndividual();
        r.setHpiiNumber("12345");
        r.setFamilyName("12345678901234567890123456789012345678901"); //41 chars
        tc.demographicSearch(r);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderIDClientWithAMissingFamilyNameDemographic() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tc = getMCATestIClient();
        SearchForProviderIndividual r = new SearchForProviderIndividual();
        r.setHpiiNumber("12345");
        tc.demographicSearch(r);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderIDClientWithAMissingFamilyName() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tc = getMCATestIClient();
        SearchForProviderIndividual r = new SearchForProviderIndividual();
        r.setHpiiNumber("12345");
        tc.identifierSearch(r);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderIDClientWithATwoSearchCriteria() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tc = getMCATestIClient();
        SearchForProviderIndividual r = new SearchForProviderIndividual();
        SearchAustralianAddressType oz = new SearchAustralianAddressType();
        oz.setPostcode("2900");
        r.setSearchAustralianAddress(oz);

        SearchInternationalAddressType intl = new SearchInternationalAddressType();
        intl.setCountry(CountryType.VALUE_101);
        r.setSearchInternationalAddress(intl);
        r.setFamilyName("123456789012345678901234567890123456789"); //41 chars
        tc.demographicSearch(r);
        fail("Exception not thrown");
    }

    @Test
    public void testWrappedGenericUInterface() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId vendorQualifiedId =
                new au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId(
                        IHITestConstants.VENDOR_QUALIFIER, IHITestConstants.VENDOR_QUALIFIER_ID);

        au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader = new
                au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType(
                IHITestConstants.PRODUCT_PLATFORM,
                IHITestConstants.PRODUCT_NAME,
                IHITestConstants.PRODUCT_VERSION,
                vendorQualifiedId
        );

        au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId userId =
                new au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId(
                        IHITestConstants.USER_QUALIFIER, IHITestConstants.USER_QUALIFIED_ID);

        ProviderSearchForProviderIndividualClient testClient = new ProviderSearchForProviderIndividualClient(MEDICARE_ENDPOINT_URL,
                productHeader,
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());

        SearchForProviderIndividual request = new SearchForProviderIndividual();
        request.setHpiiNumber(HPIOHPIITestConstants.MCA_HPII);
        request.setFamilyName("Smith");
        request.getGivenName().add("John");
        request.setOnlyNameIndicator(false);
        request.setPostcode("2900");
        request.setSex(SexType.M);

        SearchForProviderIndividual r = new SearchForProviderIndividual();
        r.setFamilyName("Smith");
        r.setOnlyNameIndicator(false);
        r.setPostcode("2900");
        r.setSex(SexType.M);

        SearchForProviderIndividualResponse demographicResponse = testClient.demographicSearch(r, userId);
        Assert.assertNotNull(demographicResponse);

        SearchForProviderIndividualResponse identifierResponse = testClient.identifierSearch(request, userId);
        Assert.assertNotNull(identifierResponse);
    }

    private ProviderSearchForProviderOrganisationClient getMCATestClient() throws GeneralSecurityException, IOException {
        ProviderSearchForProviderOrganisationClient testClient = new ProviderSearchForProviderOrganisationClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ProviderSearchForProviderIndividualClient getMCATestIClient() throws GeneralSecurityException, IOException {
        ProviderSearchForProviderIndividualClient testClient = new ProviderSearchForProviderIndividualClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    private ClientBase<ProviderSearchForProviderIndividualPortType> getGenericMCATestIClient() throws GeneralSecurityException, IOException {

        ClientBase<ProviderSearchForProviderIndividualPortType> testClient = new ProviderSearchForProviderIndividualClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
        return testClient;
    }

    public static QualifiedId getUserQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();
        qualifiedId.setId(IHITestConstants.USER_QUALIFIED_ID);
        qualifiedId.setQualifier(IHITestConstants.USER_QUALIFIER);
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
