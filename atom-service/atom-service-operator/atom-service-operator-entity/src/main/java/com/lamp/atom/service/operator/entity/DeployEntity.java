package com.lamp.atom.service.operator.entity;

import lombok.Data;

@Data
public class DeployEntity extends BaseEntity {
    private static final long serialVersionUID = 7044486250117016736L;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 服务器端口
     */
    private String serverPort;
}
