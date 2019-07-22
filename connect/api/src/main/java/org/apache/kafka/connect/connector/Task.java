/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.connect.connector;

import java.util.Map;

/**
 * <p>
 *     copies 副本，复印件
 * Tasks contain the code that actually copies data to/from another system.
 *  任务包含将数据复制到其他系统或从其他系统复制数据的代码
 * They receive a configuration from their parent Connector, assigning them a fraction of a Kafka Connect job's work.
 * 它们从其父连接器接收配置，为它们分配Kafka Connect作业的一部分工作
 * The Kafka Connect framework then pushes/pulls data from the Task. The Task must also be able to respond to reconfiguration requests.
 *  kafka连接框架从任务中推送或拉取数据。此任务必须能够响应重新配置请求
 * </p>
 * <p>
 *     minimal 极小的，最小的   shared共有，共享   functionality实用，功能
 * Task only contains the minimal shared functionality between
 * {@link org.apache.kafka.connect.source.SourceTask} and
 * {@link org.apache.kafka.connect.sink.SinkTask}.
 * </p>
 */
public interface Task {
    /**
     * corresponding 符合的，相应的，类似于
     * Get the version of this task. Usually this should be the same as the corresponding {@link Connector} class's version.
     *  获取任务版本号。通常这应该与对应的Connector类的版本相同
     * @return the version, formatted as a String
     */
    String version();

    /**
     * Start the Task
     * @param props initial configuration
     */
    void start(Map<String, String> props);

    /**
     * Stop this task.
     */
    void stop();
}
