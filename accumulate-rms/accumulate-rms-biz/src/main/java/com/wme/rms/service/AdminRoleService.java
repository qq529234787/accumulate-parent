package com.wme.rms.service;

import com.wme.base.service.BaseService;
import com.wme.base.utils.PaginationSupport;
import com.wme.base.utils.ZTreeJson;
import com.wme.rms.entity.role.AdminRole;
import com.wme.rms.entity.role.AdminRoleSearch;
import com.wme.rms.persistence.mysql.AdminRoleMapper;

import java.util.List;

/**
 * Created by ming on 2016/4/2.
 */
public interface AdminRoleService extends BaseService<AdminRoleMapper,AdminRole> {

    PaginationSupport<AdminRole> findUsersByPage(AdminRoleSearch search);

    void deleteRole(Long roleId);

    void addAdminRole(AdminRole role,String menuIds);

    void editAdminRole(AdminRole role,String oldMenuIds,String menuIds);

    boolean isExistRole(AdminRole role);

    AdminRole getAdminRole(Long roleId);

    List<ZTreeJson> findAllMenu();

    List<ZTreeJson> findCheckedMenu(Long roleId);

    List<AdminRole> getAllAdminRole();

    List<AdminRole> getRoleByUserId(Long userId);

    boolean isContainUser(Long roleId);
}
