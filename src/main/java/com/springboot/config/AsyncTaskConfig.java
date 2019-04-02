package com.springboot.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/11/2 3:19 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

//<task:executor id="executor" pool-size="2-5" queue-capacity="100" rejection-policy="CALLER_RUNS" keep-alive="120"/>
@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "spring.task.execution.pool")
public class AsyncTaskConfig implements AsyncConfigurer, InitializingBean {

    private int corePoolSize = 2;
    private int maxPoolSize = 5;
    private int queueCapacity = 100;
    private int keepAliveSeconds = 120;

    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("keepAliveSeconds -> " + keepAliveSeconds);
        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        taskExecutor.setBeanName("myExecutor");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();

    }

    @Override
    @Bean("myExecutor")
    public Executor getAsyncExecutor() {

        return taskExecutor;  // 可以使用LazyTraceExecutor包装，确保traceId和spanId正确的传递

    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
