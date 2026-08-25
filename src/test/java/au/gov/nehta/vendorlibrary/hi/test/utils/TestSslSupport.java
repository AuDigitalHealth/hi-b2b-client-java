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

import javax.net.ssl.*;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Builds mutually-authenticated TLS for integration tests: client keys from the test keystore and
 * server trust using the project truststore <strong>plus</strong> the JVM default CA anchors (so
 * public HI endpoints signed by well-known CAs validate without copying the whole cacerts file).
 */
public final class TestSslSupport {

    private TestSslSupport() {
    }

    public static SSLSocketFactory buildMutualTlsSocketFactory(
            KeyStore privateKeyStore,
            char[] keyStorePassword,
            String privateKeyAlias,
            KeyStore supplementalTrustStore) throws GeneralSecurityException {

        checkPrivateKey(privateKeyStore, keyStorePassword, privateKeyAlias);

        KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmFactory.init(privateKeyStore, keyStorePassword);
        KeyManager[] keyManagers = kmFactory.getKeyManagers();
        for (int i = 0; i < keyManagers.length; i++) {
            if (keyManagers[i] instanceof X509KeyManager) {
                keyManagers[i] = new AliasForcingX509KeyManager((X509KeyManager) keyManagers[i], privateKeyAlias);
            }
        }

        TrustManagerFactory customTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        customTmf.init(supplementalTrustStore);
        X509TrustManager customTm = firstX509(customTmf.getTrustManagers());

        TrustManagerFactory defaultTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        defaultTmf.init((KeyStore) null);
        X509TrustManager defaultTm = firstX509(defaultTmf.getTrustManagers());

        TrustManager[] trustManagers = {
                new CompositeX509TrustManager(customTm, defaultTm)
        };

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(keyManagers, trustManagers, null);
        return context.getSocketFactory();
    }

    static void checkPrivateKey(KeyStore keyStore, char[] keyPassword, String keyAlias)
            throws GeneralSecurityException {
        if (!keyStore.containsAlias(keyAlias)) {
            throw new GeneralSecurityException(
                    "The SSLSocketFactory keystore doesn't have key alias '" + keyAlias + "'.");
        }
        if (!keyStore.isKeyEntry(keyAlias)) {
            throw new GeneralSecurityException(
                    "The SSLSocketFactory keystore doesn't have a private key for alias '" + keyAlias + "'.");
        }
        try {
            keyStore.getKey(keyAlias, keyPassword);
        } catch (UnrecoverableKeyException e) {
            throw new GeneralSecurityException(
                    "Couldn't recover the private key in the SSLSocketFactory keystore."
                            + " The most likely reason is that the key password is wrong.",
                    e);
        } catch (KeyStoreException | NoSuchAlgorithmException e) {
            throw new GeneralSecurityException(e);
        }
    }

    private static X509TrustManager firstX509(TrustManager[] managers) {
        for (TrustManager tm : managers) {
            if (tm instanceof X509TrustManager) {
                return (X509TrustManager) tm;
            }
        }
        throw new IllegalStateException("No X509TrustManager in factory");
    }

    private static final class CompositeX509TrustManager implements X509TrustManager {
        private final X509TrustManager primary;
        private final X509TrustManager fallback;

        CompositeX509TrustManager(X509TrustManager primary, X509TrustManager fallback) {
            this.primary = primary;
            this.fallback = fallback;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                primary.checkClientTrusted(chain, authType);
            } catch (CertificateException e) {
                fallback.checkClientTrusted(chain, authType);
            }
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                primary.checkServerTrusted(chain, authType);
            } catch (CertificateException e) {
                fallback.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] a = primary.getAcceptedIssuers();
            X509Certificate[] b = fallback.getAcceptedIssuers();
            List<X509Certificate> merged = new ArrayList<>(a.length + b.length);
            for (X509Certificate c : a) {
                merged.add(c);
            }
            for (X509Certificate c : b) {
                merged.add(c);
            }
            return merged.toArray(new X509Certificate[0]);
        }
    }

    private static final class AliasForcingX509KeyManager implements X509KeyManager {

        private final X509KeyManager baseKM;
        private final String keyAlias;

        AliasForcingX509KeyManager(X509KeyManager keyManager, String keyAlias) {
            this.baseKM = keyManager;
            this.keyAlias = keyAlias;
        }

        @Override
        public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
            return this.keyAlias;
        }

        @Override
        public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
            return this.baseKM.chooseServerAlias(keyType, issuers, socket);
        }

        @Override
        public X509Certificate[] getCertificateChain(String alias) {
            return this.baseKM.getCertificateChain(alias);
        }

        @Override
        public String[] getClientAliases(String keyType, Principal[] issuers) {
            return this.baseKM.getClientAliases(keyType, issuers);
        }

        @Override
        public PrivateKey getPrivateKey(String alias) {
            return this.baseKM.getPrivateKey(alias);
        }

        @Override
        public String[] getServerAliases(String keyType, Principal[] issuers) {
            return this.baseKM.getServerAliases(keyType, issuers);
        }
    }
}
