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
package com.lamp.atom.service.domain;

/**
 * 算子类型
 */
public enum OperatorRuntimeType {
    /**
     * TRAIN
     */
    TRAIN("训练算子"),

    /**
     * REASONING
     */
    REASONING("推理算子"),

    /**
     * FEATURES
     */
    FEATURES("特征算子"),

    /**
     * PYTHON
     */
    PYTHON("python算子"),

    /**
     * FLINK
     */
    FLINK("flink算子"),

    /**
     * PY_FLINK
     */
    PY_FLINK("py_flink算子"),

    /**
     * ALINK
     */
    ALINK("alink算子"),

    /**
     * PYTHON_DATA
     */
    PYTHON_DATA("python数据算子");

    String name;

    OperatorRuntimeType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
