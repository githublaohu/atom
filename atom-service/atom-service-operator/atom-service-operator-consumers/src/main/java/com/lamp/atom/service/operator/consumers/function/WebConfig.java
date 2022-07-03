package com.lamp.atom.service.operator.consumers.function;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean filter1() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DispatcherFilter dispatcherFilter = new DispatcherFilter();
        filterRegistrationBean.setFilter(dispatcherFilter);
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
