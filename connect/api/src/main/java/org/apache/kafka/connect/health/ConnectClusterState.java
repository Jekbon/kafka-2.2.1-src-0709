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

package org.apache.kafka.connect.health;

import java.util.Collection;

/**
 * Provides the ability to lookup connector metadata and its health. 提供查找连接器元数据及其运行状况的功能。
 * This is made available to the {@link org.apache.kafka.connect.rest.ConnectRestExtension}  这是提供给ConnectRestExtension 的实现
 * implementations. The Connect framework provides the implementation for this interface. Connect框架提供了这个接口的实现
 */
public interface ConnectClusterState {

    /**
     * Get the names of the connectors currently deployed in this cluster. 获取当前部署在此群集中的连接器的名称
     * This is a full list of connectors in the cluster gathered from
     * the current configuration, which may change over time.
     * 这是从当前配置文件中收集的群集中的连接器的完整列表，可能会随时间更改
     * @return collection of connector names, never {@code null}
     */
    Collection<String> connectors();

    /**
     * herder 牧民  snapshot简介，简要说明  previous 先前的，以往的  invocation调用   be available 生效，有效，可使用
     * Lookup the current health of a connector and its tasks.查找连接器及其任务的当前运行状况
     * This provides the current snapshot of health by querying the underlying herder. 这通过查询底层组件来提供当前的健康快照
     * A connector returned by previous invocation of {@link #connectors()} may no longer be available and could result in {@link
     * org.apache.kafka.connect.errors.NotFoundException}. 以前调用connectors() 返回的连接器可能不再可用，并可能导致NotFoundException
     *
     *
     * @param connName name of the connector 连接器的名称
     * @return the health of the connector for the connector name  对应连接器名称的健康状况
     * @throws org.apache.kafka.connect.errors.NotFoundException if the requested connector can't be found
     */
    ConnectorHealth connectorHealth(String connName);
}
