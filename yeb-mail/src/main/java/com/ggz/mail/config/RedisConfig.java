package com.ggz.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 配置防止乱码
 *
 * @author ggz on 2022/2/15
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//
//        //String类型，Hash序列器
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        //String类型，value序列器
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        //Hash类型，Hash序列器
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        //Hash类型，Value序列器
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        RedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public DefaultRedisScript<Long> defaultRedisScript(){
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/releaseLock.lua")));
        return defaultRedisScript;
    }

}