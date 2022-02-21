package com.lamp.atom.schedule.api;

public interface AtomOperatorShedule {

	
	public void createOperators(Shedule shedule);
	
	public void startOperators(Shedule shedule);
	
	public void suspendOperators(Shedule shedule);
	
	public void uninstallPperators(Shedule shedule);
}
