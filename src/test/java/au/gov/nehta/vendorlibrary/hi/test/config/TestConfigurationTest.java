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

import org.junit.Assert;
import org.junit.Test;

public class TestConfigurationTest {

    @Test
    public void getConfigurationValueReturnsDefaultWhenKeyAbsent() {
        Assert.assertEquals("expected-default",
                TestConfiguration.getConfigurationValue("__NO_SUCH_HI_KEY__", "expected-default"));
    }

    @Test
    public void getConfigurationValueWithFallbackUsesDefaultWhenNeitherKeySet() {
        Assert.assertEquals("fallback-default",
                TestConfiguration.getConfigurationValueWithFallback(
                        "__NO_PRIMARY_HI_KEY__", "__NO_FALLBACK_HI_KEY__", "fallback-default"));
    }
}
