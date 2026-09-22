/*
 * Copyright 2011 NEHTA
 * Copyright 2021-2026 ADHA (Australian Digital Health Agency)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.hi.hpio;

import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.RetrieveSearchForProviderOrganisation;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.StandardErrorMsg;
import org.junit.Test;

import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.*;

public class SearchForProviderOrganisationBatchAsyncClientTest {

    private final SearchForProviderOrganisationBatchAsyncClient client = getMedicareTestClient();
    private final SearchForProviderOrganisationBatchAsyncClient clientNoID = getMedicareTestClientNoId();

    @Test(expected = IllegalArgumentException.class)
    public void submitSearch_nullBatchRequest_clientUser() throws StandardErrorMsg {
        client.submitSearch(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitSearch_nullBatchRequest_perRequestUser() throws StandardErrorMsg {
        clientNoID.submitSearch(null, getWrappedUserQualifiedId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitSearch_nullIndividualId_throws() throws StandardErrorMsg {
        ProviderOrganisationBatchSearch batch = new ProviderOrganisationBatchSearch();
        clientNoID.submitSearch(batch, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveSearch_nullRequest_clientUser() throws StandardErrorMsg {
        client.retrieveSearch(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveSearch_nullRequest_perRequestUser() throws StandardErrorMsg {
        clientNoID.retrieveSearch(null, getWrappedUserQualifiedId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveSearch_nullIndividualId_throws() throws StandardErrorMsg {
        RetrieveSearchForProviderOrganisation retrieve = new RetrieveSearchForProviderOrganisation();
        retrieve.setBatchIdentifier("dummy");
        clientNoID.retrieveSearch(retrieve, null);
    }

    private static SearchForProviderOrganisationBatchAsyncClient getMedicareTestClient() {
        try {
            return new SearchForProviderOrganisationBatchAsyncClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(),
                    getProductHeader(), getSigningPrivateKeyForMedicare(),
                    getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static SearchForProviderOrganisationBatchAsyncClient getMedicareTestClientNoId() {
        try {
            return new SearchForProviderOrganisationBatchAsyncClient(MEDICARE_ENDPOINT_URL, getWrappedProductHeader(),
                    getSigningPrivateKeyForMedicare(), getSigningCertificateKeyForMedicare(),
                    getSslSocketFactoryForMedicare(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
