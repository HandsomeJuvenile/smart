package com.ace.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
