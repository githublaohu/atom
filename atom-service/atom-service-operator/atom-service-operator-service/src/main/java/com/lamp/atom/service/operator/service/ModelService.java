package com.lamp.atom.service.operator.service;

import com.lamp.atom.service.operator.entity.ModelEntity;

import java.util.List;

public interface ModelService {


    /**
     * 创建模型
     * @param modelEntity
     */
    Integer createModelEntity(ModelEntity modelEntity);

    /**
     * 添加模型
     * @param modelEntity
     */
    Integer insertModelEntity(ModelEntity modelEntity);

    /**
     * 修改模型
     * @param modelEntity
     * @return
     */
    Integer updateModelEntity(ModelEntity modelEntity);

    /**
     * 查询多个模型
     * @param modelEntity
     * @return
     */
    List<ModelEntity> queryModelEntitys(ModelEntity modelEntity);

    /**
     * 查询单个模型
     * @param modelEntity
     * @return
     */
    ModelEntity queryModelEntity(ModelEntity modelEntity);
}
