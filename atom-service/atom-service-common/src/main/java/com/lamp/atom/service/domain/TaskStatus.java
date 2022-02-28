package com.lamp.atom.service.domain;

public enum TaskStatus {
    /**
     * 待运行
     */
    PENDING,
    /**
     * 运行中
     */
    RUNNING,
    /**
     * 暂停
     */
    PAUSE,
    /**
     * 停止
     */
    STOP;
}
