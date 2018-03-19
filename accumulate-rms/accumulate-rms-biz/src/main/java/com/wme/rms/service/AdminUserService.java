package com.wme.rms.service;

import com.wme.base.service.BaseService;
import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.user.AdminUser;
import com.wme.rms.entity.user.AdminUserSearch;
import com.wme.rms.persistence.mysql.AdminUserMapper;

import java.util.List;

/**
 * Created by ming on 2016/4/3.
 */
public interface AdminUserService extends BaseService<AdminUserMapper,AdminUser> {

    PaginationSupport<AdminUser> findUsersByPage(AdminUserSearch search);

    void changeUserStatus(Long userId,Integer status);

    AdminUser getAdminUserInfo(String userName);

    void addAdminUser(AdminUser user,String roleIds);

    void editAdminUser(AdminUser user,String oldRoleIds,String roleIds);

    boolean isExistUser(AdminUser user);

    AdminUser getAdminUser(Long userId);

    void deleteUser(Long userId);

}
