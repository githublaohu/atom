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
package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.CaseEntity;
import com.lamp.atom.service.operator.provider.mapper.CaseMapper;
import com.lamp.atom.service.operator.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("caseService")
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseMapper caseMapper;

    @Override
    public Integer insertCaseEntity(CaseEntity caseEntity) {
        return caseMapper.insertCaseEntity(caseEntity);
    }

    @Override
    public Integer updateCaseEntity(CaseEntity caseEntity) {
        return caseMapper.updateCaseEntity(caseEntity);
    }

    @Override
    public List<CaseEntity> queryCaseEntitys(CaseEntity caseEntity) {
        return caseMapper.queryCaseEntitys(caseEntity);
    }

    @Override
    public CaseEntity queryCaseEntity(CaseEntity caseEntity) {
        return caseMapper.queryCaseEntity(caseEntity);
    }
}
