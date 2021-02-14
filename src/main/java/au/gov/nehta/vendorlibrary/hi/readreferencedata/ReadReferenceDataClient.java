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
package au.gov.nehta.vendorlibrary.hi.readreferencedata;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.hi.client.BaseClient_3;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.hi.svc.providerreadreferencedata._3_2.*;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.SignatureContainerType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.TimestampType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * An implementation of a Healthcare Identifiers (HI) - Provider Read Reference service client. This
 * class may be used to connect to the Medicare HI Service to perform ReadReferenceData lookup.
 */
public class ReadReferenceDataClient extends BaseClient_3<ProviderReadReferenceDataPortType> {

    /**
     * Validates the arguments passed for the various operations
     */
    private ArgumentValidator argumentValidator = new ArgumentValidator();

    /**
     * Constructor which creates a new ReadReferenceDataClient with an endpoint and an SSL Socket Factory.
     *
     * @param readReferenceDataServiceEndpoint the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId            The qualified user ID for connecting to the ReadReferenceData service (Mandatory)
     * @param productHeader                    The product header data for connecting to the ReadReferenceData service (Mandatory)
     * @param signingPrivateKey                The private key to be used for signing (Mandatory)
     * @param signingCertificate               The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory                 the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ReadReferenceDataClient(final String readReferenceDataServiceEndpoint,
                                   final QualifiedId individualQualifiedId,
                                   final ProductType productHeader,
                                   final PrivateKey signingPrivateKey,
                                   final X509Certificate signingCertificate,
                                   final SSLSocketFactory sslSocketFactory) {
        this(
                readReferenceDataServiceEndpoint,
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
     * Constructor which creates a new ReadReferenceDataClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         The Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ReadReferenceData service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ReadReferenceData service (Optional)
     * @param productHeader           The product header data for connecting to the ReadReferenceData service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        The SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ReadReferenceDataClient(final String serviceEndpoint,
                                   final QualifiedId individualQualifiedId,
                                   final QualifiedId organisationQualifiedId,
                                   final ProductType productHeader,
                                   final PrivateKey signingPrivateKey,
                                   final X509Certificate signingCertificate,
                                   final SSLSocketFactory sslSocketFactory,
                                   CertificateValidator certificateValidator
    ) {

        super(ProviderReadReferenceDataPortType.class,
                ProviderReadReferenceDataService.class,
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
     * Constructor which creates a new ReadReferenceDataClient with an endpoint and an SSL Socket Factory.
     *
     * @param readReferenceDataServiceEndpoint the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId            The qualified user ID for connecting to the ReadReferenceData service (Mandatory)
     * @param productHeader                    The product header data for connecting to the ReadReferenceData service (Mandatory)
     * @param signingPrivateKey                The private key to be used for signing (Mandatory)
     * @param signingCertificate               The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory                 the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ReadReferenceDataClient(final String readReferenceDataServiceEndpoint,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                   final PrivateKey signingPrivateKey,
                                   final X509Certificate signingCertificate,
                                   final SSLSocketFactory sslSocketFactory) {
        this(
                readReferenceDataServiceEndpoint,
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
     * Constructor which creates a new ReadReferenceDataClient with an endpoint and an SSL Socket Factory.
     *
     * @param readReferenceDataServiceEndpoint the Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param productHeader                    The product header data for connecting to the ReadReferenceData service (Mandatory)
     * @param signingPrivateKey                The private key to be used for signing (Mandatory)
     * @param signingCertificate               The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory                 the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     */
    public ReadReferenceDataClient(final String readReferenceDataServiceEndpoint,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                   final PrivateKey signingPrivateKey,
                                   final X509Certificate signingCertificate,
                                   final SSLSocketFactory sslSocketFactory) {
        this(
                readReferenceDataServiceEndpoint,
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
     * Constructor which creates a new ReadReferenceDataClient with an endpoint and an SSL Socket Factory, with
     * the optional contracted service providers HPI-O organisation qualified ID set.
     *
     * @param serviceEndpoint         The Web Service endpoint for the Medicare HI Service interface (Mandatory)
     * @param individualQualifiedId   The qualified user ID for connecting to the ReadReferenceData service (Mandatory)
     * @param organisationQualifiedId The qualified organisation ID for connecting to the ReadReferenceData service (Optional)
     * @param productHeader           The product header data for connecting to the ReadReferenceData service (Mandatory)
     * @param signingPrivateKey       The private key to be used for signing (Mandatory)
     * @param signingCertificate      The certificate to be used for signing (Mandatory)
     * @param sslSocketFactory        The SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
     * @param certificateValidator    (optional) a user supplied validator to authenticate the response certificate.
     */
    public ReadReferenceDataClient(final String serviceEndpoint,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualQualifiedId,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId organisationQualifiedId,
                                   final au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader,
                                   final PrivateKey signingPrivateKey,
                                   final X509Certificate signingCertificate,
                                   final SSLSocketFactory sslSocketFactory,
                                   CertificateValidator certificateValidator
    ) {

        super(ProviderReadReferenceDataPortType.class,
                ProviderReadReferenceDataService.class,
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
     * Retrieves the  current acceptable HI reference data values for the provided readReferenceData .  <br>
     * These element include but are not limited to <br>
     * <li>providerTypeCode</li>
     * <li>providerSpeciality </li>
     * <li>providerSpecialisation</li>
     * <li>organisationTypeCode</li>
     * <li>organisationService</li>
     * <li>organisationServiceUnit</li>
     * <li>operatingSystem</li>
     *
     * @param readReferenceData containing a list of element names. (Mandatory)
     * @return Zero or more acceptable reference values for the referenceList.
     * @throws StandardErrorMsg in an event of web service failure.
     */
    public final ReadReferenceDataResponse readReferenceData(ReadReferenceData readReferenceData) throws StandardErrorMsg {
        checkUserID();
        argumentValidator.checkReadReferenceData(readReferenceData);

        TimestampType timestampHeader = new TimestampType();
        timestampHeader.setCreated(TimeUtility.nowXMLGregorianCalendar());
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.getPort().readReferenceData(
                readReferenceData,
                productHolder,
                timestampHeader,
                signatureHeader,
                this.individualQualifiedId,
                this.organisationQualifiedId);
    }

    /**
     * Retrieves the  current acceptable HI reference data values for the provided readReferenceData .  <br>
     * These element include but are not limited to <br>
     * <li>providerTypeCode</li>
     * <li>providerSpeciality </li>
     * <li>providerSpecialisation</li>
     * <li>organisationTypeCode</li>
     * <li>organisationService</li>
     * <li>organisationServiceUnit</li>
     * <li>operatingSystem</li>
     *
     * @param readReferenceData containing a list of element names. (Mandatory)
     * @param individualId      the qualified user id of the user making the request
     * @return Zero or more acceptable reference values for the referenceList.
     * @throws StandardErrorMsg in an event of web service failure.
     */
    public final ReadReferenceDataResponse readReferenceData(ReadReferenceData readReferenceData,
                                                             au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId individualId) throws StandardErrorMsg {
        argumentValidator.checkReadReferenceData(readReferenceData);

        TimestampType timestampHeader = new TimestampType();
        timestampHeader.setCreated(TimeUtility.nowXMLGregorianCalendar());
        Holder<SignatureContainerType> signatureHeader = null;
        Holder<ProductType> productHolder = new Holder<>(productHeader);
        return this.getPort().readReferenceData(
                readReferenceData,
                productHolder,
                timestampHeader,
                signatureHeader,
                individualId.as3Type(),
                this.organisationQualifiedId);
    }

    /**
     * Validates the ReadReferenceData types are correct for webservice invocation.
     */
    public static class ArgumentValidator {

        /**
         * Checks that the provided readReferenceData is valid.
         *
         * @param readReferenceData the ReadReferenceData to be validated (Mandatory)
         */
        public final void checkReadReferenceData(ReadReferenceData readReferenceData) {
            ArgumentUtils.checkNotNull(readReferenceData, "readReferenceData");
            checkElementName(readReferenceData.getElementNames());
        }

        /**
         * Checks that the provided element name list is valid.
         *
         * @param elementNamesList the list of element name to be validated (Mandatory)
         */
        public final void checkElementName(List<String> elementNamesList) {
            if (ArgumentUtils.isNullOrEmpty(elementNamesList)) {
                throw new IllegalArgumentException("The element names list must contain one or more elements");
            }
            for (String elementName : elementNamesList) {
                ArgumentUtils.checkNotNullNorBlank(elementName, "elementName");
            }
        }
    }
}
