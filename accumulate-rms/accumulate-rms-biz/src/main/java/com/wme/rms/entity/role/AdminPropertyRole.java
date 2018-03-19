package com.wme.rms.entity.role;

import java.util.Date;

/**
 * Created by ming on 2016/3/27.
 */
public class AdminPropertyRole implements java.io.Serializable {

    private Long menuRoleId;

    private Long propertyId;

    private Long roleId;

    private Integer status;

    private Date created;

    private Date modifyed;

    public Long getMenuRoleId() {
        return menuRoleId;
    }

    public void setMenuRoleId(Long menuRoleId) {
        this.menuRoleId = menuRoleId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

}
