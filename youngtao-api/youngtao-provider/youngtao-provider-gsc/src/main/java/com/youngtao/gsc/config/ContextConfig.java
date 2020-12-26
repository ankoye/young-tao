package com.youngtao.gsc.config;

import com.youngtao.web.cache.DCacheManager;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.log.GlobalLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Configuration
public class ContextConfig {

    @Bean
    public GlobalLogAspect globalLogAspect() {
        return new GlobalLogAspect();
    }

    @Bean
    public RedisManager<String> redisManager(RedisTemplate redisTemplate) {
        return new RedisManager<>(redisTemplate);
    }

    @Bean
    public DCacheManager<String> doubleCache(RedisTemplate redisTemplate) {
        return new DCacheManager<>(redisTemplate);
    }
}
