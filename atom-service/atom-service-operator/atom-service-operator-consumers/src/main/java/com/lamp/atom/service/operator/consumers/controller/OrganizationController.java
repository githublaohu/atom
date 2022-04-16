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
import com.lamp.atom.service.operator.entity.OrganizationEntity;
import com.lamp.atom.service.operator.service.OrganizationService;
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

@Slf4j
@RequestMapping("/organization")
@RestController("organizationController")
@Api(tags = {"组织操作接口"})
public class OrganizationController {

    @Reference
    private OrganizationService organizationService;

    /**
     * 添加组织
     *
     * @param organizationEntity
     */
    @PostMapping("/insertOrganization")
    @ApiOperation(value = "添加组织")
    public ResultObject<String> insertOrganization(@RequestBody OrganizationEntity organizationEntity) {
        try {
            organizationService.insertOrganizationEntity(organizationEntity);
        } catch (Exception e) {
            log.warn("组织插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改组织
     *
     * @param organizationEntity
     * @return
     */
    @PostMapping("/updateOrganization")
    @ApiOperation(value = "修改组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long"),
            @ApiImplicitParam(name="deleteFlag",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long")
    })
    public ResultObject<String> updateOrganization(@ApiIgnore @RequestBody OrganizationEntity organizationEntity) {
        try {
            organizationService.updateOrganizationEntity(organizationEntity);
        } catch (Exception e) {
            log.warn("组织修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个组织
     *
     * @param params
     * @return
     */
    @PostMapping("/queryOrganizationsByKeyword")
    @ApiOperation(value = "模糊查询多个组织")
    public List<OrganizationEntity> queryOrganizationsByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return organizationService.queryOrganizationEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("组织查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个组织
     *
     * @param organizationEntity
     * @return
     */
    @PostMapping("/queryOrganizations")
    @ApiOperation(value = "查询多个组织")
    public List<OrganizationEntity> queryOrganizations(@RequestBody(required = false) OrganizationEntity organizationEntity) {
        try {
            return organizationService.queryOrganizationEntitys(organizationEntity);
        } catch (Exception e) {
            log.warn("组织查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个组织
     *
     * @param organizationEntity
     * @return
     */
    @PostMapping("/queryOrganization")
    @ApiOperation(value = "查询单个组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body" ,dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
    })
    public OrganizationEntity queryOrganization(@ApiIgnore @RequestBody OrganizationEntity organizationEntity) {
        try {
            OrganizationEntity organizationEntity1 = organizationService.queryOrganizationEntity(organizationEntity);
            return organizationEntity1;
        } catch (Exception e) {
            log.warn("组织查询失败 {}", e);
            return null;
        }
    }
}
