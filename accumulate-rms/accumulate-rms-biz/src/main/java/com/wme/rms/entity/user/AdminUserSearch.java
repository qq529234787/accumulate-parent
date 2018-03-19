package com.wme.rms.entity.user;

import com.wme.base.utils.PageQuery;

/**
 * Created by ming on 2016/3/27.
 */
public class AdminUserSearch extends PageQuery {

    private Long userId;

    private Integer departmentId;

    private String loginName;

    private Integer status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdminUserSearch [userId=" + userId + ", departmentId="
                + departmentId + ", loginName=" + loginName + ", status="
                + status + "]";
    }
}
