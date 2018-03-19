package com.wme.rms.service.impl;

import com.wme.rms.entity.menu.AdminMenu;
import com.wme.rms.entity.menu.MenuTree;
import com.wme.rms.service.AdminMenuService;
import com.wme.rms.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by ming on 2016/4/6.
 */

@Service(value = "securityService")
public class SecurityServiceImpl implements SecurityService {

    // 菜单最多只有3级，查询级别小于3的，也就是左侧显示的菜单列表，不包含按钮级别
    private static final String MENUTREE_LEVEL = "3";

    // 菜单最多只有3级，查询级别小于4的，也就是查询所有的菜单
    private static final String MENUALL_LEVEL = "4";

    @Autowired
    private AdminMenuService adminMenuManager;

    /*@Autowired
    private SecurityCacheService securityCacheService;*/

    @Override
    public List<MenuTree> getAllowMenuTree(String userName) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("loginName", userName);
        paramMap.put("menuLevel", MENUTREE_LEVEL);
        List<AdminMenu> menuList = adminMenuManager.selectAllowMenu(paramMap);
        return convertToMenuTree(menuList);
    }

    @Override
    public List<AdminMenu> getAllMenu(String userName) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("loginName", userName);
        paramMap.put("menuLevel", MENUALL_LEVEL);
        return adminMenuManager.selectAllowMenu(paramMap);
    }

    /*@Override
    public void initAllowMenu(String userName) {
        List<AdminMenu> menus = getAllMenu(userName);
        securityCacheService.setAuthPrem(userName,menus);
    }

    @Override
    public boolean isAllowAccess(String userName, String code) {
        return securityCacheService.isExistPrem(userName,code);
    }

    @Override
    public void clearPremCode(String userName) {
        securityCacheService.deleteAuthPrem(userName);
    }*/

    private List<MenuTree> convertToMenuTree(List<AdminMenu> menuList) {
        List<MenuTree> menuTrees = new ArrayList<MenuTree>();
        if (CollectionUtils.isEmpty(menuList)) {
            return menuTrees;
        }
        Map<Long,MenuTree> menuTreeMap = new HashMap<Long,MenuTree>();
        Map<Long,List<MenuTree>> subMenuTreeMap = new HashMap<Long,List<MenuTree>>();
        for (AdminMenu menu : menuList) {
            if (menu.getMenuLevel() == 1) {
                menuTreeMap.put(menu.getMenuId(), convertToMenuTree(menu));
            }
            if (menu.getMenuLevel() == 2) {
                if (!subMenuTreeMap.containsKey(menu.getParentId())) {
                    subMenuTreeMap.put(menu.getParentId(), new ArrayList<MenuTree>());
                }
                subMenuTreeMap.get(menu.getParentId()).add(convertToMenuTree(menu));
            }
        }
        for (Long key : menuTreeMap.keySet()) {
            menuTreeMap.get(key).setSubMenu(subMenuTreeMap.get(key));
            menuTrees.add(menuTreeMap.get(key));
        }
        Collections.sort(menuTrees);
        return menuTrees;
    }

    private MenuTree convertToMenuTree(AdminMenu menu) {
        MenuTree menuTree = new MenuTree();
        menuTree.setMenuId(menu.getMenuId());
        menuTree.setMenuLevel(menu.getMenuLevel());
        menuTree.setMenuLink(menu.getMenuLink());
        menuTree.setMenuName(menu.getMenuName());
        menuTree.setParentId(menu.getParentId());
        menuTree.setSeq(menu.getSeq());
        return menuTree;
    }
}
