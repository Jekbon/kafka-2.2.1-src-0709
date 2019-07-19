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

package org.apache.kafka.connect.rest;

import org.apache.kafka.common.Configurable;
import org.apache.kafka.connect.components.Versioned;
import org.apache.kafka.connect.health.ConnectClusterState;

import java.io.Closeable;
import java.util.Map;

/**
 * mechanism 方法，机制，构造   qualified具备...的资格
 * A plugin interface to allow registration of new JAX-RS resources like Filters, REST endpoints, providers, etc. The implementations will
 * be discovered using the standard Java {@link java.util.ServiceLoader} mechanism by  Connect's plugin class loading mechanism.
 * 允许注册一个新的JAX-RX源（类似过滤器，REST端点，提供者等）的插件接口，使用Connect的插件类加载机制，使用标准的Java{Link Java.UTI.ServeloDeRe}机制来实现这些实现。
 *
 *
 * <p>The extension class(es) must be packaged as a plugin, with one JAR containing the implementation classes and a {@code
 * META-INF/services/org.apache.kafka.connect.rest.extension.ConnectRestExtension} file that contains the fully qualified name of the
 * class(es) that implement the ConnectRestExtension interface. The plugin should also include the JARs of all dependencies except those
 * already provided by the Connect framework. 该插件还应包括除那些依赖项之外的所有依赖项的jar，已由Connect框架提供
 *
 * <p>To install into a Connect installation, add a directory named for the plugin and containing the plugin's JARs into a directory that is
 * on Connect's {@code plugin.path}, and (re)start the Connect worker.
 * 要安装到Connect安装中，请将为插件命名并包含插件jar的目录添加到
 * 在connect的@code plugin.path，并（重新）启动connect worker
 *
 *
 * instantiate 例子，用具体例子说明    extension 扩大，延伸    specified in 由...规定
 * <p>When the Connect worker process starts up, it will read its configuration and instantiate all of the REST extension implementation
 * classes that are specified in the `rest.extension.classes` configuration property. Connect will then pass its configuration to each
 * extension via the {@link Configurable#configure(Map)} method, and will then call {@link #register} with a provided context.
 * 当连接工作进程启动时，它将读取其配置并实例化所有的REST扩展实现在'rest.extension.classes'配置属性中指定的类。然后，connect会将其配置传递给每个
 *通过@link configurable configure（map）方法进行扩展，然后使用提供的上下文调用@link register
 *
 * <p>When the Connect worker shuts down, it will call the extension's {@link #close} method to allow the implementation to release all of
 * its resources. 当连接工作者关闭时，它将调用扩展的close方法，以允许实现释放所有它的资源
 */
public interface ConnectRestExtension extends Configurable, Versioned, Closeable {

    /**
     * register 登记，注册   custom风俗，习惯，定制的  via通过，经由
     * ConnectRestExtension implementations can register custom JAX-RS resources via the {@link #register(ConnectRestExtensionContext)}
     * method. The Connect framework will invoke this method after registering the default Connect resources. If the implementations attempt
     * to re-register any of the Connect resources, it will be be ignored and will be logged.
     *
     * ConnectRestextension实现可以通过@link注册（ConnectRestextensionContext）注册自定义JAX-RS资源
     * 方法。连接框架将在注册默认连接资源后调用此方法。如果实现尝试
     * 要重新注册任何连接资源，它将被忽略并被记录。
     *
     * @param restPluginContext The context provides access to JAX-RS {@link javax.ws.rs.core.Configurable} and {@link
     *                          ConnectClusterState}.The custom JAX-RS resources can be registered via the {@link
     *                          ConnectRestExtensionContext#configurable()}
     */
    void register(ConnectRestExtensionContext restPluginContext);
}
