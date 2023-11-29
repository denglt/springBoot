package com.springboot.restapi.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Package: com.webflux.filters
 * @Author: denglt
 * @Date: 2018/10/30 6:09 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@Component
public class ExampleWebFilter implements WebFilter { // webflux 过滤用

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             WebFilterChain webFilterChain) {

        serverWebExchange.getResponse()
                .getHeaders().add("web-filter", "web-filter-test");
        LOG.info(
                "Starting a transaction for req : {} by ExampleWebFilter",
                serverWebExchange.getRequest().getURI());

        return webFilterChain.filter(serverWebExchange)
                .doOnSuccess((t) -> LOG.info(
                        "Committing a transaction for req : {} by ExampleWebFilter",
                        serverWebExchange.getRequest().getURI()));
    }

}
