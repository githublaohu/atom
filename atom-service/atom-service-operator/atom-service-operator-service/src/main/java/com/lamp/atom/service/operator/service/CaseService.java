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
package com.lamp.atom.service.operator.service;

import com.lamp.atom.service.operator.entity.CaseEntity;

import java.util.List;

public interface CaseService {
    /**
     * 添加任务
     * @param caseEntity
     */
    Integer insertCaseEntity(CaseEntity caseEntity);

    /**
     * 修改任务
     * @param caseEntity
     * @return
     */
    Integer updateCaseEntity(CaseEntity caseEntity);

    /**
     * 模糊查询多个实例
     * @param keyword
     * @return
     */
    List<CaseEntity> queryCaseEntitysByKeyword(String keyword);

    /**
     * 查询多个任务
     * @param caseEntity
     * @return
     */
    List<CaseEntity> queryCaseEntitys(CaseEntity caseEntity);

    /**
     * 查询单个任务
     * @param caseEntity
     * @return
     */
    CaseEntity queryCaseEntity(CaseEntity caseEntity);
}
