package com.ace.smart.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis 操作类
 */
@Component
@SuppressWarnings("all")
public class RedisDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @desc 存储键值对
     * @author zzh
     * @date 2018/6/15 8:42
     * @param
     * @return
     */
    public void setValue(Object key,Object value){
         redisTemplate.opsForValue().set(key,value);
    }

    /**
     * @desc 存储键值对 设置过期时间
     * @author zzh
     * @date 2018/6/15 8:42
     * @param  time 设置过期时间  TimeUnit 设置时间单位 s
     * @return
     */
    public void setValue(Object key, Object value, int time){
        redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
    }

    /**
     * @desc 取出值
     * @author zzh
     * @date 2018/6/15 8:42
     * @param   
     * @return 
     */
    public Object getValue(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @desc 判断键是否存在
     * @author zzh
     * @date 2018/6/15 9:26
     * @param
     * @return
     */
    public boolean isExistsKey(Object key){
        return redisTemplate.hasKey(key);
    }

    /**
     * @desc 删除对应的value
     * @author zzh
     * @date 2018/6/15 9:07
     * @param
     * @return
     */
    public void delete(Object key){
        if (isExistsKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * @desc 批量删除键值对
     * @author zzh
     * @date 2018/6/15 9:07
     * @param
     * @return
     */
    public void delete(Object[] key){
        for (Object object:key) {
            delete(object);
        }
    }

    /**
     * 向List中存储数据
     * @param key
     * @param value
     */
    public boolean push(Object key,Object value){
        if (isExistsList(key)) {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        }
        return false;
    }

    /**
     * @desc 获得list中的值
     * @author zzh
     * @date 2018/6/15 9:34
     * @param
     * @return
     */
    public List<Object> getList(Object key,int start,int end){
        if (isExistsList(key)) {
            return redisTemplate.opsForList().range(key,start,end);
        }
        return null;
    }

    /**
     * @desc 获得list中的所有值
     * @author zzh
     * @date 2018/6/15 9:34
     * @param
     * @return
     */
    public List<Object> getListAll(Object key){
        return getList(key,0,-1);
    }

    public boolean isExistsList(Object key) {
        return redisTemplate.opsForList().getOperations().hasKey(key);
    }
}
