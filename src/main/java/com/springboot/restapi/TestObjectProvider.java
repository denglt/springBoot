package com.springboot.restapi;

import com.springboot.aop.MyAopBeanPostProcessor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Package: com.springboot.restapi
 * @Author: denglt
 * @Date: 2021/8/2 17:18
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@RestController
public class TestObjectProvider {

    @Autowired
    private ObjectFactory<List<Filter>> filters; // instance is DefaultListableBeanFactory.DependencyObjectProvider

    @Autowired
    private ObjectProvider<List<Filter>> filters2; // instance is DefaultListableBeanFactory.DependencyObjectProvider

    @Autowired
    private ObjectFactory<MyAopBeanPostProcessor> noExists;

    @Autowired
    private ObjectProvider<MyAopBeanPostProcessor> noExists2;

    @Autowired
    private ObjectProvider<Optional<MyAopBeanPostProcessor>> noOptional;

    @RequestMapping(value = "/filters", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String filters() {
        List<Filter> filters = this.filters.getObject();
        return filters != null ? "size of filters is " + filters.size() : "ObjectFactory can get filters";
    }

    public void get() {
        noExists.getObject(); // throw NoSuchBeanDefinitionException;
        noExists2.getObject();// throw NoSuchBeanDefinitionException;
        noExists2.getIfAvailable(); // return null;

        noOptional.getObject();// return Optional.empty
        noOptional.getIfAvailable();// eturn Optional.empty
    }
}
