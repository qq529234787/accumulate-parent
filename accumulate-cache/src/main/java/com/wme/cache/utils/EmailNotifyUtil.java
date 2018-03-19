package com.wme.cache.utils;

import com.wme.cache.domain.EmailSendInfo;
import com.wme.cache.redis.JedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

@Component("emailNotifyUtil")
public class EmailNotifyUtil {

	private static final Logger logger = LoggerFactory.getLogger(EmailNotifyUtil.class);

	@Resource(name="emailJedisClient")
	private JedisClient emailJedisClient;

	/**
	 * 竞争使用的资源
	 */
	public void handleNotify(EmailSendInfo email) {
		Jedis jedis = null;
		try {
			jedis = emailJedisClient.getJedis();
			jedis.lpush(JedisClient.w("handle:email"), JedisClient.w(email));
			emailJedisClient.returnResource(jedis);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (jedis != null)
				emailJedisClient.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * 获取竞争资源
	 */
	public EmailSendInfo getHandleNotify() {
		Jedis jedis = null;
		try {
			jedis = emailJedisClient.getJedis();
			List<byte[]> list = jedis.brpop(30, JedisClient.w("handle:email"));
			emailJedisClient.returnResource(jedis);
			if (list != null && list.size() > 1) {
				return JedisClient.u(list.get(1));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (jedis != null)
				emailJedisClient.returnBrokenResource(jedis);
		}
		return null;
	}
	
	/**
	 * 忽略通知
	 * @return
	 */
	public boolean ignoreNotify() {
		Jedis jedis = null;
		try {
			jedis = emailJedisClient.getJedis();
			boolean exists = jedis.exists("ignore:email");
			emailJedisClient.returnResource(jedis);
			return exists;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (jedis != null)
				emailJedisClient.returnBrokenResource(jedis);
		}
		return true;
	}
	
}
