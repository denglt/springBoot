package com.springboot.restapi.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description:
 * @Package: com.springboot.restapi.filter
 * @Author: denglt
 * @Date: 2018/9/11 下午4:23
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@Component
@Order(1)
public class RquestFilter implements Filter {
    Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        LOG.info("Starting work for req : {}", req.getRequestURI());

        chain.doFilter(request, response);
        LOG.info("end work for req : {}",req.getRequestURI());
    }

    @Override
    public void destroy() {

    }
}
