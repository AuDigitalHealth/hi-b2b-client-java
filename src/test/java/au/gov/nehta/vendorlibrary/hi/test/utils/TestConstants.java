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
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.security.cert.X509Certificate;

/**
 * Test Utils class which provide HI testing utility methods and constants.
 */
public class TestConstants {

    public static final String MEDICARE_ENDPOINT_URL = "https://www5.medicareaustralia.gov.au/cert/soap/services/";
    public static final String DRP_HPII_SEARCH_ENDPOINT_URL = "https://nehta-drp-iis.nehta.net.au/MCAR3/ProviderSearchHIProviderDirectoryForIndividual/Service.svc";
    public static final String DRP_HPIO_SEARCH_ENDPOINT_URL = "https://nehta-drp-iis.nehta.net.au/MCAR3/ProviderSearchHIProviderDirectoryForOrganisation/Service.svc";

    public static final String EMPTY = "";

    public static final String USER_QUALIFIER = "http://ns.tashealth.gov.au/id/hiclient/userid/1.0";
    public static final String USER_QUALIFIED_ID = "Identifier";

    public static final String VENDOR_QUALIFIFER_ID = "TAS00001";
    public static final String VENDOR_QUALIFIER = "http://ns.medicareaustralia.gov.au/mcaVendorId/1.0";

    public static final String HPIO_QUALIFIER_ID = "8003624166667003";
    public static final String HPIO_QUALIFIER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0";

    public static final String NEHTA_HPIO_QUALIFIER_ID = "8003626566687887";
    public static final String NEHTA_HPIO_QUALIFIER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0";


    public static final String PRODUCT_PLATFORM = "Microsoft Windows XP SP3";
    public static final String PRODUCT_NAME = "TasHealthHIClient";
    public static final String PRODUCT_VERSION = "1.0";

    /**
     * Length of Date string for YYYYMMHH
     */
    public static final int DATE_LENGTH = 8;

    public static final String RESOURCES_DIR = "./src/test/resources/";

    public static final String PRIVATE_KEY_STORE_TYPE = "JKS";
    public static final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
    public static final String PRIVATE_KEY_STORE_PASSWORD = "password";
    public static final String DRP_PRIVATE_KEY_ALIAS = "8003630000000004";
    // Using ZedMed HPIO instead of Tas. Need to include Tasmania HPIO in DRP.
    public static final String MEDICARE_PRIVATE_KEY_ALIAS = "8003629900035144";
    public static final String MEDICARE_CSP_PRIVATE_KEY_ALIAS = "8003630833334588";
    public static final String PRIVATE_KEY_PASSWORD = "password";

    public static final String TRUST_STORE_TYPE = "JKS";
    public static final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
    public static final String TRUST_STORE_PASSWORD = "password";

    public static final String INVALID_KEYSTORE_FILE = PRIVATE_KEY_STORE_FILE + "Invalid";
    public static final String BLANK_KEYSTORE_FILENAME = RESOURCES_DIR + "TestTruststore.jks";

    public static final String INVALID = "Invalid";

    public static QualifiedId getUserQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();
        qualifiedId.setId(USER_QUALIFIED_ID);
        qualifiedId.setQualifier(USER_QUALIFIER);
        return qualifiedId;
    }

    public static au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId getWrappedUserQualifiedId() {
        au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId qualifiedId = new au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId(USER_QUALIFIER, USER_QUALIFIED_ID);
        return qualifiedId;
    }

    public static ProductType getProductHeader() {
        ProductType productHeader = new ProductType();
        productHeader.setPlatform(PRODUCT_PLATFORM);
        productHeader.setProductName(PRODUCT_NAME);
        productHeader.setProductVersion(PRODUCT_VERSION);
        productHeader.setVendor(getVendorQualifiedId());
        return productHeader;
    }

    public static au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType getWrappedProductHeader() {
        au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType productHeader =
                new au.gov.nehta.vendorlibrary.hi.client.wrapped.ProductType(PRODUCT_PLATFORM,
                        PRODUCT_NAME,
                        PRODUCT_VERSION,
                        getWrappedVendorQualifiedId()
                );
        return productHeader;
    }

    public static QualifiedId getHpioQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();
        qualifiedId.setId(HPIO_QUALIFIER_ID);
        qualifiedId.setQualifier(HPIO_QUALIFIER);
        return qualifiedId;
    }

    public static QualifiedId getCspHpioQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();
        qualifiedId.setId(NEHTA_HPIO_QUALIFIER_ID);
        qualifiedId.setQualifier(NEHTA_HPIO_QUALIFIER);
        return qualifiedId;
    }

    public static QualifiedId getVendorQualifiedId() {
        QualifiedId qualifiedId = new QualifiedId();
        qualifiedId.setId(VENDOR_QUALIFIFER_ID);
        qualifiedId.setQualifier(VENDOR_QUALIFIER);
        return qualifiedId;
    }

    public static au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId getWrappedVendorQualifiedId() {
        au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId qualifiedId =
                new au.gov.nehta.vendorlibrary.hi.client.wrapped.QualifiedId(VENDOR_QUALIFIER, VENDOR_QUALIFIFER_ID);
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

    public static PrivateKey getCspSigningPrivateKeyForMedicare() throws GeneralSecurityException {
        return getSigningPrivateKey(MEDICARE_CSP_PRIVATE_KEY_ALIAS);
    }

    public static PrivateKey getSigningPrivateKeyForRefPlatform() throws GeneralSecurityException {
        return getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS);
    }

    private static PrivateKey getSigningPrivateKey(String privateKeyAlias) throws GeneralSecurityException {
        PrivateKey privateKey = KeystoreUtil.getSigningPrivateKey(PRIVATE_KEY_STORE_TYPE,
                PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, privateKeyAlias);
        return privateKey;
    }

    public static X509Certificate getSigningCertificateKeyForMedicare() throws GeneralSecurityException {
        return getSigningCertificate(MEDICARE_PRIVATE_KEY_ALIAS);
    }

    public static X509Certificate getCspSigningCertificateKeyForMedicare() throws GeneralSecurityException {
        return getSigningCertificate(MEDICARE_CSP_PRIVATE_KEY_ALIAS);
    }

    public static X509Certificate getSigningCertificateKeyForRefPlatform() throws GeneralSecurityException {
        return getSigningCertificate(DRP_PRIVATE_KEY_ALIAS);
    }

    private static X509Certificate getSigningCertificate(String certificateAlias) throws GeneralSecurityException {
        X509Certificate cert = KeystoreUtil.getSigningCertificate(PRIVATE_KEY_STORE_TYPE,
                PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, certificateAlias);
        return cert;
    }

    public static SSLSocketFactory getSslSocketFactoryForMedicare()
            throws GeneralSecurityException, IOException {
        return getSslSocketFactory(MEDICARE_PRIVATE_KEY_ALIAS);
    }

    public static SSLSocketFactory getSslSocketFactoryForCspMedicare() throws IOException, GeneralSecurityException {
        return getSslSocketFactory(MEDICARE_CSP_PRIVATE_KEY_ALIAS);
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
