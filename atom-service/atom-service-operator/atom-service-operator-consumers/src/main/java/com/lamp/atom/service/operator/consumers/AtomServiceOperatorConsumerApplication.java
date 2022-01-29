package com.lamp.atom.service.operator.consumers;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
public class AtomServiceOperatorConsumerApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(AtomServiceOperatorConsumerApplication.class, args);
            log.info("{} 启动成功", AtomServiceOperatorConsumerApplication.class.getSimpleName());
        } catch (Exception e) {
        	log.error(e.getMessage() , e);
        }
    }

}
