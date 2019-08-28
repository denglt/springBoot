package com.springboot.aop;

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;

/**
 * Created by denglt on 2016/11/30.
 */

//@Service
public class MyAopBeanPostProcessor extends AbstractAdvisingBeanPostProcessor {

    public MyAopBeanPostProcessor(){
        this.advisor = new MyAopAnnotationAdvisor();
    }
}
