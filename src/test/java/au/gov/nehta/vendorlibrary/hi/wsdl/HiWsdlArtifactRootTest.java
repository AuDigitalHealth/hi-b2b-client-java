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
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class HiWsdlArtifactRootTest {

    @Before
    public void skipLocalProperties() {
        HiWsdlArtifactRoot.setSkipLocalPropertiesFileForUnitTests(true);
    }

    @After
    public void reset() {
        HiWsdlArtifactRoot.setSkipLocalPropertiesFileForUnitTests(false);
        HiWsdlArtifactRoot.setRoot(null);
    }

    @Test
    public void setRootProgrammatic() throws IOException {
        Path tmp = Files.createTempDirectory("hi-wsdl-root");
        try {
            HiWsdlArtifactRoot.setRoot(tmp);
            Assert.assertEquals(tmp.toRealPath(), HiWsdlArtifactRoot.getRootOrNull().toRealPath());
        } finally {
            deleteTree(tmp);
        }
    }

    @Test
    public void findWsdlWalksUnderWsdl() throws IOException {
        Path root = Files.createTempDirectory("hi-artifact");
        Path nested = root.resolve("wsdl").resolve("consumer").resolve("2010");
        Files.createDirectories(nested);
        Path file = nested.resolve("HI_Foo.wsdl");
        Files.write(file, "<definitions/>".getBytes());
        try {
            HiWsdlArtifactRoot.setRoot(root);
            URL url = HiWsdlArtifactRoot.findWsdlUrl("HI_Foo.wsdl");
            Assert.assertNotNull(url);
            Assert.assertTrue(url.toString(), url.toString().contains("HI_Foo.wsdl"));
        } finally {
            deleteTree(root);
        }
    }

    private static void deleteTree(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.deleteIfExists(p);
                } catch (IOException ignored) {
                }
            });
        }
    }
}
