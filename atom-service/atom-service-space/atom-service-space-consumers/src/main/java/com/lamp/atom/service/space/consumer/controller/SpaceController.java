package com.lamp.atom.service.space.consumer.controller;

import com.lamp.atom.space.operator.service.SpaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/space")
@Controller("spaceController")
public class SpaceController {

    @Autowired
    @Qualifier("spaceService")
    private SpaceService spaceService;
}
