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

import com.lamp.atom.service.domain.DeployType;
import com.lamp.atom.service.domain.OperatorRuntimeStatus;
import com.lamp.atom.service.operator.entity.OperatorEntity;
import com.lamp.atom.service.operator.service.OperatorService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/operator")
@RestController("operatorEventController")
@Api(hidden = true)
public class OperatorEventController {

    @Reference
    private OperatorService operatorService;

    /**
     * console：算子编辑中
     * @param operatorEntity
     * @return
     */
    @PostMapping("/editing")
    public Integer editStart(@RequestBody OperatorEntity operatorEntity) {
        //1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
        return operatorService.updateOperatorEntity(operatorEntity);
    }

    /**
     * console：算子编辑取消
     * @param operatorEntity
     * @return
     */
    @PostMapping("/editCancel")
    public Integer editCancel(@RequestBody OperatorEntity operatorEntity) {
        //1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDIT_CANCEL);
        return operatorService.updateOperatorEntity(operatorEntity);
    }

//    /**
//     * console：算子编辑完成
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/editFinish")
//    public Integer editFinish(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDIT_FINISH);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }

    /**
     * console：排队
     */
    @PostMapping("/queuing")
    public Integer queuing(@RequestBody OperatorEntity operatorEntity) {
        // 1、调度schedule

        // 2、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
        return operatorService.updateOperatorEntity(operatorEntity);
    }

//    /**
//     * console：排队取消中
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/queueCanceling")
//    public Integer queueCanceling(@RequestBody OperatorEntity operatorEntity) {
//        // 1、调度schedule
//
//        // 2、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUE_CANCELING);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }

    /**
     * schedule 排队取消完成
     * @param operatorEntity
     * @return
     */
    @PostMapping("/queueCancelFinish")
    public Integer queueCancelFinish(@RequestBody OperatorEntity operatorEntity) {
        // 1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUE_CANCEL);
        return operatorService.updateOperatorEntity(operatorEntity);
    }

    /**
     * console 抢占中
     * @param operatorEntity
     * @return
     */
    @PostMapping("/occupying")
    public Integer occupying(@RequestBody OperatorEntity operatorEntity) {
        // 1、调度schedule

        // 2、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
        return operatorService.updateOperatorEntity(operatorEntity);
    }

//    /**
//     * 只有训练的runtime 算子实例创建中
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/caseCreating")
//    public Integer caseCreating(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.CASE_CREATING);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * 只有训练的runtime 算子实例创建完成
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/caseCreateFinish")
//    public Integer caseCreateFinish(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.CASE_CREATE_FINISH);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * 只有训练的runtime 数据下载中
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/dataUploading")
//    public Integer dataUploading(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.DATA_UPLOADING);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * 只有训练的runtime 数据下载完成
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/dataUploadFinish")
//    public Integer dataUploadFinish(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.DATA_UPLOAD_FINISH);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }

    /**
     * runtime 算子运行开始
     * @param operatorEntity
     * @return
     */
    @PostMapping("/training")
    public Integer training(@RequestBody OperatorEntity operatorEntity) {
        //1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAINING);
        Integer status = operatorService.updateOperatorEntity(operatorEntity);
        if (status != 1) {
            return status;
        }


        return 1;
    }

    /**
     * runtime 算子测试
     * @param operatorEntity
     * @return
     */
    @PostMapping("/testing")
    public Integer testing(@RequestBody OperatorEntity operatorEntity) {
        //1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TESTING);
        return operatorService.updateOperatorEntity(operatorEntity);
    }

    /**
     * runtime 算子运行自动完成
     * @param operatorEntity
     * @return
     */
    @PostMapping("/runningAutoFinish")
    public Integer runningAutoFinish(@RequestBody OperatorEntity operatorEntity){
        // 1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAIN_AUTO_FINISH);
        Integer status = operatorService.updateOperatorEntity(operatorEntity);
        if (status != 1) {
            return status;
        }

        // 2、调度schedule（释放资源、保存数据集）

        // 3、部署
        DeployType deployType = operatorEntity.getDeployType();
        switch (deployType) {
            case AUTO_DEPLOY:
                //自动部署
                break;
            case GREY_DEPLOY:
                //灰度部署
                break;
            case TOUCH_DEPLOY:
                //触发部署
                break;
            case NOT_DEPLOY:
                //不部署
                break;
        }

        return 1;
    }

    /**
     * console 算子手动完成
     * @param operatorEntity
     * @return
     */
    @PostMapping("/runningFinish")
    public Integer runningFinish(@RequestBody OperatorEntity operatorEntity){
        // 1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAIN_FINISH);
        Integer status = operatorService.updateOperatorEntity(operatorEntity);
        if (status != 1) {
            return status;
        }

        // 2、调度schedule（释放资源、保存数据集）

        return 1;
    }

    /**
     * runtime 算子训练异常
     * @param operatorEntity
     * @return
     */
    @PostMapping("/runningException")
    public Integer runningException(@RequestBody OperatorEntity operatorEntity){
        // 1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAIN_EXCEPTION);
        Integer status = operatorService.updateOperatorEntity(operatorEntity);
        if (status != 1) {
            return status;
        }

        // 2、调度schedule（释放资源、保存数据集）

        return 1;
    }

    /**
     * runtime 算子训练服务异常
     * @param operatorEntity
     * @return
     */
    @PostMapping("/serviceException")
    public Integer serviceException(@RequestBody OperatorEntity operatorEntity) {
        // 1、修改状态
        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.SERVICE_EXCEPTION);
        Integer status = operatorService.updateOperatorEntity(operatorEntity);
        if (status != 1) {
            return status;
        }

        // 2、调度schedule（释放资源、保存数据集）

        return 1;
    }

}
