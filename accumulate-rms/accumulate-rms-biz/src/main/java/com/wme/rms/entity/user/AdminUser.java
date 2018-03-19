package com.wme.rms.entity.user;

import java.util.Date;

/**
 * Created by ming on 2016/3/27.
 */
public class AdminUser implements java.io.Serializable {

    private Long userId;

    private String loginName;

    private Integer status;

    private Long departmentId;

    private String departmentName;

    private Date created;

    private Date modifyed;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModifyed() {
        return modifyed;
    }

    public void setModifyed(Date modifyed) {
        this.modifyed = modifyed;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
