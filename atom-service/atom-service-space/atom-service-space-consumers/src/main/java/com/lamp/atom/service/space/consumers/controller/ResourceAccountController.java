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
package com.lamp.atom.service.space.consumers.controller;

import com.lamp.atom.service.space.consumers.utils.ResultObjectEnums;
import com.lamp.atom.service.space.entity.ResourceAccountEntity;
import com.lamp.atom.service.space.service.ResourceAccountService;
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
@RequestMapping("/resourceAccount")
@RestController("resourceAccountController")
@Api(tags = {"资源账户操作接口"})
public class ResourceAccountController {

    @Reference
    private ResourceAccountService resourceAccountService;

    /**
     * 添加资源账户
     *
     * @param resourceAccountEntity
     */
    @PostMapping("/insertResourceAccount")
    @ApiOperation(value = "添加资源账户")
    public ResultObject<String> insertResourceAccount(@RequestBody ResourceAccountEntity resourceAccountEntity) {
        try {
            resourceAccountService.insertResourceAccountEntity(resourceAccountEntity);
        } catch (Exception e) {
            log.warn("资源账户插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改资源账户
     *
     * @param resourceAccountEntity
     * @return
     */
    @PostMapping("/updateResourceAccount")
    @ApiOperation(value = "修改资源账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",dataTypeClass = Long.class,paramType="body" ,dataType = "Long"),
            @ApiImplicitParam(name="deleteFlag",dataTypeClass = Long.class,paramType="body" ,dataType = "Long")
    })
    public ResultObject<String> updateResourceAccount(@ApiIgnore @RequestBody ResourceAccountEntity resourceAccountEntity) {
        try {
            resourceAccountService.updateResourceAccountEntity(resourceAccountEntity);
        } catch (Exception e) {
            log.warn("资源账户修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个资源账户
     *
     * @param params
     * @return
     */
    @PostMapping("/queryResourceAccountsByKeyword")
    @ApiOperation(value = "模糊查询多个资源账户")
    public List<ResourceAccountEntity> queryResourceAccountsByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return resourceAccountService.queryResourceAccountEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("资源账户查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个资源账户
     *
     * @param resourceAccountEntity
     * @return
     */
    @PostMapping("/queryResourceAccounts")
    @ApiOperation(value = "查询多个资源账户")
    public List<ResourceAccountEntity> queryResourceAccounts(@RequestBody ResourceAccountEntity resourceAccountEntity) {
        try {
            return resourceAccountService.queryResourceAccountEntitys(resourceAccountEntity);
        } catch (Exception e) {
            log.warn("资源账户查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个资源账户
     *
     * @param resourceAccountEntity
     * @return
     */
    @PostMapping("/queryResourceAccount")
    @ApiOperation(value = "查询单个资源账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body" ,dataType = "Long", dataTypeClass = Long.class, defaultValue = "1")
    })
    public ResourceAccountEntity queryResourceAccount(@ApiIgnore @RequestBody ResourceAccountEntity resourceAccountEntity) {
        try {
            return resourceAccountService.queryResourceAccountEntity(resourceAccountEntity);
        } catch (Exception e) {
            log.warn("资源账户查询失败 {}", e);
            return null;
        }
    }
}
