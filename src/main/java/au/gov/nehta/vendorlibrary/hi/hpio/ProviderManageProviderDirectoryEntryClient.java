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

package au.gov.nehta.vendorlibrary.hi.hpio;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.hi.client.BaseClient_3;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.*;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * An implementation of a Provider Manage Provider Directory Entry client.
 * This class may be used to connect to the Medicare HI Service to do Manage Provider Directory Entry operations
 */
public class ProviderManageProviderDirectoryEntryClient extends BaseClient_3<ProviderManageProviderDirectoryEntryPortType> {

    /**
     * Constructor which creates a new Client with an endpoint and an SSL Socket Factory.
     *
     * @param serviceEndpoint       the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param productHeader         The product header data for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param signingPrivateKey     The private key to be used for signing (Mandatory)
     * @param signingCertificate    The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory      the SSL Socket Factory to be used when connecting to the Web Service provider
     */
    public ProviderManageProviderDirectoryEntryClient(final String serviceEndpoint,
                                                      final QualifiedId individualQualifiedId,
                                                      final ProductType productHeader,
                                                      final PrivateKey signingPrivateKey,
                                                      final X509Certificate signingCertificate,
                                                      final SSLSocketFactory sslSocketFactory) {
        this(
                serviceEndpoint,
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
     * Constructor which creates a new Client with an endpoint and an SSL Socket Factory.
     * This constructor uses the wrapped version of the product header, and expects qualified id to be supplied per request
     *
     * @param serviceEndpoint      the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader        The product header data for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param signingPrivateKey    The private key to be used for signing (Mandatory)
     * @param signingCertificate   The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory     the SSL Socket Factory to be used when connecting to the Web Service provider
     * @param certificateValidator (optional) a user supplied validator to authenticate the response certificate.
     */
    public ProviderManageProviderDirectoryEntryClient(String serviceEndpoint,
                                                      au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                                      PrivateKey signingPrivateKey,
                                                      X509Certificate signingCertificate,
                                                      SSLSocketFactory sslSocketFactory,
                                                      CertificateValidator certificateValidator
    ) {
        super(
                ProviderManageProviderDirectoryEntryPortType.class,
                ProviderManageProviderDirectoryEntryService.class,
                null,
                null,
                productHeader,
                signingPrivateKey,
                signingCertificate,
                sslSocketFactory,
                serviceEndpoint,
                certificateValidator
        );
    }

    /**
     * Constructor which creates a new ProviderSearchForProviderOrganisation Client with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ProviderSearchHIProviderDirectoryForOrganisation
     *                                service (Optional)
     * @param productHeader           The product header data for connecting to the ProviderSearchHIProviderDirectoryForOrganisation service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        the SSL Socket Factory to be used when connecting to the Web Service provider
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ProviderManageProviderDirectoryEntryClient(final String serviceEndpoint,
                                                      final QualifiedId individualQualifiedId,
                                                      final QualifiedId organisationQualifiedId,
                                                      final ProductType productHeader,
                                                      final PrivateKey signingPrivateKey,
                                                      final X509Certificate signingCertificate,
                                                      final SSLSocketFactory sslSocketFactory,
                                                      CertificateValidator certificateValidator) {

        super(ProviderManageProviderDirectoryEntryPortType.class,
                ProviderManageProviderDirectoryEntryService.class,
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
     * Executes a ManageProviderDirectoryEntry request.
     *
     * @param request the ManageProviderDirectoryEntry request object
     * @return the response from the  ManageProviderDirectoryEntryResponse
     * service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final ManageProviderDirectoryEntryResponse manageProviderDirectoryEntry(ManageProviderDirectoryEntry request)
            throws StandardErrorMsg {

        ArgumentUtils.checkNotNull(request, "request");
        ArgumentUtils.checkNotNullNorBlank(request.getQualifiedIdentifier(), "Qualified Identifier");

        checkUserID();
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.port.manageProviderDirectoryEntry(request, productHolder, getTimestampHeader(), signatureHeader, this.individualQualifiedId);
    }

    /**
     * /**
     * <p>
     * Executes a ManageProviderDirectoryEntry request.
     *
     * @param request the ManageProviderDirectoryEntry request object
     * @return the response from the  ManageProviderDirectoryEntryResponse
     * service
     * @throws StandardErrorMsg if the Web Service call fails.
     */
    public final ManageProviderDirectoryEntryResponse manageProviderDirectoryEntry(ManageProviderDirectoryEntry request, QualifiedId userId) throws StandardErrorMsg {
        ArgumentUtils.checkNotNull(request, "request");
        ArgumentUtils.checkNotNullNorBlank(request.getQualifiedIdentifier(), "Qualified Identifier");

        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.port.manageProviderDirectoryEntry(request, productHolder, getTimestampHeader(), signatureHeader, userId);
    }
}
