package com.springboot.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;

/**
 * Created by denglt on 2016/11/30.
 *
 *  使用 @Aspect @Aound("@annotation") 更简单 @see MyAopAspect
 */
@Service
public class MyAopAnnotationAdvisor extends AbstractPointcutAdvisor {

    private Advice advice;

    private Pointcut pointcut;

    public MyAopAnnotationAdvisor(){
        this.advice = buildAdvice();
        this.pointcut = buildPointcut(MyAopAnnotation.class);
    }

    public MyAopAnnotationAdvisor(Class<? extends Annotation> annotationType){
        this.advice = buildAdvice();
        this .pointcut = buildPointcut(annotationType);

    }
    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    private Advice buildAdvice(){
        return new MyAopExecuteInterceptor();
    }

    private Pointcut buildPointcut(Class<? extends Annotation> annotationType){
        Pointcut cpc = new AnnotationMatchingPointcut(annotationType); // 类上
        Pointcut mpc = new AnnotationMatchingPointcut(null,annotationType); // 方法上
        return new ComposablePointcut(cpc).union(mpc); // or

    }
}
