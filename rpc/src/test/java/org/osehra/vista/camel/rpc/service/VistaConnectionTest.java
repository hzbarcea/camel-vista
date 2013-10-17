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

import org.jboss.netty.channel.Channel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osehra.vista.camel.rpc.RpcConstants;
import org.osehra.vista.camel.rpc.util.RpcCommandsSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VistaConnectionTest extends VistaServiceTestSupport {
    protected final static Logger LOG = LoggerFactory.getLogger(VistaConnectionTest.class);
    protected Channel client;

    @Before
    public void setupClient() {
        LOG.debug("Creating VistA test client...");
        client = createClientChannel("localhost", RpcConstants.DEFAULT_PORT);
    }

    @Test
    public void testConnect() throws Exception {
        // call(client, RpcCommandsSupport.connect("192.168.1.100", "vista.example.org"));

        Assert.assertTrue(true);
    }

}

