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
package org.apache.kafka.connect.storage;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * OffsetStorageReader provides access to the offset storage used by sources. This can be used by
 * connectors to determine offsets to start consuming data from. This is most commonly used during
 * initialization of a task, but can also be used during runtime, e.g. when reconfiguring a task.
 *  OffsetStorageReader提供对源使用的偏移存储的访问，这可由连接器用于确定开始使用数据的偏移量，
 *  这通常在任务初始化期间使用，但也可以在运行时使用，例如在重新配置任务时
 * </p>
 * <p>
 * Offsets are always defined as Maps of Strings to primitive types, i.e. all types supported by
 * {@link org.apache.kafka.connect.data.Schema} other than Array, Map, and Struct.
 * 偏移量始终定义为字符串到基元类型的映射，即Schema支持的除数组，映射和结构之外的所有类型
 * </p>
 */
public interface OffsetStorageReader {
    /**
     * round trips往返旅程  backing store后备存储器，辅助存储器，备份存储器
     * available可获得的，可购得的，可找到的
     * Get the offset for the specified partition. If the data isn't already available locally, this
     * gets it from the backing store, which may require some network round trips.
     * 获取这个指定分区的偏移量。 如果数据在本地尚不可用，这将从备份存储中获取，
     * 这可能需要一些网络往返
     * @param partition object uniquely identifying the partition of data 唯一标识分区
     * @return object uniquely identifying the offset in the partition of data 唯一标识数据分区中偏移量的对象
     */
    <T> Map<String, Object> offset(Map<String, T> partition);

    /**
     * <p>
     *     repeatedly重复的
     * Get a set of offsets for the specified partition identifiers. This may be more efficient
     * than calling {@link #offset(Map)} repeatedly.
     * 获取指定分区标识符的偏移量集，这可能比重复调用{@link #offset(Map)}更有效
     * </p>
     * <p>
     * Note that when errors occur, this method omits the associated data and tries to return as
     * many of the requested values as possible. This allows a task that's managing many partitions to
     * still proceed with any available data. Therefore, implementations should take care to check
     * that the data is actually available in the returned response. The only case when an
     * exception will be thrown is if the entire request failed, e.g. because the underlying
     * storage was unavailable.
     * 请注意，当出现错误时，此方法会忽略相关的数据，并尝试返回尽可能多的请求值。这样，
     * 管理多个分区的任务仍然可以继续处理任何可用的数据。因此，实现应该注意检查返回的响应中的数据是否实际可用。
     * 唯一会引发异常的情况是整个请求失败，例如，因为底层存储不可用
     * </p>
     *
     * @param partitions set of identifiers for partitions of data  数据分区的标识符集
     * @return a map of partition identifiers to decoded offsets   分区标识符到解码偏移量的映射
     */
    <T> Map<Map<String, T>, Map<String, Object>> offsets(Collection<Map<String, T>> partitions);
}
