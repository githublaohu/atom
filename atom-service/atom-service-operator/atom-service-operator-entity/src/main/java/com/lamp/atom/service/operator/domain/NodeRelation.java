package com.lamp.atom.service.operator.domain;

import lombok.Data;

@Data
public class NodeRelation {

    private Long nodeId;

    private Long modelID;

    private Long dataSourceId;

    private Long serviceInfoId;

    private Long maxServiceInfoId;

    private Long minServiceInfoId;

}
