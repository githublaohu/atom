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

import com.lamp.atom.service.operator.common.OperatorCreateTo;
import com.lamp.atom.service.operator.entity.OperatorEntity;
import com.lamp.atom.service.operator.service.AvaliablePortService;
import com.lamp.atom.service.operator.service.OperatorService;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.decoration.core.result.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RequestMapping("/operator")
@RestController("operatorController")
public class OperatorController {

    @Reference
    private OperatorService operatorService;
    @Reference
    private AvaliablePortService avaliablePortService;

    /**
     * 添加算子
     * @param operatorEntity
     */
    @PostMapping("/insertOperator")
    public ResultObject<String> insertOperator(@RequestBody OperatorEntity operatorEntity){
        //字段判空
        if (Objects.isNull(operatorEntity.getSpaceId()) || Objects.isNull(operatorEntity.getSceneId()) || Objects.isNull(operatorEntity.getExperimentId())) {
            log.info("参数校验失败 {}", operatorEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            operatorService.insertOperatorEntity(operatorEntity);
        } catch (Exception e) {
            log.warn("算子插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改算子
     * @param operatorEntity
     * @return
     */
    @PostMapping("/updateOperator")
    public ResultObject<String> updateOperator(@RequestBody OperatorEntity operatorEntity){
        try {
            operatorService.updateOperatorEntity(operatorEntity);
        } catch (Exception e) {
            log.warn("算子修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个算子
     * @param params
     * @return
     */
    @PostMapping("/queryOperatorsByKeyword")
    public List<OperatorEntity> queryOperatorsByKeyword(@RequestBody HashMap<String, String> params){
        try {
            return operatorService.queryOperatorEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("算子查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个算子
     * @param operatorEntity
     * @return
     */
    @PostMapping("/queryOperators")
    public List<OperatorEntity> queryOperators(@RequestBody OperatorEntity operatorEntity){
        try {
            return operatorService.queryOperatorEntitys(operatorEntity);
        } catch (Exception e) {
            log.warn("算子查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个算子
     * @param operatorEntity
     * @return
     */
    @PostMapping("/queryOperator")
    public OperatorCreateTo queryOperator(@RequestBody OperatorEntity operatorEntity){
        try {
            OperatorEntity operatorEntity1 = operatorService.queryOperatorEntity(operatorEntity);
            //查出算子的数据
            OperatorCreateTo operatorCreateTo = new OperatorCreateTo();



            operatorCreateTo.setOperatorTo(operatorEntity1);



            return operatorCreateTo;
        } catch (Exception e) {
            log.warn("算子查询失败 {}", e);
            return null;
        }
    }
}
