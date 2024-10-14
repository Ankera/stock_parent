package com.ankers.stock.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 定义redisTemplate的配置
 */
@Configuration
public class CacheConfig {

    @Bean
    public RedisTemplate restTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        //定义模板对象
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置序列化方式
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //替换原有的jdk序列化方式
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);

        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);

        //初始化设置
        redisTemplate.afterPropertiesSet();
        return  redisTemplate;
    }

    @Bean
    public Cache<String, Object> caffeineCache() {
        Cache<String, Object> cache = Caffeine
                .newBuilder()
                .maximumSize(200)
                .initialCapacity(100)
                .recordStats()
                .build();
        return cache;
    }
}
