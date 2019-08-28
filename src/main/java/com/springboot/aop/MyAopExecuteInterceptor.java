package com.springboot.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;


/**
 * Created by denglt on 2016/11/30.
 */
//@Service
public class MyAopExecuteInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MyAopExecuteInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.info("MyAopAnnotation begin...");
        logger.info("MethodInvocation:" + invocation.getClass());
        logger.info("Target Object: " + invocation.getThis());

        Class<?> targetClass = invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null;
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        MyAopAnnotation myAopAnnotation = AnnotatedElementUtils.findMergedAnnotation(specificMethod, MyAopAnnotation.class);
        if (myAopAnnotation == null) {
            myAopAnnotation = AnnotatedElementUtils.findMergedAnnotation(specificMethod.getDeclaringClass(), MyAopAnnotation.class);
        }
        logger.info("myAopAnnotation => {}", myAopAnnotation == null ? null : myAopAnnotation.value());
        Object result = invocation.proceed();
        logger.info("MyAopAnnotation end !");
        return result;
    }
}
