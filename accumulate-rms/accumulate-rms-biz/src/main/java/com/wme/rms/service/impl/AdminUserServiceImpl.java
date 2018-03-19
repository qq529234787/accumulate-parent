package com.wme.rms.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.wme.base.constants.StatusEnum;
import com.wme.base.service.impl.BaseServiceImpl;
import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.user.AdminUser;
import com.wme.rms.entity.user.AdminUserRole;
import com.wme.rms.entity.user.AdminUserSearch;
import com.wme.rms.persistence.mysql.AdminUserMapper;
import com.wme.rms.persistence.mysql.AdminUserRoleMapper;
import com.wme.rms.service.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ming on 2016/4/3.
 */
@Service(value = "adminUserService")
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUserMapper,AdminUser> implements AdminUserService {

    @Resource
    private AdminUserRoleMapper adminUserRoleMapper;

    @Override
    public PaginationSupport<AdminUser> findUsersByPage(AdminUserSearch search) {
        return this.pageList(search);
    }

    @Override
    public void changeUserStatus(Long userId, Integer status) {
        AdminUser user = new AdminUser();
        user.setUserId(userId);
        user.setStatus(status);
        user.setModifyed(new Date());
        mapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public AdminUser getAdminUserInfo(String userName) {
        AdminUser user = new AdminUser();
        user.setLoginName(userName);
        List<AdminUser> userList = mapper.selectUser(user);
        return userList != null && userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public void addAdminUser(AdminUser user, String roleIds) {
        // 保存用户
        user.setStatus(StatusEnum.VALID.getStatus());
        user.setCreated(new Date());
        mapper.insert(user);
        // 保存用户和角色的关系
        saveUserRole(user.getUserId(),convertList(roleIds));
    }

    @Override
    public void editAdminUser(AdminUser user, String oldRoleIds, String roleIds) {
        // 更新用户
        user.setModifyed(new Date());
        mapper.updateByPrimaryKeySelective(user);
        if (isChange(oldRoleIds,roleIds)) {
            // 删除用户和角色的旧关系
            deleteUserRole(user.getUserId());

            // 保存用户和角色的新关系
            saveUserRole(user.getUserId(),convertList(roleIds));
        }
    }

    @Override
    public boolean isExistUser(AdminUser user) {
        List<AdminUser> userList = mapper.selectUser(user);
        return userList != null && userList.size() > 0;
    }

    @Override
    public AdminUser getAdminUser(Long userId) {
        return mapper.get(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        // 删除用户
        AdminUser user = new AdminUser();
        user.setUserId(userId);
        user.setStatus(StatusEnum.IN_VALID.getStatus());
        mapper.updateByPrimaryKeySelective(user);

        // 删除用户和角色的对应关系
        deleteUserRole(userId);
    }

    private boolean isChange(String oldRoleIds, String roleIds) {
        List<Long> oldList = convertList(oldRoleIds);
        List<Long> newList = convertList(roleIds);
        return !(oldList.containsAll(newList) && newList.containsAll(oldList));
    }

    private List<Long> convertList(String roleIds) {
        List<Long> roleIdList = new ArrayList<Long>();
        if (StringUtils.isNotBlank(roleIds)) {
            for (String roleId : roleIds.split(",")) {
                roleIdList.add(Long.valueOf(roleId));
            }
        }
        return roleIdList;
    }

    /**
     * 保存用户和角色的关系
     * @param userId
     * @param roleIds
     */
    private void saveUserRole(Long userId,List<Long> roleIds) {
        List<AdminUserRole> userRoleList = new ArrayList<AdminUserRole>();
        for (Long roleId : roleIds) {
            AdminUserRole userRole = new AdminUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setStatus(StatusEnum.VALID.getStatus());
            userRole.setCreated(new Date());
            userRoleList.add(userRole);
        }
        if (CollectionUtils.isNotEmpty(userRoleList)) {
            adminUserRoleMapper.batchInsert(userRoleList);
        }
    }

    /**
     * 删除用户和角色的关系
     * @param userId
     */
    private void deleteUserRole(Long userId) {
        AdminUserRole userRole = new AdminUserRole();
        userRole.setUserId(userId);
        userRole.setStatus(StatusEnum.IN_VALID.getStatus());
        userRole.setModifyed(new Date());
        adminUserRoleMapper.deleteByUserId(userRole);
    }
}


