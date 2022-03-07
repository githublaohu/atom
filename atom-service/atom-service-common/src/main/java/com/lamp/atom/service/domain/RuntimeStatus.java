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
 * 训练状态
 */
public enum RuntimeStatus {
    /**
     * console 编辑中
     */
    EDITING("编辑中"),

    /**
     * console 编辑取消
     */
    EDIT_CANCEL("编辑取消"),

    /**
     * console 编辑完成
     */
    EDIT_FINISH("编辑完成"),

    /**
     * console 排队中
     */
    QUEUING("排队中"),

    /**
     * console 排队取消中
     */
    QUEUE_CANCELING("排队取消中"),

    /**
     * schedule 排队取消完成
     */
    QUEUE_CANCEL("排队取消"),

    /**
     * console 抢占中
     */
    OCCUPYING("抢占中"),

    /**
     * schedule 抢占成功
     */
    OCCUPY_SUCCESS("抢占成功"),

    /**
     * schedule 抢占失败
     */
    OCCUPY_FAIL("抢占失败"),

    /**
     * runtime 算子实例创建中
     */
    CASE_CREATING("算子实例创建中"),

    /**
     * runtime 算子实例创建完成
     */
    CASE_CREATE_FINISH("算子实例创建完成"),

    /**
     * runtime 数据下载中
     */
    DATA_UPLOADING("数据下载中"),

    /**
     * runtime 数据下载完成
     */
    DATA_UPLOAD_FINISH("数据下载完成"),

    /**
     * runtime 运行中
     */
    RUNNING("运行中"),

    /**
     * runtime 测试中
     */
    TESTING("测试中"),

    /**
     * runtime 运行自动结束
     */
    RUNNING_AUTO_FINISH("运行自动结束"),

    /**
     * runtime 运行手动终止
     */
    RUNNING_FINISH("运行手动终止"),

    /**
     * runtime 运行异常结束
     */
    RUNNING_EXCEPTION("运行异常结束"),

    /**
     * runtime 服务异常结束
     */
    SERVICE_EXCEPTION("服务异常结束");

    private String name;

    RuntimeStatus(String name) {
        this.name = name;
    }
}
