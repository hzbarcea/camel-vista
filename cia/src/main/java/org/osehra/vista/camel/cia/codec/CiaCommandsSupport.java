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


import org.osehra.vista.camel.cia.CiaRequest;


public class CiaCommandsSupport {

    public static CiaRequest connect() {
        // TODO: use computed vs hardcoded defaults
        return connect("NOTVALID");
    }
    public static CiaRequest connect(String ipaddress) {
        return request()
            .type('C')
            .parameter("IP", ipaddress)
            .parameter("UCI", "")
            .parameter("DBG", "0")
            .parameter("LP", "0")
            .parameter("VER", "1.6.5.26");
    }

    public static CiaRequest request() {
        return new CiaRequest().type('R');
    }

    private CiaCommandsSupport() {
    }

}

