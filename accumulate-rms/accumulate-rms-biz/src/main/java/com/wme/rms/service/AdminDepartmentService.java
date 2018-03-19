package com.wme.rms.service;

import com.wme.base.service.BaseService;
import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.department.AdminDepartment;
import com.wme.rms.entity.department.AdminDepartmentSearch;
import com.wme.rms.persistence.mysql.AdminDepartmentMapper;

import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
public interface AdminDepartmentService extends BaseService<AdminDepartmentMapper,AdminDepartment> {

    PaginationSupport<AdminDepartment> findDepartmentByPage(AdminDepartmentSearch search);

    int addAdminDepartment(AdminDepartment department);

    int updateByPrimaryKeySelective(AdminDepartment department);

    boolean isExistDepartment(AdminDepartment department);

    List<AdminDepartment> findAllDepartment();

    boolean isContainUser(Long departmentId);

    int deleteDepartment(Long departmentId);
}
