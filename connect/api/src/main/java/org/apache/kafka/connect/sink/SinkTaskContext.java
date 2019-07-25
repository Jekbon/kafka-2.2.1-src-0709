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

import org.apache.kafka.common.TopicPartition;

import java.util.Map;
import java.util.Set;

/**
 * Context passed to SinkTasks, allowing them to access utilities in the Kafka Connect runtime.
 * 上下文传递给SinkTasks，允许它们访问Kafka Connect运行时中的实用程序
 * access通道  utilities公用事业，实用
 */
public interface SinkTaskContext {

    /**
     * Get the Task configuration.  This is the latest configuration and may differ from that passed on startup.
     * 获取任务的配置文件。这是最新的配置文件可能与传递的配置不同
     * For example, this method can be used to obtain the latest configuration if an external secret has changed,
     * and the configuration is using variable references such as those compatible with
     * {@link org.apache.kafka.common.config.ConfigTransformer}.
     * variable变量   obtain获得，存在  compatible可共用的，兼容的，关系好的
     * 例如，如果外部秘钥变更，并且配置使用变量引用时，可以使用此方法获取最新配置
     */
    public Map<String, String> configs();

    /**
     * Reset the consumer offsets for the given topic partitions. SinkTasks should use this if they manage offsets
     * in the sink data store rather than using Kafka consumer offsets. For example, an HDFS connector might record
     * offsets in HDFS to provide exactly once delivery. When the SinkTask is started or a rebalance occurs, the task
     * would reload offsets from HDFS and use this method to reset the consumer to those offsets.
     * 给主题的分区重新设置消费者的偏移量。【如果他们管理在sink数据存储宁愿使用kafka的消费者偏移量偏移量SinkTask应该使用这个<个人翻译- ->】
     * 如果SinkTasks在sink数据存储中管理偏移量，而不是使用kafka消费者偏移量，则应使用此方法。
     * 例如，一个hdfs连接器可以记录偏移量来提供一次确切的传递。
     * 例如，一个HDFS连接器可能会在HDFS中记录偏移量，以提供精确的一次性交付
     * 当SinkTask被启动或者重新分区发生时，此任务将会从HDFS中重新加载偏移量并且使用此方法重新设置消费者到当前偏移量
     *
     * 当sinktask启动或发生重新平衡时，该任务将重新加载来自HDF的偏移量，并使用此方法将使用者重置为这些偏移量。
     * SinkTasks that do not manage their own offsets do not need to use this method.
     * 不管理自己偏移量的下沉任务不需要使用此方法。
     * @param offsets map of offsets for topic partitions 主题分区的偏移量
     */
    void offset(Map<TopicPartition, Long> offsets);

    /**
     * Reset the consumer offsets for the given topic partition. SinkTasks should use if they manage offsets
     * in the sink data store rather than using Kafka consumer offsets. For example, an HDFS connector might record
     * offsets in HDFS to provide exactly once delivery. When the topic partition is recovered the task
     * would reload offsets from HDFS and use this method to reset the consumer to the offset.
     *  给主题分区重新设置消费者偏移量。如果SinkTasks在sink数据存储中管理偏移量，而不是使用kafka消费者偏移量，则应使用此方法
     *  例如，一个HDFS连接器可能会在HDFS中记录偏移量，以提供精确的一次性交付
     *
     * SinkTasks that do not manage their own offsets do not need to use this method.
     * 不管理自己偏移量的下沉任务不需要使用此方法。
     * @param tp the topic partition to reset offset. 重置偏移量的主题分区
     * @param offset the offset to reset to. 要重置为的偏移量
     */
    void offset(TopicPartition tp, long offset);

    /**
     * indicate 表明，显示  retry重试  certain确定，肯定  operations操作  in case of若在...情况下，万一
     * temporary暂时的
     * Set the timeout in milliseconds. SinkTasks should use this to indicate that they need to retry certain
     * operations after the timeout. SinkTasks may have certain operations on external systems that may need
     * to retry in case of failures. For example, append a record to an HDFS file may fail due to temporary network
     * issues. SinkTasks use this method to set how long to wait before retrying.
     * 设置毫秒级的超时。SinkTasks应该使用此方法显示，他们超时之后需要重试 直到可以操作了
     * SinkTasks应使用此项指示它们需要在超时后重试某些操作。
     * SinkTasks在外部系统有明确指示，万一失败就需要重试。
     * SinkTasks在外部系统上可能有某些操作，在出现故障时可能需要重试
     * 例如，在HDFS文件追加一个记录可能失败由于暂时的网络问题
     * 例如，将记录附加到HDFS文件可能由于临时网络问题而失败
     * SinkTasks使用这个方法在重试之前设置等待多长时间
     * SinkTasks使用此方法设置重试前等待的时间
     * @param timeoutMs the backoff timeout in milliseconds.
     *                  backoff 退后  milliseconds毫秒
     *                  回退超时毫秒
     */
    void timeout(long timeoutMs);

    /**
     * When you contribute code,you affirm  that the contribution is your original work and that you license the work
     * to the project under the project's open source licence.Whether or not you state this explicitly,but submitting
     * any copyrighted material via pull request,email,or other means you agree to license the material under the project's
     * open source license and warrant that you have the legal authority to do this.
     * 当您贡献代码时，您确认贡献是您的原始工作，并且您根据项目的开源许可证将工作授权给项目。无论您是否明确声明，通过拉取请求
     * ，电子邮件或其他方式提交任何受版权保护的材料，您都同意根据项目的开源许可对材料进行许可，并保证您具有这样做的法律权限
     */

    /**
     * for this为此   assigned分配的
     * Get the current set of assigned TopicPartitions for this task. 获取当前为此任务分配的topicPartitions集
     * @return the set of currently assigned TopicPartitions 当前分配的TopicPartitions集
     */
    Set<TopicPartition> assignment();

    /**
     * pause暂停 consumption消费
     * Pause consumption of messages from the specified TopicPartitions. 从指定的TopicPartitions集暂停消费信息
     * @param partitions the partitions which should be paused 需要被暂停的分区
     */
    void pause(TopicPartition... partitions);

    /**
     * resume重新开始，继续   previously之前的，以前，先前
     * Resume consumption of messages from previously paused TopicPartitions. 继续消费之前暂停的TopicPartitions消息
     * @param partitions the partitions to resume 重新开始的分区
     */
    void resume(TopicPartition... partitions);

    /**
     * minimize使减少到最低限度，降低  as soon as 立刻，一...就   destination目的地，终点，作为目的地的
     * Request an offset commit. Sink tasks can use this to minimize the potential for redelivery
     * by requesting an offset commit as soon as they flush data to the destination system.
     * 请求提交的偏移量。接收任务可以使用此功能，通过在将数据刷新到目标系统后立即请求偏移提交，将
     * 重新传递的可能性降到了最低
     * hint提示，暗示，示意   timing定时，时间的选择   assumed假定的，假设的，认为，承担
     * It is only a hint to the runtime and no timing guarantee should be assumed.
     * 它只是对运行时的一个提示，不应该假定时间保证
     */
    void requestCommit();

}
