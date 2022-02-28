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

import com.lamp.atom.service.operator.entity.AvaliablePortEntity;
import com.lamp.atom.service.operator.provider.mapper.AvaliablePortMapper;
import com.lamp.atom.service.operator.service.AvaliablePortService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AvaliablePortServiceImpl implements AvaliablePortService {

    @Autowired
    private AvaliablePortMapper avaliablePortMapper;

    @Override
    public Integer insertAvaliablePortEntity(AvaliablePortEntity avaliablePortEntity) {
        return avaliablePortMapper.insertAvaliablePortEntity(avaliablePortEntity);
    }

    @Override
    public Integer updateAvaliablePortEntity(AvaliablePortEntity avaliablePortEntity) {
        return avaliablePortMapper.updateAvaliablePortEntity(avaliablePortEntity);
    }

    @Override
    public AvaliablePortEntity queryAvaliablePortEntity(AvaliablePortEntity avaliablePortEntity) {
        return avaliablePortMapper.queryAvaliablePortEntity(avaliablePortEntity);
    }
}
