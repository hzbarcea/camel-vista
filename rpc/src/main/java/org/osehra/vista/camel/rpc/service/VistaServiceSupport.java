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


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class VistaServiceSupport implements VistaService {

    private AtomicBoolean active = new AtomicBoolean(false);
    private AtomicBoolean starting = new AtomicBoolean(false);
    private CountDownLatch started = new CountDownLatch(1);
    private AtomicBoolean stopping = new AtomicBoolean(false);
    private CountDownLatch stopped = new CountDownLatch(1);

    public void start() {
        while (!active.get()) {
            if (starting.compareAndSet(false, true)) {
                try {
                    startInternal();
                    active.set(true);
                    started.countDown();
                    stopped = new CountDownLatch(1);
                } catch (RuntimeException ex) {     // ignore; starting flag is reset regardless
                }
                starting.set(false);
            } else {
                try {
                    started.await();
                } catch (InterruptedException e) {  // ignore
                }
            }
        }
    }

    public void stop() {
        while (active.get()) {
            if (stopping.compareAndSet(false, true)) {
                try {
                    stopInternal();
                    active.set(false);
                    stopped.countDown();
                    started = new CountDownLatch(1);
                } catch (RuntimeException ex) {
                }
                stopping.set(false);
            } else {
                try {
                    stopped.await();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public boolean isActive() {
        return active.get();
    }

    protected abstract void startInternal() throws RuntimeException;
    protected abstract void stopInternal() throws RuntimeException;

}
