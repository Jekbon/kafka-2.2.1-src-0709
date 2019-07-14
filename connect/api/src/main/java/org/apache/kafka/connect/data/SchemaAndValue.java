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

public class SchemaAndValue {
    private final Schema schema;
    private final Object value;

    public static final SchemaAndValue NULL = new SchemaAndValue(null, null);

    public SchemaAndValue(Schema schema, Object value) {
        this.value = value;
        this.schema = schema;
    }

    public Schema schema() {
        return schema;
    }

    public Object value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemaAndValue that = (SchemaAndValue) o;
        return Objects.equals(schema, that.schema) &&
                Objects.equals(value, that.value);
    }

    /**
     * Hash()函数是把一个大范围映射到一个小范围，为了节省空间，数据容易保存
     * 单项函数，给定输入容易计算结果，给定输出很难计算输入，这种可用于对数据进行加密
     * 散列函数逼近单项函数
     * 还可以用来查找数据
     * 加法hash
     * 位运算hash
     * 乘法hash
     * 除法hash
     * 查表hash
     * 混合hash
     * 质数：又叫做素数，是一个大于一的自然数，并且因数只有1和它自身，不能整除其他自然数  1*3
     * 自然数：0,1,2,3,4，... 一个接一个，组成一个无穷的集体，就是非负整数
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(schema, value);
    }

    @Override
    public String toString() {
        return "SchemaAndValue{" +
                "schema=" + schema +
                ", value=" + value +
                '}';
    }
}
