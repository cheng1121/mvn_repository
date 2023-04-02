package com.cheng.framework.config;

import com.cheng.framework.service.RedisService;
import com.cheng.framework.service.impl.RedisServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @time: 2022/5/18 4:39 下午
 * @author: licheng
 * @desc: redis基础配置
 */
@Configuration
public class BaseRedisConfig {

//
//
//    @Bean
//    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
//        RedisSerializer<Object>  serializer = redisSerializer();
//        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(serializer);
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(serializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//
//
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//        //设置Redis缓存有效期为1天
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer())).entryTtl(Duration.ofDays(1));
//         return new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
//    }
//
//    @Bean
//    public RedisSerializer<Object> redisSerializer(){
//        //创建json序列化器
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        //必须设置，否则无法将JSON转化为对象，会转为Map对象
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(objectMapper);
//        return serializer;
//    }

    @Bean
    public RedisService redisService(){
        return new RedisServiceImpl();
    }

}
