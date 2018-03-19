package com.wme.rms.entity.role;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Date;

/**
 * Created by ming on 2016/3/27.
 */
public class AdminRole implements java.io.Serializable {

    private Long roleId;

    private String roleName;

    private Integer status;

    private Date created;

    private Date modifyed;

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

    @Override
    public int hashCode() {
        return roleId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj == null) {
            isEqual = false;
        } else if (this == obj) {
            isEqual = true;
        } else if (!(obj instanceof AdminRole)) {
            isEqual = false;
        } else if (!this.getClass().equals(obj.getClass())) {
            isEqual = false;
        } else {
            AdminRole castObj = (AdminRole) obj;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(this.getRoleId(), castObj.getRoleId());
            isEqual = builder.isEquals();
        }
        return isEqual;
    }
}
