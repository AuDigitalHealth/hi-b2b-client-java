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

package au.gov.nehta.vendorlibrary.hi.test.utils;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.hi.test.config.TestConfiguration;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

import javax.net.ssl.SSLSocketFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * Test Utils class which provide HI testing utility methods and constants.
 */
public class TestConstants {

    public static final String MEDICARE_ENDPOINT_URL = TestConfiguration.getConfigurationValue(TestConfiguration.HI_MEDICARE_ENDPOINT_BASE,
            "https://www5.medicareaustralia.gov.au/cert/soap/services/");
    public static final String DRP_HPII_SEARCH_ENDPOINT_URL = TestConfiguration.getConfigurationValue(TestConfiguration.HI_DRP_HPII_SEARCH_ENDPOINT_URL,
            "https://nehta-drp-iis.nehta.net.au/MCAR3/ProviderSearchHIProviderDirectoryForIndividual/Service.svc");
    public static final String DRP_HPIO_SEARCH_ENDPOINT_URL = TestConfiguration.getConfigurationValue(TestConfiguration.HI_DRP_HPIO_SEARCH_ENDPOINT_URL,
            "https://nehta-drp-iis.nehta.net.au/MCAR3/ProviderSearchHIProviderDirectoryForOrganisation/Service.svc");

    public static final String EMPTY = "";

    public static final String USER_QUALIFIER = TestConfiguration.getConfigurationValue(TestConfiguration.HI_USER_QUALIFIER,
            "http://ns.tashealth.gov.au/id/hiclient/userid/1.0");
    public static final String USER_QUALIFIED_ID = TestConfiguration.getConfigurationValue(TestConfiguration.HI_USER_QUALIFIED_ID, "Identifier");

    public static final String VENDOR_QUALIFIFER_ID = TestConfiguration.getConfigurationValue(TestConfiguration.HI_VENDOR_QUALIFIED_ID, "TAS00001");
    public static final String VENDOR_QUALIFIER = TestConfiguration.getConfigurationValue(TestConfiguration.HI_VENDOR_QUALIFIER,
            "http://ns.medicareaustralia.gov.au/mcaVendorId/1.0");

    public static final String HPIO_QUALIFIER_ID = TestConfiguration.getConfigurationValue(TestConfiguration.HI_HPIO_QUALIFIED_ID, "8003624166667003");
    public static final String HPIO_QUALIFIER = TestConfiguration.getConfigurationValue(TestConfiguration.HI_HPIO_QUALIFIER,
            "http://ns.electronichealth.net.au/id/hi/hpio/1.0");

    public static final String NEHTA_HPIO_QUALIFIER_ID = TestConfiguration.getConfigurationValue(TestConfiguration.HI_NEHTA_HPIO_QUALIFIED_ID, "8003626566687887");
    public static final String NEHTA_HPIO_QUALIFIER = TestConfiguration.getConfigurationValue(TestConfiguration.HI_NEHTA_HPIO_QUALIFIER,
            "http://ns.electronichealth.net.au/id/hi/hpio/1.0");


    public static final String PRODUCT_PLATFORM = TestConfiguration.getConfigurationValue(TestConfiguration.HI_PRODUCT_PLATFORM, "Microsoft Windows XP SP3");
    public static final String PRODUCT_NAME = TestConfiguration.getConfigurationValue(TestConfiguration.HI_PRODUCT_NAME, "TasHealthHIClient");
    public static final String PRODUCT_VERSION = TestConfiguration.getConfigurationValue(TestConfiguration.HI_PRODUCT_VERSION, "1.0");

    /** Length of date strings formatted as {@code YYYYMMDD}. */
    public static final int DATE_LENGTH = 8;

    public static final String RESOURCES_DIR = "./src/test/resources/";

    public static final String PRIVATE_KEY_STORE_TYPE = TestConfiguration.getConfigurationValue(TestConfiguration.HI_KEYSTORE_TYPE, "JKS");
    public static final String PRIVATE_KEY_STORE_FILE = TestConfiguration.getConfigurationValue(TestConfiguration.HI_KEYSTORE_PATH, RESOURCES_DIR + "keystore.jks");
    public static final String PRIVATE_KEY_STORE_PASSWORD = TestConfiguration.getConfigurationValue(TestConfiguration.HI_KEYSTORE_PASSWORD, "password");
    public static final String DRP_PRIVATE_KEY_ALIAS = TestConfiguration.getConfigurationValue(TestConfiguration.HI_KEY_ALIAS_DRP, "8003630000000004");
    public static final String MEDICARE_PRIVATE_KEY_ALIAS = TestConfiguration.getConfigurationValueWithFallback(
            TestConfiguration.HI_KEY_ALIAS_MEDICARE_HPIO, TestConfiguration.HI_KEY_ALIAS_MEDICARE, "8003629900035144");
    public static final String MEDICARE_CSP_PRIVATE_KEY_ALIAS = TestConfiguration.getConfigurationValue(TestConfiguration.HI_KEY_ALIAS_MEDICARE_CSP, "8003630833334588");
    public static final String PRIVATE_KEY_PASSWORD = TestConfiguration.getConfigurationValue(TestConfiguration.HI_KEY_PASSWORD, "password");

    public static final String TRUST_STORE_TYPE = TestConfiguration.getConfigurationValue(TestConfiguration.HI_TRUSTSTORE_TYPE, "JKS");
    public static final String TRUST_STORE_FILE = TestConfiguration.getConfigurationValue(TestConfiguration.HI_TRUSTSTORE_PATH, RESOURCES_DIR + "truststore.jks");
    public static final String TRUST_STORE_PASSWORD = TestConfiguration.getConfigurationValue(TestConfiguration.HI_TRUSTSTORE_PASSWORD, "password");

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

        return TestSslSupport.buildMutualTlsSocketFactory(
                privateKeyStore,
                PRIVATE_KEY_PASSWORD.toCharArray(),
                privateKeyAlias,
                trustStore);
    }

    private static KeyStore loadKeyStore(String type, String file, String password) throws GeneralSecurityException, IOException {

        KeyStore keyStore = KeyStore.getInstance(type);
        keyStore.load(new FileInputStream(file), password.toCharArray());
        return keyStore;
    }
}
