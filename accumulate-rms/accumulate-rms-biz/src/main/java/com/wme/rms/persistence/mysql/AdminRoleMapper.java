package com.wme.rms.persistence.mysql;

import com.wme.base.persistance.BaseMapper;
import com.wme.rms.entity.role.AdminRole;

import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {


    List<AdminRole> selectRoleByUserId(Long userId);

    List<AdminRole> selectRole(AdminRole role);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    int updateByPrimaryKeySelective(AdminRole record);

}
