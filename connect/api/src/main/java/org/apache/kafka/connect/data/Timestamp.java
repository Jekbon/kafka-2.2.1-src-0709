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
package org.apache.kafka.connect.data;

import org.apache.kafka.connect.errors.DataException;

/**
 * <p>
 *     corresponding 相应的  underlying根本的，底层的
 *     A timestamp representing an absolute time, without timezone information. The corresponding Java type is a
 *     表示绝对时间的时间戳，没有时区信息，相应的java类型是Date，底层表示是一个长的表示自Unix epoch以来的毫秒数
 *     java.util.Date. The underlying representation is a long representing the number of milliseconds since Unix epoch.
 * </p>
 */
public class Timestamp {
    /**
     * 如果拉开了时间帷幕来窥视一眼，我们职业生涯在21世纪是很漫长的，起码30年是有的，所以眼下的未知和迷茫是可以拨开的
     */
    public static final String LOGICAL_NAME = "org.apache.kafka.connect.data.Timestamp";

    /**
     * Returns a SchemaBuilder for a Timestamp. By returning a SchemaBuilder you can override additional schema settings such
     * as required/optional, default value, and documentation.
     * 返回时间戳的SchemaBuilder，通过返回SchemaBuilder，您可以覆盖其他模式设置，如必需/可选，默认值和文档
     *
     * @return a SchemaBuilder
     */
    public static SchemaBuilder builder() {
        return SchemaBuilder.int64()
                .name(LOGICAL_NAME)
                .version(1);
    }

    public static final Schema SCHEMA = builder().schema();

    /**
     * Convert a value from its logical format (Date) to it's encoded format.
     * 将值从其逻辑格式（日期）转换为其编码格式
     * @param value the logical value 逻辑值
     * @return the encoded value 编码值
     */
    public static long fromLogical(Schema schema, java.util.Date value) {
        if (!(LOGICAL_NAME.equals(schema.name())))
            throw new DataException("Requested conversion of Timestamp object but the schema does not match.");
        return value.getTime();
    }

    public static java.util.Date toLogical(Schema schema, long value) {
        if (!(LOGICAL_NAME.equals(schema.name())))
            throw new DataException("Requested conversion of Timestamp object but the schema does not match.");
        return new java.util.Date(value);
    }
}
