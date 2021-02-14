/*
 * Copyright 2011 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.hi.ihi;

import au.gov.nehta.vendorlibrary.hi.client.BaseClient_3;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchsyncrequest._3.ConsumerSearchIHIBatchSyncPortType;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchsyncrequest._3.ConsumerSearchIHIBatchSyncService;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchsyncrequest._3.SearchIHIBatchResponse;
import au.net.electronichealth.ns.hi.svc.consumersearchihibatchsyncrequest._3.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.TimestampType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Healthcare Identifiers (HI) - Individual Healthcare Identifier (IHI) batch search client.
 * This class may be used to connect to the Medicare HI Service to do Consumer Search IHI Synchronized Batch
 * searches.
 */
public class ConsumerSearchIHIBatchSyncClient extends BaseClient_3<ConsumerSearchIHIBatchSyncPortType> {


    /**
     * Constructor which creates a new ConsumerSearchIHIBatchSyncClient with an endpoint and an SSL Socket Factory.
     *
     * @param searchIhiServiceEndpoint the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId    The qualified user ID for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param productHeader            The product header data for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param signingPrivateKey        The private key to be used for signing (Mandatory)
     * @param signingCertificate       The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory         the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ConsumerSearchIHIBatchSyncClient(final String searchIhiServiceEndpoint,
                                            final QualifiedId individualQualifiedId,
                                            final ProductType productHeader,
                                            final PrivateKey signingPrivateKey,
                                            final X509Certificate signingCertificate,
                                            final SSLSocketFactory sslSocketFactory) {
        this(
                searchIhiServiceEndpoint,
                individualQualifiedId,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                null
        );
    }

    /**
     * Constructor which creates a new ConsumerSearchIHIBatchSyncClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ConsumerSearchIHIBatchSync service (Optional)
     * @param productHeader           The product header data for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    private ConsumerSearchIHIBatchSyncClient(final String serviceEndpoint,
                                             final QualifiedId individualQualifiedId,
                                             final QualifiedId organisationQualifiedId,
                                             final ProductType productHeader,
                                             final PrivateKey signingPrivateKey,
                                             final X509Certificate signingCertificate,
                                             final SSLSocketFactory sslSocketFactory,
                                             CertificateValidator certificateValidator
    ) {

        super(ConsumerSearchIHIBatchSyncPortType.class,
                ConsumerSearchIHIBatchSyncService.class,
                individualQualifiedId,
                organisationQualifiedId,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                serviceEndpoint,
                certificateValidator
        );
    }

    /**
     * Constructor which creates a new ConsumerSearchIHIBatchSyncClient with an endpoint and an SSL Socket Factory.
     *
     * @param searchIhiServiceEndpoint the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader            The product header data for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param signingPrivateKey        The private key to be used for signing (Mandatory)
     * @param signingCertificate       The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory         the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ConsumerSearchIHIBatchSyncClient(final String searchIhiServiceEndpoint,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                            final PrivateKey signingPrivateKey,
                                            final X509Certificate signingCertificate,
                                            final SSLSocketFactory sslSocketFactory) {
        this(
                searchIhiServiceEndpoint,
                null,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                null
        );
    }

    /**
     * Constructor which creates a new ConsumerSearchIHIBatchSyncClient with an endpoint and an SSL Socket Factory.
     *
     * @param searchIhiServiceEndpoint the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId    The qualified user ID for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param productHeader            The product header data for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param signingPrivateKey        The private key to be used for signing (Mandatory)
     * @param signingCertificate       The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory         the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ConsumerSearchIHIBatchSyncClient(final String searchIhiServiceEndpoint,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                            final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                            final PrivateKey signingPrivateKey,
                                            final X509Certificate signingCertificate,
                                            final SSLSocketFactory sslSocketFactory) {
        this(
                searchIhiServiceEndpoint,
                individualQualifiedId,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                null
        );
    }

    /**
     * Constructor which creates a new ConsumerSearchIHIBatchSyncClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ConsumerSearchIHIBatchSync service (Optional)
     * @param productHeader           The product header data for connecting to the ConsumerSearchIHIBatchSync service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    private ConsumerSearchIHIBatchSyncClient(final String serviceEndpoint,
                                             final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                             final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId organisationQualifiedId,
                                             final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                             final PrivateKey signingPrivateKey,
                                             final X509Certificate signingCertificate,
                                             final SSLSocketFactory sslSocketFactory,
                                             CertificateValidator certificateValidator) {

        super(ConsumerSearchIHIBatchSyncPortType.class,
                ConsumerSearchIHIBatchSyncService.class,
                individualQualifiedId,
                organisationQualifiedId,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                serviceEndpoint,
                certificateValidator
        );
    }

    /**
     * Executes a SearchIHIBatchSync search.
     *
     * @param request the SearchBatch request object containing the searches to be performed.
     * @return the response from the SearchIHIBatchSync service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIBatchResponse batchSearch(SearchBatch request) throws StandardErrorMsg {
        checkUserID();
        TimestampType timestampHeader = getTimestampHeader();
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHIBatchSync(
                request.getBatch(),
                productHolder,
                timestampHeader,
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId
        );
    }

    /**
     * Executes a SearchIHIBatchSync search.
     *
     * @param request      the SearchBatch request object containing the searches to be performed.
     * @param individualId the qualified user id of the user making the request
     * @return the response from the SearchIHIBatchSync service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final SearchIHIBatchResponse batchSearch(SearchBatch request,
                                                    au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {

        TimestampType timestampHeader = getTimestampHeader();
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return getPort().searchIHIBatchSync(
                request.getBatch(),
                productHolder,
                timestampHeader,
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId
        );
    }
}
