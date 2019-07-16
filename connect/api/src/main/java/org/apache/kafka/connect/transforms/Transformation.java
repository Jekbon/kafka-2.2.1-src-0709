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
package org.apache.kafka.connect.transforms;

import org.apache.kafka.common.Configurable;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;

import java.io.Closeable;

/**
 * Single message transformation for Kafka Connect record types.
 *  Kafka Connect 记录类型的单个消息转换
 *  message-at-a-time 及时消息    lightweight轻量的    modifications修改，改进
 * Connectors can be configured with transformations to make lightweight message-at-a-time modifications.
 * 连接器可以通过转换进行配置，以进行轻量级消息一次修改
 */
public interface Transformation<R extends ConnectRecord<R>> extends Configurable, Closeable {

    /**
     * Apply transformation to the {@code record} 引用转换到record
     * and return another record object (which may be {@code record} itself) or {@code null},并且返回另一个record对象（此对象可以记录自己或者是null）
     * corresponding to a map or filter operation respectively.分别对应于映射或过滤操作
     *
     * The implementation must be thread-safe. 实现必须是线程安全的
     */
    R apply(R record);

    /** Configuration specification for this transformation.
     *  此变换的配置规范
     *  specification 规范
     * **/
    ConfigDef config();

    /** Signal that this transformation instance will no longer will be used.
     *  表示将不再使用此转换实例
     * signal 标记，信号，暗示
     * **/
    @Override
    void close();

}
