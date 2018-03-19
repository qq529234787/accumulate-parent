package com.wme.rms.persistence.mysql;

import com.wme.base.persistance.BaseMapper;
import com.wme.base.utils.ZTreeJson;
import com.wme.rms.entity.role.AdminPropertyRole;

import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
public interface AdminPropertyRoleMapper extends BaseMapper<AdminPropertyRole> {

    int insertSelective(AdminPropertyRole record);

    int updateByPrimaryKeySelective(AdminPropertyRole record);

    int batchInsert(List<AdminPropertyRole> roleMenuList);

    int deleteByRoleId(AdminPropertyRole roleMenu);

    List<ZTreeJson> selectCheckedMenu(Long roleId);
}
