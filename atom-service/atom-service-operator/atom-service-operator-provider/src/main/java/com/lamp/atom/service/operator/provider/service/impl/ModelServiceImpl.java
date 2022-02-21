package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.ModelEntity;
import com.lamp.atom.service.operator.provider.mapper.ModelMapper;
import com.lamp.atom.service.operator.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("modelService")
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Integer insertModelEntity(ModelEntity modelEntity) {
        return modelMapper.insertModelEntity(modelEntity);
    }

    @Override
    public Integer updateModelEntity(ModelEntity modelEntity) {
        return modelMapper.updateModelEntity(modelEntity);
    }

    @Override
    public List<ModelEntity> queryModelEntitys(ModelEntity modelEntity) {
        return modelMapper.queryModelEntitys(modelEntity);
    }

    @Override
    public ModelEntity queryModelEntity(ModelEntity modelEntity) {
        return modelMapper.queryModelEntity(modelEntity);
    }
}
