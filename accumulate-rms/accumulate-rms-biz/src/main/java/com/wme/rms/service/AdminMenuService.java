package com.wme.rms.service;

import com.wme.base.service.BaseService;
import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.menu.AdminMenu;
import com.wme.rms.entity.menu.AdminMenuSearch;
import com.wme.rms.persistence.mysql.AdminMenuMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by ming on 2016/3/28.
 */
public interface AdminMenuService extends BaseService<AdminMenuMapper,AdminMenu> {

    PaginationSupport<AdminMenu> queryMenuList(AdminMenuSearch menuSearch) ;

    Long addAdminMenu(AdminMenu adminMenu);

    Integer deleteAdminMenu(Long menuId);

    Integer updateAdminMenu(AdminMenu adminMenu);

    Integer updateAdminMenuSeq(AdminMenu adminMenu);

    boolean isExistMenu(AdminMenu adminMenu);

    List<AdminMenu> selectAllowMenu(Map<String,Object> paramMap);
}
