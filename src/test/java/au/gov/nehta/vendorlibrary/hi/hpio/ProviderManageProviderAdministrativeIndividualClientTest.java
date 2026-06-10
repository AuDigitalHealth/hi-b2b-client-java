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
import au.net.electronichealth.ns.hi.svc.providermanageprovideradministrativeindividual._3_2.ManageProviderAdministrativeIndividual;
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

public class ProviderManageProviderAdministrativeIndividualClientTest {

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        ProviderManageProviderAdministrativeIndividualClient tc = getMedicareTestClient();
        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ProviderManageProviderAdministrativeIndividualClient testClient = getMedicareTestClient();
        TestReflect.setField(testClient, "loggingHandler", null);
        Assert.assertEquals(LoggingHandler.EMPTY, testClient.getLastSoapRequest());
        Assert.assertEquals(LoggingHandler.EMPTY, testClient.getLastSoapResponse());
    }

    @Test(expected = IllegalArgumentException.class)
    public void manageProviderAdministrativeIndividual_nullRequest_clientUser() throws Exception {
        getMedicareTestClient().manageProviderAdministrativeIndividual(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void manageProviderAdministrativeIndividual_nullRequest_explicitUser() throws Exception {
        getMedicareTestClient().manageProviderAdministrativeIndividual(null, getUserQualifiedId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void manageProviderAdministrativeIndividual_blankQualifiedIdentifier_clientUser() throws Exception {
        ManageProviderAdministrativeIndividual req = new ManageProviderAdministrativeIndividual();
        req.setQualifiedIdentifier("  ");
        getMedicareTestClient().manageProviderAdministrativeIndividual(req);
    }

    @Test(expected = IllegalArgumentException.class)
    public void manageProviderAdministrativeIndividual_blankQualifiedIdentifier_explicitUser() throws Exception {
        ManageProviderAdministrativeIndividual req = new ManageProviderAdministrativeIndividual();
        req.setQualifiedIdentifier("  ");
        getMedicareTestClient().manageProviderAdministrativeIndividual(req, getUserQualifiedId());
    }

    @Test
    public void runManageAdministrativeIndividualClient() throws Exception {
        setSystemVariablesForTest();
        ProviderManageProviderAdministrativeIndividualClient tc = getMedicareTestClient();
        ManageProviderAdministrativeIndividual request = new ManageProviderAdministrativeIndividual();
        request.setQualifiedIdentifier(HPIOHPIITestConstants.MCA_HPIO);
        tc.manageProviderAdministrativeIndividual(request);
    }

    @Test
    public void runManageAdministrativeIndividualClient_explicitUser() throws Exception {
        setSystemVariablesForTest();
        ProviderManageProviderAdministrativeIndividualClient tc = getMedicareTestClient();
        ManageProviderAdministrativeIndividual request = new ManageProviderAdministrativeIndividual();
        request.setQualifiedIdentifier(HPIOHPIITestConstants.MCA_HPIO);
        tc.manageProviderAdministrativeIndividual(request, getUserQualifiedId());
    }

    private ProviderManageProviderAdministrativeIndividualClient getMedicareTestClient()
            throws GeneralSecurityException, IOException {
        return new ProviderManageProviderAdministrativeIndividualClient(
                MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
    }
}
