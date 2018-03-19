package com.wme.rms.entity.department;

import com.wme.base.utils.PageQuery;

/**
 * Created by ming on 2016/3/27.
 */
public class AdminDepartmentSearch extends PageQuery {

    private Long departmentId;

    private String departmentName;

    private Integer status;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
