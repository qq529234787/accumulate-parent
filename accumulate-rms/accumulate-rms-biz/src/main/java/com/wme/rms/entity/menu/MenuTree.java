package com.wme.rms.entity.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
public class MenuTree implements Comparable<MenuTree> {

    private Long menuId;

    private Long parentId;

    private String menuLink;

    private String menuName;

    private Integer seq;

    private Integer menuLevel;

    List<MenuTree> subMenu = new ArrayList<MenuTree>();

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<MenuTree> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<MenuTree> subMenu) {
        this.subMenu = subMenu;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public int compareTo(MenuTree menuTree) {
        Collections.sort(menuTree.getSubMenu());
        Collections.sort(this.getSubMenu());
        return this.getSeq().compareTo(menuTree.getSeq());
    }

}
