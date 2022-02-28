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


import com.lamp.atom.service.operator.entity.CaseEntity;
import com.lamp.atom.service.operator.entity.ConnectionEntity;
import com.lamp.atom.service.operator.service.CaseService;
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
@RequestMapping("/case")
@RestController("caseController")
public class CaseController {

    @Autowired
    @Qualifier("caseService")
    private CaseService caseService;

    /**
     * 添加实例
     * @param caseEntity
     */
    @PostMapping("/insertCase")
    public ResultObject<String> insertCase(@RequestBody CaseEntity caseEntity){
        //字段判空
        if (Objects.isNull(caseEntity.getOperatorId())) {
            log.info("参数校验失败 {}", caseEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            caseService.insertCaseEntity(caseEntity);
        } catch (Exception e) {
            log.warn("实例插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改实例
     * @param caseEntity
     * @return
     */
    @PostMapping("/updateCase")
    public ResultObject<String> updateCase(@RequestBody CaseEntity caseEntity){
        try {
            caseService.updateCaseEntity(caseEntity);
        } catch (Exception e) {
            log.warn("实例修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个实例
     * @param keyword
     * @return
     */
    @PostMapping("/queryCasesByKeyword")
    public List<CaseEntity> queryCasesByKeyword(@RequestBody String keyword){
        try {
            return caseService.queryCaseEntitysByKeyword(keyword);
        } catch (Exception e) {
            log.warn("实例查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个实例
     * @param caseEntity
     * @return
     */
    @PostMapping("/queryCases")
    public List<CaseEntity> queryCases(@RequestBody CaseEntity caseEntity){
        try {
            return caseService.queryCaseEntitys(caseEntity);
        } catch (Exception e) {
            log.warn("实例查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个实例
     * @param caseEntity
     * @return
     */
    @PostMapping("/queryCase")
    public CaseEntity queryCase(@RequestBody CaseEntity caseEntity){
        try {
            return caseService.queryCaseEntity(caseEntity);
        } catch (Exception e) {
            log.warn("实例查询失败 {}", e);
            return null;
        }
    }
}
