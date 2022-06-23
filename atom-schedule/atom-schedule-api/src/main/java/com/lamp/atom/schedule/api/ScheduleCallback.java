package com.lamp.atom.schedule.api;

public enum ScheduleCallback {

    OK("Success"),

    FAIL("FAIL");

    private String status;

    ScheduleCallback(String status) {
        this.status = status;
    }
}
