package com.springboot.restapi;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "springweb")
public class SpringWebInfo {


    @RequestMapping(value = "appContext")
    public String infoApplicationContext(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        WebApplicationContext context = RequestContextUtils.findWebApplicationContext(request);
        sb.append("RequestContextUtils.findWebApplicationContext:" + context);
        sb.append("\n");
        sb.append("通过类WebApplicationContextUtils获取:");
        sb.append("\n");
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        sb.append("WebApplicationContextUtils.getRequiredWebApplicationContext:"
                + context);
        sb.append("\n");
        return sb.toString();
    }

    @RequestMapping(value = "beans", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Map<String, String>> infoBeanName(HttpServletRequest request) {
        List<Map<String, String>> beans = new ArrayList<>();
        WebApplicationContext context = RequestContextUtils.findWebApplicationContext(request);
        String[] beanNames = context.getBeanDefinitionNames();
        for (String name : beanNames) {
            Map<String, String> bean = new HashMap<>();
            bean.put(name, context.getBean(name).toString());
            beans.add(bean);
        }
        return beans;
    }

    @RequestMapping(value = "requestMappingHandlerAdapter")
    public String requestMappingHandlerAdapter(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        WebApplicationContext context = RequestContextUtils.findWebApplicationContext(request);
        RequestMappingHandlerAdapter adapter = null;
        if (context.containsBean("requestMappingHandlerAdapter")) {
            adapter = (RequestMappingHandlerAdapter) context.getBean("requestMappingHandlerAdapter");
            sb.append("用户定义RequestMappingHandlerAdapter:" + adapter + "\n");
            if (adapter != null) {
                List<HttpMessageConverter<?>> coverters = adapter.getMessageConverters();
                sb.append("MessageConverters:\n");
                for (HttpMessageConverter converter : coverters) {
                    sb.append(converter.getClass().getName() + "=" + converter + "\n");
                }
            }
        }
        sb.append("\n\n\n");
        adapter = (RequestMappingHandlerAdapter) context.getBean("org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#0");
        sb.append("Spring默认RequestMappingHandlerAdapter:" + adapter + "\n");
        if (adapter != null) {
            List<HttpMessageConverter<?>> coverters = adapter
                    .getMessageConverters();
            sb.append("MessageConverters:\n");
            for (HttpMessageConverter converter : coverters) {
                sb.append(converter.getClass().getName() + "=" + converter + "\n");
            }
        }
        return sb.toString();
    }

    @RequestMapping(value = "dispatcherServlet")
    public String dispatcherServlet(HttpServletRequest request) {

        ServletContext sc = request.getServletContext();
        ServletRegistration sr = sc.getServletRegistration("extjs-web");
        Collection<String> cs = sr.getMappings();
        System.out.println(cs);
        System.out.println(sr);
        return null;
    }
}
