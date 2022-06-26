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

import com.lamp.atom.service.operator.entity.OperatorEntity;
import com.lamp.atom.service.operator.provider.mapper.OperatorMapper;
import com.lamp.atom.service.operator.service.OperatorService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    public List<OperatorEntity> queryOperatorEntitysByKeyword(String keyword) {
        return operatorMapper.queryOperatorEntitysByKeyword(keyword);
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
