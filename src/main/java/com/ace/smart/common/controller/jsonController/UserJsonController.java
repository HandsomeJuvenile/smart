package com.ace.smart.common.controller.jsonController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Sys/user")
public class UserJsonController {
    @RequestMapping("/index")
    public String index(){
        return "user/index";
    }
}
