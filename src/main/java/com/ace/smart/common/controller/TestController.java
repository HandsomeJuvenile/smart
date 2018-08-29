package com.ace.smart.common.controller;

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

    @RequestMapping("/happy")
    public String happy(){
        return "christmas";
    }

    @RequestMapping("/head")
    public String head(){
        return "head";
    }

    @RequestMapping("kuayu")
    public String kuayu(){
        return "test";
    }
}
