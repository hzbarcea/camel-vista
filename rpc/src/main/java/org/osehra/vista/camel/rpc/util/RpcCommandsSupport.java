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

package org.osehra.vista.camel.rpc.util;

import org.osehra.vista.camel.rpc.EmptyParameter;
import org.osehra.vista.camel.rpc.GlobalParameter;
import org.osehra.vista.camel.rpc.LiteralParameter;
import org.osehra.vista.camel.rpc.Parameter;
import org.osehra.vista.camel.rpc.ReferenceParameter;
import org.osehra.vista.camel.rpc.RpcConstants;
import org.osehra.vista.camel.rpc.RpcRequest;


public class RpcCommandsSupport {

    public static RpcRequest connect() {
        // TODO: use computed vs hardcoded defaults
        return connect("120.0.0.1", "localhost");
    }
    public static RpcRequest connect(String ipaddress, String hostname) {
        return request()
            .code("10304")
            .version(null)
            .name("TCPConnect")
            .parameter(literal(ipaddress))
            .parameter(literal("0"))
            .parameter(literal(hostname));
    }
    public static RpcRequest disconnect() {
        return request()
            .code("10304")
            .version(null)
            .name("#BYE#");    
    }
    public static RpcRequest login(String access, String verify) {
        return request()
            .name("XUS AV CODE")
            .parameter(literal(one(new StringBuffer()
                .append(access).append(";").append(verify).toString())));
    }
    public static RpcRequest signonSetup() {
        return request()
            .name("XUS SIGNON SETUP");
    }
    public static RpcRequest context(String context) {
        return request()
            .name("XWB CREATE CONTEXT")
            .parameter(literal(one(context)));
    }

    public static RpcRequest request() {
        return new RpcRequest()
            .namespace(RpcConstants.RPC_DEFAULT_NS)
            .code(RpcConstants.RPC_DEFAULT_CODE)
            .version(RpcConstants.RPC_VERSION);
    }

    public static Parameter literal(String value) {
        return new LiteralParameter(value);
    }
    public static Parameter ref(String value) {
        return new ReferenceParameter(value);
    }
    public static Parameter global(String key, String value) {
        return new GlobalParameter(key, value);
    }
    public static Parameter empty() {
        return new EmptyParameter();
    }
    public static String one(String param) {
        return new StringBuffer().append("1").append(param).append("1").toString();
    }

    protected RpcCommandsSupport() {
        // TODO: allow inheritance for convenience or enforce static utility class?
    }

}

