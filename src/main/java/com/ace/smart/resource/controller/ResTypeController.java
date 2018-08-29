package com.ace.smart.resource.controller;

import com.ace.smart.common.annotation.Log;
import com.ace.smart.common.entity.PRole;
import com.ace.smart.common.entity.R;
import com.ace.smart.common.util.LayuiMap;
import com.ace.smart.resource.entity.ResType;
import com.ace.smart.resource.service.ResTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/res/type")
public class ResTypeController {

    @Autowired
    private ResTypeService resTypeService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "res/restype/resType";
    }

    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> index(Integer page,Integer limit){
        page = page == null ?page =1:page;
        limit = limit == null ?limit =10:limit;
        PageInfo rolePage = resTypeService.selectAll(page,limit);
        List<PRole> roleList = rolePage.getList();
        Map<String,Object> roleMap = LayuiMap.retrunMap();
        roleMap.put("count", rolePage.getTotal());
        roleMap.put("data", roleList);
        return roleMap;
    }

    private boolean validation(ResType resType){
        if (resType == null || StringUtils.isEmpty(resType.getResId()+"") || StringUtils.isEmpty(resType.getResName())){
            return false;
        }
        return true;
    }

    @Log("添加资源分类")
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String insert() {
        return "res/restype/add";
    }

    @Log("添加资源分类")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public R insert(@RequestBody ResType resType) {
        if (!validation(resType)) {
            return R.error();
        }
        return resTypeService.insert(resType)>0?R.ok():R.error();
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update() {
        return "res/restype/update";
    }

    @Log("修改资源分类")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public R update(@RequestBody ResType resType) {
        if (!validation(resType)) {
            return R.error();
        }
        return resTypeService.updateByPrimaryKey(resType)>0?R.ok():R.error();
    }

    @Log("删除资源分类")
    @RequestMapping(value = "/del/{resId}",method = RequestMethod.DELETE)
    @ResponseBody
    public R delete(@PathVariable("resId") Long resId) {
        if (resId == null) {
            throw new NullPointerException("resId 不可以为空");
        }
        return resTypeService.deleteByPrimaryKey(resId)>0?R.ok():R.error();
    }

}
