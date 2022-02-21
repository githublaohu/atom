package com.lamp.atom.schedule.api;

import java.util.Map;

import com.lamp.atom.schedule.api.deploy.Deploy;
import com.lamp.atom.schedule.api.strategy.Strategy;

import lombok.Data;

@Data
public class Shedule {

	private Strategy strategy;
	
	private Deploy deploy;
	
	private Map<String ,String> limits;
	
	private Map<String,String> hardwareConfig;
	
	private Map<String,String> runParameter;
	
	private Map<String,String> envs;
	
	private Map<String,String> labels;
	
	private Object object;
	
	private String noteId;
	
	private String noteName;
	
	private String namespace;
}
