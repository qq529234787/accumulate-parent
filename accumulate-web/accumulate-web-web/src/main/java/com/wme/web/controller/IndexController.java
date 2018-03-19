package com.wme.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Wangmingen on 2015/9/14.
 */

@Controller
public class IndexController {

    @RequestMapping("index")
    public String index(){
        return "httpData";
    }

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(@ModelAttribute("name") String name){
        return "login";
    }

}
