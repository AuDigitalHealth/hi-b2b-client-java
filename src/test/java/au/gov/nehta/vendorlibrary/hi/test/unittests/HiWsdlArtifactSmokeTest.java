package au.gov.nehta.vendorlibrary.hi.test.unittests;

import static org.junit.Assert.assertTrue;

import jakarta.xml.ws.Service;
import org.junit.Test;

/**
 * Smoke test: generated HI SOAP types are on the compile classpath (from in-repo wsimport or {@code -Phi-wsdl-artifact}).
 */
public class HiWsdlArtifactSmokeTest {

    @Test
    public void consumerSearchIhiServiceTypeIsOnClasspath() throws Exception {
        Class<?> serviceType = Class.forName(
            "au.net.electronichealth.ns.hi.svc.consumersearchihi._3.ConsumerSearchIHIService");
        assertTrue(Service.class.isAssignableFrom(serviceType));
    }
}
