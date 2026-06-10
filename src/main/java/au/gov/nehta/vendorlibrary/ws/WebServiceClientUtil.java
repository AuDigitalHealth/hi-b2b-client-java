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
package au.gov.nehta.vendorlibrary.ws;

import au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot;

import javax.net.SocketFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.AddressingFeature;

import java.net.URL;
import java.util.List;
import java.util.Map;

public final class WebServiceClientUtil {

    private static final String REQUEST_CONTEXT_SSL_SOCKET_FACTORY_PROPERTY_NAME =
            "com.sun.xml.ws.transport.https.client.SSLSocketFactory";
    private static final String INTERNAL_REQUEST_CONTEXT_SSL_SOCKET_FACTORY_PROPERTY_NAME =
            "com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory";

    private WebServiceClientUtil() {
    }

    public static <T> T getPort(
            Class<T> serviceInterface,
            Class<? extends Service> serviceClass,
            SocketFactory sslSocketFactory,
            String endpoint,
            List<Handler> handlerChain) {

        T port = getPort(serviceInterface, serviceClass, sslSocketFactory, endpoint);
        addHandlerChain(port, handlerChain);

        return port;
    }

    public static <T> T getPort(
            Class<T> serviceInterface,
            Class<? extends Service> serviceClass,
            SocketFactory sslSocketFactory,
            String endpoint) {
        WebServiceClient annotation = serviceClass.getAnnotation(WebServiceClient.class);
        String wsdlFileLoc = annotation.wsdlLocation();
        Service service = getService(annotation, wsdlFileLoc);
        T port = service.getPort(serviceInterface);
        configurePortWithEndpoint(port, endpoint);
        configurePortWithSslSocketFactory(port, sslSocketFactory);

        return port;
    }

    public static <T> T getPort(
            Class<T> serviceInterface,
            Class<? extends Service> serviceClass,
            SocketFactory sslSocketFactory,
            String endpoint,
            List<Handler> handlerChain,
            boolean addressingFeature) {
        WebServiceClient annotation = serviceClass.getAnnotation(WebServiceClient.class);
        String wsdlFileLoc = annotation.wsdlLocation();
        Service service = getService(annotation, wsdlFileLoc);
        T port = service.getPort(serviceInterface, new AddressingFeature(addressingFeature));
        configurePortWithEndpoint(port, endpoint);
        configurePortWithSslSocketFactory(port, sslSocketFactory);
        addHandlerChain(port, handlerChain);

        return port;
    }

    public static <T> T getPort(
            Class<T> serviceInterface,
            Class<? extends Service> serviceClass,
            SocketFactory sslSocketFactory,
            List<Handler> handlerChain) {

        T port = getPort(serviceInterface, serviceClass, sslSocketFactory);
        addHandlerChain(port, handlerChain);

        return port;
    }

    public static <T> T getPort(
            Class<T> serviceInterface,
            Class<? extends Service> serviceClass,
            SocketFactory sslSocketFactory) {
        WebServiceClient annotation = serviceClass.getAnnotation(WebServiceClient.class);
        String wsdlFileLoc = annotation.wsdlLocation();
        Service service = getService(annotation, wsdlFileLoc);
        T port = service.getPort(serviceInterface);
        configurePortWithSslSocketFactory(port, sslSocketFactory);

        return port;
    }

    private static Service getService(WebServiceClient annotation, String wsdlFileLoc) {
        final URL wsdlURL = retrieveWsdlUrl(wsdlFileLoc);
        if (wsdlURL == null) {
            throw new WebServiceException(
                    "WSDL not found: " + wsdlFileLoc + ". Set " + HiWsdlArtifactRoot.HI_WSDL_ARTIFACT_ROOT
                            + " (environment, local.properties in the working directory, or JVM system property) "
                            + "to the directory containing wsdl/ and schema/, call " + HiWsdlArtifactRoot.class.getName()
                            + ".setRoot(Path), or place the WSDL on the classpath; see README.md.");
        }
        final QName serviceQName = new QName(annotation.targetNamespace(), annotation.name());
        return Service.create(wsdlURL, serviceQName);
    }

    private static URL retrieveWsdlUrl(String filePath) {
        if (filePath == null) {
            return null;
        }
        URL fromArtifacts = HiWsdlArtifactRoot.findWsdlUrl(filePath);
        if (fromArtifacts != null) {
            return fromArtifacts;
        }
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        URL result = tccl != null ? tccl.getResource(filePath) : null;
        if (result == null) {
            ClassLoader cl = WebServiceClientUtil.class.getClassLoader();
            result = cl != null ? cl.getResource(filePath) : null;
        }
        return result;
    }

    private static void configurePortWithSslSocketFactory(final Object servicePort, final SocketFactory sslSocketFactory) {
        final BindingProvider bindingProvider = (BindingProvider) servicePort;

        final Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(REQUEST_CONTEXT_SSL_SOCKET_FACTORY_PROPERTY_NAME, sslSocketFactory);
        requestContext.put(INTERNAL_REQUEST_CONTEXT_SSL_SOCKET_FACTORY_PROPERTY_NAME, sslSocketFactory);
    }

    private static void configurePortWithEndpoint(final Object servicePort, final String endpoint) {
        final BindingProvider bindingProvider = (BindingProvider) servicePort;

        final Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint.trim());
    }

    private static void addHandlerChain(final Object servicePort, final List<Handler> handlerChain) {

        final BindingProvider bindingProvider = (BindingProvider) servicePort;
        bindingProvider.getBinding().setHandlerChain(handlerChain);
    }
}
