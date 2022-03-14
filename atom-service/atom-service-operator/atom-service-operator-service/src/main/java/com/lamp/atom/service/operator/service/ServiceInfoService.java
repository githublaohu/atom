
package com.lamp.atom.service.operator.service;

import java.util.List;

import com.lamp.atom.service.operator.entity.ServiceInfoEntity;

public interface ServiceInfoService {

    /**
     * 创建服务配置
     *
     * @param serviceInfoEntity
     */
    Integer createServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

    /**
     * 添加服务配置
     *
     * @param serviceInfoEntity
     */
    Integer insertServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

    /**
     * 模糊查询多个服务配置
     *
     * @param keyword
     * @return
     */
    List<ServiceInfoEntity> queryServiceInfoEntitysByKeyword(String keyword);

    /**
     * 修改服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    Integer updateServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

    /**
     * 查询多个服务配置
     *
     * @return
     */
    List<ServiceInfoEntity> queryServiceInfoEntitys();

    /**
     * 查询单个服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    ServiceInfoEntity queryServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);
}
