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
package au.gov.nehta.vendorlibrary.common.security;

import au.gov.nehta.common.utils.ArgumentUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

public final class KeystoreUtil {

    private static final Logger LOG = Logger.getLogger(KeystoreUtil.class.getName());

    private KeystoreUtil() {
    }

    public static KeyStore loadKeyStore(final String keystoreType, final String keystorePassword,
                                        final String keystorePathname) throws GeneralSecurityException {
        KeyStore keystore;
        InputStream in = null;
        ArgumentUtils.checkNotNullNorBlank(keystoreType, "keystoreType");
        ArgumentUtils.checkNotNullNorBlank(keystorePassword, "keystorePassword");
        ArgumentUtils.checkNotNullNorBlank(keystorePathname, "keystorePathname");
        try {
            keystore = KeyStore.getInstance(keystoreType);

            File store = new File(keystorePathname);
            if (store.exists()) {
                in = new FileInputStream(keystorePathname);
            } else {
                in = null;
            }

            keystore.load(in, keystorePassword.toCharArray());
            if (in != null) {
                in.close();
                in = null;
            } else {
                throw new FileNotFoundException("File not found " + keystorePathname);
            }
            LOG.info("Keystore " + keystorePathname + " loaded");
        } catch (KeyStoreException e) {
            throw new GeneralSecurityException("getInstance(" + keystoreType + ") error", e);
        } catch (FileNotFoundException ex) {
            throw new GeneralSecurityException("Couldn't find truststore " + keystorePathname, ex);
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new GeneralSecurityException(ex.getMessage(), ex);
        } catch (CertificateException ex) {
            throw new GeneralSecurityException("Certificate error", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                throw new GeneralSecurityException(ex.getMessage(), ex);
            }
        }
        return keystore;
    }

    public static X509Certificate getSigningCertificate(final String keystoreType, final String keystorePassword,
                                                        final String keystorePathname, final String certificateAlias)
            throws GeneralSecurityException {
        KeyStore localKeystore = KeystoreUtil.loadKeyStore(keystoreType,
                keystorePassword, keystorePathname);
        return getSigningCertificate(localKeystore, certificateAlias);
    }

    public static X509Certificate getSigningCertificate(final KeyStore keystore, final String certificateAlias)
            throws GeneralSecurityException {
        ArgumentUtils.checkNotNull(keystore, "keystore");
        ArgumentUtils.checkNotNullNorBlank(certificateAlias, "certificateAlias");
        try {
            Certificate certificate = keystore.getCertificate(certificateAlias);
            return (certificate != null) ? (X509Certificate) certificate : null;
        } catch (KeyStoreException e) {
            throw new GeneralSecurityException(e);
        }
    }

    public static PrivateKey getSigningPrivateKey(final String keystoreType, final String keystorePassword,
                                                  final String keystorePathname, final String privateKeyAlias)
            throws GeneralSecurityException {
        KeyStore localKeystore = KeystoreUtil.loadKeyStore(keystoreType,
                keystorePassword, keystorePathname);
        return getSigningPrivateKey(localKeystore, keystorePassword, privateKeyAlias);
    }

    public static PrivateKey getSigningPrivateKey(final KeyStore keystore, final String keystorePassword,
                                                  final String privateKeyAlias)
            throws GeneralSecurityException {
        ArgumentUtils.checkNotNull(keystore, "keystore");
        ArgumentUtils.checkNotNullNorBlank(privateKeyAlias, "privateKeyAlias");
        ArgumentUtils.checkNotNullNorBlank(keystorePassword, "keystorePassword");
        ArgumentUtils.checkNotNull(keystore.containsAlias(privateKeyAlias), "keystore.containsAlias(privateKeyAlias)");
        try {
            Key key = keystore.getKey(privateKeyAlias, keystorePassword
                    .toCharArray());
            return (key != null) ? ((PrivateKey) key) : null;
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            throw new GeneralSecurityException(e);
        }
    }

    public static SSLSocketFactory getSslSocketFactory(String privateKeyStoreType,
                                                       String privateKeyStoreFile,
                                                       String privateKeyStorePassword,
                                                       String privateKeyPassword,
                                                       String privateKeyAlias,
                                                       String trustStoreType,
                                                       String trustStoreFile,
                                                       String trustStorePassword)
            throws GeneralSecurityException {

        KeyStore privateKeyStore = loadKeyStore(privateKeyStoreType, privateKeyStorePassword, privateKeyStoreFile);
        KeyStore trustStore = loadKeyStore(trustStoreType, trustStorePassword, trustStoreFile);

        checkPrivateKey(privateKeyStore, privateKeyPassword.toCharArray(), privateKeyAlias);

        final KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        kmFactory.init(privateKeyStore, privateKeyPassword.toCharArray());
        final KeyManager[] keyManagers = kmFactory.getKeyManagers();
        for (int i = 0; i < keyManagers.length; i++) {
            if (keyManagers[i] instanceof X509KeyManager) {
                keyManagers[i] = new AliasForcingX509KeyManager((X509KeyManager) keyManagers[i], privateKeyAlias);
            }
        }

        final TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        tmFactory.init(trustStore);
        final TrustManager[] trustManagers = tmFactory.getTrustManagers();

        final SSLContext context = SSLContext.getInstance("TLS");

        context.init(keyManagers, trustManagers, null);

        return context.getSocketFactory();
    }

    private static void checkPrivateKey(final KeyStore keyStore, final char[] keyPassword, final String keyAlias)
            throws GeneralSecurityException {
        if (!keyStore.containsAlias(keyAlias)) {
            final String errorMsg = "The SSLSocketFactory keystore doesn't have key alias '" + keyAlias + "'.";
            throw new GeneralSecurityException(errorMsg);
        }

        if (!keyStore.isKeyEntry(keyAlias)) {
            final String errorMsg = "The SSLSocketFactory keystore doesn't have a private key for alias '" + keyAlias + "'.";
            throw new GeneralSecurityException(errorMsg);
        }

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
