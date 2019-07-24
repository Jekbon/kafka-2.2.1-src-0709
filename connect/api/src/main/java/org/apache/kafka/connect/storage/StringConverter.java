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

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaAndValue;
import org.apache.kafka.connect.errors.DataException;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link Converter} and {@link HeaderConverter} implementation that only supports serializing to strings. When converting Kafka Connect
 * data to bytes, the schema will be ignored and {@link Object#toString()} will always be invoked to convert the data to a String.
 * When converting from bytes to Kafka Connect format, the converter will only ever return an optional string schema and
 * a string or null.
 *  Converter和HeaderConverter只支持序列化到字符串的实现。将kafka connect数据转换为字节时，架构将被忽略，object的toString（）将始终被
 *  调用以将数据转换为字符串。当从字节转换为kafka connect格式时，转换器将只返回可选的字符串架构和字符串或空值
 * Encoding configuration is identical to {@link StringSerializer} and {@link StringDeserializer}, but for convenience
 * this class can also be configured to use the same encoding for both encoding and decoding with the
 * {@link StringConverterConfig#ENCODING_CONFIG converter.encoding} setting.
 *  编码配置与@Link StringSerializer和@Link StringDeserializer相同，但为了方便起见，
 *  也可以将该类配置为使用@Link StringConverterConfig Encoding Config Converter.Encoding设置对编码和解码使用相同的编码。
 *
 * This implementation currently does nothing with the topic names or header names.
 *  此实现当前与主题名或标题名无关
 */
public class StringConverter implements Converter, HeaderConverter {

    private final StringSerializer serializer = new StringSerializer();
    private final StringDeserializer deserializer = new StringDeserializer();

    public StringConverter() {
    }

    @Override
    public ConfigDef config() {
        return StringConverterConfig.configDef();
    }

    @Override
    public void configure(Map<String, ?> configs) {
        StringConverterConfig conf = new StringConverterConfig(configs);
        String encoding = conf.encoding();

        Map<String, Object> serializerConfigs = new HashMap<>(configs);
        Map<String, Object> deserializerConfigs = new HashMap<>(configs);
        serializerConfigs.put("serializer.encoding", encoding);
        deserializerConfigs.put("deserializer.encoding", encoding);

        boolean isKey = conf.type() == ConverterType.KEY;
        serializer.configure(serializerConfigs, isKey);
        deserializer.configure(deserializerConfigs, isKey);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Map<String, Object> conf = new HashMap<>(configs);
        conf.put(StringConverterConfig.TYPE_CONFIG, isKey ? ConverterType.KEY.getName() : ConverterType.VALUE.getName());
        configure(conf);
    }

    @Override
    public byte[] fromConnectData(String topic, Schema schema, Object value) {
        try {
            return serializer.serialize(topic, value == null ? null : value.toString());
        } catch (SerializationException e) {
            throw new DataException("Failed to serialize to a string: ", e);
        }
    }

    @Override
    public SchemaAndValue toConnectData(String topic, byte[] value) {
        try {
            return new SchemaAndValue(Schema.OPTIONAL_STRING_SCHEMA, deserializer.deserialize(topic, value));
        } catch (SerializationException e) {
            throw new DataException("Failed to deserialize string: ", e);
        }
    }

    @Override
    public byte[] fromConnectHeader(String topic, String headerKey, Schema schema, Object value) {
        return fromConnectData(topic, schema, value);
    }

    @Override
    public SchemaAndValue toConnectHeader(String topic, String headerKey, byte[] value) {
        return toConnectData(topic, value);
    }

    @Override
    public void close() {
        // do nothing
    }
}
