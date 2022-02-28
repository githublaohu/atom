/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
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
