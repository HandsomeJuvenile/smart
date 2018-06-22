package com.ace.smart.controller;

import com.ace.smart.annotation.Log;
import com.ace.smart.entity.PRole;
import com.ace.smart.entity.R;
import com.ace.smart.entity.SysTask;
import com.ace.smart.service.SysTaskService;
import com.ace.smart.util.CollectionUtil;
import com.ace.smart.util.LayuiMap;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Sys/task")
public class SysTaskController {
    private static final Logger logger = LoggerFactory.getLogger(SysTaskController.class);

    @Autowired
    private SysTaskService sysTaskService;


    @RequestMapping("/show")
    public String show(){
        return "sys/task/task";
    }

    @GetMapping("/add")
    public String add(){
        return "sys/task/add";
    }

    @PostMapping("/add")
    @ResponseBody
    @Log("添加任务计划")
    public R add(@RequestBody  SysTask sysTask){
        int affect = sysTaskService.insert(sysTask);
        return affect>0?R.ok():R.error();
    }

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> show(Integer page, Integer limit,String key){
        page = page == null ?page =1:page;
        limit = limit == null ?limit =10:limit;
        PageInfo rolePage = sysTaskService.selectAll(page,limit,key);
        List<SysTask> roleList = rolePage.getList();
        Map<String,Object> roleMap = LayuiMap.retrunMap();
        roleMap.put("count", rolePage.getTotal());
        roleMap.put("data", roleList);
        return roleMap;
    }

    /**
     * 修改任务的启停状态
     * @param id
     * @param jobStatus
     * @return
     */
    @Log("任务状态修改")
    @RequestMapping("/updateStatu")
    @ResponseBody
    public int updateStatus(@RequestParam("id") String id,
                            @RequestParam("jobStatus") String jobStatus){
        if (id.isEmpty() || jobStatus .isEmpty()){
            return 0;
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("id",id);
        map.put("jobStatus",jobStatus);
        int affect =sysTaskService.updateState(map);
        return affect>0?affect:0;
    }

    @Log("删除任务")
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public int delete(@PathVariable("id") Long id){
        if (id == 0l) {
            return 0;
        }
        return sysTaskService.deleteByPrimaryKey(id);
    }

    @Log("删除多个任务")
    @RequestMapping("/batchDelete")
    @ResponseBody
    public Integer batchDelete(@RequestBody  String[] id) {
        List<Long> longs = CollectionUtil.strToList(id);

        if (id != null && !"".equals(Arrays.asList(id))) {
            int affect = sysTaskService.batchDelete(longs);
            return affect>0?affect:0;
        }
        return 0;
    }
}
