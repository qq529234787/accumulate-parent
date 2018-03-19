package com.wme.rms.controller;

import com.wme.rms.entity.menu.MenuTree;
import com.wme.rms.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Wangmingen on 2015/9/14.
 */

@Controller
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(AdminMenuController.class);

    @Autowired
    private SecurityService securityService;


    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView view = new ModelAndView("/index");
        List<MenuTree> menuTree = securityService.getAllowMenuTree("admin");
        MenuTree tree = new  MenuTree();
        tree.setMenuId(142L);
        tree.setMenuName("百度搜索");
        tree.setMenuLevel(2);
        tree.setMenuLink("111");
        view.addObject("menuTree", menuTree);
        return view;
    }

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(@ModelAttribute("name") String name){
        return "login";
    }


}
