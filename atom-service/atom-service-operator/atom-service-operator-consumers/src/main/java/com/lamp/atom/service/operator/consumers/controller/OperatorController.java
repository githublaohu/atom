package com.lamp.atom.service.operator.consumers.controller;

import com.lamp.atom.service.operator.entity.OperatorEntity;
import com.lamp.atom.service.operator.service.OperatorService;
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
@RequestMapping("/operator")
@RestController("operatorController")
public class OperatorController {

    @Autowired
    @Qualifier("operatorService")
    private OperatorService operatorService;

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
    public OperatorEntity queryOperator(@RequestBody OperatorEntity operatorEntity){
        try {
            OperatorEntity operatorEntity1 = operatorService.queryOperatorEntity(operatorEntity);
            return operatorEntity1;
        } catch (Exception e) {
            log.warn("算子查询失败 {}", e);
            return null;
        }
    }

}
