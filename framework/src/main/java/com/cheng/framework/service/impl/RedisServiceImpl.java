package com.cheng.framework.service.impl;

import com.cheng.framework.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @time: 2022/5/18 10:17 上午
 * @author: licheng
 * @desc:
 */
@Service
public class RedisServiceImpl implements RedisService {

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void set(String key, String value) {
//        stringRedisTemplate.opsForValue().set(key,value);
    }

    @Override
    public String get(String key) {
//        return stringRedisTemplate.opsForValue().get(key);
        return null;
    }

    @Override
    public boolean expire(String key, long expire) {
//        return Boolean.TRUE.equals(stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS));
        return false;
    }

    @Override
    public void remove(String key) {
//       stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
//        return stringRedisTemplate.opsForValue().increment(key,delta);
        return null;
    }
}
