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

import java.util.Objects;

/**
 * <p>
 *     A field in a {@link Struct}, consisting of a field name, index, and {@link Schema} for the field value.
 *     数据结构包括字段名，索引，模式
 * </p>
 */
public class Field {
    private final String name;
    private final int index;
    private final Schema schema;

    public Field(String name, int index, Schema schema) {
        this.name = name;
        this.index = index;
        this.schema = schema;
    }

    /**
     * Get the name of this field.  获取字段名称
     * @return the name of this field
     */
    public String name() {
        return name;
    }


    /**
     * within 在里面
     * Get the index of this field within the struct. 获取索引
     * @return the index of this field
     */
    public int index() {
        return index;
    }

    /**
     * Get the schema of this field 获取字段你的模式
     * @return the schema of values of this field
     */
    public Schema schema() {
        return schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(index, field.index) &&
                Objects.equals(name, field.name) &&
                Objects.equals(schema, field.schema);
    }

    /**
     *  hash() hash是散列的意思，就是把任意长度的输入，通过散列算法输出固定长度的输出，该输出值就是散列值
     *  不同输入值经过一个散列算法输出同一个值，这种叫碰撞
     *  在同一个散列算法的处理下hash值不同，这两个原始输入必定不同
     *
     *  hashcode()是hash的一个索引，方便查找哈希值，
     *  equals是比较内容是否一致（string自己改了），object是直接比较内存地址，和 == 一样
     *  想做到无重复set，就需要比较，每次用equals比较内容浪费资源，不如用hash，然后用hashcode获取存放的地址
     *
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, index, schema);
    }

    @Override
    public String toString() {
        return "Field{" +
                "name=" + name +
                ", index=" + index +
                ", schema=" + schema +
                "}";
    }
}
