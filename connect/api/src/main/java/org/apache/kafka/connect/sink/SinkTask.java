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
package org.apache.kafka.connect.sink;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.connector.Task;

import java.util.Collection;
import java.util.Map;

/**
 * SinkTask is a Task that takes records loaded from Kafka and sends them to another system. Each task
 * instance is assigned a set of partitions by the Connect framework and will handle all records received
 * from those partitions. As records are fetched from Kafka, they will be passed to the sink task using the
 * {@link #put(Collection)} API, which should either write them to the downstream system or batch them for
 * later writing. Periodically, Connect will call {@link #flush(Map)} to ensure that batched records are
 * actually pushed to the downstream system..
 * sinktask是一个从kafka加载的记录并将其发送到另一个系统的任务。每项任务
 *
 * 实例由connect框架分配一组分区，并将处理接收到的所有记录
 *
 * 从那些分区。当从Kafka中提取记录时，将使用
 *
 * @link put（collection）api，应将其写入下游系统或批处理
 *
 * 以后再写。Connect将定期调用@link flush（map）以确保批处理记录
 *
 * 实际上被推到下游系统。
 *
 * Below we describe the lifecycle of a SinkTask.
 * 下面我们描述一个sinktask的生命周期
 *
 * <ol>
 *     <li><b>Initialization:</b> SinkTasks are first initialized using {@link #initialize(SinkTaskContext)}
 *     to prepare the task's context and {@link #start(Map)} to accept configuration and start any services
 *     needed for processing.</li>
 *     初始化：SinkTasks 最开始使用SinkTaskContext初始化准备任务文本接受配置并且启动处理所需的任何服务
 *     <li><b>Partition Assignment:</b> After initialization, Connect will assign the task a set of partitions
 *     using {@link #open(Collection)}. These partitions are owned exclusively by this task until they
 *     have been closed with {@link #close(Collection)}.</li>
 *     exclusively 完全的，独有的
 *     分区工作：初始化后，connect将使用Collection为任务分配一组分区
 *     这些分区在此任务独占，直到他们已使用Collection来关闭
 *
 *     <li><b>Record Processing:</b> Once partitions have been opened for writing, Connect will begin forwarding
 *     records from Kafka using the {@link #put(Collection)} API. Periodically, Connect will ask the task
 *     to flush records using {@link #flush(Map)} as described above.</li>
 *     记录处理：一旦打开分区进行写入，connect将会开始转发使用Collection的put api。connect会定期地询问任务来刷新记录使用上面描述的样子
 *
 *      assignment工作
 *     <li><b>Partition Rebalancing:</b> Occasionally, Connect will need to change the assignment of this task.
 *     When this happens, the currently assigned partitions will be closed with {@link #close(Collection)} and
 *     the new assignment will be opened using {@link #open(Collection)}.</li>
 *     分区重新平衡：有时候，connect将需要更改此任务的分配，发生这种情况的时候，当前分配的分区将通过Collection关闭
 *     并且新的任务将会使用collection开启
 *
 *     <li><b>Shutdown:</b> When the task needs to be shutdown, Connect will close active partitions (if there
 *     are any) and stop the task using {@link #stop()}</li>
 *      关闭：当任务需要关闭时，connect将关闭活动分区（如果有）并使用stop停止任务
 *
  * </ol>
 *
 */
public abstract class SinkTask implements Task {

    /**
     * <p>
     * The configuration key that provides the list of topics that are inputs for this
     * SinkTask.
     *  提供作为此SinkTask输入的主题列表的配置键
     * </p>
     */
    public static final String TOPICS_CONFIG = "topics";

    /**
     * <p>
     *     specifying 具体说明，详列  for this 为此
     * The configuration key that provides a regex specifying which topics to include as inputs
     * for this SinkTask.
     * 提供regex的配置键，指定要包括哪些主题作为此SinkTask的输入
     *
     * </p>
     */
    public static final String TOPICS_REGEX_CONFIG = "topics.regex";

    protected SinkTaskContext context;

    /**
     * Initialize the context of this task. Note that the partition assignment will be empty until
     * Connect has opened the partitions for writing with {@link #open(Collection)}.
     *  初始化此任务的上下文，请注意，分区分配将为空，直到Connect已打开分区以便使用open进行写入
     * @param context The sink task's context
     */
    public void initialize(SinkTaskContext context) {
        this.context = context;
    }

    /**
     * Start the Task. This should handle any configuration parsing and one-time setup of the task.
     *  开始任务，这应该处理任何配置解析和任务的一次性设置
     * @param props initial configuration
     */
    @Override
    public abstract void start(Map<String, String> props);

    /**
     * Put the records in the sink. Usually this should send the records to the sink asynchronously
     * and immediately return.
     * 把记录放到水槽里，通常，这应该将记录异步发送到接收器，并立即返回
     *
     * If this operation fails, the SinkTask may throw a {@link org.apache.kafka.connect.errors.RetriableException} to
     * indicate that the framework should attempt to retry the same call again. Other exceptions will cause the task to
     * be stopped immediately. {@link SinkTaskContext#timeout(long)} can be used to set the maximum time before the
     * batch will be retried.
     *  如果操作失败，SinkTask可能会向指示框架尝试重试相同的请求，其他任务将会导致任务立即被停止。SinkTaskContext 可用于设置
     *  批处理重试前的最长时间
     *
     * @param records the set of records to send
     */
    public abstract void put(Collection<SinkRecord> records);

    /**
     * Flush all records that have been {@link #put(Collection)} for the specified topic-partitions.
     *  刷新指定主题分区的所有记录
     *
     *  convenience 方便
     *
     * @param currentOffsets the current offset state as of the last call to {@link #put(Collection)}},
     *                       provided for convenience but could also be determined by tracking all offsets included in the {@link SinkRecord}s
     *                       passed to {@link #put}.
     *                       上次调用put时的当前偏移量状态，为方便起见，也可通过跟踪SinkRecord中包含的所有偏移量来确定传递给put
     */
    public void flush(Map<TopicPartition, OffsetAndMetadata> currentOffsets) {
    }

    /**
     *
     * hook钩  invoked提出  prior to 在前，居先
     * Pre-commit hook invoked prior to an offset commit.
     * 在偏移提交之前调用预提交挂钩
     * The default implementation simply invokes {@link #flush(Map)} and is thus able to assume all {@code currentOffsets} are safe to commit.
     * 默认实现只调用flush方法，
     *
     * @param currentOffsets the current offset state as of the last call to {@link #put(Collection)}},
     *                       provided for convenience but could also be determined by tracking all offsets included in the {@link SinkRecord}s
     *                       passed to {@link #put}.
     *                       上次调用put时的当前偏移量状态，为方便起见，也可通过跟踪SinkRecord中包含的所有偏移量来确定传递给put
     *
     * @return an empty map if Connect-managed offset commit is not desired, otherwise a map of offsets by topic-partition that are safe to commit.
     *          如果不需要连接托管偏移量提交，则为空映射，否则为安全提交的按主题分区的偏移映射
     */
    public Map<TopicPartition, OffsetAndMetadata> preCommit(Map<TopicPartition, OffsetAndMetadata> currentOffsets) {
        flush(currentOffsets);
        return currentOffsets;
    }

    /**
     * The SinkTask use this method to create writers for newly assigned partitions in case of partition
     * rebalance. This method will be called after partition re-assignment completes and before the SinkTask starts
     * fetching data. Note that any errors raised from this method will cause the task to stop.
     *  SinkTask使用此方法为新分配的分区创建写入程序，以防分区重新平衡。此方法将在分区重新分配完成后和sinktask开始获取数据之前调用。
     *  请注意，此方法引发的任何错误都将导致任务停止
     * @param partitions The list of partitions that are now assigned to the task (may include
     *                   partitions previously assigned to the task)
     *                   现在分配给任务的分区列表（可能包含以前分配给任务的分区）
     */
    public void open(Collection<TopicPartition> partitions) {
        this.onPartitionsAssigned(partitions);
    }

    /**
     * @deprecated Use {@link #open(Collection)} for partition initialization.
     *              使用open方法进行分区的初始化
     */
    @Deprecated
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
    }

    /**
     * The SinkTask use this method to close writers for partitions that are no
     * longer assigned to the SinkTask. This method will be called before a rebalance operation starts
     * and after the SinkTask stops fetching data. After being closed, Connect will not write
     * any records to the task until a new set of partitions has been opened. Note that any errors raised
     * from this method will cause the task to stop.
     * SinkTask使用此方法关闭不再分配给SinkTask的分区的写入程序。此方法将在重新平衡操作开始之前和SinkTask停止获取数据之后调用。关闭后，在打开一组新分区之前
     * Connect不会向任务写入任何记录。请注意，此方法引发的任何错误都将导致任务停止
     *
     * @param partitions The list of partitions that should be closed  应关闭的分区列表
     */
    public void close(Collection<TopicPartition> partitions) {
        this.onPartitionsRevoked(partitions);
    }

    /**
     * @deprecated Use {@link #close(Collection)} instead for partition cleanup.
     *            使用close代替分区清理
     */
    @Deprecated
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
    }

    /**
     * Perform any cleanup to stop this task. In SinkTasks, this method is invoked only once outstanding calls to other
     * methods have completed (e.g., {@link #put(Collection)} has returned) and a final {@link #flush(Map)} and offset
     * commit has completed. Implementations of this method should only need to perform final cleanup operations, such
     * as closing network connections to the sink system.
     *  执行任何清理以停止此任务。在SinkTask中，只有在对其他方法的未完成调用（例如，put已返回）和最终的flush方法和抵消提交完成后。
     *  此方法的实现只需要执行最终的清理操作，例如关闭与接收器系统的网络连接
     */
    @Override
    public abstract void stop();
}
