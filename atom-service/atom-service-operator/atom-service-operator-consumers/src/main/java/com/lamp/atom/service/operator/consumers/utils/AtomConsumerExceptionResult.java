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

import com.lamp.decoration.core.exception.CustomExceptionResult;
import com.lamp.decoration.core.exception.ExceptionResult;
import com.lamp.decoration.core.exception.ExceptionResultTypeEnum;

import java.util.List;

public class AtomConsumerExceptionResult implements CustomExceptionResult {
    ExceptionResult exceptionResult;

    {
        exceptionResult = new ExceptionResult();
        exceptionResult.setCode(400);
        exceptionResult.setMessage("系统出现异常");
        exceptionResult.setResultType(ExceptionResultTypeEnum.JSON);

    }

    @Override
    public ExceptionResult getDefaultExceptionResult() {
        return exceptionResult;
    }

    @Override
    public List<ExceptionResult> getExceptionResultList() {
        return null;
    }


}
