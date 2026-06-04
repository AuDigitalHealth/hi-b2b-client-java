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
package au.gov.nehta.vendorlibrary.hi.wsdl;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * Root directory for the licensed HI WSDL and XSD tree at runtime. The layout must match the
 * Services Australia / ADHA bundle: immediate children {@code wsdl/} and {@code schema/} (same as
 * {@code wsdls/xml/} in a source checkout). WSDL files may live in nested folders under {@code wsdl/};
 * {@link au.gov.nehta.vendorlibrary.ws.WebServiceClientUtil} resolves {@code @WebServiceClient#wsdlLocation()}
 * filenames by searching under {@code wsdl/}.
 * <p>
 * Resolution order for the effective root (same key name everywhere): value set with
 * {@link #setRoot(Path)}, then a non-blank process environment variable {@code HI_WSDL_ARTIFACT_ROOT},
 * then the same key in {@code local.properties} in the process working directory (next to
 * {@code pom.xml} in a typical checkout). That file is re-read whenever the root is resolved and
 * no programmatic root or environment override applies, so the path can be maintained like other
 * local configuration without restarting the JVM before first class load. Finally, the JVM system
 * property {@code HI_WSDL_ARTIFACT_ROOT}.
 * <p>
 * Maven resolves {@code hi.wsdl.tree.root} from the POM default, {@code -Dhi.wsdl.tree.root},
 * {@code HI_WSDL_TREE_ROOT}, or the effective user settings for that invocation; the build does not
 * read {@code HI_WSDL_ARTIFACT_ROOT}. See README.md (runtime) and CONTRIBUTING.md / wsdls/readme.txt (build).
 */
public final class HiWsdlArtifactRoot {

    /**
     * Environment variable, {@code local.properties} key, and system property name for the
     * directory that contains {@code wsdl/} and {@code schema/}.
     */
    public static final String HI_WSDL_ARTIFACT_ROOT = "HI_WSDL_ARTIFACT_ROOT";

    private static final AtomicReference<Path> CONFIGURED = new AtomicReference<>();

    private static final ThreadLocal<Boolean> SKIP_LOCAL_PROPERTIES_FILE_FOR_TESTS = new ThreadLocal<>();

    static void setSkipLocalPropertiesFileForUnitTests(boolean skip) {
        if (skip) {
            SKIP_LOCAL_PROPERTIES_FILE_FOR_TESTS.set(Boolean.TRUE);
        } else {
            SKIP_LOCAL_PROPERTIES_FILE_FOR_TESTS.remove();
        }
    }

    private static String readHiWsdlArtifactRootFromLocalPropertiesFile() {
        if (Boolean.TRUE.equals(SKIP_LOCAL_PROPERTIES_FILE_FOR_TESTS.get())) {
            return null;
        }
        Path local = Paths.get(System.getProperty("user.dir", ".")).resolve("local.properties");
        if (!Files.isRegularFile(local)) {
            return null;
        }
        Properties p = new Properties();
        try (Reader r = Files.newBufferedReader(local, StandardCharsets.UTF_8)) {
            p.load(r);
        } catch (IOException e) {
            return null;
        }
        String v = p.getProperty(HI_WSDL_ARTIFACT_ROOT);
        if (v == null || v.trim().isEmpty()) {
            return null;
        }
        return v.trim();
    }

    private HiWsdlArtifactRoot() {
    }

    /**
     * Sets the artifact root for this JVM, overriding environment, {@code local.properties}, and
     * system property. Pass {@code null} to clear a programmatic value so those sources are used
     * again.
     */
    public static void setRoot(Path root) {
        CONFIGURED.set(root);
    }

    /**
     * @return explicit {@link #setRoot(Path)} value, else non-blank env {@code HI_WSDL_ARTIFACT_ROOT},
     *         else the same key in {@code local.properties} (working directory), else non-blank
     *         system property, else {@code null}
     */
    public static Path getRootOrNull() {
        Path p = CONFIGURED.get();
        if (p != null) {
            return p;
        }
        String env = System.getenv(HI_WSDL_ARTIFACT_ROOT);
        if (env != null && !env.trim().isEmpty()) {
            return Paths.get(env.trim());
        }
        String fromFile = readHiWsdlArtifactRootFromLocalPropertiesFile();
        if (fromFile != null && !fromFile.isEmpty()) {
            return Paths.get(fromFile);
        }
        String prop = System.getProperty(HI_WSDL_ARTIFACT_ROOT);
        if (prop != null && !prop.trim().isEmpty()) {
            return Paths.get(prop.trim());
        }
        return null;
    }

    /**
     * Locates a WSDL file under the configured artifact root.
     *
     * @param wsdlLocation value from {@code @WebServiceClient#wsdlLocation()} (typically a bare
     *                     {@code *.wsdl} filename)
     * @return a {@code file:} URL to an existing file, or {@code null} if there is no root, no
     * {@code wsdl/} directory, or no matching file
     */
    public static URL findWsdlUrl(String wsdlLocation) {
        if (wsdlLocation == null || wsdlLocation.isEmpty()) {
            return null;
        }
        Path root = getRootOrNull();
        if (root == null) {
            return null;
        }
        Path normalizedRoot = root.toAbsolutePath().normalize();
        Path relative = Paths.get(wsdlLocation);
        try {
            if (relative.getNameCount() > 1 || wsdlLocation.contains("/") || wsdlLocation.contains("\\")) {
                Path direct = normalizedRoot.resolve(relative).normalize();
                if (direct.startsWith(normalizedRoot) && Files.isRegularFile(direct)) {
                    return direct.toUri().toURL();
                }
                Path underWsdl = normalizedRoot.resolve("wsdl").resolve(relative).normalize();
                if (underWsdl.startsWith(normalizedRoot) && Files.isRegularFile(underWsdl)) {
                    return underWsdl.toUri().toURL();
                }
            }
            String baseName = relative.getFileName().toString();
            Path wsdlDir = normalizedRoot.resolve("wsdl");
            if (!Files.isDirectory(wsdlDir)) {
                return null;
            }
            try (Stream<Path> stream = Files.walk(wsdlDir)) {
                Optional<Path> hit = stream
                        .filter(Files::isRegularFile)
                        .filter(path -> Objects.equals(path.getFileName().toString(), baseName))
                        .findFirst();
                if (!hit.isPresent()) {
                    return null;
                }
                return hit.get().toUri().toURL();
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }
}
