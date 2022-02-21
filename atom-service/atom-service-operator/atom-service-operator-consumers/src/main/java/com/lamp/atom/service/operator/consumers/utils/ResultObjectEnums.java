package com.lamp.atom.service.operator.consumers.utils;

import com.lamp.decoration.core.result.ResultObject;

public enum ResultObjectEnums {

    SUCCESS(200,"success"),

    FAIL(500,"fail"),

    CHECK_PARAMETERS_FAIL(120001, "check parameters fail");

    private ResultObject<String> resultObject;

    ResultObjectEnums(Integer code, String message){
        this.resultObject = ResultObject.getResultObjectMessgae(code,message);
    }

    public ResultObject<String> getResultObject(){
        return this.resultObject;
    }
}
