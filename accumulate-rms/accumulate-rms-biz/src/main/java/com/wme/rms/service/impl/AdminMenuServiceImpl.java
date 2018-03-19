package com.wme.rms.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.wme.base.constants.StatusEnum;
import com.wme.base.service.impl.BaseServiceImpl;
import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.menu.AdminMenu;
import com.wme.rms.entity.menu.AdminMenuSearch;
import com.wme.rms.persistence.mysql.AdminMenuMapper;
import com.wme.rms.service.AdminMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ming on 2016/3/28.
 */
@Service(value = "adminMenuService")
public class AdminMenuServiceImpl extends BaseServiceImpl<AdminMenuMapper,AdminMenu> implements AdminMenuService {

    @Override
    public PaginationSupport<AdminMenu> queryMenuList(AdminMenuSearch menuSearch) {
        PaginationSupport<AdminMenu> adminMenus = this.pageList(menuSearch);
        return adminMenus;
    }

    @Override
    public Long addAdminMenu(AdminMenu adminMenu) {
        adminMenu.setStatus(StatusEnum.VALID.getStatus());
        mapper.insert(adminMenu);
        return adminMenu.getMenuId();
    }

    @Override
    public Integer deleteAdminMenu(Long menuId) {
        AdminMenu adminMenu = mapper.get(menuId);
        if (adminMenu == null) {
            return 0;
        }

        List<AdminMenu> childMenu = mapper.queryMenuListByMenuId(menuId);
        if (CollectionUtils.isNotEmpty(childMenu)) {
            return -1;
        }

        return mapper.deleteByPrimaryKey(menuId);
    }

    @Override
    public Integer updateAdminMenu(AdminMenu adminMenu) {
        AdminMenu menu = get(adminMenu.getMenuId());
        if (menu == null || !adminMenu.getParentId().equals(menu.getParentId())) {
            return 0;
        }
        menu.setSeq(adminMenu.getSeq());
        menu.setMenuLink(adminMenu.getMenuLink());
        menu.setMenuName(adminMenu.getMenuName());
        menu.setCode(adminMenu.getCode());
        return mapper.update(menu);
    }

    @Override
    public Integer updateAdminMenuSeq(AdminMenu adminMenu) {
        if (adminMenu == null || adminMenu.getMenuId() == null
                || adminMenu.getMenuId() <= 0 || adminMenu.getSeq() == null ||
                adminMenu.getSeq() <= 0) {
            return 0;
        }
        AdminMenu menu = mapper.get(adminMenu.getMenuId());
        if (menu == null) {
            return 0;
        }
        menu.setSeq(adminMenu.getSeq());
        int rows = mapper.update(adminMenu);
        return rows;
    }

    @Override
    public boolean isExistMenu(AdminMenu adminMenu) {
        List<AdminMenu> menuList = mapper.selectMenu(adminMenu);
        return menuList != null && menuList.size() > 0;
    }

    @Override
    public List<AdminMenu> selectAllowMenu(Map<String, Object> paramMap) {
        return mapper.selectAllowMenu(paramMap);
    }
}
