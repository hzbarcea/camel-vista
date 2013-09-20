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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.embedder.DecoderEmbedder;
import org.osehra.vista.camel.rpc.RpcRequest;
import org.osehra.vista.camel.rpc.RpcResponse;
import org.osehra.vista.camel.rpc.VistaRuntime;
import org.osehra.vista.camel.rpc.codec.RpcResponseDecoder;
import org.osehra.vista.camel.rpc.service.VistaServiceTestSupport;
import org.osehra.vista.camel.rpc.util.NettyLogLineParser;
import org.osehra.vista.camel.rpc.util.TextParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecordPlayerRuntime implements VistaRuntime {
    private final static Logger LOG = LoggerFactory.getLogger(VistaServiceTestSupport.class);
    private AtomicInteger index = new AtomicInteger(-1);
    private List<RpcResponse> responses;

    public RecordPlayerRuntime(InputStream input) {
        NettyLogLineParser logParser = new NettyLogLineParser();
        try {
            new TextParser(logParser).parse(input);
        } catch (Exception e) {
            LOG.warn("Failed to parse input: {}", e.getMessage());
            return;
        }

        responses = new ArrayList<RpcResponse>();
        for (byte[] entry : logParser.getEntries()) {
            DecoderEmbedder<RpcResponse> e = new DecoderEmbedder<RpcResponse>(new RpcResponseDecoder());
            e.offer(ChannelBuffers.copiedBuffer(entry));
            responses.add(e.poll());
        }
    }

    @Override
    public RpcResponse execute(RpcRequest request) {
        LOG.debug(request.getName());
        int next = index.incrementAndGet();
        return next < responses.size() ? responses.get(next) : null;
    }

    public List<RpcResponse> getResponses() {
        return responses;
    }
}

