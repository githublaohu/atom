package com.lamp.atom.service.operator.domain;

import com.lamp.atom.service.operator.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class NodeInformation {

    /**
     * Node基本信息
     */
    private NodeEntity nodeEntity;

    /**
     * 节点实验
     */
    private List<ResourceRelationEntity> examRelationList;

    /**
     * 节点依赖资源
     */
    private OperatorEntity operatorEntity;

    private ModelEntity modelEntity;

    private DataSourceEntity dataSourceEntity;

    private ServiceInfoEntity serviceInfoEntity;

    private ServiceInfoEntity maxServiceInfoEntity;

    private ServiceInfoEntity minServiceInfoEntity;

    /**
     * Runtime信息
     */
    private List<RuntimeEntity> runtimeEntityList;
}
