package com.lamp.atom.service.operator.consumers.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class JsonUtils {
    /**
     * 属性赋值
     * @param source
     * @param target
     * @return
     */
    public static Object copyNonNullPropertiesStatic(Object source, Object target) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        String[] nullPropertyNames = Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);

        BeanUtils.copyProperties(source, target, nullPropertyNames);
        return target;
    }
}

