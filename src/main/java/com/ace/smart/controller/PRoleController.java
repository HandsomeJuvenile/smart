package com.ace.smart.controller;

import com.ace.smart.entity.PRole;
import com.ace.smart.entity.vo.PRoleVo;
import com.ace.smart.service.PRoleService;
import com.ace.smart.util.LayuiMap;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Sys/role/")
public class PRoleController {
    @Autowired
    private PRoleService pRoleService;

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> show(Integer page,Integer limit,String rName){
        page = page == null ?page =1:page;
        limit = limit == null ?limit =10:limit;
        PageInfo rolePage = pRoleService.selectAll(page,limit,rName);
        List<PRole> roleList = rolePage.getList();
        Map<String,Object> roleMap = LayuiMap.retrunMap();
        roleMap.put("count", rolePage.getTotal());
        roleMap.put("data", roleList);
        return roleMap;
    }

    @RequestMapping("/show")
    public String show(){
        return "/sys/role/role";
    }

    @RequestMapping("/toCreate")
    public String toCreate(){
        return "/sys/role/addRole";
    }

    @RequestMapping(value = "addRole",method = RequestMethod.POST)
    @ResponseBody
    public String addRole(@RequestBody PRoleVo pRole){
        if (pRole.getrName() == null){
            return "0";
        }
        if (pRole.getrStatus() == null){
            pRole.setrStatus("0");
        }
        return pRoleService.addRolePer(pRole)>0 ?"1":"0";
    }

    @RequestMapping(value = "updateRole",method = RequestMethod.POST)
    @ResponseBody
    public String updateRole(@RequestBody PRoleVo pRole){
        if (pRole.getrName() == null){
            return "0";
        }
        if (pRole.getrStatus() == null){
            pRole.setrStatus("0");
        }
        return pRoleService.update(pRole)>0 ?"1":"0";
    }

    @RequestMapping(value = "/deleteRole" ,method = RequestMethod.DELETE)
    @ResponseBody
    public int delteRole(@RequestBody Long[] rids){
        if(rids.length < 1){
            return 0;
        }
        int affect = pRoleService.deleteByPrimaryKey(rids);
        return affect;
    }

    @RequestMapping(value = "/showOne",method = RequestMethod.GET)
    public String showOne(Model model, long rid){
        PRole pRole = pRoleService.selectByPrimaryKey(rid);
        model.addAttribute("pRole",pRole);
        return "/sys/role/updateRole";
    }


}
