package com.springboot.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Package: com.springboot.postprocessor
 * @Author: denglt
 * @Date: 2018/12/14 3:59 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        System.out.println("create MyBeanFactoryPostProcessor ");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //  beanFactory.addBeanPostProcessor();
    }
}
