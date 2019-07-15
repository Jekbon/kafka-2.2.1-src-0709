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
package org.apache.kafka.connect.util;

import java.util.ArrayList;
import java.util.List;

/**
 * utilities 实用，有用    building clocks 积木，基块
 * Utilities that connector implementations might find useful. Contains common building blocks
 * for writing connectors.
 * 连接器实现可能会发现有用的实用程序，包含用于写入连接器的通用构建基块
 */
public class ConnectorUtils {
    /**
     * intuitive 直观   alphabetical 按字母顺序地
     * Given a list of elements and a target number of groups,给定元素列表和目标组数
     * generates list of groups of  elements to match the target number of groups,
     * 生成组列表元素以匹配目标组数
     * spreading them evenly among the groups.在组之间均匀分布
     * This generates groups with contiguous elements,这将生成具有连续元素的组
     * which results in intuitive ordering if your elements are also ordered 从而在元素也按顺序排列
     *  (e.g. alphabetical lists of table names if you sort
     *   例如，如果按字母顺序排列表名以生成原始分区，则按字母顺序排列表名列表
     * table names alphabetically to generate the raw partitions) or can result in efficient
     * partitioning if elements are sorted according to some criteria that affects performance
     *  如果按照影响性能的某些标准，例如具有相同前导的主题分区，对元素进行排序，则可能导致高效分区
     * (e.g. topic partitions with the same leader).
     *
     * @param elements list of elements to partition 要分区的元素列表
     * @param numGroups the number of output groups to generate. 要生成的输出组数
     */
    public static <T> List<List<T>> groupPartitions(List<T> elements, int numGroups) {
        if (numGroups <= 0)
            throw new IllegalArgumentException("Number of groups must be positive.");//组数必须为正数

        List<List<T>> result = new ArrayList<>(numGroups);

        // Each group has either n+1 or n raw partitions  每个组有n+1 或 n个原始分区
        int perGroup = elements.size() / numGroups;//相除
        int leftover = elements.size() - (numGroups * perGroup);

        int assigned = 0;// assigned 分配，布置，分派
        for (int group = 0; group < numGroups; group++) {
            int numThisGroup = group < leftover ? perGroup + 1 : perGroup;
            List<T> groupList = new ArrayList<>(numThisGroup);
            for (int i = 0; i < numThisGroup; i++) {
                groupList.add(elements.get(assigned));
                assigned++;
            }
            result.add(groupList);
        }

        return result;
    }
}
