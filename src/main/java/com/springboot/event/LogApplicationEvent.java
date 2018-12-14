package com.springboot.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LogApplicationEvent  implements ApplicationListener<ApplicationEvent> {


    private static final Logger logger = LoggerFactory.getLogger(LogApplicationEvent.class);

    @EventListener(value = ApplicationEvent.class)
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info("ApplicationEvent -> " + event.toString());
        if (event instanceof ContextRefreshedEvent) {
            logger.info("spring容易初始化完毕================================================888");
        }
    }
}
