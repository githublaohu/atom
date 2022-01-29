package com.lamp.atom.service.operator.consumers.controller;

import org.apache.dubbo.config.annotation.Reference;

import com.lamp.atom.service.operator.service.ConnectionService;

public class ConnectionController {
	
	@Reference(url = "127.0.0.1:19001")
	private ConnectionService connectionService;
}
