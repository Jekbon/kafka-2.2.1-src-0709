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

package org.apache.kafka.connect.components;

/**
 * Connect requires some components implement this interface to define a version string.
 * 连接需要一些组件实现此接口来定义一个版本号
 * requires需要
 */
public interface Versioned {
    /**
     * Get the version of this component. 组件component
     * 获取该组件的版本号
     * @return the version, formatted as a String. The version may not be (@code null} or empty.
     * 版本号格式化为一个字符串，这个版本号不能为空或者null
     */
    String version();
}
