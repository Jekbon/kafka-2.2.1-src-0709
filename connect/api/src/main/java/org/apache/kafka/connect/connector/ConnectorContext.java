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

/**
 * proactively 主动地
 * interact with与…相互作用
 * ConnectorContext allows Connectors to proactively interact with the Kafka Connect runtime.
 * ConnectorContext 允许连接器主动与Kafka Connect运行时交互
 */
public interface ConnectorContext {
    /**
     * Requests that the runtime reconfigure the Tasks for this source. This should be used to
     * indicate to the runtime that something about the input/output has changed (e.g. partitions
     * added/removed) and the running Tasks will need to be modified.
     * reconfigure 重新配置
     * 请求运行时重新配置此源的任务，她应该被用来表明运行一些关于输入输出有变动（例如 分区，增加，移除）
     * 并且运行的任务需要被调整
     */
    void requestTaskReconfiguration();

    /**
     * Raise an unrecoverable exception to the Connect framework. This will cause the status of the
     * connector to transition to FAILED. 连接框架突出不可恢复异常，这会导致连接器的状态转为失败
     * unrecoverable 不可恢复的 adj
     * transition 过渡，转换
     * @param e Exception to be raised. 突出异常
     *          raised突出，提高
     */
    void raiseError(Exception e);
}
