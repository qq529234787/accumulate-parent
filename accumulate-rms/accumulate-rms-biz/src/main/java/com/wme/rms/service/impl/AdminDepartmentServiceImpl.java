package com.wme.rms.service.impl;

import com.wme.base.constants.StatusEnum;
import com.wme.base.service.impl.BaseServiceImpl;
import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.department.AdminDepartment;
import com.wme.rms.entity.department.AdminDepartmentSearch;
import com.wme.rms.entity.user.AdminUser;
import com.wme.rms.persistence.mysql.AdminDepartmentMapper;
import com.wme.rms.persistence.mysql.AdminUserMapper;
import com.wme.rms.service.AdminDepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
@Service(value = "adminDepartmentService")
public class AdminDepartmentServiceImpl extends BaseServiceImpl<AdminDepartmentMapper,AdminDepartment> implements AdminDepartmentService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public PaginationSupport<AdminDepartment> findDepartmentByPage(AdminDepartmentSearch search) {
        return super.pageList(search);
    }

    @Override
    public int addAdminDepartment(AdminDepartment department) {
        return mapper.insert(department);
    }

    @Override
    public int updateByPrimaryKeySelective(AdminDepartment department) {
        return mapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public boolean isExistDepartment(AdminDepartment department) {
        List<AdminDepartment> list = mapper.selectDepartment(department);
        return list != null && list.size() > 0;
    }

    @Override
    public List<AdminDepartment> findAllDepartment() {
        AdminDepartment department = new AdminDepartment();
        department.setStatus(StatusEnum.VALID.getStatus());
        return mapper.selectDepartment(department);
    }

    @Override
    public boolean isContainUser(Long departmentId) {
        AdminUser user = new AdminUser();
        user.setDepartmentId(departmentId);
        List<AdminUser> adminUsers = adminUserMapper.selectUser(user);
        return adminUsers != null && adminUsers.size()>0;
    }

    @Override
    public int deleteDepartment(Long departmentId) {
        AdminDepartment department = new AdminDepartment();
        department.setDepartmentId(departmentId);
        department.setStatus(StatusEnum.IN_VALID.getStatus());
        return mapper.updateByPrimaryKeySelective(department);
    }
}
