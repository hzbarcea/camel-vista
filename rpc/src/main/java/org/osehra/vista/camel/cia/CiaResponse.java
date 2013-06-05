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

package org.osehra.vista.camel.cia;


public final class CiaResponse {

    private byte sequence;
    private String message;

    public byte getSequence() {
        return sequence;
    }
    public String getMessage() {
        return message;
    }

    public CiaResponse sequence(byte sequence) {
        this.sequence = sequence;
        return this;
    }
    public CiaResponse message(String value) {
        this.message = message;
        return this;
    }

}
