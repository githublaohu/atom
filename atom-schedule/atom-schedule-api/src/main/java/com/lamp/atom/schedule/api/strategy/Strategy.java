package com.lamp.atom.schedule.api.strategy;

import java.util.Map;

import lombok.Data;

@Data
public class Strategy {

	private SheduleStrategyType sheduleStrategyType;
	
	private Map<String,String> label;
	
	private String timing;
}
