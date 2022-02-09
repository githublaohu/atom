package com.lamp.atom.service.operator.consumers.controller;


import com.lamp.atom.service.operator.entity.ConnectionEntity;
import com.lamp.atom.service.operator.service.ConnectionService;
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
@RequestMapping("/connection")
@RestController("connectionController")
public class ConnectionController {

    @Autowired
    @Qualifier("connectionService")
    private ConnectionService connectionService;

    /**
     * 添加连接
     * @param connectionEntity
     */
    @PostMapping("/insertConnection")
    public ResultObject<String> insertConnection(@RequestBody ConnectionEntity connectionEntity){
        //字段判空
        if (Objects.isNull(connectionEntity.getSpaceId()) || Objects.isNull(connectionEntity.getSceneId()) || Objects.isNull(connectionEntity.getExperimentId())) {
            log.info("参数校验失败 {}", connectionEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            connectionService.insertConnectionEntity(connectionEntity);
        } catch (Exception e) {
            log.warn("连接插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改连接
     * @param connectionEntity
     * @return
     */
    @PostMapping("/updateConnection")
    public ResultObject<String> updateConnection(@RequestBody ConnectionEntity connectionEntity){
        try {
            connectionService.updateConnectionEntity(connectionEntity);
        } catch (Exception e) {
            log.warn("连接修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 查询多个连接
     * @param connectionEntity
     * @return
     */
    @PostMapping("/queryConnections")
    public List<ConnectionEntity> queryConnections(@RequestBody ConnectionEntity connectionEntity){
        try {
            return connectionService.queryConnectionEntitys(connectionEntity);
        } catch (Exception e) {
            log.warn("连接查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个连接
     * @param connectionEntity
     * @return
     */
    @PostMapping("/queryConnection")
    public ConnectionEntity queryConnection(@RequestBody ConnectionEntity connectionEntity){
        try {
            ConnectionEntity connectionEntity1 = connectionService.queryConnectionEntity(connectionEntity);
            return connectionEntity1;
        } catch (Exception e) {
            log.warn("连接查询失败 {}", e);
            return null;
        }
    }
}
