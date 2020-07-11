package com.zsy.admin.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: Hr
 * @description:
 * @author: 张思远
 * @create: 2020-03-24 15:20
 */
@Component
@Data
public class RedisUtil {
    @Autowired
    @Qualifier("Redis")
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object value, long i) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value,i, TimeUnit.SECONDS);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public Boolean del(String key){
        return  redisTemplate.delete(key);
    }
}
