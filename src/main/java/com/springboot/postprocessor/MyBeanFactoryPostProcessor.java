package com.springboot.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Package: com.springboot.postprocessor
 * @Author: denglt
 * @Date: 2018/12/14 3:59 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor , ApplicationContextAware {

    private ApplicationContext applicationContext;
    public MyBeanFactoryPostProcessor() {
        System.out.println("create MyBeanFactoryPostProcessor ");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor: runing postProcessBeanFactory");
        Environment environment = applicationContext.getEnvironment();  // 这儿 applicationContext 可以使用，setApplicationContext 方法已经执行
        // 这儿可以注册自己要创建的对接
        //((DefaultListableBeanFactory)beanFactory).registerBeanDefinition(BeanDefinitionBuilder.genericBeanDefinition() );
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor: setApplicationContext");
        this.applicationContext =  applicationContext;
    }
}
