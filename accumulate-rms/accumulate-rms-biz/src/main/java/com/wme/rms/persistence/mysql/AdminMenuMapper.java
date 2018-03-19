package com.wme.rms.persistence.mysql;

import com.wme.base.persistance.BaseMapper;
import com.wme.base.utils.ZTreeJson;
import com.wme.rms.entity.menu.AdminMenu;

import java.util.List;
import java.util.Map;

/**
 * Created by ming on 2016/3/27.
 */
public interface AdminMenuMapper extends BaseMapper<AdminMenu> {

    int deleteByPrimaryKey(Long menuId);

    List<AdminMenu> queryMenuListByMenuId(Long parentMenuId);

    List<ZTreeJson> selectAllMenu();

    List<AdminMenu> selectAllowMenu(Map<String,Object> paramMap);

    List<AdminMenu> selectMenu(AdminMenu menu);

}
