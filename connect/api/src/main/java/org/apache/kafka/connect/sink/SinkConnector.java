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
package org.apache.kafka.connect.sink;

import org.apache.kafka.connect.connector.Connector;

/**
 * SinkConnectors implement the Connector interface to send Kafka data to another system.
 * SinkConnector实现Connectorj接口 发送数据从kafka到另一个系统
 *  从kafka导出数据
 */
public abstract class SinkConnector extends Connector {

    /**
     * <p>
     * Configuration key for the list of input topics for this connector.此连接器的输入主题列表的配置键
     * </p>
     * <p>
     * Usually this setting is only relevant（紧密相关） to the Kafka Connect framework, but is provided here for
     * the convenience of Connector developers if they also need to know the set of topics.
     * 通常这个设置仅仅和kafka连接器框架紧密相关；但是，如果连接器开发人员还需要了解一组主题，那么这里提供的是为了方便他们
     * </p>
     */
    public static final String TOPICS_CONFIG = "topics";

}
