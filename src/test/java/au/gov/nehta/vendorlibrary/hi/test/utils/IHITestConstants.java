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

package au.gov.nehta.vendorlibrary.hi.test.utils;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.*;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.security.cert.X509Certificate;

/**
 * Test utility class which provide IHI testing utility methods and constants.
 */
public class IHITestConstants {

    public static final String MEDICARE_ENDPOINT_URL = "https://www5.medicareaustralia.gov.au/cert/soap/services/";
    public static final String DRP_IHI_SEARCH_ENDPOINT_URL = "https://nehta-drp-iis.nehta.net.au/MCAR3/ConsumerSearchIHI/Service.svc";

    public static final String EMPTY = "";

    public static final String USER_QUALIFIER = "http://ns.tashealth.gov.au/id/hiclient/userid/1.0";
    public static final String USER_QUALIFIED_ID = "Identifier";

    public static final String VENDOR_QUALIFIER_ID = "TAS00001";
    public static final String VENDOR_QUALIFIER = "http://ns.medicareaustralia.gov.au/mcaVendorId/1.0";

    public static final String PRODUCT_PLATFORM = "Microsoft Windows XP SP3";
    public static final String PRODUCT_NAME = "TasHealthHIClient";
    public static final String PRODUCT_VERSION = "1.0";

    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER = "http://ns.electronichealth.net.au/id/hi/ihi/1.0/8003601240022579";
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_MEDICARE_CARD_NUMBER = "2950141861";
    public static final Integer MEDICARE_GENERIC_TEST_INDIVIDUAL_MEDICARE_IRN = 1;
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME = "Wood";
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME = "Jessica";
    public static final XMLGregorianCalendar MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH = TimeUtility.getXMLGregorianDate("20021212");
    public static final SexType MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX = SexType.F;

    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_NUMBER = "Test Number";
    public static final PostalDeliveryType MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_TYPE_CODE = PostalDeliveryType.GPO_BOX;
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_SUBURB = "Brisbane";
    public static final StateType MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_STATE = StateType.QLD;
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTCODE = "4000";

    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NUMBER = "21";
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NAME = "Ross";
    public static final StreetType MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_TYPE = StreetType.RD;
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_SUBURB = "Queanbeyan";
    public static final StateType MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STATE = StateType.NSW;
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_POST_CODE = "2620";

    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_LINE = "1 Wall Street";
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_STATE_PROVINCE = "New York";
    public static final String MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_POSTCODE = "1234";
    public static final CountryType MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_COUNTRY = CountryType.VALUE_1;


    public static final String MEDICARE_DVA_TEST_INDIVIDUAL_IHI_NUMBER = "http://ns.electronichealth.net.au/id/hi/ihi/1.0/8003604567897656";
    public static final String MEDICARE_DVA_TEST_INDIVIDUAL_DVA_FILE_NUMBER = "N 908030D";
    public static final String MEDICARE_DVA_TEST_INDIVIDUAL_FAMILY_NAME = "Mccane";
    public static final String MEDICARE_DVA_TEST_INDIVIDUAL_GIVEN_NAME = "Amy";
    public static final XMLGregorianCalendar MEDICARE_DVA_TEST_INDIVIDUAL_DATE_OF_BIRTH = TimeUtility.getXMLGregorianDate("19601212");
    public static final SexType MEDICARE_DVA_TEST_INDIVIDUAL_SEX = SexType.F;

    public static final String DRP_TEST_INDIVIDUAL_IHI_NUMBER = "http://ns.electronichealth.net.au/id/hi/ihi/1.0/8003609876543210";
    public static final String DRP_TEST_INDIVIDUAL_MEDICARE_CARD_NUMBER = "8921319706";
    public static final Integer DRP_TEST_INDIVIDUAL_MEDICARE_IRN = 1;

    public static final String DRP_TEST_INDIVIDUAL_DVA_FILE_NUMBER = "1";
    public static final String DRP_TEST_INDIVIDUAL_FAMILY_NAME = "Citizen";
    public static final String DRP_TEST_INDIVIDUAL_GIVEN_NAME = "Fred";
    public static final XMLGregorianCalendar DRP_TEST_INDIVIDUAL_DATE_OF_BIRTH = TimeUtility.getXMLGregorianDate("19800101");
    public static final SexType DRP_TEST_INDIVIDUAL_SEX = SexType.M;

    public static final String DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_NUMBER = "Test Number";
    public static final PostalDeliveryType DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_TYPE_CODE = PostalDeliveryType.GPO_BOX;
    public static final String DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_SUBURB = "Brisbane";
    public static final StateType DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_STATE = StateType.QLD;
    public static final String DRP_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTCODE = "4000";

    public static final String DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NUMBER = "10";
    public static final String DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NAME = "Browning";
    public static final StreetType DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_TYPE = StreetType.ST;
    public static final String DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_SUBURB = "West End";
    public static final StateType DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STATE = StateType.QLD;
    public static final String DRP_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_POST_CODE = "4101";

    public static final String DRP_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_LINE = "1 Wall Street";
    public static final String DRP_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_STATE_PROVINCE = "New York";
    public static final String DRP_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_POSTCODE = "1234";
    public static final CountryType DRP_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_COUNTRY = CountryType.VALUE_1;

    public static final String RESOURCES_DIR = "./src/test/resources/";

    public static final String PRIVATE_KEY_STORE_TYPE = "JKS";
    public static final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
    public static final String PRIVATE_KEY_STORE_PASSWORD = "password";
    public static final String DRP_PRIVATE_KEY_ALIAS = "8003630000000004";
    // Using ZedMed HPIO instead of Tas. Need to include Tasmania HPIO in DRP.
    public static final String MEDICARE_PRIVATE_KEY_ALIAS = "8003628233352432";// "8003630833334588";// "8003620000030192";
    public static final String PRIVATE_KEY_PASSWORD = "password";


    public static final String TRUST_STORE_TYPE = "JKS";
    public static final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
    public static final String TRUST_STORE_PASSWORD = "password";

    public static QualifiedId getUserQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();
        qualifiedId.setId(USER_QUALIFIED_ID);
        qualifiedId.setQualifier(USER_QUALIFIER);
        return qualifiedId;
    }

    //  <ns2:productName>Product Name</ns2:productName>
//  <ns2:productVersion>Product Version</ns2:productVersion>
//  <ns2:platform>Windows XP SP3</ns2:platform>
    public static ProductType getProductHeader() {
        ProductType productHeader = new ProductType();
        productHeader.setPlatform(PRODUCT_PLATFORM);
        productHeader.setProductName(PRODUCT_NAME);
        productHeader.setProductVersion(PRODUCT_VERSION);
        productHeader.setVendor(getVendorQualifiedId());
        return productHeader;
    }

    public static QualifiedId getVendorQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();
        qualifiedId.setId(VENDOR_QUALIFIER_ID);
        qualifiedId.setQualifier(VENDOR_QUALIFIER);
        return qualifiedId;
    }

    public static void setSystemVariablesForTest() {
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        // Enable this for SSL debugging purpose.
        //    System.setProperty("javax.net.debug", "ssl, handshake");
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
    }

    public static PrivateKey getSigningPrivateKeyForMedicare() throws GeneralSecurityException {
        return getSigningPrivateKey(MEDICARE_PRIVATE_KEY_ALIAS);
    }

    public static PrivateKey getSigningPrivateKeyForRefPlatform() throws GeneralSecurityException {
        return getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS);
    }

    private static PrivateKey getSigningPrivateKey(String privateKeyAlias) throws GeneralSecurityException {
        return KeystoreUtil.getSigningPrivateKey(PRIVATE_KEY_STORE_TYPE,
                PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, privateKeyAlias);
    }

    public static X509Certificate getSigningCertificateKeyForMedicare() throws GeneralSecurityException {
        return getSigningCertificate(MEDICARE_PRIVATE_KEY_ALIAS);
    }

    public static X509Certificate getSigningCertificateKeyForRefPlatform() throws GeneralSecurityException {
        return getSigningCertificate(DRP_PRIVATE_KEY_ALIAS);
    }

    private static X509Certificate getSigningCertificate(String certificateAlias) throws GeneralSecurityException {
        return KeystoreUtil.getSigningCertificate(PRIVATE_KEY_STORE_TYPE,
                PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, certificateAlias);
    }

    public static SSLSocketFactory getSslSocketFactoryForMedicare()
            throws GeneralSecurityException, IOException {
        return getSslSocketFactory(MEDICARE_PRIVATE_KEY_ALIAS);
    }

    public static SSLSocketFactory getSslSocketFactoryForRefPlatform()
            throws GeneralSecurityException, IOException {
        return getSslSocketFactory(DRP_PRIVATE_KEY_ALIAS);
    }

    public static SSLSocketFactory getSslSocketFactory(String privateKeyAlias)
            throws GeneralSecurityException, IOException {

        KeyStore privateKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
        KeyStore trustStore = loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_FILE, TRUST_STORE_PASSWORD);

        // Check private key can be retrieved
        checkPrivateKey(privateKeyStore, PRIVATE_KEY_PASSWORD.toCharArray(), privateKeyAlias);

        // Build the key managers.
        final KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        kmFactory.init(privateKeyStore, PRIVATE_KEY_PASSWORD.toCharArray());
        final KeyManager[] keyManagers = kmFactory.getKeyManagers();
        for (int i = 0; i < keyManagers.length; i++) {
            if (keyManagers[i] instanceof X509KeyManager) {
                keyManagers[i] = new AliasForcingX509KeyManager((X509KeyManager) keyManagers[i], privateKeyAlias);
            }
        }

        // Build the trust managers.
        final TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        tmFactory.init(trustStore);
        final TrustManager[] trustManagers = tmFactory.getTrustManagers();

        // Build the SSLSocketFactory.
        final SSLContext context = SSLContext.getInstance("TLS");

        context.init(keyManagers, trustManagers, null);

        return context.getSocketFactory();
    }

    private static KeyStore loadKeyStore(String type, String file, String password) throws GeneralSecurityException, IOException {

        KeyStore keyStore = KeyStore.getInstance(type);
        keyStore.load(new FileInputStream(file), password.toCharArray());
        return keyStore;
    }

    /*
     * Check that there is a private key in the key store with the given alias that can be retrieved using the given
     * key password.
     */

    private static void checkPrivateKey(final KeyStore keyStore, final char[] keyPassword, final String keyAlias)
            throws GeneralSecurityException {
        // Check key store has alias
        if (!keyStore.containsAlias(keyAlias)) {
            final String errorMsg = "The SSLSocketFactory keystore doesn't have key alias '" + keyAlias + "'.";
            throw new GeneralSecurityException(errorMsg);
        }

        // Check key store has private key entry
        if (!keyStore.isKeyEntry(keyAlias)) {
            final String errorMsg = "The SSLSocketFactory keystore doesn't have a private key for alias '" + keyAlias + "'.";
            throw new GeneralSecurityException(errorMsg);
        }

        // Check password works
        try {
            keyStore.getKey(keyAlias, keyPassword);
        } catch (final UnrecoverableKeyException e) {
            final String errorMsg = "Couldn't recover the private key in the SSLSocketFactory keystore."
                    + " The most likely reason is that the key password is wrong.";
            throw new GeneralSecurityException(errorMsg, e);
        }
    }

    private static class AliasForcingX509KeyManager implements X509KeyManager {

        private final X509KeyManager baseKM;
        private final String keyAlias;

        public AliasForcingX509KeyManager(final X509KeyManager keyManager, final String keyAlias) {
            this.baseKM = keyManager;
            this.keyAlias = keyAlias;
        }

        public String chooseClientAlias(final String[] keyType,
                                        final Principal[] issuers,
                                        final Socket socket) {
            return this.keyAlias;
        }

        public String chooseServerAlias(final String keyType,
                                        final Principal[] issuers,
                                        final Socket socket) {
            return this.baseKM.chooseServerAlias(keyType, issuers, socket);
        }

        public X509Certificate[] getCertificateChain(final String alias) {
            return this.baseKM.getCertificateChain(alias);
        }

        public String[] getClientAliases(final String keyType, final Principal[] issuers) {
            return this.baseKM.getClientAliases(keyType, issuers);
        }

        public PrivateKey getPrivateKey(final String alias) {
            return this.baseKM.getPrivateKey(alias);
        }

        public String[] getServerAliases(final String keyType, final Principal[] issuers) {
            return this.baseKM.getServerAliases(keyType, issuers);
        }
    }
}
