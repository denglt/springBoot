package com.springboot.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/11/2 9:25 AM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class CacheConfig {


    /**
     * 设置CacheManager属性
     *
     * @return
     */
    @Bean
    public CacheManagerCustomizer<CacheManager> cacheManagerCustomizer() {
        return cacheManager -> {
            System.out.println("Spring Use CacheManager ->" + cacheManager);
            if (cacheManager instanceof ConcurrentMapCacheManager) {
                ((ConcurrentMapCacheManager) cacheManager).setAllowNullValues(false);
            }

            if (cacheManager instanceof RedisCacheManager){

            }
        };
    }
}
