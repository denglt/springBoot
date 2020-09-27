package com.springboot.event;

import com.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class OnlyContextRefreshedEvent implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(OnlyContextRefreshedEvent.class);


    @Async
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("spring容器初始化完毕================================================888");
    }

    public static void main(String[] args) {
        ApplicationListener applicationListener = new OnlyContextRefreshedEvent();
        Type superClass = applicationListener.getClass().getGenericInterfaces()[0];
        System.out.println(superClass);
        if (superClass instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        } else {
            Type _type = ((ParameterizedType)superClass).getActualTypeArguments()[0];
            System.out.println(_type);
        }

        TypeReference<User> typeReference = new TypeReference<User>() {
        };

        Type type2 = typeReference.getType();
        System.out.println(type2);
    }
}

abstract class TypeReference<T> implements Comparable<com.fasterxml.jackson.core.type.TypeReference<T>> {
    protected final Type _type;

    protected TypeReference() {
        System.out.println(getClass());
        Type superClass = this.getClass().getGenericSuperclass();
        System.out.println(superClass);
        if (superClass instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        } else {
            this._type = ((ParameterizedType)superClass).getActualTypeArguments()[0];
        }
    }

    public Type getType() {
        return this._type;
    }

    public int compareTo(com.fasterxml.jackson.core.type.TypeReference<T> o) {
        return 0;
    }
}
