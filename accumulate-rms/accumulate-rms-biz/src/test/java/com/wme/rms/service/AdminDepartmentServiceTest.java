package com.wme.rms.service;

import com.wme.base.constants.StatusEnum;
import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.department.AdminDepartment;
import com.wme.rms.entity.department.AdminDepartmentSearch;
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
 * Created by ming on 2016/3/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/spring-context.xml"})
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class AdminDepartmentServiceTest {

    @Resource
    private AdminDepartmentService adminDepartmentService;

    @Test
    @Rollback(value = false)
    public void saveTest(){
        AdminDepartment department = new AdminDepartment();
        department.setCreated(new Date());
        department.setDepartmentName("测试department");
        department.setStatus(StatusEnum.VALID.getStatus());
        int save = adminDepartmentService.save(department);
        System.out.print(save);
    }

    @Test
    @Rollback(value = false)
    public void updateTest(){
        AdminDepartment department = new AdminDepartment();
        department.setDepartmentId(2L);
        department.setModifyed(new Date());
        department.setDepartmentName("测试department2");
        department.setStatus(StatusEnum.VALID.getStatus());
        int save = adminDepartmentService.updateByPrimaryKeySelective(department);
        System.out.print(save);
    }

    @Test
    @Rollback(value = false)
    public void deleteTest(){
        int save = adminDepartmentService.deleteDepartment(3L);
        System.out.print(save);
    }

    @Test
    public void pageListTest(){
        AdminDepartmentSearch search = new AdminDepartmentSearch();
        search.setPageNo(1);
        search.setPageSize(10);
        PaginationSupport<AdminDepartment> paginationSupport = adminDepartmentService.pageList(search);
        System.out.print(paginationSupport.getTotalCount());
    }



}
