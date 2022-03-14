package com.lamp.atom.service.operator.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AvaliablePortEntity extends BaseEntity {

    private static final long serialVersionUID = 6178707504251128831L;

    private Integer port;
}
