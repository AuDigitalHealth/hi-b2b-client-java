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
package au.gov.nehta.vendorlibrary.ws.handler;

import com.sun.xml.ws.api.handler.MessageHandler;
import com.sun.xml.ws.api.handler.MessageHandlerContext;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import jakarta.xml.ws.handler.MessageContext;

import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.logging.Logger;

public final class LoggingHandler implements MessageHandler<MessageHandlerContext> {

    public static final String EMPTY = "";
    public static final String ENCODING = "utf-8";
    private static final Logger LOG = Logger.getLogger(LoggingHandler.class.getName());
    private String lastSoapRequest;
    private String lastSoapResponse;
    private boolean dump;

    public LoggingHandler(boolean dump) {
        this.dump = dump;
    }

    public String getLastSoapResponse() {
        return lastSoapResponse;
    }

    public String getLastSoapRequest() {
        return lastSoapRequest;
    }

    public boolean handleMessage(final MessageHandlerContext context) {
        logSOAPMessage(context);
        return true;
    }

    public boolean handleFault(final MessageHandlerContext context) {
        logSOAPMessage(context);
        return true;
    }

    private void logSOAPMessage(final MessageHandlerContext context) {
        boolean outgoing = (Boolean) context
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outgoing) {
            lastSoapRequest = getSoapAsString(context);
            if (dump) {
                LOG.info("Outgoing" + lastSoapRequest);
            }
        } else {
            lastSoapResponse = getSoapAsString(context);
            if (dump) {
                LOG.info("Incoming" + lastSoapResponse);
            }
        }
    }

    private String getSoapAsString(MessageHandlerContext mhc) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Message m = mhc.getMessage().copy();

        XMLStreamWriter writer = XMLStreamWriterFactory.create(baos);
        String soap = "";
        try {
            m.writeTo(writer);
            soap = baos.toString(ENCODING);

        } catch (XMLStreamException | java.io.UnsupportedEncodingException e) {
            LOG.severe("Error logging soap message: " + e.getMessage());
        }

        return soap;
    }

    public Set<QName> getHeaders() {
        return null;
    }

    public void close(final MessageContext context) {
    }
}
