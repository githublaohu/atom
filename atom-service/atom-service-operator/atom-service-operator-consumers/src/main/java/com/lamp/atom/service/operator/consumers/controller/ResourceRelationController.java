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
import com.lamp.atom.service.operator.entity.ResourceRelationEntity;
import com.lamp.atom.service.operator.service.ResourceRelationService;
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
@RequestMapping("/resourceRelation")
@RestController("resourceRelationController")
@Api(tags = {"关联关系操作接口"})
public class ResourceRelationController {

    @Reference
    private ResourceRelationService resourceRelationService;

    /**
     * 添加关联关系
     *
     * @param resourceRelationEntity
     */
    @PostMapping("/insertResourceRelation")
    @ApiOperation(value = "添加关联关系")
    public ResultObject<String> insertResourceRelation(@RequestBody ResourceRelationEntity resourceRelationEntity) {
        // 字段判空
        if (Objects.isNull(resourceRelationEntity.getRelateId()) || Objects.isNull(resourceRelationEntity.getBeRelatedId())) {
            log.info("参数校验失败 {}", resourceRelationEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            resourceRelationService.insertResourceRelationEntity(resourceRelationEntity);
        } catch (Exception e) {
            log.warn("关联关系插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改关联关系
     *
     * @param resourceRelationEntity
     * @return
     */
    @PostMapping("/updateResourceRelation")
    @ApiOperation(value = "修改关联关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long"),
            @ApiImplicitParam(name="deleteFlag",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long")
    })
    public ResultObject<String> updateResourceRelation(@ApiIgnore @RequestBody ResourceRelationEntity resourceRelationEntity) {
        try {
            resourceRelationService.updateResourceRelationEntity(resourceRelationEntity);
        } catch (Exception e) {
            log.warn("关联关系修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个关联关系
     *
     * @param params
     * @return
     */
    @PostMapping("/queryResourceRelationsByKeyword")
    @ApiOperation(value = "模糊查询多个关联关系")
    public List<ResourceRelationEntity> queryResourceRelationsByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return resourceRelationService.queryResourceRelationEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("关联关系查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个关联关系
     *
     * @param resourceRelationEntity
     * @return
     */
    @PostMapping("/queryResourceRelations")
    @ApiOperation(value = "查询多个关联关系")
    public List<ResourceRelationEntity> queryResourceRelations(@RequestBody(required = false) ResourceRelationEntity resourceRelationEntity) {
        try {
            return resourceRelationService.queryResourceRelationEntitys(resourceRelationEntity);
        } catch (Exception e) {
            log.warn("关联关系查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个关联关系
     *
     * @param resourceRelationEntity
     * @return
     */
    @PostMapping("/queryResourceRelation")
    @ApiOperation(value = "查询单个关联关系")
    public ResourceRelationEntity queryResourceRelation(@RequestBody ResourceRelationEntity resourceRelationEntity) {
        try {
            ResourceRelationEntity resourceRelationEntity1 = resourceRelationService.queryResourceRelationEntity(resourceRelationEntity);
            return resourceRelationEntity1;
        } catch (Exception e) {
            log.warn("关联关系查询失败 {}", e);
            return null;
        }
    }
}
