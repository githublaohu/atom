package com.lamp.atom.service.domain;

/**
 * 训练状态
 */
public enum OperatorStatus {
    /**
     * 编辑中
     */
    EDITING("编辑中"),

    /**
     * 编辑取消
     */
    EDIT_CANCEL("编辑取消"),

    /**
     * 排队中
     */
    QUEUING("排队中"),

    /**
     * 抢占中
     */
    OCCUPYING("抢占中"),

    /**
     * 排队取消
     */
    QUEUE_CANCEL("排队取消"),

    /**
     * 运行中
     */
    RUNNING("运行中"),

    /**
     * 测试中
     */
    TESTING("测试中"),

    /**
     * 运行自动结束
     */
    RUNNING_AUTO_FINISH("运行自动结束"),

    /**
     * 运行手动终止
     */
    RUNNING_FINISH("运行手动终止"),

    /**
     * 运行异常结束
     */
    RUNNING_EXCEPTION("运行异常结束"),

    /**
     * 运行异常结束
     */
    SERVICE_EXCEPTION("服务异常结束");

    private String name;

    OperatorStatus(String name) {
        this.name = name;
    }
}
