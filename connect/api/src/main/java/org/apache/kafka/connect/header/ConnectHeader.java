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

import java.util.Objects;

/**
 * A {@link Header} implementation.
 */
class ConnectHeader implements Header {

    private static final SchemaAndValue NULL_SCHEMA_AND_VALUE = new SchemaAndValue(null, null);

    private final String key;
    private final SchemaAndValue schemaAndValue;

    protected ConnectHeader(String key, SchemaAndValue schemaAndValue) {
        Objects.requireNonNull(key, "Null header keys are not permitted");
        this.key = key;
        this.schemaAndValue = schemaAndValue != null ? schemaAndValue : NULL_SCHEMA_AND_VALUE;
        assert this.schemaAndValue != null; //此处使用的是断言，如果不为null就继续，为null就抛出错误
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public Object value() {
        return schemaAndValue.value();
    }

    @Override
    public Schema schema() {
        Schema schema = schemaAndValue.schema();
        if (schema == null && value() instanceof Struct) {
            schema = ((Struct) value()).schema();
        }
        return schema;
    }

    @Override
    public Header rename(String key) {
        Objects.requireNonNull(key, "Null header keys are not permitted");//key为null，直接返回NullPointerException
        if (this.key.equals(key)) {
            return this; //如果和当前的key一样，直接返回
        }
        return new ConnectHeader(key, schemaAndValue);//不一样就创建新的（用新的key），schema和value不变
    }

    @Override
    public Header with(Schema schema, Object value) {
        return new ConnectHeader(key, new SchemaAndValue(schema, value));//key不变，生成新的schema和value
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, schemaAndValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Header) {
            Header that = (Header) obj;
            return Objects.equals(this.key, that.key()) && Objects.equals(this.schema(), that.schema()) && Objects.equals(this.value(),
                                                                                                                          that.value());
        }
        return false;
    }

    @Override
    public String toString() {
        return "ConnectHeader(key=" + key + ", value=" + value() + ", schema=" + schema() + ")";
    }
}
