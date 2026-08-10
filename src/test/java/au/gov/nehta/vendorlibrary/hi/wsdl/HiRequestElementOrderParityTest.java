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

import jakarta.xml.bind.annotation.XmlType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Asserts JAXB {@code @XmlType.propOrder} for selected HI request types used by
 * Consumer Search IHI (Messages, Interface, batch, and TDS where applicable).
 */
public class HiRequestElementOrderParityTest {

    /**
     * Messages {@code searchIHI} (batch synchronous / asynchronous): {@code electronicCommunication}
     * before {@code dateOfBirth}.
     */
    @Test
    public void searchIhiMessagesPropOrderForBatchSync() {
        assertPropOrder(
                "au.net.electronichealth.ns.hi.xsd.consumermessages.searchihi._3.SearchIHI",
                Arrays.asList(
                        "ihiNumber",
                        "medicareCardNumber",
                        "medicareIRN",
                        "dvaFileNumber",
                        "electronicCommunication",
                        "dateOfBirth",
                        "sex",
                        "familyName",
                        "givenName",
                        "australianPostalAddress",
                        "australianStreetAddress",
                        "internationalAddress",
                        "australianUnstructuredStreetAddress",
                        "history"
                )
        );
    }

    /**
     * Interface {@code searchIHI} (IHI inquiry): unstructured street address before international address.
     */
    @Test
    public void searchIhiInterfacePropOrderForInquiry() {
        assertPropOrder(
                "au.net.electronichealth.ns.hi.svc.consumersearchihi._3.SearchIHI",
                Arrays.asList(
                        "ihiNumber",
                        "medicareCardNumber",
                        "medicareIRN",
                        "dvaFileNumber",
                        "electronicCommunication",
                        "dateOfBirth",
                        "sex",
                        "familyName",
                        "givenName",
                        "australianPostalAddress",
                        "australianStreetAddress",
                        "australianUnstructuredStreetAddress",
                        "internationalAddress",
                        "history"
                )
        );
    }

    /**
     * Batch wrapper {@code SearchIHIRequestType}.
     */
    @Test
    public void searchIhiRequestTypePropOrder() {
        assertPropOrder(
                "au.net.electronichealth.ns.hi.xsd.consumermessages.searchihibatch._3.SearchIHIRequestType",
                Arrays.asList("searchIHI", "requestIdentifier")
        );
    }

    /**
     * TDS search {@code providerIndividual}.
     */
    @Test
    public void searchTdsProviderIndividualPropOrder() {
        assertPropOrder(
                "au.net.electronichealth.ns.hi.xsd.providermessages.searchtdsproviderindividual._5_1.ProviderIndividual",
                Arrays.asList(
                        "hpiiNumber",
                        "status",
                        "personalDetails",
                        "individualNameRecord",
                        "addressRecord",
                        "electronicCommunicationRecord",
                        "providerTypeRecord"
                )
        );
    }

    /**
     * Loads {@code className}, reads {@link XmlType#propOrder()}, and asserts it equals {@code expected}.
     *
     * @param className fully qualified generated request type
     * @param expected  local element names in schema order
     */
    private static void assertPropOrder(String className, List<String> expected) {
        Class<?> type;
        try {
            type = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Assert.fail("Missing generated type on classpath: " + className);
            return;
        }
        XmlType xmlType = type.getAnnotation(XmlType.class);
        Assert.assertNotNull("@XmlType required on " + className, xmlType);
        Assert.assertArrayEquals(
                className + " propOrder",
                expected.toArray(new String[0]),
                xmlType.propOrder()
        );
    }
}
