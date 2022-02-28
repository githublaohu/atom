package com.lamp.atom.service.task.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -7162490774296452711L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 状态：0正常1删除
     */
    private Integer deleteFlag;
}