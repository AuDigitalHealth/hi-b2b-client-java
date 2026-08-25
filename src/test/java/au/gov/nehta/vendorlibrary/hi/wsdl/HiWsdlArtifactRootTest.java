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

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class HiWsdlArtifactRootTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Before
    @After
    public void resetRoot() {
        HiWsdlArtifactRoot.setRoot(null);
        System.clearProperty(HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT);
    }

    @Test
    public void findWsdlUrlReturnsNullWhenSetRootPointsToNonExistentRelativePath() {
        HiWsdlArtifactRoot.setRoot(Paths.get("artifact-root-absent-" + System.nanoTime()));
        Assert.assertNull(HiWsdlArtifactRoot.findWsdlUrl("HI_ConsumerSearchIHI-3.0.wsdl"));
    }

    @Test
    public void findWsdlUrlReturnsNullWhenRootDirectoryHasNoWsdlChild() throws Exception {
        Path root = temp.newFolder().toPath();
        HiWsdlArtifactRoot.setRoot(root);
        Assert.assertNull(HiWsdlArtifactRoot.findWsdlUrl("HI_ConsumerSearchIHI-3.0.wsdl"));
    }

    @Test
    public void findWsdlUrlReturnsNullWhenSystemPropertyPointsToNonExistentArtifactRoot() {
        String env = System.getenv(HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT);
        Assume.assumeTrue("Unset HI_WSDL_ARTIFACT_ROOT in the process environment for this assertion",
                env == null || env.trim().isEmpty());
        HiWsdlArtifactRoot.setSkipLocalPropertiesFileForUnitTests(true);
        try {
            Path absent = temp.getRoot().toPath().resolve("no-such-artifact-root-" + System.nanoTime());
            Assert.assertFalse(Files.exists(absent));
            System.setProperty(HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT, absent.toAbsolutePath().toString());
            Assert.assertNull(HiWsdlArtifactRoot.findWsdlUrl("HI_ConsumerSearchIHI-3.0.wsdl"));
        } finally {
            HiWsdlArtifactRoot.setSkipLocalPropertiesFileForUnitTests(false);
        }
    }

    @Test
    public void localPropertiesHiWsdlArtifactRootMustResolveSentinelWsdlWhenKeyPresent() throws Exception {
        String env = System.getenv(HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT);
        Assume.assumeTrue("skipped when HI_WSDL_ARTIFACT_ROOT is set in the environment (overrides local.properties)",
                env == null || env.trim().isEmpty());

        Path cwd = Paths.get(System.getProperty("user.dir", ".")).toAbsolutePath().normalize();
        Path localFile = cwd.resolve("local.properties");
        if (!Files.isRegularFile(localFile)) {
            return;
        }
        Properties p = new Properties();
        try (Reader r = Files.newBufferedReader(localFile, StandardCharsets.UTF_8)) {
            p.load(r);
        }
        String v = p.getProperty(HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT);
        if (v == null || v.trim().isEmpty()) {
            return;
        }
        HiWsdlArtifactRoot.setRoot(null);
        System.clearProperty(HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT);
        URL url = HiWsdlArtifactRoot.findWsdlUrl("HI_ConsumerSearchIHI-3.0.wsdl");
        Assert.assertNotNull(
                "local.properties HI_WSDL_ARTIFACT_ROOT must point to a directory containing wsdl/ and "
                        + "schema/ (see README Implementors); failed to resolve HI_ConsumerSearchIHI-3.0.wsdl",
                url);
    }

    @Test
    public void findWsdlUrlReturnsNullForMissingWsdlUnderExplicitRoot() throws Exception {
        Path root = temp.newFolder().toPath();
        Files.createDirectories(root.resolve("wsdl"));
        Files.createDirectories(root.resolve("schema"));
        HiWsdlArtifactRoot.setRoot(root);
        Assert.assertNull(HiWsdlArtifactRoot.findWsdlUrl("NoSuchWsdl-7f2a9c1e.wsdl"));
    }

    @Test
    public void findWsdlUrlReturnsNullWhenNoRootCanBeResolved() {
        String env = System.getenv(HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT);
        Assume.assumeTrue("Unset HI_WSDL_ARTIFACT_ROOT in the process environment for this assertion",
                env == null || env.trim().isEmpty());
        HiWsdlArtifactRoot.setSkipLocalPropertiesFileForUnitTests(true);
        try {
            Assert.assertNull(HiWsdlArtifactRoot.getRootOrNull());
            Assert.assertNull(HiWsdlArtifactRoot.findWsdlUrl("Any.wsdl"));
        } finally {
            HiWsdlArtifactRoot.setSkipLocalPropertiesFileForUnitTests(false);
        }
    }

    @Test
    public void findWsdlUrlFindsNestedFileByBasename() throws Exception {
        Path root = temp.newFolder().toPath();
        Path nested = root.resolve("wsdl/consumer/20100731");
        Files.createDirectories(nested);
        Path file = nested.resolve("HI_Test.wsdl");
        Files.write(file, new byte[0]);
        Files.createDirectories(root.resolve("schema"));
        HiWsdlArtifactRoot.setRoot(root);
        URL url = HiWsdlArtifactRoot.findWsdlUrl("HI_Test.wsdl");
        Assert.assertNotNull(url);
        Assert.assertTrue(url.toString().startsWith("file:"));
        Assert.assertTrue(url.getPath().replace('\\', '/').contains("HI_Test.wsdl"));
    }

    @Test
    public void programmaticSetRootOverridesSystemProperty() throws Exception {
        Path rootProp = temp.newFolder().toPath();
        Files.createDirectories(rootProp.resolve("wsdl"));
        Files.write(rootProp.resolve("wsdl/X.wsdl"), new byte[0]);
        Path rootProg = temp.newFolder().toPath();
        Files.createDirectories(rootProg.resolve("wsdl"));
        Files.write(rootProg.resolve("wsdl/Y.wsdl"), new byte[0]);
        Files.createDirectories(rootProg.resolve("schema"));
        System.setProperty(HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT, rootProp.toString());
        HiWsdlArtifactRoot.setRoot(rootProg);
        Assert.assertNotNull(HiWsdlArtifactRoot.findWsdlUrl("Y.wsdl"));
        Assert.assertNull(HiWsdlArtifactRoot.findWsdlUrl("X.wsdl"));
    }

    @Test
    public void findWsdlUrlResolvesRelativePathUnderWsdl() throws Exception {
        Path root = temp.newFolder().toPath();
        Path nested = root.resolve("wsdl/consumer/20100731");
        Files.createDirectories(nested);
        Path file = nested.resolve("HI_Test.wsdl");
        Files.write(file, new byte[0]);
        Files.createDirectories(root.resolve("schema"));
        HiWsdlArtifactRoot.setRoot(root);
        URL url = HiWsdlArtifactRoot.findWsdlUrl("consumer/20100731/HI_Test.wsdl");
        Assert.assertNotNull(url);
    }
}
