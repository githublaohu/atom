package com.lamp.atom.reasoing.service.reasoning.entity;


import lombok.Data;

@Data
public class ReasoningRequest {

	private String unique;
	
	private Long sceneId;
	
	private Long flowId;
	
	private String customerName;
}
