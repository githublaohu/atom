package com.lamp.atom.schedule.api.deploy;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author laohu
 *
 */
@Data
public class Deploy {

	private List<AtomInstances> instancesList;
	
	private int count;
}
