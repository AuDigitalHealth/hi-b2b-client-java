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
import au.gov.nehta.vendorlibrary.hi.test.utils.TestReflect;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderorganisation._5_0.SearchForProviderOrganisation;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderorganisation._5_0.SearchForProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.svc.providersearchforproviderorganisation._5_0.StandardErrorMsg;
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
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.MEDICARE_ENDPOINT_URL;
import static org.junit.Assert.fail;

public class ProviderSearchForProviderOrganisationClientTest {

    @Test
    public void bindingProviderIsNotNull() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderSearchForProviderOrganisationClient tc = getMCATestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ProviderSearchForProviderOrganisationClient testClient = getMCATestClient();
        TestReflect.setField(testClient, "loggingHandler", null);
        Assert.assertEquals(testClient.getLastSoapRequest(), LoggingHandler.EMPTY);
        Assert.assertEquals(testClient.getLastSoapResponse(), LoggingHandler.EMPTY);
    }

    @Test
    public void runProviderOrgClient() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderSearchForProviderOrganisationClient tc = getMCATestClient();

        SearchForProviderOrganisation request = new SearchForProviderOrganisation();
        request.setHpioNumber(HPIOHPIITestConstants.MCA_HPIO);
        SearchForProviderOrganisationResponse response = tc.identifierSearch(request);
        Assert.assertNotNull(response.getSearchForProviderOrganisationResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderOrgClientNegativeTestNoHPIO() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderSearchForProviderOrganisationClient tc = getMCATestClient();

        SearchForProviderOrganisation request = new SearchForProviderOrganisation();

        tc.identifierSearch(request);
        fail("Exception not thrown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void runProviderOrgClientNegativeTestNullRequest() throws GeneralSecurityException, IOException, StandardErrorMsg {
        ProviderSearchForProviderOrganisationClient tc = getMCATestClient();
        tc.identifierSearch(null);
        fail("Exception not thrown");
    }

    private ProviderSearchForProviderOrganisationClient getMCATestClient() throws GeneralSecurityException, IOException {
        return new ProviderSearchForProviderOrganisationClient(MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
    }
}
