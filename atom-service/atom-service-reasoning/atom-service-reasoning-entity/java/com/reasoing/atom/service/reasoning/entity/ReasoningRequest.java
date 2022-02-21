package com.reasoing.atom.service.reasoning.entity;

import lombok.Data;

@Data
public class ReasoningRequest {

	private String unique;
	
	private Long sceneId;
	
	private Long flowId;
	
	private String customerName;
}
