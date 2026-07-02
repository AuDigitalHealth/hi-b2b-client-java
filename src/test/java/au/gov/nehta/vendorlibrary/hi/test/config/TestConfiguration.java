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

package au.gov.nehta.vendorlibrary.hi.test.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Resolves Healthcare Identifiers integration test settings from the process environment and from
 * {@code local.properties} in the project root (working directory), when that file is present.
 * For each key, resolution order is: non-empty environment variable, then the same key in
 * {@code local.properties}, then the default supplied by the caller. Environment variables override
 * the property file when both define a value.
 * <p>
 * This follows common Java practice: deployment-specific values (passwords, endpoints, registered
 * identifiers) should be supplied externally rather than edited into source and committed.
 */
public final class TestConfiguration {

    public static final String HI_KEYSTORE_TYPE = "HI_KEYSTORE_TYPE";
    public static final String HI_KEYSTORE_PATH = "HI_KEYSTORE_PATH";
    public static final String HI_KEYSTORE_PASSWORD = "HI_KEYSTORE_PASSWORD";
    public static final String HI_KEY_PASSWORD = "HI_KEY_PASSWORD";
    /**
     * Shared Medicare signing key alias when {@link #HI_KEY_ALIAS_MEDICARE_IHI} / {@link #HI_KEY_ALIAS_MEDICARE_HPIO} are unset.
     */
    public static final String HI_KEY_ALIAS_MEDICARE = "HI_KEY_ALIAS_MEDICARE";
    /** Medicare signing key alias for IHI tests ({@code IHITestConstants}). */
    public static final String HI_KEY_ALIAS_MEDICARE_IHI = "HI_KEY_ALIAS_MEDICARE_IHI";
    /** Medicare signing key alias for HPIO/HPII tests ({@code TestConstants}). */
    public static final String HI_KEY_ALIAS_MEDICARE_HPIO = "HI_KEY_ALIAS_MEDICARE_HPIO";
    public static final String HI_KEY_ALIAS_MEDICARE_CSP = "HI_KEY_ALIAS_MEDICARE_CSP";
    public static final String HI_KEY_ALIAS_DRP = "HI_KEY_ALIAS_DRP";
    public static final String HI_TRUSTSTORE_TYPE = "HI_TRUSTSTORE_TYPE";
    public static final String HI_TRUSTSTORE_PATH = "HI_TRUSTSTORE_PATH";
    public static final String HI_TRUSTSTORE_PASSWORD = "HI_TRUSTSTORE_PASSWORD";
    public static final String HI_MEDICARE_ENDPOINT_BASE = "HI_MEDICARE_ENDPOINT_BASE";
    public static final String HI_DRP_IHI_SEARCH_ENDPOINT_URL = "HI_DRP_IHI_SEARCH_ENDPOINT_URL";
    public static final String HI_DRP_HPII_SEARCH_ENDPOINT_URL = "HI_DRP_HPII_SEARCH_ENDPOINT_URL";
    public static final String HI_DRP_HPIO_SEARCH_ENDPOINT_URL = "HI_DRP_HPIO_SEARCH_ENDPOINT_URL";
    public static final String HI_USER_QUALIFIER = "HI_USER_QUALIFIER";
    public static final String HI_USER_QUALIFIED_ID = "HI_USER_QUALIFIED_ID";
    public static final String HI_VENDOR_QUALIFIER = "HI_VENDOR_QUALIFIER";
    public static final String HI_VENDOR_QUALIFIED_ID = "HI_VENDOR_QUALIFIED_ID";
    public static final String HI_HPIO_QUALIFIER = "HI_HPIO_QUALIFIER";
    public static final String HI_HPIO_QUALIFIED_ID = "HI_HPIO_QUALIFIED_ID";
    public static final String HI_NEHTA_HPIO_QUALIFIER = "HI_NEHTA_HPIO_QUALIFIER";
    public static final String HI_NEHTA_HPIO_QUALIFIED_ID = "HI_NEHTA_HPIO_QUALIFIED_ID";
    public static final String HI_PRODUCT_NAME = "HI_PRODUCT_NAME";
    public static final String HI_PRODUCT_VERSION = "HI_PRODUCT_VERSION";
    public static final String HI_PRODUCT_PLATFORM = "HI_PRODUCT_PLATFORM";
    /** Same key as {@link au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot#HI_WSDL_ARTIFACT_ROOT}. */
    public static final String HI_WSDL_ARTIFACT_ROOT = "HI_WSDL_ARTIFACT_ROOT";

    private static final Properties LOCAL = new Properties();

    static {
        Path local = Paths.get(System.getProperty("user.dir", ".")).resolve("local.properties");
        if (Files.isRegularFile(local)) {
            try (Reader r = Files.newBufferedReader(local, StandardCharsets.UTF_8)) {
                LOCAL.load(r);
            } catch (IOException ignored) {
            }
        }
    }

    private TestConfiguration() {
    }

    /**
     * Resolves {@code primaryKey}, then {@code fallbackKey}, then {@code defaultValue}.
     * Each key uses the same rules as {@link #getConfigurationValue(String, String)}.
     *
     * @param primaryKey   the key tried first (for example a scenario-specific alias)
     * @param fallbackKey  the key used when {@code primaryKey} is unset (for example a shared alias)
     * @param defaultValue the value when neither key resolves
     */
    public static String getConfigurationValueWithFallback(final String primaryKey, final String fallbackKey,
            final String defaultValue) {
        String v = lookupNonEmptyValueForKey(primaryKey);
        if (v != null) {
            return v;
        }
        v = lookupNonEmptyValueForKey(fallbackKey);
        if (v != null) {
            return v;
        }
        return defaultValue;
    }

    /**
     * @param key          the {@code HI_*} property or environment variable name
     * @param defaultValue the value when neither environment nor {@code local.properties} defines the key
     */
    public static String getConfigurationValue(final String key, final String defaultValue) {
        String v = lookupNonEmptyValueForKey(key);
        return v != null ? v : defaultValue;
    }

    private static String lookupNonEmptyValueForKey(final String key) {
        final String env = System.getenv(key);
        if (env != null && !env.trim().isEmpty()) {
            return env.trim();
        }
        final String fromFile = LOCAL.getProperty(key);
        if (fromFile != null && !fromFile.trim().isEmpty()) {
            return fromFile.trim();
        }
        return null;
    }
}
