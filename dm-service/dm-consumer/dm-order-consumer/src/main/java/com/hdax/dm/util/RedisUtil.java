package com.hdax.dm.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, String value){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(key,value);
    }

    public void delete(String key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        connection.del(stringRedisSerializer.serialize(key));
    }

    public Object get(String key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate.opsForValue().get(key);
    }


    public Boolean setnx(final String key, final String value) throws Exception {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                boolean flag = false;
                try {
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
                    byte[] keys = stringRedisSerializer.serialize(key);
                    byte[] values = stringRedisSerializer.serialize(value);
                    flag = connection.setNX(keys, values);
                    return flag;
                }catch (Exception e){
                    return flag;
                }
            }
        });
    }

    public void unlock(String key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        connection.del(stringRedisSerializer.serialize(key));
    }

    public boolean lock(String key){
        boolean flag = false;
        try{
            flag = setnx(key,"lock");
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
