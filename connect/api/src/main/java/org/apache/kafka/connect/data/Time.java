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

import java.util.Calendar;
import java.util.TimeZone;

/**
 * <p>
 *     tied系，捆绑   corresponding符合的，相应的，相关的
 *     effectively有效的，实际上   in time合拍，最终，迟早  first day星期日   epoch时代，纪元，时期
 *     underlying根本的，下层的   representation表现，描述，描绘  milliseconds毫秒  midnight午夜，子夜
 *     A time representing a specific point in a day, not tied to any specific date. The corresponding Java type is a
 *     java.util.Date where only hours, minutes, seconds, and milliseconds can be non-zero. This effectively makes it a
 *     point in time during the first day after the Unix epoch. The underlying representation is an integer
 *     representing the number of milliseconds after midnight.
 *     表示一天中某一特定点的时间，与任何特定日期无关。符合java类型的java.util.Date，其中只有小时，分钟，秒，毫秒可以为非零。
 *      这实际上使它成为Unix时代后第一天的时间点。基础表示是表示午夜后毫秒数的整数
 * </p>
 */
public class Time {
    public static final String LOGICAL_NAME = "org.apache.kafka.connect.data.Time";

    private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    /**
     * Returns a SchemaBuilder for a Time. By returning a SchemaBuilder you can override additional schema settings such
     * as required/optional, default value, and documentation.
     * 通过返回SchemaBuilder，您可以覆盖其他模式设置，如必需/可选，默认值和文档
     * @return a SchemaBuilder 架构生成器
     */
    public static SchemaBuilder builder() {
        return SchemaBuilder.int32()
                .name(LOGICAL_NAME)
                .version(1);
    }

    public static final Schema SCHEMA = builder().schema();

    /**
     * Convert a value from its logical format (Time) to it's encoded format.
     * 将值从其逻辑格式(时间)转换为其编码格式
     * @param value the logical value
     * @return the encoded value
     */
    public static int fromLogical(Schema schema, java.util.Date value) {
        if (!(LOGICAL_NAME.equals(schema.name())))
            throw new DataException("Requested conversion of Time object but the schema does not match.");
        Calendar calendar = Calendar.getInstance(UTC);
        calendar.setTime(value);
        long unixMillis = calendar.getTimeInMillis();
        if (unixMillis < 0 || unixMillis > MILLIS_PER_DAY) {
            throw new DataException("Kafka Connect Time type should not have any date fields set to non-zero values.");
        }
        return (int) unixMillis;
    }

    public static java.util.Date toLogical(Schema schema, int value) {
        if (!(LOGICAL_NAME.equals(schema.name())))
            throw new DataException("Requested conversion of Date object but the schema does not match.");
        if (value  < 0 || value > MILLIS_PER_DAY)
            throw new DataException("Time values must use number of milliseconds greater than 0 and less than 86400000");
        return new java.util.Date(value);
    }
}
