package com.ace.smart.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/404")
    public String notFound(){
        return "error-404";
    }

    @RequestMapping("/500")
    public String badCode(){
        return "error-500";
    }
}
