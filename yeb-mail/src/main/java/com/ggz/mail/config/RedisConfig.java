package com.ggz.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);

        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

}