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
package org.apache.kafka.connect.errors;

/**
 * entity实体  operation手术，活动，企业  indicates表明，显示，象征，暗示，示意
 * Indicates the operation tried to create an entity that already exists.
 * 显示操作尝试创建一个已经存在的实体
 */
public class AlreadyExistsException extends ConnectException {
    public AlreadyExistsException(String s) {
        super(s);
    }

    public AlreadyExistsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AlreadyExistsException(Throwable throwable) {
        super(throwable);
    }
}
