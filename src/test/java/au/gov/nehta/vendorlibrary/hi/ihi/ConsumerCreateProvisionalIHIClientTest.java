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
package au.gov.nehta.vendorlibrary.hi.ihi;

import au.gov.nehta.vendorlibrary.hi.test.utils.TestReflect;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
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

public class ConsumerCreateProvisionalIHIClientTest {

    @Test
    public void bindingProviderIsNotNull() throws Exception {
        ConsumerCreateProvisionalIHIClient tc = getMedicareTestClient();

        Assert.assertNotNull(tc.getBindingProvider());
        Assert.assertNotNull(tc.getPort());
    }

    @Test
    public void testNullLoggingHandler() throws Exception {
        setSystemVariablesForTest();
        ConsumerCreateProvisionalIHIClient testClient = getMedicareTestClient();

        TestReflect.setField(testClient, "loggingHandler", null);

        String lastSoapRequest = testClient.getLastSoapRequest();
        Assert.assertEquals(lastSoapRequest, LoggingHandler.EMPTY);

        String lastSoapResponse = testClient.getLastSoapResponse();
        Assert.assertEquals(lastSoapResponse, LoggingHandler.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createProvisionalIHI_nullRequest_clientUser() throws Exception {
        getMedicareTestClient().createProvisionalIHI(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createProvisionalIHI_nullRequest_perRequestUser() throws Exception {
        getMedicareTestClient().createProvisionalIHI(null, getWrappedUserQualifiedId());
    }

    private ConsumerCreateProvisionalIHIClient getMedicareTestClient() throws GeneralSecurityException, IOException {
        return new ConsumerCreateProvisionalIHIClient(
                MEDICARE_ENDPOINT_URL,
                getUserQualifiedId(),
                getProductHeader(),
                getSigningPrivateKeyForMedicare(),
                getSigningCertificateKeyForMedicare(),
                getSslSocketFactoryForMedicare());
    }
}
