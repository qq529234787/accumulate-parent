package com.wme.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;

/**
 * Created by Wangmingen on 2015/9/11.
 */

public class JedisClient {

    private static final Logger logger = LoggerFactory.getLogger(JedisClient.class);

    private JedisPool jedisPool;

    private Integer db = 0;

    /**
     * 获取Jedis对象
     * @return
     */
    public Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        jedis.select(db);
        return jedis;
    }

    /**
     * 放回Jedis对象
     * @param jedis
     */
    public void returnResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    public void returnBrokenResource(Jedis jedis) {
        jedisPool.returnBrokenResource(jedis);
    }

    /**
     * 将Serializable对象通过流写为字节数组
     * @param obj
     * @return
     */
    public static byte[] w(Serializable obj) {
        if(obj == null){
            return null;
        }
        if(obj instanceof String){
            return ((String) obj).getBytes();
        }
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if(baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 将字节数组通过流写为Serializable对象
     * @param bytes
     * @return
     */
    public static <T> T u(byte[] bytes){
        if(bytes == null){
            return null;
        }
        ObjectInputStream ois = null;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if(bais != null){
                try {
                    bais.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setDb(Integer db) {
        this.db = db;
    }
}
