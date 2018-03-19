package com.wme.rms.service;

import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.collect.CollectUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by ming on 2016/3/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/spring-context.xml"})
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class CollectUrlServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CollectUrlService collectUrlService;

    @Test
    @Rollback(value = false)
    public void testSave(){
        CollectUrl cu = new CollectUrl();
        cu.setCreated(new Date());
        cu.setLocalAddr("127.0.0.1");
        cu.setLoginName("test 测试");
        int save = collectUrlService.save(cu);
        logger.info(save+"");
    }

    @Test
    public void testPageList(){
        CollectUrl cu = new CollectUrl();
        cu.setCreated(new Date());
        cu.setLocalAddr("127.0.0.1");
        cu.setLoginName("test 测试");
        cu.setPageNo(1);
        cu.setPageSize(10);
        PaginationSupport<CollectUrl> collectUrls = collectUrlService.pageList(cu);
        logger.info(collectUrls.getPageCount()+"");
    }

}
