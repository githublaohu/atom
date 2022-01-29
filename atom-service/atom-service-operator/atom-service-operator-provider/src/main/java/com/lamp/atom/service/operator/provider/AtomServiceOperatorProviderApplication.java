package com.lamp.atom.service.operator.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class AtomServiceOperatorProviderApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(AtomServiceOperatorProviderApplication.class, args);
            log.info("{} 启动成功", AtomServiceOperatorProviderApplication.class.getSimpleName());
        } catch (Exception e) {
        	log.error(e.getMessage() , e);
        }
    }

}
