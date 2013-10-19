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

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.component.netty.NettyComponent;
import org.apache.camel.component.netty.NettyConfiguration;
import org.osehra.vista.camel.proxy.RpcClientPipelineFactory;
import org.osehra.vista.camel.proxy.RpcServerPipelineFactory;


public class VistaRpcComponent extends NettyComponent {
    private static final String TCP_PROTOCOL = "tcp://";

    public VistaRpcComponent() {
        setConfiguration(defaultConfiguration());
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        return super.createEndpoint(uri, TCP_PROTOCOL + remaining, parameters);
    }
    
    private static NettyConfiguration defaultConfiguration() {
        NettyConfiguration defaultConfig = new NettyConfiguration();
        defaultConfig.setClientPipelineFactory(new RpcClientPipelineFactory(null));
        defaultConfig.setServerPipelineFactory(new RpcServerPipelineFactory(null));
        defaultConfig.setSync(true);
        return defaultConfig;
    }
}