package com.lamp.atom.service.operator.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ServiceInfo {

	private Long siId;
	
	private Long spaceId;

	private String siName;

	private String siType;

	private String siRuntimePattern;

	private String siIp;

	private String siPodIp;

	private Integer siPort;

	private Date siRuntimeStartTime;

	private Date siRuntimeEndTime;

	private Integer siCpu;

	private Integer siGpu;

	private Integer siMemory;

	private Integer siDisplayMemory;

	private String siStatus;

	private String slLobel;

	private Long siStartId;

	private String siStartName;

	private Long siEndId;

	private String siEndName;
}
