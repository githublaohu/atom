/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.atom.schedule.python.operator;

import com.lamp.light.annotation.Body;
import com.lamp.light.annotation.POST;

public interface AtomOperatorRPCServier {

	@Body
	@POST("/operator/create_operators")
	public Result createOperators(CreateOperator createOperator);

	@POST("/operator/start_operators")
	public Result startOperators(Operator operator);

	@POST("/operator/suspend_operators")
	public Result suspendOperators(Operator operator);

	@POST("/operator/uninstall_operators")
	public Result uninstallPperators(Operator operator);
}
