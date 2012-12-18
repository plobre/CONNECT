/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.messaging.service.decorator.cxf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.client.CONNECTTestClient;
import gov.hhs.fha.nhinc.messaging.service.ServiceEndpoint;
import gov.hhs.fha.nhinc.messaging.service.port.TestServicePortDescriptor;
import gov.hhs.fha.nhinc.messaging.service.port.TestServicePortType;

/**
 * @author akong
 *
 */
public class TLSClientServiceEndpointDecoratorTest {
    
    private String systemPassword = null;

    @Before
    public void setup() {
        systemPassword = System.getProperty("javax.net.ssl.keyStorePassword");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
    }

    @After
    public void resetSystemPassword() {
        if (systemPassword != null) {
            System.setProperty("javax.net.ssl.keyStorePassword", systemPassword);
        }
    }
    
    @Test
    public void testTLSConfiguration() {
        CONNECTClient<TestServicePortType> client = createClient();
        
        verifyTLSConfiguration(client);
    }
    
    public void verifyTLSConfiguration(CONNECTClient<?> client) { 
        Client clientProxy = ClientProxy.getClient(client.getPort());
        HTTPConduit conduit = (HTTPConduit) clientProxy.getConduit();
        TLSClientParameters tlsCP = conduit.getTlsClientParameters();
        
        assertTrue(tlsCP.isDisableCNCheck());
        assertNotNull(tlsCP.getKeyManagers());
        assertNotNull(tlsCP.getTrustManagers());                
    }
    
    private CONNECTClient<TestServicePortType> createClient() {
        CONNECTTestClient<TestServicePortType> testClient = new CONNECTTestClient<TestServicePortType>(
                new TestServicePortDescriptor(), "", new AssertionType());

        ServiceEndpoint<TestServicePortType> serviceEndpoint = testClient.getServiceEndpoint();
        serviceEndpoint = new TLSClientServiceEndpointDecorator<TestServicePortType>(serviceEndpoint);

        serviceEndpoint.configure();

        return testClient;
    }
    
}
