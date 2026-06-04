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
package au.gov.nehta.vendorlibrary.hi.handler.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.gov.nehta.common.utils.ArgumentUtils;

public class HIHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    /**
     * CSP HPI-O header name.
     */
    public static final String HPIO_CSP_HEADER_ELEMENT_NAME = "hpio";

    /* FaultTo header name. Medicare cannot handle FaultTo:
     *     so we strip it from the outbound messages before signing.
     */
    public static final String FAULT_TO_HEADER_ELEMENT_NAME = "FaultTo";

    /**
     * Header node name(s) to remove from the SOAP message.
     */
    private final List<String> headerNames;

    /**
     * Default constructor.
     *
     * @param headerNames Headers to remove from message.
     */
    public HIHeaderHandler(List<String> headerNames) {
        ArgumentUtils.checkNotNull(headerNames, "headerNames");

        this.headerNames = headerNames;
    }

    /**
     * Updates the SOAP headers in outgoing SOAP requests as required.
     *
     * @param context the incoming / outgoing soap message context
     * @return true Always returns true.
     * @see jakarta.xml.ws.handler.Handler#handleMessage(jakarta.xml.ws.handler.MessageContext)
     */
    public final boolean handleMessage(final SOAPMessageContext context) {
        Boolean isOutgoing = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (isOutgoing) {
            removeSoapHeaders(context);
        } else {
            // No action required.
        }
        return true;
    }

    /**
     * Remove all unnecessary headers from the message.
     *
     * @param context outgoing soap message context
     */
    private void removeSoapHeaders(SOAPMessageContext context) {

        // Retrieve SOAP header from SOAP message.
        SOAPMessage message = context.getMessage();
        SOAPHeader header;
        try {
            header = message.getSOAPHeader();

            // Process each header that needs to be removed.
            for (String name : headerNames) {

                // Get child nodes of SOAP header.
                NodeList nodeList = header.getChildNodes();
                List<Node> removeNodesList = new ArrayList<>();

                // Build up a list of nodes that need to be deleted.
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeName().endsWith(name)) {
                        removeNodesList.add(nodeList.item(i));
                    }
                }

                // Remove node(s).
                for (Node node : removeNodesList) {
                    node.getParentNode().removeChild(node);
                }
            }
        } catch (SOAPException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Does nothing returns false.<br>
     *
     * @param context the incoming / outgoing soap message context
     * @return true if the handle signature check is successful.
     * @see jakarta.xml.ws.handler.Handler#handleFault(jakarta.xml.ws.handler.MessageContext)
     */
    public final boolean handleFault(final SOAPMessageContext context) {
        return false;
    }

    /**
     * Does nothing returns null.<br>
     *
     * @return @see jakarta.xml.ws.handler.soap.SOAPHandler#getHeaders()
     */
    public final Set<QName> getHeaders() {
        return null;
    }

    /**
     * Does nothing <br>
     * Not utilised for dumping SOAP message.
     *
     * @param context the message context
     * @see jakarta.xml.ws.handler.Handler#close(jakarta.xml.ws.handler.MessageContext)
     */
    public void close(final MessageContext context) {
        // Do nothing
    }
}
