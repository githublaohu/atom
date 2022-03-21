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

import com.lamp.atom.service.operator.entity.RuntimeEntity;
import com.lamp.atom.service.operator.provider.mapper.RuntimeMapper;
import com.lamp.atom.service.operator.service.RuntimeService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RuntimeServiceImpl implements RuntimeService {

    @Autowired
    private RuntimeMapper runtimeMapper;

    @Override
    public Integer insertRuntimeEntity(RuntimeEntity runtimeEntity) {
        return runtimeMapper.insertRuntimeEntity(runtimeEntity);
    }

    @Override
    public Integer batchInsertRuntimeEntity(List<RuntimeEntity> runtimeEntityList) {
        return runtimeMapper.batchInsertRuntimeEntity(runtimeEntityList);
    }

    @Override
    public Integer updateRuntimeEntity(RuntimeEntity runtimeEntity) {
        return runtimeMapper.updateRuntimeEntity(runtimeEntity);
    }

    @Override
    public Integer batchUpdateRuntimeEntity(List<RuntimeEntity> runtimeEntityList) {
        return runtimeMapper.batchUpdateRuntimeEntity(runtimeEntityList);
    }

    @Override
    public List<RuntimeEntity> queryRuntimeEntitysByKeyword(String keyword) {
        return runtimeMapper.queryRuntimeEntitysByKeyword(keyword);
    }

    @Override
    public List<RuntimeEntity> queryRuntimeEntitys(RuntimeEntity runtimeEntity) {
        return runtimeMapper.queryRuntimeEntitys(runtimeEntity);
    }

    @Override
    public RuntimeEntity queryRuntimeEntity(RuntimeEntity runtimeEntity) {
        return runtimeMapper.queryRuntimeEntity(runtimeEntity);
    }
}
