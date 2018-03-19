package com.wme.rms.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.wme.base.constants.StatusEnum;
import com.wme.base.service.impl.BaseServiceImpl;
import com.wme.base.utils.PaginationSupport;
import com.wme.base.utils.ZTreeJson;
import com.wme.rms.entity.role.AdminPropertyRole;
import com.wme.rms.entity.role.AdminRole;
import com.wme.rms.entity.role.AdminRoleSearch;
import com.wme.rms.entity.user.AdminUserRole;
import com.wme.rms.persistence.mysql.AdminMenuMapper;
import com.wme.rms.persistence.mysql.AdminPropertyRoleMapper;
import com.wme.rms.persistence.mysql.AdminRoleMapper;
import com.wme.rms.persistence.mysql.AdminUserRoleMapper;
import com.wme.rms.service.AdminRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ming on 2016/4/2.
 */
@Service(value = "adminRoleService")
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRoleMapper,AdminRole> implements AdminRoleService {

    @Resource
    private AdminPropertyRoleMapper adminPropertyRoleMapper;
    @Resource
    private AdminMenuMapper adminMenuMapper;
    @Resource
    private AdminUserRoleMapper adminUserRoleMapper;

    @Override
    public PaginationSupport<AdminRole> findUsersByPage(AdminRoleSearch search) {
        PaginationSupport<AdminRole> page = this.pageList(search);
        return page;
    }

    @Override
    public void deleteRole(Long roleId) {
        // 删除角色
        AdminRole role = new AdminRole();
        role.setRoleId(roleId);
        role.setStatus(StatusEnum.IN_VALID.getStatus());
        mapper.updateByPrimaryKeySelective(role);

        // 删除角色和权限的关系
        deleteRoleMenu(roleId);
    }

    @Override
    public void addAdminRole(AdminRole role, String menuIds) {
        // 保存角色
        role.setStatus(StatusEnum.VALID.getStatus());
        role.setCreated(new Date());
        mapper.insert(role);
        saveRoleMenu(role.getRoleId(),convertList(menuIds));
    }
    /**
     * 保存角色和权限的关系
     * @param roleId
     * @param menuIds
     */
    private void saveRoleMenu(Long roleId,List<Long> menuIds) {
        List<AdminPropertyRole> roleMenuList = new ArrayList<AdminPropertyRole>();
        for (Long menuId : menuIds) {
            AdminPropertyRole roleMenu = new AdminPropertyRole();
            roleMenu.setRoleId(roleId);
            roleMenu.setPropertyId(menuId);
            roleMenu.setStatus(StatusEnum.VALID.getStatus());
            roleMenu.setCreated(new Date());
            roleMenuList.add(roleMenu);
        }
        if (CollectionUtils.isNotEmpty(roleMenuList)) {
            adminPropertyRoleMapper.batchInsert(roleMenuList);
        }
    }
    /**
     * 删除角色和权限的关系
     * @param roleId
     */
    private void deleteRoleMenu(Long roleId) {
        AdminPropertyRole roleMenu = new AdminPropertyRole();
        roleMenu.setRoleId(roleId);
        roleMenu.setStatus(StatusEnum.IN_VALID.getStatus());
        roleMenu.setModifyed(new Date());
        adminPropertyRoleMapper.deleteByRoleId(roleMenu);
    }

    @Override
    public void editAdminRole(AdminRole role, String oldMenuIds, String menuIds) {
        role.setModifyed(new Date());
        mapper.updateByPrimaryKeySelective(role);
        if (isChange(oldMenuIds,menuIds)) {
            // 删除角色和权限的旧关系
            deleteRoleMenu(role.getRoleId());

            // 保存角色和权限的新关系
            saveRoleMenu(role.getRoleId(),convertList(menuIds));
        }
    }

    @Override
    public boolean isExistRole(AdminRole role) {
        role.setStatus(StatusEnum.VALID.getStatus());
        List<AdminRole> adminRoles = mapper.selectRole(role);
        return adminRoles != null && adminRoles.size() > 0;
    }

    @Override
    public AdminRole getAdminRole(Long roleId) {
        return mapper.get(roleId);
    }

    @Override
    public List<ZTreeJson> findAllMenu() {
        return adminMenuMapper.selectAllMenu();
    }

    @Override
    public List<ZTreeJson> findCheckedMenu(Long roleId) {
        return adminPropertyRoleMapper.selectCheckedMenu(roleId);
    }

    @Override
    public List<AdminRole> getAllAdminRole() {
        AdminRole role = new AdminRole();
        role.setStatus(StatusEnum.VALID.getStatus());
        return mapper.selectRole(role);
    }

    @Override
    public List<AdminRole> getRoleByUserId(Long userId) {
        return mapper.selectRoleByUserId(userId);
    }

    @Override
    public boolean isContainUser(Long roleId) {
        AdminUserRole userRole = new AdminUserRole();
        userRole.setRoleId(roleId);
        List<AdminUserRole> userRoles = adminUserRoleMapper.selectUserRole(userRole);
        return CollectionUtils.isNotEmpty(userRoles);
    }

    private List<Long> convertList(String menuIds) {
        List<Long> menuIdList = new ArrayList<Long>();
        if (StringUtils.isNotBlank(menuIds)) {
            for (String menuId : menuIds.split(",")) {
                menuIdList.add(Long.valueOf(menuId));
            }
        }
        return menuIdList;
    }

    private boolean isChange(String oldMenuIds, String menuIds) {
        List<Long> oldList = convertList(oldMenuIds);
        List<Long> newList = convertList(menuIds);
        return !(oldList.containsAll(newList) && newList.containsAll(oldList));
    }

}
