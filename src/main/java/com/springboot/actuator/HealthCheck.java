package com.springboot.actuator;

import com.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Package: com.springboot.actuator
 * @Author: denglt
 * @Date: 2018/9/18 下午2:43
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@Component
public class HealthCheck implements HealthIndicator {

    @Autowired
    UserServiceImpl userService;

    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
                    .withDetail("Error Code", errorCode).build();
        }
        return Health.status(Status.UP)
                .withDetail("usercount", userService.getUsers().size())
                .withDetail("author", "denglt")
                .build();
    }

    public int check() {
        // Our logic to check health
        return 0;
    }
}