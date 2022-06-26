package com.lamp.atom.service.operator.domain;

import com.lamp.atom.service.domain.OperatorRuntimeType;
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
     * 算子类型
     */
    private OperatorRuntimeType operatorRuntimeType;

    /**
     * 运行参数
     */
    private Map<String, String> runParameter = new HashMap<String, String>();

    /**
     * 环境配置
     */
    private Map<String, String> envs = new HashMap<String, String>();
}
