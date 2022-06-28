package com.ggz.server.utils;

import ch.qos.logback.core.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * redis分布式锁工具类
 *
 * @author ggz on 2022/3/7
 */
@Component
public class RedisDistributedLockUtils {
    private static Lock lock;
    private static final Long expireTime = 10L;
    private static final Long RELEASE_SUCCESS = 1L;

    private final RedisTemplate redisTemplate;

    private final DefaultRedisScript defaultRedisScript;

    public RedisDistributedLockUtils(RedisTemplate redisTemplate, DefaultRedisScript defaultRedisScript) {
        this.redisTemplate = redisTemplate;
        this.defaultRedisScript = defaultRedisScript;
    }

    /**
     * 设置锁，如果已经存在则返回0
     * @param key 使用包名全路径可保持唯一
     * @param value
     * @return
     */
    public boolean lock(String key, String value) {
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, value, 60, TimeUnit.SECONDS);
        return absent;
    }

    /**
     * 解锁，如果lua脚本返回为1，则删除key成功，释放锁成功。
     *      如果lua脚本返回为0，则删除锁失败
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key,String value){
        Long result = (Long) redisTemplate.execute(defaultRedisScript, Arrays.asList(key,value));
        return RELEASE_SUCCESS.equals(result);
    }



}