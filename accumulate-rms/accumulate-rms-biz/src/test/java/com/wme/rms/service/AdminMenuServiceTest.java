package com.wme.rms.service;

import com.wme.base.constants.StatusEnum;
import com.wme.rms.entity.menu.AdminMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 * Created by ming on 2016/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/spring-context.xml"})
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class AdminMenuServiceTest {


    @Resource
    private AdminMenuService adminMenuService;

    @Test
    @Rollback(value = false)
    public void saveTest(){
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setParentId(0L);
        adminMenu.setMenuLevel(1);
        adminMenu.setCode("test");
        adminMenu.setMenuLink("www.baidu.com");
        adminMenu.setMenuName("百度搜索");
        adminMenu.setCreated(new Date());
        adminMenu.setStatus(StatusEnum.VALID.getStatus());
        int save = adminMenuService.save(adminMenu);
        System.out.print(save);
    }

}
