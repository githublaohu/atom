package com.lamp.atom.service.operator.consumers.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/runtimeEvent")
@RestController("runtimeEventController")
@Api(tags = {"运行实例事件操作接口"})
public class RuntimeEventController {


}
