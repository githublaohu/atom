package com.lamp.atom.service.domain;

public enum TaskLifecycle {
    /**
     * 创建中
     */
    CREATE,
    /**
     * 修改中
     */
    UPDATE,
    /**
     * 检查中
     */
    CHECK,
    /**
     * 创建完成
     */
    FINISH,
    /**
     * 作废
     */
    ABANDON;
}
