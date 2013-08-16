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


import java.io.ByteArrayInputStream;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.embedder.DecoderEmbedder;

import org.junit.Assert;
import org.junit.Test;

import org.osehra.vista.camel.rpc.RpcRequest;
import org.osehra.vista.camel.rpc.RpcResponse;
import org.osehra.vista.camel.rpc.codec.RpcRequestDecoder;
import org.osehra.vista.camel.rpc.codec.RpcResponseDecoder;


public class NettyBufferParseTest {

    @Test
    public void testParseRequest() throws Exception {
        String content = ""
            + "         +-------------------------------------------------+\n"
            + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |\n"
            + "+--------+-------------------------------------------------+----------------+\n"
            + "|00000000| 5b 58 57 42 5d 31 30 33 30 34 0a 54 43 50 43 6f |[XWB]10304.TCPCo|\n"
            + "|00000010| 6e 6e 65 63 74 35 30 30 31 33 31 39 32 2e 31 36 |nnect50013192.16|\n"
            + "|00000020| 38 2e 31 2e 31 30 30 66 30 30 30 31 30 66 30 30 |8.1.100f00010f00|\n"
            + "|00000030| 31 37 76 69 73 74 61 2e 65 78 61 6d 70 6c 65 2e |17vista.example.|\n"
            + "|00000040| 6f 72 67 66 04                                  |orgf.           |\n"
            + "+--------+-------------------------------------------------+----------------+\n";

        NettyLogBuffer buffer = new NettyLogBuffer();
        TextParser parser = new TextParser(new NettyLogLineParser(buffer));
        parser.parse(new ByteArrayInputStream(content.getBytes()));
        
        Assert.assertEquals(69, buffer.getBuffer().length);

        DecoderEmbedder<RpcRequest> e = new DecoderEmbedder<RpcRequest>(new RpcRequestDecoder());
        e.offer(ChannelBuffers.copiedBuffer(buffer.getBuffer()));
        RpcRequest result = e.poll();
        Assert.assertNotNull(result);
    }

    @Test
    public void testParseResponse() throws Exception {
        String content = ""
            + "         +-------------------------------------------------+\n"
            + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |\n"
            + "+--------+-------------------------------------------------+----------------+\n"
            + "|00000000| 00 00 45 43 32 41 4d 41 5a 2d 39 36 54 51 32 50 |..EC2AMAZ-96TQ2P|\n"
            + "|00000010| 4b 0d 0a 52 4f 55 0d 0a 43 50 4d 0d 0a 2f 2f 2e |K..ROU..CPM..//.|\n"
            + "|00000020| 2f 6e 75 6c 3a 34 36 34 30 0d 0a 35 0d 0a 30 0d |/nul:4640..5..0.|\n"
            + "|00000030| 0a 55 56 41 2e 44 4f 4d 41 49 4e 2e 47 4f 56 0d |.UVA.DOMAIN.GOV.|\n"
            + "|00000040| 0a 30 0d 0a 04                                  |.0...           |\n"
            + "+--------+-------------------------------------------------+----------------+\n";

        NettyLogBuffer buffer = new NettyLogBuffer();
        TextParser parser = new TextParser(new NettyLogLineParser(buffer));
        parser.parse(new ByteArrayInputStream(content.getBytes()));
            
        Assert.assertEquals(69, buffer.getBuffer().length);

        DecoderEmbedder<RpcResponse> e = new DecoderEmbedder<RpcResponse>(new RpcResponseDecoder());
        e.offer(ChannelBuffers.copiedBuffer(buffer.getBuffer()));
        RpcResponse result = e.poll();
        Assert.assertNotNull(result);
    }

}

