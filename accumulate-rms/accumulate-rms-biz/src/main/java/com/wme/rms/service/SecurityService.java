package com.wme.rms.service;

import com.wme.rms.entity.menu.AdminMenu;
import com.wme.rms.entity.menu.MenuTree;

import java.util.List;

/**
 * Created by ming on 2016/4/6.
 */
public interface SecurityService {

    /**
     * 获取用户可以访问的所有菜单
     * @param userName
     * @return
     */
    List<MenuTree> getAllowMenuTree(String userName);

    /**
     * 获取用户可以访问的所有资源
     * @param userName
     * @return
     */
    List<AdminMenu> getAllMenu(String userName);

    /**
     * 初始化用户的权限菜单
     * @param userName
     */
    //void initAllowMenu(String userName);

    /**
     * 用户是否可以访问
     * @param userName
     * @param code
     * @return
     */
    //boolean isAllowAccess(String userName,String code);

    /**
     * 清空redis中用户对应的权限码信息
     * @param userName
     */
    //void clearPremCode(String userName);

}
