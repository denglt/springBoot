package com.springboot.restapi;

import com.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

/**
 * @Description:
 * @Package: com.springboot.restapi
 * @Author: denglt
 * @Date: 2018/9/12 下午2:45
 * @Copyright: 版权归 HSYUNTAI 所有
 */

/**
 * spring WebFlux
 */

@RequestMapping("/webflux")
@RestController
public class WebFluxController {

    @Autowired
    private User author;

    @Autowired
    private Executor executor;

    private Scheduler scheduler;

    @PostConstruct
    void init() {
        scheduler = Schedulers.fromExecutor(executor);
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/hello")
    public Mono<String> hello() {   // 【改】返回类型为Mono<String>
        return Mono.just("Welcome to reactive world ~");     // 【改】使用Mono.just生成响应式数据
    }

    @GetMapping("/hello2")
    public Mono<String> hello2() {   // 【改】返回类型为Mono<String>
        return Mono.fromSupplier(() -> {
            logger.info("run hello2 on WebFlux");
            return "hello world!";
        }).publishOn(scheduler);
    }

    @GetMapping("/hello3")
    public Flux<String> hello3() {   // 【改】返回类型为Mono<String>
        return Flux.just("hello", "world", "!")
                .publishOn(scheduler);
    }

}
