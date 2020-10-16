package com.springboot.config;

import com.springboot.model.User;
import com.springboot.restapi.WebFluxController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/9/13 上午11:43
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
//@EnableWebFlux
public class RoutingConfig {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction() {
        System.out.println("RouterFunction<ServerResponse> monoRouterFunction()");
        //return RouterFunctions.route(RequestPredicates.GET("/rfUser"), r -> getUser(r));

        return RouterFunctions.route(RequestPredicates.GET("/rfUser").and(accept(APPLICATION_JSON, TEXT_PLAIN)), this::getUser);
    }


    public Mono<ServerResponse> getUser(ServerRequest request) {
        System.out.println("WebFluxController.getUser");
        User u = new User();
        u.setName("WebFlux");
        u.setId(0);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(u), User.class);
    }
}
