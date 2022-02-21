package com.lamp.atom.service.operator.entity;

import com.lamp.atom.service.domain.ColonyType;
import com.lamp.atom.service.domain.SourceType;
import lombok.Data;

@Data
public class ConnectionEntity extends BaseEntity {

    private static final long serialVersionUID = 7945055154629636963L;

    /**
     * 空间id
     */
    private Long spaceId;

    /**
     * 空间名
     */
    private String spaceName;

    /**
     * 空间别名
     */
    private String spaceAlias;

    /**
     * 场景id
     */
    private Long sceneId;

    /**
     * 场景名
     */
    private String sceneName;

    /**
     * 场景别名
     */
    private String sceneAlias;

    /**
     * 实验id
     */
    private Long experimentId;

    /**
     * 实验名
     */
    private String experimentName;

    /**
     * 实验别名
     */
    private String experimentAlias;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 数据源类型
     */
    private SourceType sourceType;

    /**
     * 数据源名
     */
    private String sourceName;

    /**
     * 源数据地址
     */
    private String sourceAddr;

    /**
     * 源登录账户
     */
    private String sourceAccount;

    /**
     * 源登录密码
     */
    private String sourcePassword;

    /**
     * 源登录空间：关系型数据库的数据库，oss的bucket,redis的index,es的index
     */
    private String sourceSpace;

    /**
     * 模式
     */
    private String mode;

    /**
     * 集群模式
     */
    private ColonyType colonyType;

    /**
     * 源数据配置
     */
    private String sourceConf;

    /**
     * 源数据路径
     */
    private String sourceRoute;

    /**
     * 源数据大小
     */
    private String sourceSize;

    /**
     * 源数据条数
     */
    private Long sourceCount;
}
