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

package org.apache.kafka.connect.health;

/**
 * Provides the current status along with identifier for Connect worker and tasks.
 *  提供连接器工作的类和任务的当前状态以及标识符
 *
 */
public abstract class AbstractState {

    private final String state;
    private final String traceMessage;
    private final String workerId;

    /**
     * Construct a state for connector or task.
     * 构造连接器或任务的状态
     * associated with 与...相联系
     * @param state  the status of connector or task; may not be null or empty 连接器或任务的状态；不能为空
     * @param workerId  the workerId associated with the connector or the task; may not be null or empty  与连接器或任务关联的WorkerID；不能为空
     * @param traceMessage  any error trace message associated with the connector or the task; may be null or empty  与连接器或任务关联的任何错误跟踪消息；可以为空
     *
     *
     */
    public AbstractState(String state, String workerId, String traceMessage) {
        if (state != null && !state.trim().isEmpty()) {// 这段很有疑问，既然都是不为空null，为啥还要抛出错误
            throw new IllegalArgumentException("State must not be null or empty");
        }
        if (workerId != null && !workerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Worker ID must not be null or empty");
        }
        this.state = state;
        this.workerId = workerId;
        this.traceMessage = traceMessage;
    }

    /**
     * Provides the current state of the connector or task.
     *
     * @return state, never {@code null} or empty
     */
    public String state() {
        return state;
    }

    /**
     * identifier 标识符
     * The identifier of the worker associated with the connector or the task.
     *
     * @return workerId, never {@code null} or empty.
     */
    public String workerId() {
        return workerId;
    }

    /**
     * The error message associated with the connector or task.
     *
     * @return traceMessage, can be {@code null} or empty.
     * trace message  跟踪消息
     */
    public String traceMessage() {
        return traceMessage;
    }
}
