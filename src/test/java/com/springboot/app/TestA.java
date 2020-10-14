package com.springboot.app;


import org.junit.Test;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Package: com.springboot.app
 * @Author: denglt
 * @Date: 2020/10/13 17:38
 * @Copyright: 版权归 HSYUNTAI 所有
 */
public class TestA {

    @Test
    public void propertySource() {
        Map<String, Object> mapA = new HashMap<>();
        Map<String, Object> mapB = new HashMap<>();
        List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
        sources.add(new MapPropertySource("sourceA", mapA));
        sources.add(new MapPropertySource("sourceB", mapB));
        System.out.println(sources.contains(PropertySource.named("sourceA")));
        System.out.println(sources.contains(PropertySource.named("sourceB")));
    }

    @Test
    public void SimpleCommandLinePropertySource(){
        SimpleCommandLinePropertySource propertySource = new SimpleCommandLinePropertySource("--name=denglt","-age=10","-sex=1");
        System.out.println(propertySource);
        System.out.print(propertySource.getProperty("name"));
        System.out.print(propertySource.getProperty("nonOptionArgs"));
    }
}
