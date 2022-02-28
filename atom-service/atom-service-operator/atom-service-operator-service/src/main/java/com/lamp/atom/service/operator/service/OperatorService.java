package com.lamp.atom.service.operator.service;

import com.lamp.atom.service.operator.entity.OperatorEntity;

import java.util.List;

public interface OperatorService {
    /**
     * 添加算子
     * @param operatorEntity
     */
    Integer insertOperatorEntity(OperatorEntity operatorEntity);

    /**
     * 修改算子
     * @param operatorEntity
     * @return
     */
    Integer updateOperatorEntity(OperatorEntity operatorEntity);

    /**
     * 查询多个算子
     * @param operatorEntity
     * @return
     */
    List<OperatorEntity> queryOperatorEntitys(OperatorEntity operatorEntity);

    /**
     * 查询单个算子
     * @param operatorEntity
     * @return
     */
    OperatorEntity queryOperatorEntity(OperatorEntity operatorEntity);
}
