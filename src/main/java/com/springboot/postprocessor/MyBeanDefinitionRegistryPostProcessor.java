package com.springboot.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 *  BeanFactoryPostProcessor
 *  BeanDefinitionRegistryPostProcessor 【ConfigurationClassPostProcessor】
 *
 * @Description:
 * @Package: com.springboot.postprocessor
 * @Author: denglt
 * @Date: 2018/12/14 4:23 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    public MyBeanDefinitionRegistryPostProcessor(){
        System.out.println("create MyBeanDefinitionRegistryPostProcessor");
    }

    /**
     * 先于postProcessBeanFactory()执行
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println(registry);
        // registry.registerBeanDefinition();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(beanFactory);
       //  beanFactory.registerSingleton();
    }
}
