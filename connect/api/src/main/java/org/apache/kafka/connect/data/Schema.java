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

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 *     Definition of an abstract data type. Data types can be primitive types (integer types, floating point types,
 *     抽象数据类型的定义。数据类型可以使基本数据类型（整数型，浮点型，布尔型，字符串，字节）或者是复杂类型(数组类型，key-value的map类型)
 *     boolean, strings, and bytes) or complex types (typed arrays, maps with one key schema and value schema,
 *     and structs that have a fixed set of field names each with an associated value schema). Any type can be specified
 *     以及具有一组固定字段名（每个字段名都具有关联的值模式）。任何类型都可以指定为可选，允许省略它（当他丢失时会导致空值），并且可以指定默认值
 *     as optional, allowing it to be omitted (resulting in null values when it is missing) and can specify a default
 *     value.
 * </p>
 *  associated有关联的，相关的  schemas模式  part of 部分   considered经过慎重考虑，以为，认为
 *  comparing比较，对比   logical必然的，符合逻辑的
 * <p>
 *     All schemas may have some associated metadata: a name, version, and documentation.
 *      所有的模式都可能是有关联的源数据，一个名称，版本号，还有文档
 *     These are all considered part of the schema itself and included when comparing schemas.
 *      这些都是模式本身的一部分，并且在比较模式时包含在其中
 *     Besides adding important metadata, these fields enable
 *     the specification of logical types that specify additional constraints and semantics (e.g. UNIX timestamps are
 *     just an int64, but the user needs the know about the additional semantics to interpret it properly).
 *     除了添加重要的元数据，这些字段还支持指定附加约束和语义的逻辑类型的规范(例如，Unix时间戳只是一个int64，
 *     但用户需要了解附加语义才能正确地解释它)
 * </p>
 * <p>
 *     Schemas can be created directly, but in most cases using {@link SchemaBuilder} will be simpler.
 *     模式可以直接创建，但是大多数情况下，使用SchemaBuilder 会更简单
 * </p>
 */
public interface Schema {
    /**
     * The type of a schema. These only include the core types; logical types must be determined by checking the schema name.
     *  架构的类型，这些只包括核心类型；逻辑类型必须通过检查架构名称来确定
     */
    /**
     *   无符号整数和有符号整数怎么区分？
     *   有无符号的整数，在计算机内存中是区别不出有无符号的，而是在程序里有区分。计算机中数据是以补码形式存放的，
     *   用二进制表示。比如：默认无符号型，只要在类型符号加unsigned就是无符号型，Int是有符号的。
     *   其实说白了就是：定义带符号整数的，则可以存储正负整数，定义无符号整数的，则只可以存储正整数
     */
    enum Type {
        /**
         *  8-bit signed integer 八位有符号整型
         *
         *  Note that if you have an unsigned 8-bit data source, {@link Type#INT16} will be required to safely capture all valid values
         *  请注意，如果您有一个无符号的8位数据源，int16将需要安全捕获所有有效值
         */
        INT8,
        /**
         *  16-bit signed integer  16位有符号整数
         *  note笔记，记录，注释     capture捕获   require需要   safely安全地   valid有效的，合理的
         *  Note that if you have an unsigned 16-bit data source, {@link Type#INT32} will be required to safely capture all valid values
         *  请注意，如果您有一个无符号的16位数据源，int32将需要捕获所有有效值
         */
        INT16,
        /**
         *  32-bit signed integer  32位有符号整数
         *
         *  Note that if you have an unsigned 32-bit data source, {@link Type#INT64} will be required to safely capture all valid values
         *  请注意，如果您有一个无符号的32位数据源，int64位将需要捕获所有有效值
         */
        INT32,
        /**
         *  64-bit signed integer 64位有符号整数
         *
         *  Note that if you have an unsigned 64-bit data source, the {@link Decimal} logical type (encoded as {@link Type#BYTES})
         *  will be required to safely capture all valid values
         *  请注意，如果您有一个无符号的64位数据源，则需要Decimal 逻辑类型（编码为bytes类型）来安全捕获所有有效值
         */
        INT64,
        /**
         *  32-bit IEEE 754 floating point number  32位（电气与电子工程师学会）754浮点数
         */
        FLOAT32,
        /**
         *  64-bit IEEE 754 floating point number  64位（电气与电子工程师学会）754浮点数
         */
        FLOAT64,
        /**
         * Boolean value (true or false)
         */
        BOOLEAN,
        /**
         * Character string that supports all Unicode characters. 支持所有Unicode字符的字符串
         *
         *      imply暗示，含有...的意思
         * Note that this does not imply any specific encoding (e.g. UTF-8) as this is an in-memory representation.
         * 注意，这并不意味着任何特定的编码（例如UTF-8），因为这是内存中的一种表示
         */
        STRING,
        /**
         * Sequence of unsigned 8-bit bytes  无符号8位字节序列
         */
        BYTES,
        /**
         * An ordered sequence of elements, each of which shares the same type.
         * 元素的有序序列，每个元素共享同一类型【数组】
         */
        ARRAY,
        /**
         * A mapping from keys to values. Both keys and values can be arbitrarily complex types, including complex types
         * such as {@link Struct}.
         * 从键到值的映射。键和值都可以是任意复杂的类型，包括复杂类型，如 Struct
         */
        MAP,
        /**
         * A structured record containing a set of named fields, each field using a fixed, independent {@link Schema}.
         * 包含一组命名字段的结构化记录，每个字段使用固定的，独立的 模式（Schema）
         */
        STRUCT;

        private String name;

        Type() {
            this.name = this.name().toLowerCase(Locale.ROOT);
        }

        public String getName() {
            return name;
        }

        public boolean isPrimitive() {
            switch (this) {
                case INT8:
                case INT16:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64:
                case BOOLEAN:
                case STRING:
                case BYTES:
                    return true;
            }
            return false;
        }
    }


    Schema INT8_SCHEMA = SchemaBuilder.int8().build();//
    Schema INT16_SCHEMA = SchemaBuilder.int16().build();
    Schema INT32_SCHEMA = SchemaBuilder.int32().build();
    Schema INT64_SCHEMA = SchemaBuilder.int64().build();
    Schema FLOAT32_SCHEMA = SchemaBuilder.float32().build();
    Schema FLOAT64_SCHEMA = SchemaBuilder.float64().build();
    Schema BOOLEAN_SCHEMA = SchemaBuilder.bool().build();
    Schema STRING_SCHEMA = SchemaBuilder.string().build();
    Schema BYTES_SCHEMA = SchemaBuilder.bytes().build();

    Schema OPTIONAL_INT8_SCHEMA = SchemaBuilder.int8().optional().build();
    Schema OPTIONAL_INT16_SCHEMA = SchemaBuilder.int16().optional().build();
    Schema OPTIONAL_INT32_SCHEMA = SchemaBuilder.int32().optional().build();
    Schema OPTIONAL_INT64_SCHEMA = SchemaBuilder.int64().optional().build();
    Schema OPTIONAL_FLOAT32_SCHEMA = SchemaBuilder.float32().optional().build();
    Schema OPTIONAL_FLOAT64_SCHEMA = SchemaBuilder.float64().optional().build();
    Schema OPTIONAL_BOOLEAN_SCHEMA = SchemaBuilder.bool().optional().build();
    Schema OPTIONAL_STRING_SCHEMA = SchemaBuilder.string().optional().build();
    Schema OPTIONAL_BYTES_SCHEMA = SchemaBuilder.bytes().optional().build();

    /**
     * @return the type of this schema
     */
    Type type();

    /**
     * @return true if this field is optional, false otherwise
     */
    boolean isOptional();

    /**
     * @return the default value for this schema
     */
    Object defaultValue();

    /**
     * @return the name of this schema
     */
    String name();

    /**
     * Get the optional version of the schema. If a version is included, newer versions *must* be larger than older ones.
     * @return the version of this schema
     */
    Integer version();

    /**
     * @return the documentation for this schema
     */
    String doc();

    /**
     * Get a map of schema parameters.
     * @return Map containing parameters for this schema, or null if there are no parameters
     */
    Map<String, String> parameters();

    /**
     * Get the key schema for this map schema. Throws a DataException if this schema is not a map.
     * @return the key schema
     */
    Schema keySchema();

    /**
     * Get the value schema for this map or array schema. Throws a DataException if this schema is not a map or array.
     * @return the value schema
     */
    Schema valueSchema();

    /**
     * Get the list of fields for this Schema. Throws a DataException if this schema is not a struct.
     * @return the list of fields for this Schema
     */
    List<Field> fields();

    /**
     * Get a field for this Schema by name. Throws a DataException if this schema is not a struct.
     * @param fieldName the name of the field to look up
     * @return the Field object for the specified field, or null if there is no field with the given name
     */
    Field field(String fieldName);

    /**
     * Return a concrete instance of the {@link Schema}
     * @return the {@link Schema}
     */
    Schema schema();
}
