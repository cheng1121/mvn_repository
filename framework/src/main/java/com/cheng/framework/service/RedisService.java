package com.cheng.framework.service;

/**
 * @author:  licheng
 * @time:   2022/5/18 10:14 上午
 * @desc:   常用Redis操作
 *        对象和数据都以json形式进行存储
 */
public interface RedisService {

    /**
     *
     *
     */
    /**
     * 存储数据
     * @param key
     * @param value
     */
    void set(String key,String value);

    /**
     * 读取数据
     * @param key
     */
    String get(String key);

    /**
     * 设置过期时间
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key,long expire);

    /**
     * 删除数据
     * @param key
     */
    void remove(String key);

    /**
     * 自增操作
     * @param key
     * @param delta 自增步长
     * @return
     */
    Long increment(String key,long delta);



}
