package com.wme.rms.entity.role;

import com.wme.base.utils.PageQuery;

/**
 * Created by ming on 2016/3/27.
 */
public class AdminRoleSearch extends PageQuery {

    private Long roleId;

    private String roleName;

    private Integer status;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdminRoleSearch [roleId=" + roleId + ", roleName=" + roleName
                + ", status=" + status + "]";
    }

}
