package com.springboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 *     MybatisAutoConfiguration
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2019-12-16 21:18
 * @Copyright: 版权归 HSYUNTAI 所有
 */

/**
 * 这儿有个很好的列子：
 * https://github.com/ityouknow/spring-boot-examples/tree/master/spring-boot-mybatis
 */
@Configuration
@MapperScan("com.springboot.orm")  // 扫描Dao
public class MybatisConfig {
}
