package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.OperatorEntity;
import com.lamp.atom.service.operator.provider.mapper.OperatorMapper;
import com.lamp.atom.service.operator.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorMapper operatorMapper;

    @Override
    public Integer insertOperatorEntity(OperatorEntity operatorEntity) {
        return operatorMapper.insertOperatorEntity(operatorEntity);
    }

    @Override
    public Integer updateOperatorEntity(OperatorEntity operatorEntity) {
        return operatorMapper.updateOperatorEntity(operatorEntity);
    }

    @Override
    public List<OperatorEntity> queryOperatorEntitys(OperatorEntity operatorEntity) {
        return operatorMapper.queryOperatorEntitys(operatorEntity);
    }

    @Override
    public OperatorEntity queryOperatorEntity(OperatorEntity operatorEntity) {
        return operatorMapper.queryOperatorEntity(operatorEntity);
    }
}
