package com.springboot.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class LogApplicationEvent  implements ApplicationListener<ApplicationEvent>, Ordered {


    private static final Logger logger = LoggerFactory.getLogger(LogApplicationEvent.class);

   // @EventListener(value = ApplicationEvent.class)
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info("ApplicationEvent -> " + event.toString());
        if (event instanceof ContextRefreshedEvent) {
            logger.info("spring容器初始化完毕================================================888");
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
