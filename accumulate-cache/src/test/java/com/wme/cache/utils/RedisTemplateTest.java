package com.wme.cache.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by baowp on 15-7-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/spring-context.xml"})
public class RedisTemplateTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testValueOperation() {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String value=valueOps.get("foo2");
        System.out.println(value);
        valueOps.set("foo2", "bar3");
        value = valueOps.get("foo2");
        assertEquals("bar3", value);
    }

    @Test
    public void testListOperation() {
        ListOperations brandListOps = redisTemplate.opsForList();
        List<Long> couponIds = brandListOps.range("100001", 0, 100000);
        for (int i=0;i<couponIds.size();i++){
            Long id = (Long)brandListOps.leftPop("100001");
        }

    }


}
