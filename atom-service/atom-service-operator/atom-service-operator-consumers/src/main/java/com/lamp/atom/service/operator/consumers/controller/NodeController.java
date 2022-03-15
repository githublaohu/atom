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

import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.atom.service.operator.entity.NodeEntity;
import com.lamp.atom.service.operator.service.NodeService;
import com.lamp.decoration.core.result.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping("/node")
@RestController("nodeController")
@Api(tags = {"节点操作接口"})
public class NodeController {

    @Reference
    private NodeService nodeService;

    /**
     * 添加节点
     *
     * @param nodeEntity
     */
    @PostMapping("/insertNode")
    @ApiOperation(value = "添加节点")
    public ResultObject<String> insertNode(@RequestBody NodeEntity nodeEntity) {
        // 字段判空
        if (Objects.isNull(nodeEntity.getSpaceId()) || Objects.isNull(nodeEntity.getNodeTemplateId())) {
            log.info("参数校验失败 {}", nodeEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            nodeService.insertNodeEntity(nodeEntity);
        } catch (Exception e) {
            log.warn("节点插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改节点
     *
     * @param nodeEntity
     * @return
     */
    @PostMapping("/updateNode")
    @ApiOperation(value = "修改节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long"),
            @ApiImplicitParam(name="deleteFlag",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long")
    })
    public ResultObject<String> updateNode(@ApiIgnore @RequestBody NodeEntity nodeEntity) {
        try {
            nodeService.updateNodeEntity(nodeEntity);
        } catch (Exception e) {
            log.warn("节点修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个节点
     *
     * @param params
     * @return
     */
    @PostMapping("/queryNodesByKeyword")
    @ApiOperation(value = "模糊查询多个节点")
    public List<NodeEntity> queryNodesByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return nodeService.queryNodeEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("节点查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个节点
     *
     * @param nodeEntity
     * @return
     */
    @PostMapping("/queryNodes")
    @ApiOperation(value = "查询多个节点")
    public List<NodeEntity> queryNodes(@RequestBody(required = false) NodeEntity nodeEntity) {
        try {
            return nodeService.queryNodeEntitys(nodeEntity);
        } catch (Exception e) {
            log.warn("节点查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个节点
     *
     * @param nodeEntity
     * @return
     */
    @PostMapping("/queryNode")
    @ApiOperation(value = "查询单个节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body" ,dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
    })
    public NodeEntity queryNode(@ApiIgnore @RequestBody NodeEntity nodeEntity) {
        try {
            NodeEntity nodeEntity1 = nodeService.queryNodeEntity(nodeEntity);
            return nodeEntity1;
        } catch (Exception e) {
            log.warn("节点查询失败 {}", e);
            return null;
        }
    }
}
