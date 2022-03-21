package com.lamp.atom.service.operator.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class NodeRelation {

    private Long nodeId;

    private Long operatorId;

    private Long connectionId;

    private Long modelId;

    private Long serviceInfoId;

    /**
     * 服务配置
     */
    private Map<String ,String> limits = new HashMap<String, String>();

    /**
     * 运行参数
     */
    private Map<String,String> runParameter = new HashMap<String, String>();

    /**
     * 环境配置
     */
    private Map<String,String> envs = new HashMap<String, String>();
}
