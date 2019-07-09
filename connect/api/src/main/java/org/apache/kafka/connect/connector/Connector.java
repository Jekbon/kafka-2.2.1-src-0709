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
package org.apache.kafka.connect.connector;

import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigValue;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.components.Versioned;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Connectors manage integration of Kafka Connect with another system, either as an input that ingests
 *  Connectors管理kafka连接器和其他系统的集成，作为输入数据到kafka或者输出数据到外部的系统
 * data into Kafka or an output that passes data to an external system. Implementations should
 * not use this class directly; 启动位置不能直接使用这个类they should inherit from {@link org.apache.kafka.connect.source.SourceConnector SourceConnector}
 * or {@link org.apache.kafka.connect.sink.SinkConnector SinkConnector}.应该继承SourceConnector或者SinkConnector
 * </p>
 * <p>
 * Connectors have two primary tasks. First, given some configuration, they are responsible for
 * Connectors有两个主要的任务，第一个，给定一些配置，【他们为创建一套使数据进程分开的配置文件】
 * creating configurations for a set of {@link Task}s that split up the data processing. For
 * 他们负责为一组任务创建配置，这些任务将拆分数据处理
 * example, a database Connector might create Tasks by dividing the set of tables evenly among
 * 例如，【一个数据库连接器可能创建任务通过一组表】数据库连接器可以通过在任务之间均匀地划分表集来创建任务
 * tasks. Second, they are responsible for monitoring inputs for changes that require
 *  第二，他们负责监控输入的改变需要重新配置并且经由kafka通知运行的ConnectorContext
 *  他们负责监控需要重新配置的更改的输入，并通过ConnectorContext通知Kafka Connect运行时。
 * reconfiguration and notifying the Kafka Connect runtime via the {@link ConnectorContext}. Continuing the
 * previous example, the connector might periodically check for new tables and notify Kafka Connect of
 * additions and deletions. Kafka Connect will then request new configurations and update the running
 * Tasks.
 * </p>
 */
public abstract class Connector implements Versioned {

    protected ConnectorContext context;


    /**
     * Initialize this connector, using the provided ConnectorContext to notify the runtime of
     * input configuration changes.
     *  初始化此连接器，使用提供的ConnectorContext通知运行时输入配置更改
     * @param ctx context object used to interact with the Kafka Connect runtime
     *        context对象用于与Kafka Connect运行时交互上下文对象
     */
    public void initialize(ConnectorContext ctx) {
        context = ctx;
    }

    /**
     * <p>
     * Initialize this connector, using the provided ConnectorContext to notify the runtime of
     * input configuration changes and using the provided set of Task configurations.任务配置集合
     * This version is only used to recover from failures. 这个版本仅用于从故障中恢复
     * </p>
     * <p>
     * The default implementation ignores the provided Task configurations.
     * 默认实现忽略提供的任务配置
     * During recovery, Kafka Connect will request an updated set of configurations and update the running Tasks appropriately.
     * 在恢复期，Kafka Connect 将会请求更新一组配置并且相应地更新正在运行的任务
     * However, Connectors should implement special handling of this case if it will avoid unnecessary changes to running Tasks.
     * 但是，如果连接器可以避免对正在运行的任务进行不必要的更改，那么它应该实现对这种情况的特殊处理
     * </p>
     *
     * @param ctx context object used to interact with the Kafka Connect runtime
     *            用于与Kafka Connect运行时交互上下文对象
     * @param taskConfigs existing task configurations, which may be used when generating new task configs to avoid
     *                    配置文件存在，他可以在生成新的任务配置时使用，以避免在分区中对任务分配进行搅乱
     *                    churn in partition to task assignments
     *                    churn 搅乱  assignments工作 generating 产生
     */
    public void initialize(ConnectorContext ctx, List<Map<String, String>> taskConfigs) {
        context = ctx;
        // Ignore taskConfigs. May result in more churn of tasks during recovery if updated configs
        // are very different, but reduces the difficulty of implementing a Connector
    }

    /**
     * Start this Connector. This method will only be called on a clean Connector, i.e. it has
     *  启动此连接器，只能在干净的连接器上调用此方法，即它刚刚被实例化和初始化，或者调用了stop()
     * either just been instantiated and initialized or {@link #stop()} has been invoked.
     * either任何一个
     * @param props configuration settings 配置设置
     */
    public abstract void start(Map<String, String> props);

    /**
     * Reconfigure this Connector. Most implementations will not override this, using the default
     * 重新配置此连接器，大多数实现都不会覆盖这一点，使用 调用stop()和start()的默认实现
     * implementation that calls {@link #stop()} followed by {@link #start(Map)}.
     * Implementations only need to override this if they want to handle this process more
     *  如果实现想要更有效地处理这个过程，例如不关闭与外部系统的网络链接，则只需要覆盖这个过程
     * efficiently, e.g. without shutting down network connections to the external system.
     *
     * @param props new configuration settings 新配置设置
     */
    public void reconfigure(Map<String, String> props) {
        stop();
        start(props);
    }

    /**
     * Returns the Task implementation for this Connector. 返回此连接器的任务实现
     */
    public abstract Class<? extends Task> taskClass();

    /**
     * Returns a set of configurations for Tasks based on the current configuration,
     *  返回基于当前配置的任务配置集，最多造出配置文件的计数
     * producing at most count configurations.
     *
     * @param maxTasks maximum number of configurations to generate 要生成的最大配置数
     * @return configurations for Tasks 任务的配置
     */
    public abstract List<Map<String, String>> taskConfigs(int maxTasks);

    /**
     * Stop this connector. 停止这个连接器
     */
    public abstract void stop();

    /**
     * Validate the connector configuration values against configuration definitions.
     *  根据配置定义验证连接器配置值
     * @param connectorConfigs the provided configuration values 提供的配置值
     * @return List of Config, each Config contains the updated configuration information given
     * the current configuration values. 配置列表，每个配置包含给定当前配置值的更新配置信息
     */
    public Config validate(Map<String, String> connectorConfigs) {
        ConfigDef configDef = config();
        if (null == configDef) {
            throw new ConnectException(
                String.format("%s.config() must return a ConfigDef that is not null.", this.getClass().getName())
            );
        }
        List<ConfigValue> configValues = configDef.validate(connectorConfigs);
        return new Config(configValues);
    }

    /**
     * Define the configuration for the connector. 定义连接器的配置
     * @return The ConfigDef for this connector; may not be null. 此连接器的配置定义，不能为null
     */
    public abstract ConfigDef config();
}
