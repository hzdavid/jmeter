/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.jmeter.control;

import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestElement;

/**
 * This interface is used by JMeterThread in the following manner:
 * <p>
 * <code>while (running &amp;&amp; (sampler = controller.next()) != null)</code>
 */
public interface Controller extends TestElement {
    /**
     * Delivers the next Sampler or null
     * 如果控制器嵌套了，就递归调用
     * @return org.apache.jmeter.samplers.Sampler or null
     */
    Sampler next();

    /**
     * Indicates whether the Controller is done delivering Samplers for the rest
     * of the test.
     * 控制器执行完了，表示它控制取样器或子控制器已经结束了
     * When the top-level controller returns true to JMeterThread,
     * the thread is complete.
     *
     * @return boolean
     */
    boolean isDone();

    /**
     * Controllers have to notify listeners of when they begin an iteration
     * through their sub-elements.   控制器取要通知监听器，当它们开始一次迭代。 一次迭代也不一定都是一次循环，它代表控制器对取样器的一次返回，控制器要返回哪些取样器，返回多少次，那是控制器的逻辑
     * @param listener The {@link LoopIterationListener} to add
     */
    void addIterationListener(LoopIterationListener listener);

    /**
     * Called to initialize a controller at the beginning of a test iteration.   test iteration即线程组配置的迭代次数
     */
    void initialize();

    /**
     * Unregister IterationListener
     * @param iterationListener {@link LoopIterationListener}
     */
    void removeIterationListener(LoopIterationListener iterationListener);

    /**
     * Trigger end of loop condition on controller (used by Start Next Loop feature)
     */
    void triggerEndOfLoop();//触发迭代结束事件
}
