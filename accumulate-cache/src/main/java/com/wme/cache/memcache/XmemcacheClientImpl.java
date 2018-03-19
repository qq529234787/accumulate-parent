package com.wme.cache.memcache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Wangmingen on 2015/10/13.
 */

@Component(value = "xmemcacheClient")
public class XmemcacheClientImpl implements XmemcacheClient {

    private final static Logger logger = LoggerFactory.getLogger(XmemcacheClientImpl.class);

    @Resource(name = "memcachedClientList")
    private List<MemcachedClient> memcachedClientList;

    /***
     * 添加缓存 只有当不存在该key时，返回true
     * @param key
     * @param expire
     * @param obj
     */
    @Override
    public void add(String key, int expire, Object obj) throws TimeoutException, InterruptedException, MemcachedException {
        if(expire <= 0){
            expire = DEFAULT_TIME;
        }
        for (MemcachedClient client : memcachedClientList){
            client.add(key,expire,obj);
        }
    }

    /***
     * Delete key's data item from memcached。This method doesn't wait for reply。
     * @param key
     */
    @Override
    public void delete(String key) throws TimeoutException, InterruptedException, MemcachedException {
        for (MemcachedClient client : memcachedClientList){
            client.deleteWithNoReply(key);
        }
    }

    /***
     *
     * @param expire
     * @param key
     * @param obj
     */
    @Override
    public void set(int expire, String key, Object obj) throws TimeoutException, InterruptedException, MemcachedException {
        if(expire <=0){
            expire = DEFAULT_TIME;
        }
        for(MemcachedClient client : memcachedClientList){
            client.set(key, expire, obj);
        }
    }

    /***
     * Replace the key's data item in memcached,success only when the key's data item is exists in memcached.
     * This method will wait for reply from server.
     * @param expire
     * @param key
     * @param obj
     */
    @Override
    public void replace(int expire, String key, Object obj) throws TimeoutException, InterruptedException, MemcachedException {
        if(expire <=0){
            expire = DEFAULT_TIME;
        }
        for(MemcachedClient client : memcachedClientList){
            client.replace(key, expire, obj);
        }
    }

    /**
     * 获取缓存中的对象
     *
     * @param key
     * @param timeout 超时时间，单位：毫秒,如果timeout=0 则表示使用默认时间，默认时间是3秒
     * @return 返回null表示没有获得对象，需要查询数据库
     */
    @Override
    public Object get(String key, long timeout) {
        Object obj = null;
        if(timeout<=0){
            timeout = DEFAULT_TIMEOUT;
        }
        for(MemcachedClient client : memcachedClientList){
            try {
                obj = client.get(key,timeout);
            } catch (TimeoutException e) {
                logger.error(client.getName()+":"+e.getMessage(), e);
            } catch (InterruptedException e) {
                logger.error(client.getName()+":"+e.getMessage(), e);
            } catch (MemcachedException e) {
                logger.error(client.getName()+":"+e.getMessage(), e);
            }finally{
                if(obj!=null){
                    break;
                }
            }
        }
        return obj;
    }

    /**
     * 清空缓存
     * Make All connected memcached's data item invalid。This method doesn't wait for reply。
     */
    @Override
    public void flushAll() {
        for (MemcachedClient client : memcachedClientList){
            try {
                client.flushAllWithNoReply();
            } catch (InterruptedException e) {
                logger.error(client.getName()+":"+e.getMessage(), e);
            } catch (MemcachedException e) {
                logger.error(client.getName() + ":"+e.getMessage(), e);
            }
        }
    }
}
