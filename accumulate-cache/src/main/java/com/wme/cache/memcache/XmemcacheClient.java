package com.wme.cache.memcache;


import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.concurrent.TimeoutException;

/**
 * Created by Wangmingen on 2015/10/13.
 */
public interface XmemcacheClient {

    /***
     * 默认超时时间,单位:毫秒
     */
    static final long DEFAULT_TIMEOUT = 3000;

    /***
     * 默认缓存过期时间,1天，单位:秒
     */
    static final int DEFAULT_TIME = 24*3600;

    /***
     * 添加缓存 只有当不存在该key时，返回true
     * @param key
     * @param expire
     * @param obj
     */
    void add(String key, int expire, Object obj)throws TimeoutException, InterruptedException, MemcachedException;

    /***
     * Delete key's data item from memcached。This method doesn't wait for reply。
     * @param key
     */
    void delete(String key)throws TimeoutException, InterruptedException, MemcachedException;

    /***
     *
     */
    void set(int expire, String key, Object obj)throws TimeoutException, InterruptedException, MemcachedException;

    /***
     * Replace the key's data item in memcached,success only when the key's data item is exists in memcached.
     * This method will wait for reply from server.
     * @param expire
     * @param key
     * @param obj
     */
    void replace(int expire, String key, Object obj)throws TimeoutException, InterruptedException, MemcachedException;

    /**
     * 获取缓存中的对象
     * @param key
     * @param timeout 超时时间，单位：毫秒,如果timeout=0 则表示使用默认时间，默认时间是3秒
     * @return 返回null表示没有获得对象，需要查询数据库
     */
    Object get(String key, long timeout);

    /**
     * 清空缓存
     */
    void flushAll();

}
