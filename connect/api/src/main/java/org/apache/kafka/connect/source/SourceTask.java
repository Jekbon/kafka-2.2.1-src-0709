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
package org.apache.kafka.connect.source;

import org.apache.kafka.connect.connector.Task;

import java.util.List;
import java.util.Map;

/**
 * storage 存储 （名词）
 * SourceTask is a Task that pulls records from another system for storage in Kafka.
 *  SourceTask是一个从其他系统拉取数据到kafka存储的任务
 */
public abstract class SourceTask implements Task {

    protected SourceTaskContext context;

    /**
     * Initialize this SourceTask with the specified context object.
     *  实用指定上下文对象初始化SourceTask
     */
    public void initialize(SourceTaskContext context) {
        this.context = context;
    }

    /**
     * Start the Task. This should handle any configuration parsing and one-time setup of the task.
     * 开始Task。这应该处理任何配置解析和任务的一次性设置
     * @param props initial configuration 初始化配置
     */
    @Override
    public abstract void start(Map<String, String> props);

    /**
     * <p>
     * Poll this source task for new records. If no data is currently available, this method
     * should block but return control to the caller regularly (by returning {@code null}) in
     * order for the task to transition to the {@code PAUSED} state if requested to do so.
     *  轮询此源任务以查找新纪录。如果当前没有可用的数据，则此方法应阻止但定期将控制权返回给调用方，以便任务
     *  在请求时转换到PAUSED 状态
     * </p>
     * <p>
     * The task will be {@link #stop() stopped} on a separate thread, and when that happens
     * this method is expected to unblock, quickly finish up any remaining processing, and
     * return.
     *  任务将在单独的线程上被stop方法停止，当发生这种情况时，此方法将被取消阻止，快速完成所有剩余的处理，然后返回
     * </p>
     *
     * @return a list of source records 源记录列表
     */
    public abstract List<SourceRecord> poll() throws InterruptedException;

    /**
     * <p>
     * Commit the offsets, up to the offsets that have been returned by {@link #poll()}. This
     * method should block until the commit is complete.
     * 提交偏移量，直到poll方法返回的偏移量为止。这个方法应该阻塞，直到提交完成
     * </p>
     * <p>
     * SourceTasks are not required to implement this functionality; Kafka Connect will record offsets
     * automatically. This hook is provided for systems that also need to store offsets internally
     * in their own system.
     *  实现此功能不需要SourceTasks，Kafka Connect将自动记录偏移量，这个钩子是为系统提供的，这些系统也需要在自己的
     *  系统内部存储偏移量
     * </p>
     */
    public void commit() throws InterruptedException {
        // This space intentionally left blank.
    }

    /**
     * Signal this SourceTask to stop. In SourceTasks, this method only needs to signal to the task that it should stop
     * trying to poll for new data and interrupt any outstanding poll() requests. It is not required that the task has
     * fully stopped. Note that this method necessarily may be invoked from a different thread than {@link #poll()} and
     * {@link #commit()}.
     * 向此源任务发出停止信号，在SourceTasks中，此方法只需要向任务发出信号，表示他应该停止尝试轮询新数据并中断任何未完成的poll()请求，
     * 不要求任务已完全停止。请注意，此方法可以从poll()和commit（）之外的线程调用
     *
     * For example, if a task uses a {@link java.nio.channels.Selector} to receive data over the network,
     * 例如，如果一个任务使用java.nio.channels.Selector 来从网络端接收数据
     * this method could set a flag that will force {@link #poll()} to exit immediately and invoke
     * {@link java.nio.channels.Selector#wakeup() wakeup()} to interrupt any ongoing requests.
     *  这个方法将会设置一个标记并且将会强迫poll()立即退出，请求java.nio.channels.Selector的wakeup()方法来打断任何正在运行的请求
     */
    @Override
    public abstract void stop();

    /**
     * <p>
     *     individual单独的，个别的  producer生产者  received被承认
     * Commit an individual {@link SourceRecord} when the callback from the producer client is received, or if a record is filtered by a transformation.
     * 当接收到来自生产者客户端的回调时，或者如果记录被转换筛选，则提交单个SourceRecord
     * </p>
     * <p>
     * SourceTasks are not required to implement this functionality; Kafka Connect will record offsets
     * automatically. This hook is provided for systems that also need to store offsets internally
     * in their own system.
     * 实现此功能不需要SourceTasks，Kafka Connect将自动记录偏移量，这个钩子是为系统提供的，这些系统也需要在自己的
     *   系统内部存储偏移量
     *
     * </p>
     *
     * @param record {@link SourceRecord} that was successfully sent via the producer.
     * @throws InterruptedException
     */
    public void commitRecord(SourceRecord record) throws InterruptedException {
        // This space intentionally left blank.
    }
}
