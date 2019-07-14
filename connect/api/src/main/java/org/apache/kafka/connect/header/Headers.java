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
import org.apache.kafka.connect.data.SchemaAndValue;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.errors.DataException;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A mutable ordered collection of {@link Header} objects. Note that multiple headers may have the same {@link Header#key() key}.
 * header 对象的可变有序集合，请注意，多个标题可能具有相同的key键
 * multiple数量多的，多种多样的，倍数
 *
 */
public interface Headers extends Iterable<Header> {

    /**
     * Get the number of headers in this object.
     * 获取此对象中headers的数目
     * @return the number of headers; never negative  返回headers的数目，从不为负
     */
    int size();

    /**
     * Determine whether this object has no headers.
     * Determine 查明，测定，决定
     * 确定此对象是否没有标题
     * @return true if there are no headers, or false if there is at least one header 如果他没有headers返回true，如果他至少有一个header，就返回false
     */
    boolean isEmpty();

    /**
     * Get the collection of {@link Header} objects whose {@link Header#key() keys} all match the specified key.
     * 获取header对象的集合，这些对象的header key()键都与指定的键匹配
     * @param key the key; may not be null 参数是key，不能为null
     * @return the iterator over headers with the specified key; may be null if there are no headers with the specified key
     *  在具有指定键的头上返回迭代器；如果没有具有指定键的头，则可能为空
     *
     */
    Iterator<Header> allWithName(String key);

    /**
     * Return the last {@link Header} with the specified key.
     *  指定key返回最后一个header
     * @param key the key for the header; may not be null 参数是header的key，不能为null
     * @return the last Header, or null if there are no headers with the specified key
     *  返回最后一个header，如果没有指定的key就是null
     */
    Header lastWithName(String key);

    /**
     * Add the given {@link Header} to this collection.
     *  添加这个Header到此集合
     * @param header the header; may not be null 参数是Header，不可以为null
     * @return this object to facilitate chaining multiple methods; never null
     *  返回此对象以便于链接多个方法，不能为null
     *   facilitate促使，便利
     *   chaining 用锁链拴住
     *   multiple 数量多的，多种多样的，倍数
     */
    Headers add(Header header);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     * 使用给定的键和值向此集合添加header
     * @param key            the header's key; may not be null  header的key，不能为空
     * @param schemaAndValue the {@link SchemaAndValue} for the header; may be null 参数header的SchemaAndValue，不可以为null
     * @return this object to facilitate chaining multiple methods; never null
     *  返回此对象以便于链接多个方法，从不为null
     */
    Headers add(String key, SchemaAndValue schemaAndValue);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     * 给了key和value的Header添加到集合中
     * @param key    the header's key; may not be null  header的key不能为null
     * @param value  the header's value; may be null   header的value不能为null
     * @param schema the schema for the header's value; may not be null if the value is not null 模式对header的值，如果value不为null，schema就不为null
     * @return this object to facilitate chaining multiple methods; never null
     * 返回此对象以便于链接多个方法，从不为null
     */
    Headers add(String key, Object value, Schema schema);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *  给了key和value的Header添加到集合中
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addString(String key, String value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addBoolean(String key, boolean value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addByte(String key, byte value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addShort(String key, short value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addInt(String key, int value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addLong(String key, long value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addFloat(String key, float value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addDouble(String key, double value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addBytes(String key, byte[] value);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key    the header's key; may not be null
     * @param value  the header's value; may be null
     * @param schema the schema describing the list value; may not be null
     * @return this object to facilitate chaining multiple methods; never null
     * @throws DataException if the header's value is invalid
     */
    Headers addList(String key, List<?> value, Schema schema);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key    the header's key; may not be null
     * @param value  the header's value; may be null
     * @param schema the schema describing the map value; may not be null
     * @return this object to facilitate chaining multiple methods; never null
     * @throws DataException if the header's value is invalid
     */
    Headers addMap(String key, Map<?, ?> value, Schema schema);

    /**
     * Add to this collection a {@link Header} with the given key and value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     * @throws DataException if the header's value is invalid
     */
    Headers addStruct(String key, Struct value);

    /**
     * Add to this collection a {@link Header} with the given key and {@link org.apache.kafka.connect.data.Decimal} value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's {@link org.apache.kafka.connect.data.Decimal} value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addDecimal(String key, BigDecimal value);

    /**
     * Add to this collection a {@link Header} with the given key and {@link org.apache.kafka.connect.data.Date} value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's {@link org.apache.kafka.connect.data.Date} value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addDate(String key, java.util.Date value);

    /**
     * Add to this collection a {@link Header} with the given key and {@link org.apache.kafka.connect.data.Time} value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's {@link org.apache.kafka.connect.data.Time} value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addTime(String key, java.util.Date value);

    /**
     * Add to this collection a {@link Header} with the given key and {@link org.apache.kafka.connect.data.Timestamp} value.
     *
     * @param key   the header's key; may not be null
     * @param value the header's {@link org.apache.kafka.connect.data.Timestamp} value; may be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers addTimestamp(String key, java.util.Date value);

    /**
     * Removes all {@link Header} objects whose {@link Header#key() key} matches the specified key.
     * 删除其 header的key与指定键匹配的所有的header对象
     * @param key the key; may not be null
     * @return this object to facilitate chaining multiple methods; never null
     * 返回此对象以便于链接多个方法，从不为null
     */
    Headers remove(String key);

    /**the latest 最新的
     * all but 几乎，差不多
     * Removes all but the latest {@link Header} objects whose {@link Header#key() key} matches the specified key.
     *  删除除了最新header对象以外的所有对象，这个header的key键与指定键匹配
     * @param key the key; may not be null
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers retainLatest(String key);

    /**
     * last 最后的，末尾的  retain保留，持有
     * Removes all but the last {@Header} object with each key.
     *  用每个键删除除最后一个header对象之外的所有对象（保留最新的）
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers retainLatest();

    /**
     * Removes all headers from this object.
     * 清除所有的headers从这个对象
     * @return this object to facilitate chaining multiple methods; never null
     */
    Headers clear();

    /**
     * duplicate 名词，完全一样的东西
     * Create a copy of this {@link Headers} object. 复制这个对象
     * The new copy will contain all of the same {@link Header} objects as this object. 新副本将包含此对象相同的header对象
     * @return the copy; never null
     */
    Headers duplicate();

    /**
     * store存储 result in 终于  transform使改变形态，使改观
     * Get all {@link Header}s, apply the transform to each and store the result in place of the original.
     * 获取所有header，对每个头应用转换，并将结果存储在原始位置
     * @param transform the transform to apply; may not be null 要应用的转换，不能为null
     * @return this object to facilitate chaining multiple methods; never null  返回此对象以便于链接多个方法，从不为null
     * @throws DataException if the header's value is invalid 无效的
     */
    Headers apply(HeaderTransform transform);

    /**
     * Get all {@link Header}s with the given key, apply the transform to each and store the result in place of the original.
     *  通过key获取所有的header，对每个header应用转换，并将结果存储在原始位置
     * @param key       the header's key; may not be null
     * @param transform the transform to apply; may not be null 要应用的转换，不能为null
     * @return this object to facilitate chaining multiple methods; never null
     * @throws DataException if the header's value is invalid
     */
    Headers apply(String key, HeaderTransform transform);

    /**
     * A function to transform the supplied {@link Header}. Implementations will likely need to use {@link Header#with(Schema, Object)}
     * to create the new instance. 转换提供的header函数。 实现可能需要使用 {@link Header#with(Schema, Object)} 来创建新实例
     */
    interface HeaderTransform {
        /**
         * Transform the given {@link Header} and return the updated {@link Header}.
         * 转换给的header并且返回一个更新后的header
         * @param header the input header; never null 输入的header，不能为null
         * @return the new header, or null if the supplied {@link Header} is to be removed  返回一个新的header，如果提供的header被删掉了，就是null
         */
        Header apply(Header header);
    }
}
