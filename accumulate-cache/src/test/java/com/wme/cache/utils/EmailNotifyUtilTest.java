package com.wme.cache.utils;

import com.wme.cache.domain.EmailSendInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Wangmingen on 2015/9/11.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/spring-context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class EmailNotifyUtilTest {


    @Resource
    private EmailNotifyUtil emailNotifyUtil;

    @Test
    public void testHandleNotify(){
        EmailSendInfo info = new EmailSendInfo();
        info.setMessage("email testHandleNotify");
        info.setSubject("subject");
        info.setUserAddress("wangme@t3.com.cn");
        info.setUserName("wangme");
        emailNotifyUtil.handleNotify(info);
    }

    @Test
    public void testGetHandleNotify(){
        EmailSendInfo info = emailNotifyUtil.getHandleNotify();
        assertNotNull(info);
    }

}
