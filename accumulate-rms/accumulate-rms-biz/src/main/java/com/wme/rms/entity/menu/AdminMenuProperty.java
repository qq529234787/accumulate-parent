package com.wme.rms.entity.menu;

import java.util.Date;

/**
 * Created by ming on 2016/3/27.
 */
public class AdminMenuProperty implements java.io.Serializable {

    private Long menuPropertyId;

    private String propertyName;

    private Long menuId;

    private Integer status;

    private Date created;

    private Date modifyed;

    public Long getMenuPropertyId() {
        return menuPropertyId;
    }

    public void setMenuPropertyId(Long menuPropertyId) {
        this.menuPropertyId = menuPropertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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
