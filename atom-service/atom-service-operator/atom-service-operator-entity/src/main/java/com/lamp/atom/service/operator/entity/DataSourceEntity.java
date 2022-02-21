package com.lamp.atom.service.operator.entity;

import com.lamp.atom.service.domain.DataSourceType;
import lombok.Data;

@Data
public class DataSourceEntity extends BaseEntity {

    private static final long serialVersionUID = -9016196677726614802L;

    /**
     * 算子id
     */
    private Long operatorId;

    /**
     * 连接id
     */
    private Long connectionId;

    /**
     * 数据源类型
     */
    private DataSourceType dataSourceType;

    /**
     * 源登录空间：关系型数据库的数据库，oss的bucket,redis的index,es的index
     */
    private String sourceSpace;

    /**
     * 数据源配置
     */
    private String sourceConf;

    /**
     * 初始化
     */
    private String taskInitExecute;

    /**
     * 任务开始
     */
    private String operateExecuteBefore;

    /**
     * 数据执行之前
     */
    private String dataExecuteBefore;

    /**
     * 执行内容
     */
    private String operateExecute;

    /**
     * 数据执行之后
     */
    private String dataExecuteAfter;

    /**
     * 任务结束
     */
    private String operateExecuteAfter;

    /**
     * 数据格式
     */
    private String dataFormat;

    /**
     * 算子从source 一次读取数量
     */
    private Integer operatorReadNum;

    /**
     * 从连接中一次读取数据量
     */
    private Integer connectReadNum;

    /**
     * source从connect是否一次行读取
     */
    private Boolean disposable;

    /**
     * 分页加载数量
     */
    private Integer paginateReadNum;

    /**
     * 是否异步从connect读取数据
     */
    private Boolean asyncLoad;

    /**
     * 排序
     */
    private Integer order;

}