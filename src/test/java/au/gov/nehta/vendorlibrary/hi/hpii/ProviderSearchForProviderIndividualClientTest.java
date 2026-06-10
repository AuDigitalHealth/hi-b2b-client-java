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

import au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.TestReflect;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.SearchForProviderIndividual;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.SearchForProviderIndividualResponse;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._5_0.SearchAustralianAddressType;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._5_0.SearchInternationalAddressType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.setSystemVariablesForTest;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getProductHeader;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningCertificateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningPrivateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSslSocketFactoryForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getUserQualifiedId;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getWrappedUserQualifiedId;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.MEDICARE_ENDPOINT_URL;
import static org.junit.Assert.fail;

public class ProviderSearchForProviderIndividualClientTest {

    @Test
    public void bindingProviderIsNotNull() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tc = getMCATestIClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchForProviderIndividualClient testClient = getMCATestIClient();
        TestReflect.setField(testClient, "loggingHandler", null);
        Assert.assertEquals(testClient.getLastSoapRequest(), LoggingHandler.EMPTY);
        Assert.assertEquals(testClient.getLastSoapResponse(), LoggingHandler.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void demographicSearch_nullRequest_clientUser() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        getMCATestIClient().demographicSearch(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void demographicSearch_nullRequest_wrappedUser() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        getMCATestIClient().demographicSearch(null, getWrappedUserQualifiedId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void identifierSearch_nullRequest_clientUser() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        getMCATestIClient().identifierSearch(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void identifierSearch_nullRequest_wrappedUser() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        getMCATestIClient().identifierSearch(null, getWrappedUserQualifiedId());
    }

    @Test
    public void demographicSearch_clientUser() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tic = getMCATestIClient();
        SearchForProviderIndividual r = new SearchForProviderIndividual();
        r.setFamilyName("Smith");
        r.setOnlyNameIndicator(false);
        r.setPostcode("2900");
        r.setSex(SexType.M);
        SearchForProviderIndividualResponse response = tic.demographicSearch(r);
        Assert.assertNotNull(response);
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

        Assert.assertNotNull(identifierSearch.getSearchForProviderIndividualResult());
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
        r.setFamilyName("12345678901234567890123456789012345678901");
        tc.identifierSearch(r);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderIDClientWithALongFamilyNameDemographic() throws GeneralSecurityException, IOException, au.net.electronichealth.ns.hi.svc.providersearchforproviderindividual._5_0.StandardErrorMsg {
        ProviderSearchForProviderIndividualClient tc = getMCATestIClient();
        SearchForProviderIndividual r = new SearchForProviderIndividual();
        r.setHpiiNumber("12345");
        r.setFamilyName("12345678901234567890123456789012345678901");
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
        intl.setCountry("101");
        r.setSearchInternationalAddress(intl);
        r.setFamilyName("123456789012345678901234567890123456789");
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

    private ProviderSearchForProviderIndividualClient getMCATestIClient() throws GeneralSecurityException, IOException {
        return new ProviderSearchForProviderIndividualClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
    }
}
