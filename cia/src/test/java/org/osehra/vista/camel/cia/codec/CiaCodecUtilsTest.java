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

package org.osehra.vista.camel.cia.codec;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import org.junit.Assert;
import org.junit.Test;


public class CiaCodecUtilsTest {

    @Test
    public void testCiaLengthEncoding() {
        int[] values = { 10, 100, 1000, 10000, 100000, 1000000 };
        for (int value : values) {
            ChannelBuffer b = ChannelBuffers.dynamicBuffer();
            byte[] encoded = CiaCodecUtils.encodeLen(value);
            b.writeBytes(encoded);
    
            Assert.assertTrue(b.writerIndex() > 0);
            byte len = b.readByte();
            Assert.assertEquals(value, CiaCodecUtils.decodeLen(b, len));
        }
    }

}

