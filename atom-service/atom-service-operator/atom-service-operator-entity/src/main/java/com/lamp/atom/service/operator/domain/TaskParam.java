package com.lamp.atom.service.operator.domain;

import com.lamp.atom.service.domain.ModelCreateType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TaskParam {

    /**
     * 实验ID/节点ID
     */
    private Long taskId;

    /**
     * 模型创建类型
     */
    private ModelCreateType modelCreateType;

    /**
     * 运行参数
     */
    private Map<String, String> runParameter = new HashMap<String, String>();

    /**
     * 环境配置
     */
    private Map<String, String> envs = new HashMap<String, String>();
}
