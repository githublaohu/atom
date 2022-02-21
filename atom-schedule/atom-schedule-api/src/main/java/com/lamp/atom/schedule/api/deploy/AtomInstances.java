package com.lamp.atom.schedule.api.deploy;

import lombok.Data;

@Data
public class AtomInstances {

	/**
	 * instance ip.
	 */
	private String ip;

	/**
	 * instance port.
	 */
	private int port;
}
