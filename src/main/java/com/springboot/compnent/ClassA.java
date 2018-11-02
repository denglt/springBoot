package com.springboot.compnent;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Package: com.springboot.compnent
 * @Author: denglt
 * @Date: 2018/11/1 5:01 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
@AutoConfigureAfter(ClassB.class) // 没有作用

public class ClassA {

    public ClassA(){
        System.out.println("new ClassA");
    }
}
