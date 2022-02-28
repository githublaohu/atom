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
