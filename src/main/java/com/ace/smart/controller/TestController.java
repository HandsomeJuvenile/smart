package com.ace.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class TestController {

    @RequestMapping("/hello")
    public String hell(Map<String ,String> map){
        map.put("name","hello");
        return "/blank";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "roll/login";
    }

    @RequestMapping("/happy")
    public String happy(){
        return "/christmas";
    }
}
