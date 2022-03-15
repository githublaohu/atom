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

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import com.lamp.atom.service.operator.common.OperatorCreateTo;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.atom.service.operator.entity.OperatorEntity;
import com.lamp.atom.service.operator.service.AvaliablePortService;
import com.lamp.atom.service.operator.service.OperatorService;
import com.lamp.decoration.core.result.ResultObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RequestMapping("/operator")
@RestController("operatorController")
@Api(tags = {"算子操作接口"})
public class OperatorController {

    @Reference
    private OperatorService operatorService;
    @Reference
    private AvaliablePortService avaliablePortService;

    /**
     * 添加算子
     *
     * @param operatorEntity
     */
    @PostMapping("/insertOperator")
    @ApiOperation(value = "添加算子")
    public ResultObject<String> insertOperator(@RequestBody OperatorEntity operatorEntity) {
        //字段判空
        if (Objects.isNull(operatorEntity.getSpaceId())) {
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
     *
     * @param operatorEntity
     * @return
     */
    @PostMapping("/updateOperator")
    @ApiOperation(value = "修改算子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataTypeClass = java.lang.Long.class, paramType = "body", dataType = "Long"),
            @ApiImplicitParam(name = "deleteFlag", dataTypeClass = java.lang.Long.class, paramType = "body", dataType = "Long")
    })
    public ResultObject<String> updateOperator(@ApiIgnore @RequestBody OperatorEntity operatorEntity) {
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
     *
     * @param params
     * @return
     */
    @PostMapping("/queryOperatorsByKeyword")
    @ApiOperation(value = "模糊查询多个算子")
    public List<OperatorEntity> queryOperatorsByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return operatorService.queryOperatorEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("算子查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个算子
     *
     * @param operatorEntity
     * @return
     */
    @PostMapping("/queryOperators")
    @ApiOperation(value = "查询多个算子")
    public List<OperatorEntity> queryOperators(@RequestBody(required = false) OperatorEntity operatorEntity) {
        try {
            return operatorService.queryOperatorEntitys(operatorEntity);
        } catch (Exception e) {
            log.warn("算子查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个算子
     *
     * @param operatorEntity
     * @return
     */
    @PostMapping("/queryOperator")
    @ApiOperation(value = "查询单个算子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body", dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
    })
    public OperatorEntity queryOperator(@ApiIgnore @RequestBody OperatorEntity operatorEntity) {
        try {
            return operatorService.queryOperatorEntity(operatorEntity);
        } catch (Exception e) {
            log.warn("算子查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询算子信息
     *
     * @param operatorEntity
     * @return
     */
    @PostMapping("/queryOperatorData")
    @ApiOperation(value = "查询单个算子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body", dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
    })
    public OperatorCreateTo queryOperatorData(@ApiIgnore @RequestBody OperatorEntity operatorEntity) {
        try {
            OperatorCreateTo operatorCreateTo = new OperatorCreateTo();

            OperatorEntity operatorEntity1 = operatorService.queryOperatorEntity(operatorEntity);
            operatorCreateTo.setOperatorTo(operatorEntity1);


            return operatorCreateTo;
        } catch (Exception e) {
            log.warn("算子查询失败 {}", e);
            return null;
        }
    }
}
