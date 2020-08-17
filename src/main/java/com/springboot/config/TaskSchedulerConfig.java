package com.springboot.config;

import com.springboot.app.MyStartupRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/11/2 4:35 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */
//<task:scheduler id="taskScheduler" pool-size="3" />

@Configuration
@EnableScheduling  // SchedulingConfiguration -> ScheduledAnnotationBeanPostProcessor
@ConfigurationProperties(prefix = "spring.task.scheduler.pool")
public class TaskSchedulerConfig implements SchedulingConfigurer {  // ScheduledAnnotationBeanPostProcessor 会处理该接口

    private static final Logger logger = LoggerFactory.getLogger(MyStartupRunner.class);
    private int poolSize = 10;
    private String threadNamePrefix = "my-scheduled-task-pool-";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {  // ScheduledTaskRegistrar (默认会创建一个 Executors.newSingleThreadScheduledExecutor())
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(poolSize);
        threadPoolTaskScheduler.setThreadNamePrefix(threadNamePrefix);
        System.out.println(threadNamePrefix);
        threadPoolTaskScheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task :: Execution Time - {}", LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);   // 最终 5s 一次
        } catch (InterruptedException ex) {
            logger.error("Ran into an error {}", ex);
           // throw new IllegalStateException(ex);
        }
    }

    //@Scheduled(fixedDelay = 2000)
    public void scheduleTaskWithFixedDelay() {
        logger.info("Fixed Delay Task :: Execution Time - {}", LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);  // 最终 7s 一次
        } catch (InterruptedException ex) {
            logger.error("Ran into an error {}", ex);
            throw new IllegalStateException(ex);
        }
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize){
        this.poolSize = poolSize;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }
}
