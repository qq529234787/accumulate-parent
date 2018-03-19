package com.wme.rms.entity.menu;

import java.util.Date;

/**
 * Created by ming on 2016/3/27.
 */
public class AdminMenu implements java.io.Serializable {

    private Long menuId;

    private Long parentId;

    private String menuLink;

    private String code;

    private String menuName;

    private Integer seq;

    private Integer menuLevel;

    private Integer status;

    private Date created;

    private Date modifyed;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    @Override
    public String toString() {
        return "AdminMenu{" +
                "menuId=" + menuId +
                ", parentId=" + parentId +
                ", menuLink='" + menuLink + '\'' +
                ", code='" + code + '\'' +
                ", menuName='" + menuName + '\'' +
                ", seq=" + seq +
                ", menuLevel=" + menuLevel +
                ", status=" + status +
                ", created=" + created +
                ", modifyed=" + modifyed +
                '}';
    }
}