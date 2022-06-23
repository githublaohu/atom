package com.lamp.atom.service.domain;

public enum ScheduleRuntimeStatus {
    /**
     * console 调度中
     */
    SCHEDULING("调度中"),

    /**
     * console 调度失败
     */
    SCHEDULE_FAIL("调度失败"),

    /**
     * console 运行中
     */
    RUNNING("运行中"),

    /**
     * runtime 运行自动结束
     */
    AUTO_FINISH("运行自动结束"),

    /**
     * runtime 服务异常结束
     */
    SERVICE_EXCEPTION("服务异常结束");

    private String name;

    ScheduleRuntimeStatus(String name) {
        this.name = name;
    }

}
