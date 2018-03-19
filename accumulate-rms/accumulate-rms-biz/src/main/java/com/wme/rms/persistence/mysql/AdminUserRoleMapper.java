package com.wme.rms.persistence.mysql;

import com.wme.base.persistance.BaseMapper;
import com.wme.rms.entity.user.AdminUserRole;

import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
public interface AdminUserRoleMapper extends BaseMapper<AdminUserRole> {

    int batchInsert(List<AdminUserRole> userRoleList);

    int insertSelective(AdminUserRole record);

    int updateByPrimaryKeySelective(AdminUserRole record);

    int deleteByUserId(AdminUserRole userRole);

    List<AdminUserRole> selectUserRole(AdminUserRole userRole);

}
