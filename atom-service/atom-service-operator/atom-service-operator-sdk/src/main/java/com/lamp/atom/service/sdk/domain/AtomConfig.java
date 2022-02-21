package com.lamp.atom.service.sdk.domain;

import lombok.Data;

@Data
public class AtomConfig {

    /**
     * 实例ID
     */
    private Integer id;

    /**
     * nacos配置
     */
    private String nacosAddr;

    private String nacosNamespace;

    private String nacosDataId;

    private String group = "DEFAULT_GROUP";
}
