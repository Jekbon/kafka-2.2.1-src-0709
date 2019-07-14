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
package org.apache.kafka.connect.header;

import org.apache.kafka.connect.data.Schema;

/**
 * A {@link Header} is a key-value pair, and multiple headers can be included with the key, value, and timestamp in each Kafka message.
 * Header是键值对，多种多样的headers可以包含key，value和时间戳在每一个Kafka的消息中
 * If the value contains schema information, then the header will have a non-null {@link #schema() schema}.
 *  如果值里面包含了schema信息，这个header将不能有null的schema
 * <p>
 * This is an immutable interface.
 * 这是一个不可变的接口
 */
public interface Header {

    /**
     * The header's key, which is not necessarily unique within the set of headers on a Kafka message.
     * header的key，在kafka消息的头集合中不一定是唯一的
     * within在之内，在里面
     * @return the header's key; never null
     */
    String key();

    /**
     * Return the {@link Schema} associated with this header, if there is one. Not all headers will have schemas.
     * associated with  参加，与...有关   与...相联系
     * 返回与此标题相关的模式（如果有），并非所有的头都有模式
     * @return the header's schema, or null if no schema is associated with this header
     *  返回header的模式，如果没有和此header相关的模式，就返回null
     */
    Schema schema();

    /**
     * Get the header's value as deserialized by Connect's header converter.
     * 获取由connect的头转换器反序列化的头值
     * converter 转换器，使发生转化的人
     * @return the deserialized object representation of the header's value; may be null
     * 返回头值的反序列化对象表示形式，可以为空
     */
    Object value();

    /**
     * Return a new {@link Header} object that has the same key but with the supplied value.
     * 返回一个新的header对象，该对象具有相同的键，但具有提供的值（大概是重新赋值了value吧）
     * @param schema the schema for the new value; may be null 新的模式的值，也可能是null
     * @param value  the new value 一个新的值
     * @return the new {@link Header}; never null 返回一个新的Header，不可以是null
     */
    Header with(Schema schema, Object value);

    /**
     * Return a new {@link Header} object that has the same schema and value but with the supplied key.
     *  返回一个新的Header对象，有一样的模式和值，但是额外赋予key
     * @param key the key for the new header; may not be null 参数header新的key，不能为null
     * @return the new {@link Header}; never null  返回一个新的Header，从不为null
     */
    Header rename(String key);
}
