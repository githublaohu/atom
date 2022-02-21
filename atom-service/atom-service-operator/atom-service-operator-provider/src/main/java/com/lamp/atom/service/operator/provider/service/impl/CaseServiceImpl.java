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
