package com.ace.smart.common.controller;


import com.ace.smart.common.service.DictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/Sys/dic")
public class DictionaryTypeController {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @GetMapping(value = "/add")
    public String add(){
        return "";
    }
}
