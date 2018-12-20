package com.springboot.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 *  BeanPostProcessor
 *  DestructionAwareBeanPostProcessor 【ApplicationListenerDetector】
 *  InstantiationAwareBeanPostProcessor
 *  SmartInstantiationAwareBeanPostProcessor
 *  MergedBeanDefinitionPostProcessor
 *
 *  AbstractAutowireCapableBeanFactory.createBean() 中：处理BeanPostProcessor、InstantiationAwareBeanPostProcessor、SmartInstantiationAwareBeanPostProcessor
 *
 * @Description:
 * @Package: com.springboot.postprocessor
 * @Author: denglt
 * @Date: 2018/12/14 4:02 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor(){
        System.out.println("create MyBeanPostProcessor");
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
