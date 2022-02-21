package com.lamp.atom.schedule.python.operator;

import com.lamp.light.annotation.POST;

public interface AtomOperatorRPCServier {

	@POST("/operator/create_operators")
	public Result createOperators(CreateOperator createOperator);

	@POST("/operator/start_operators")
	public Result startOperators(Operator operator);

	@POST("/operator/suspend_operators")
	public Result suspendOperators(Operator operator);

	@POST("/operator/uninstall_operators")
	public Result uninstallPperators(Operator operator);
}
