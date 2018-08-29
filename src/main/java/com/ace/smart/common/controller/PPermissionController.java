package com.ace.smart.common.controller;

import com.ace.smart.common.annotation.Log;
import com.ace.smart.common.util.CollectionUtil;
import com.ace.smart.common.entity.PPermission;
import com.ace.smart.common.service.PPermissionService;
import com.ace.smart.common.service.PRoleService;
import com.ace.smart.common.util.LayuiMap;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Sys/menu/")
public class PPermissionController {
    @Autowired
    private PPermissionService pPermissionService;
    @Autowired
    private PRoleService roleService;

    /**
     * 显示菜单
     * @param page
     * @param limit
     * @param parentId
     * @return
     */
    @RequestMapping("/showData/{parentId}")
    @ResponseBody
    public Map<String, Object> show(@RequestParam(value = "page", required = false)Integer page,
                       @RequestParam(value = "limit",required = false)Integer limit,@PathVariable(value = "parentId",required = false)
                                    Long parentId){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("parentId",parentId);
        return showmain(page,limit,paramMap);
    }

    /**
     * 菜单搜索
     * @param page
     * @param limit
     * @param name
     * @return
     */
    @RequestMapping("/showSearch")
    @ResponseBody
    public Map<String, Object> showSearch(@RequestParam(value = "page", required = false)Integer page,
                                    @RequestParam(value = "limit",required = false)Integer limit,String name){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        return showmain(page,limit,paramMap);
    }

    /**
     * 将显示方法抽出来
     * @param page
     * @param limit
     * @param map
     * @return
     */
    private Map<String, Object> showmain(Integer page,Integer limit,HashMap<String,Object> map){
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        PageInfo menuPage = pPermissionService.selectAllByParetnId(page,limit,map);
        List permissionList = menuPage.getList();
        if(map.containsKey("parentId") && permissionList.size() ==0 ){
            Long parentId =(Long) map.get("parentId");
            map.remove("parentId");
            map.put("menuId",parentId);
            menuPage = pPermissionService.selectAllByParetnId(page,limit,map);
            permissionList = menuPage.getList();
        }
        Map<String,Object> menuMap = LayuiMap.retrunMap();
        menuMap.put("count", menuPage.getTotal());
        if(CollectionUtil.listIsNull(permissionList)){
            menuMap.put("data", permissionList);
        }else{
            menuMap.put("data", new ArrayList<>());
        }
        return menuMap;
    }



    /**
     * 显示菜单数数据
     * @return
     */
    @RequestMapping("showTreeData")
    @ResponseBody
    public List<PPermission> sortMenu(){
        List<PPermission> permission = pPermissionService.sortMenu(0);
        if(permission!=null && permission.size()>0){
            return permission;
        }
        return new ArrayList<>();
    }

    /**
     * 显示菜单数数据
     * @return
     */
    @RequestMapping("showCheckTreeData")
    @ResponseBody
    public List<PPermission> sortMenuAndCheck(Long rid){
        List<PPermission> permission = roleService.selectChecked(rid);
        if(permission!=null && permission.size()>0){
            return permission;
        }
        return new ArrayList<>();
    }

    /**
     * 跳转到菜单数页面
     * @return
     */
    @RequestMapping("showTree")
    public String treeMenu(){
        return "sys/menu/treeMenu";
    }

    /**
     * 跳转到菜单页面
     * @return
     */
    @RequestMapping("show")
    public String show(){
        return "sys/menu/index";
    }

    /**
     * 菜单修改
     * @param pPermission
     * @return
     */
    @Log("修改菜单")
    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody @Validated PPermission pPermission){
        int affect = pPermissionService.updateByPrimaryKeySelective(pPermission);
        if (affect>0){
            return "true";
        }
        return "false";
    }

    /**
     * 添加菜单
     * @param pPermission
     * @return
     */
    @Log("添加菜单")
    @RequestMapping("post")
    @ResponseBody
    public String add(@RequestBody @Validated PPermission pPermission){
        if (pPermission!=null && !"".equals(pPermission.getName())) {
            int affect = pPermissionService.insert(pPermission);
            if (affect > 0) {
                return "true";
            }
        }
        return "false";
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @Log("菜单删除")
    @RequestMapping("/delete/{menuId}")
    @ResponseBody
    public Integer delete(@PathVariable Long menuId){
        if (menuId==null) {
            return 0;
        }
        List<PPermission> list = pPermissionService.sortMenu(menuId); //判断是否有子菜单
        if (CollectionUtil.listIsNull(list)) {
                return 2;
        }
        int affect = pPermissionService.deleteByPrimaryKey(menuId);
        return affect>0?affect:0;
    }

    /**
     * 修改资源的启用禁用状态
     * @param menuId
     * @param status
     * @return
     */
    @Log("菜单状态修改")
    @RequestMapping("/updateStatu")
    @ResponseBody
    public int updateStatus(@RequestParam("menuId") String menuId,
                            @RequestParam("status") String status){
        if (menuId.isEmpty() || status .isEmpty()){
            return 0;
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("menuId",menuId);
        map.put("status",status);
        int affect =pPermissionService.updateState(map);
        return affect>0?affect:0;
    }

    @RequestMapping("ztree")
    public String showZtree(){
        return "sys/menu/zTreeMenu";
    }
}