package com.xiupeilian.carpart.Cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * @Description: cache µœ÷¿‡
 * @Author: Tu Xu
 * @CreateDate: 2019/8/26 16:37
 * @Version: 1.0
 **/
public class RedisCache implements Cache {

    private RedisTemplate<String, Object> redisTemplate;
    private String name;
    /**
     * @Description: «Âø’ª∫¥Ê
     * @Author:      Administrator
     * @Param:       []
     * @Return       void
     **/
    @Override
    public void clear() {
        System.out.println("-------clear cache-----");
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
    /**
     * @Description: …æ≥˝ª∫¥Ê
     * @Author:      Administrator
     * @Param:       [key]
     * @Return       void
     **/
    @Override
    public void evict(Object key) {
        System.out.println("-------remove cache------");
        final String keyf=key.toString();
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(keyf.getBytes());
            }

        });

    }
    /**
     * @Description: √¸÷–ª∫¥Ê »°ª∫¥Ê
     * @Author:      Administrator
     * @Param:       [key]
     * @Return       org.springframework.cache.Cache.ValueWrapper
     **/
    @Override
    public ValueWrapper get(Object key) {
        System.out.println("------get cache-------"+key.toString());
        final String keyf = key.toString();
        Object object = null;
        object = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    System.out.println("------hit no cache-------");
                    return null;
                }
                return SerializationUtils.deserialize(value);
            }
        });
        ValueWrapper obj=(object != null ? new SimpleValueWrapper(object) : null);
        System.out.println("------hit cache-------"+obj);
        return  obj;
    }
    /**
     * @Description: ¥Êª∫¥Ê
     * @Author:      Administrator
     * @Param:       [key, value]
     * @Return       void
     **/
    @Override
    public void put(Object key, Object value) {
        System.out.println("-------add cache------");
        System.out.println("key----:"+key);
        System.out.println("key----:"+value);
        final String keyString = key.toString();
        final Object valuef = value;
        final long liveTime = 86400;
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = keyString.getBytes();
                byte[] valueb = SerializationUtils.serialize((Serializable) valuef);
                connection.set(keyb, valueb);
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                return 1L;
            }
        });

    }

    @Override
    public <T> T get(Object arg0, Class<T> arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }
}

