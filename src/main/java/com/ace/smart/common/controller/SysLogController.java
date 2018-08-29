package com.ace.smart.common.controller;

import com.ace.smart.common.entity.SysLog;
import com.ace.smart.common.service.SysLogService;
import com.ace.smart.common.util.LayuiMap;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/Sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/show")
    public String show(){
        return "sys/log/logs";
    }


    @RequestMapping("/data")
    @ResponseBody
    public  Map<String,Object> show(Integer page,Integer limit,String rName){
        page = page == null ?page =1:page;
        limit = limit == null ?limit =10:limit;
        PageInfo pageInfo = sysLogService.selectAll(page,limit);
        List<SysLog> roleList = pageInfo.getList();
        Map<String,Object> roleMap = LayuiMap.retrunMap();
        roleMap.put("count", pageInfo.getTotal());
        roleMap.put("data", roleList);
        return roleMap;
    }

}
