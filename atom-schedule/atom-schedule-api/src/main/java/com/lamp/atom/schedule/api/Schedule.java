/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.atom.schedule.api;

import java.util.HashMap;
import java.util.Map;

import com.lamp.atom.schedule.api.deploy.Deploy;
import com.lamp.atom.schedule.api.strategy.Strategy;

import lombok.Data;

@Data
public class Schedule {

    private Strategy strategy;

    private Deploy deploy;

    private Map<String, String> limits = new HashMap<String, String>();

    private Map<String, String> hardwareConfig = new HashMap<String, String>();

    private Map<String, String> runParameter = new HashMap<String, String>();

    private Map<String, String> envs = new HashMap<String, String>();

    /**
     * 调度服务的标签
     */
    private Map<String, String> label;

    private Object object;

    private Long nodeId;

    private String nodeName;

    private String namespace = "default";
}
