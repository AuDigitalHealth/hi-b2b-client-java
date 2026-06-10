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

import au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants;
import au.gov.nehta.vendorlibrary.hi.test.utils.TestReflect;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.providermanageproviderorganisation._3_2.ManageProviderOrganisation;
import au.net.electronichealth.ns.hi.svc.providermanageproviderorganisation._3_2.ManageProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.svc.providermanageproviderorganisation._3_2.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;
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
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ProviderManageProviderOrganisationClient testClient = getMCATestClient();
        TestReflect.setField(testClient, "loggingHandler", null);
        Assert.assertEquals(LoggingHandler.EMPTY, testClient.getLastSoapRequest());
        Assert.assertEquals(LoggingHandler.EMPTY, testClient.getLastSoapResponse());
    }

    @Test(expected = IllegalArgumentException.class)
    public void manageProviderOrganisation_nullRequest_clientUser() throws Exception {
        getMCATestClient().manageProviderOrganisation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void manageProviderOrganisation_nullRequest_explicitUser() throws Exception {
        getMCATestClient().manageProviderOrganisation(null, getUserQualifiedId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void manageProviderOrganisation_blankHpio_clientUser() throws Exception {
        ManageProviderOrganisation req = new ManageProviderOrganisation();
        req.setHpioNumber("  ");
        getMCATestClient().manageProviderOrganisation(req);
    }

    @Test(expected = IllegalArgumentException.class)
    public void manageProviderOrganisation_blankHpio_explicitUser() throws Exception {
        ManageProviderOrganisation req = new ManageProviderOrganisation();
        req.setHpioNumber("  ");
        getMCATestClient().manageProviderOrganisation(req, getUserQualifiedId());
    }

    @Test
    public void runProviderOrgClient() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderManageProviderOrganisationClient tc = getMCATestClient();

        ManageProviderOrganisation request = new ManageProviderOrganisation();
        request.setHpioNumber(HPIOHPIITestConstants.MCA_HPIO);
        ManageProviderOrganisationResponse response = tc.manageProviderOrganisation(request);
        Assert.assertNotNull(response.getManageProviderOrganisationResult());
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
