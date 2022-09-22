package com.ggz.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * redis 配置
 *
 * @author ggz on 2022/1/24
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//
//        //String类型，key序列器
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        //String类型，value序列器
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        //Hash类型，Hash序列器
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //String类型，key序列器
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        //String类型，value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //Hash类型，Hash序列器
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        //Hash类型，Value序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public DefaultRedisScript<Long> defaultRedisScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/releaseLock.lua")));
        return defaultRedisScript;
    }

}