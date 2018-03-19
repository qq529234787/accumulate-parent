package com.wme.rms.persistence.mysql;

import com.wme.base.persistance.BaseMapper;
import com.wme.rms.entity.department.AdminDepartment;

import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
public interface AdminDepartmentMapper extends BaseMapper<AdminDepartment> {

    List<AdminDepartment> selectDepartment(AdminDepartment department);

    int insertSelective(AdminDepartment record);

    int updateByPrimaryKeySelective(AdminDepartment record);

}
