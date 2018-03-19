package com.wme.rms.service;

import com.wme.rms.service.DualService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

//import static org.junit.Assert.assertNotNull;

/**
 * Created by Wangmingen on 2015/9/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/spring-context.xml"})
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class DualServiceTest {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private DualService dualService;

    @Test
    public void testGetByItemId(){
        Date systemTime = dualService.getSystemTime();
        logger.info(systemTime+"");
    }

}
