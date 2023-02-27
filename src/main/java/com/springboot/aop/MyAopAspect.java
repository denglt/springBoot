package com.springboot.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description:   EnableAspectJAutoProxy
 * @Package: com.springboot.aop
 * @Author: denglt
 * @Date: 2022/7/13 10:52
 * @Copyright:
 */

@Aspect
@Component
public class MyAopAspect { // implements Ordered  like @Order

    private static final Logger logger = LoggerFactory.getLogger(MyAopAspect.class);

    @Order(Integer.MIN_VALUE)
    @Around("@annotation(MyAopAnnotation)")
    Object myAopAround(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("MyAopAspect myAopAround begin ...");
        Object retVal = pjp.proceed();
        logger.info("MyAopAspect myAopAround end ...");
        return retVal;
    }

    @Order(Integer.MIN_VALUE+1)
    @Around("@annotation(myAopAnnotation)")  //  @Around("@within(myAopAnnotation) || @annotation(myAopAnnotation)") // @within 类上   @annotation 方法上
    Object myAopAround2(ProceedingJoinPoint pjp, MyAopAnnotation myAopAnnotation) throws Throwable{
        logger.info("MyAopAspect myAopAround2 begin ..." + myAopAnnotation.value());
        Object retVal = pjp.proceed();
        logger.info("MyAopAspect myAopAround2 end ...");
        return retVal;
    }

}
/**  执行日志

 2022-07-13 11:42:54.618 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopExecuteInterceptor:24 - MyAopAnnotation begin...
 2022-07-13 11:42:54.619 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopExecuteInterceptor:25 - MethodInvocation:class org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation
 2022-07-13 11:42:54.619 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopExecuteInterceptor:26 - Target Object: com.springboot.restapi.UserController@6926ffe4
 2022-07-13 11:42:54.619 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopExecuteInterceptor:36 - myAopAnnotation => u/ser/get
 2022-07-13 11:42:54.629 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopAspect:29 - MyAopAspect myAopAround begin ...
 2022-07-13 11:42:54.630 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopAspect:38 - MyAopAspect myAopAround2 begin ...u/ser/get
 ....
 2022-07-13 11:42:54.691 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopAspect:40 - MyAopAspect myAopAround2 end ...
 2022-07-13 11:42:54.691 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopAspect:31 - MyAopAspect myAopAround end ...
 2022-07-13 11:42:54.692 [http-nio-8088-exec-1] [INFO ] com.springboot.aop.MyAopExecuteInterceptor:38 - MyAopAnnotation end !
 */