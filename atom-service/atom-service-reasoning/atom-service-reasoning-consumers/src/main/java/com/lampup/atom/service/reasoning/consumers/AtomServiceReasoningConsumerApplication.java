package com.lampup.atom.service.reasoning.consumers;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
public class AtomServiceReasoningConsumerApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(AtomServiceReasoningConsumerApplication.class, args);
            log.info("{} 启动成功", AtomServiceReasoningConsumerApplication.class.getSimpleName());
        } catch (Exception e) {
        	log.error(e.getMessage() , e);
        }
    }

}
