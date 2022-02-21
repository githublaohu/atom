package com.lamp.atom.space.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class AtomSpaceOperatorConsumerApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(AtomSpaceOperatorConsumerApplication.class, args);
            log.info("{} 启动成功", AtomSpaceOperatorConsumerApplication.class.getSimpleName());
        } catch (Exception e) {
            log.error(e.getMessage() , e);
        }
    }
}
