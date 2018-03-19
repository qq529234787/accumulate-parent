package com.wme.rms.persistence.mysql;

import com.wme.base.persistance.BaseMapper;
import com.wme.rms.entity.user.AdminUser;

import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    List<AdminUser> selectUser(AdminUser user);

    int insertSelective(AdminUser record);

    int updateByPrimaryKeySelective(AdminUser record);


}
