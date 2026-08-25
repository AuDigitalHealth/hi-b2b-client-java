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

import au.gov.nehta.xsp.CertificateValidationException;
import au.gov.nehta.xsp.CertificateValidator;
import au.gov.nehta.xsp.XspException;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

public class SimpleCertificateValidator implements CertificateValidator {

    private final KeyStore truststore;

    public SimpleCertificateValidator(KeyStore truststore) {
        this.truststore = truststore;
    }

    @Override
    public void validate(X509Certificate cert) throws CertificateValidationException, XspException {
        try {
            cert.checkValidity();
            isTrustedCertificate(cert);
        } catch (Exception ex) {
            throw new XspException(ex.getMessage(), ex);
        }
    }

    private boolean isTrustedCertificate(X509Certificate cert) throws KeyStoreException {

        if (truststore == null) {
            return true;
        }

        boolean trusted = false;

        if (cert != null) {
            String alias = truststore.getCertificateAlias(cert);

            if (alias != null) {
                trusted = true;
            }
        }

        return trusted;
    }
}
