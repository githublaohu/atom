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
package com.lamp.atom.service.operator.consumers.controller;


import com.lamp.atom.service.operator.entity.ModelEntity;
import com.lamp.atom.service.operator.entity.OperatorEntity;
import com.lamp.atom.service.operator.service.ModelService;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.decoration.core.result.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping("/model")
@RestController("modelController")
public class ModelController {

    @Autowired
    @Qualifier("modelService")
    private ModelService modelService;

    /**
     * 添加模型
     * @param modelEntity
     */
    @PostMapping("/insertModel")
    public ResultObject<String> insertModel(@RequestBody ModelEntity modelEntity){
        //字段判空
        if (Objects.isNull(modelEntity.getSpaceId()) || Objects.isNull(modelEntity.getSceneId()) || Objects.isNull(modelEntity.getExperimentId())) {
            log.info("参数校验失败 {}", modelEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            modelService.insertModelEntity(modelEntity);
        } catch (Exception e) {
            log.warn("模型插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改模型
     * @param modelEntity
     * @return
     */
    @PostMapping("/updateModel")
    public ResultObject<String> updateModel(@RequestBody ModelEntity modelEntity){
        try {
            modelService.updateModelEntity(modelEntity);
        } catch (Exception e) {
            log.warn("模型修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个模型
     * @param keyword
     * @return
     */
    @PostMapping("/queryModelsByKeyword")
    public List<ModelEntity> queryModelsByKeyword(@RequestBody String keyword){
        try {
            return modelService.queryModelEntitysByKeyword(keyword);
        } catch (Exception e) {
            log.warn("模型查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个模型
     * @param modelEntity
     * @return
     */
    @PostMapping("/queryModels")
    public List<ModelEntity> queryModels(@RequestBody ModelEntity modelEntity){
        try {
            return modelService.queryModelEntitys(modelEntity);
        } catch (Exception e) {
            log.warn("模型查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个模型
     * @param modelEntity
     * @return
     */
    @PostMapping("/queryModel")
    public ModelEntity queryModel(@RequestBody ModelEntity modelEntity){
        try {
            ModelEntity modelEntity1 = modelService.queryModelEntity(modelEntity);
            return modelEntity1;
        } catch (Exception e) {
            log.warn("模型查询失败 {}", e);
            return null;
        }
    }
}
