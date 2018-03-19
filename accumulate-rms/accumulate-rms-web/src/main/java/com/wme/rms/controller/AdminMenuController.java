package com.wme.rms.controller;

import com.wme.base.utils.PaginationSupport;
import com.wme.rms.entity.menu.AdminMenu;
import com.wme.rms.entity.menu.AdminMenuSearch;
import com.wme.rms.service.AdminMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by ming on 2016/4/9.
 */
@Controller
@RequestMapping(value = "menu")
public class AdminMenuController {

    private static Logger logger = LoggerFactory.getLogger(AdminMenuController.class);

    @Resource
    private AdminMenuService adminMenuService;

    @RequestMapping(value = "pageList")
    public ModelAndView pageList(AdminMenuSearch menuSearch){
        ModelAndView view = new ModelAndView("/menu/page_list");
        if (menuSearch.getParentId() == null || menuSearch.getParentId() <= 0) {
            menuSearch.setParentId(0L);
        }
        try {
            PaginationSupport<AdminMenu> pagination = adminMenuService.pageList(menuSearch);
            view.addObject("menuSearch", menuSearch);
            view.addObject("pagination", pagination);
            if (menuSearch.getParentId() > 0) {
                AdminMenu parentMenu = adminMenuService.get(menuSearch.getParentId());
                view.addObject("parentMenu", parentMenu);
            }
        } catch (Exception ex) {
            logger.error("获取菜单列表异常 parentId={}", menuSearch.getParentId(), ex);
        }

        return view;
    }
}
