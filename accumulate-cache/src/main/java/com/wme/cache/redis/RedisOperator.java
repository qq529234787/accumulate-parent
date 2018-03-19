package com.wme.cache.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 3/4/14
 * Time: 9:09 AM
 */
public class RedisOperator {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 更新列表
     *
     * @param key
     * @param list
     */
    @SuppressWarnings("unchecked")
    public <T> void updateListByTransactions(final String key, final List<T> list) {
        redisTemplate.execute(new SessionCallback<Object>() {
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                redisTemplate.delete(key);
                ListOperations<String, T> listOps = operations.opsForList();
                for (T outT : list) {
                    listOps.leftPush(key, outT);
                }
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 更新Map
     *
     * @param key
     * @param map
     */
    @SuppressWarnings("unchecked")
    public <T> void updateMapByTransactions(final String key, final Map<Object, Object> map) {
        redisTemplate.execute(new SessionCallback<Object>() {
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                redisTemplate.delete(key);
                HashOperations<String, Object, Object> hashOps = operations.opsForHash();
                hashOps.putAll(key, map);
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 查询list
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> selectList(String key) {
        ListOperations<String, T> listOps = redisTemplate.opsForList();
        return listOps.range(key, 0, listOps.size(key) - 1);
    }

    /**
     * 查询map
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<Object, Object> selectMap(String key) {
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(key);
    }

    /**
     * 查询map中的数据
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object selectMap(String mapKey, String key) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.get(mapKey, key);
    }

    @SuppressWarnings("unchecked")
    public <E> ListOperations<String, E> listOps() {
        return redisTemplate.opsForList();
    }

    @SuppressWarnings("unchecked")
    public <K, V> HashOperations<String, K, V> hashOps() {
        // todo construct
        return redisTemplate.opsForHash();
    }

    @SuppressWarnings("unchecked")
    public <T> ValueOperations<String, T> valueOps() {
        return redisTemplate.opsForValue();
    }

    public <K> Boolean expire(K redisKey,long seconds){
        return redisTemplate.expire(redisKey,seconds, TimeUnit.SECONDS);
    }

    public RedisTemplate redisTemplate(){
        return redisTemplate;
    }
}
