/*
 * Copyright 2012-2013 The Open Source Electronic Health Record Agent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osehra.vista.camel.component;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.osehra.vista.camel.rpc.RpcRequest;
import org.osehra.vista.camel.rpc.RpcResponse;
import org.osehra.vista.camel.rpc.util.RpcCommandsSupport;


public class VistaRpcEndpointTest extends CamelTestSupport {

    @Test
    public void testVistaConsumer() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        // mock.expectedMessageCount(1);
        // mock.expectedBodiesReceived("");

        RpcRequest request = RpcCommandsSupport.connect();
        // RpcResponse reply = template.requestBody("vista://localhost:9200", request, RpcResponse.class);
        // assertNotNull(reply);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                // from("netty:tcp://0.0.0.0:9200?serverPipelineFactory=#rpc-in&amp;sync=true").to("log:FOO").to("mock:result");
                from("vista://localhost:9200").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(new RpcResponse());
                    }}).to("mock:result");
            }
        };
    }


}
