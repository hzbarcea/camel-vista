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

package org.osehra.vista.camel.rpc.service;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.junit.After;
import org.junit.Before;
import org.osehra.vista.camel.rpc.RpcRequest;
import org.osehra.vista.camel.rpc.RpcResponse;
import org.osehra.vista.camel.rpc.codec.RpcClientPipelineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class VistaServiceTestSupport {
    protected final static Logger LOG = LoggerFactory.getLogger(VistaServiceTestSupport.class);
    protected int port = VistaServer.DEFAULT_PORT;
    protected VistaServer server;

    @Before
    public void setUp() {
        LOG.debug("Starting VistA test server...");
        server = createServer(port);
        runServer(server);
    }
    @After
    public void tearDown() {
        LOG.debug("Shutting down VistA test server...");
        server.completed();
    }

    protected VistaServer createServer(int port) {
        return new VistaServer().setPort(port);
    }

    protected void runServer(final VistaServer server) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                server.run();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    protected Channel createClientChannel(String host, int port) {
        // Configure the client.
        ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
            Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool()));

        // Set up the event pipeline factory.
        bootstrap.setPipelineFactory(new RpcClientPipelineFactory());
        return bootstrap.connect(new InetSocketAddress(host, port))
            .awaitUninterruptibly().getChannel();
    }
    
    protected RpcResponse call(Channel channel, RpcRequest request) {
        ChannelFuture response = channel.write(request);
        response.awaitUninterruptibly();
        return null;
    }
}

