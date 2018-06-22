package com.ace.smart.controller;

import com.ace.smart.annotation.Log;
import com.ace.smart.entity.PPermission;
import com.ace.smart.entity.PUser;
import com.ace.smart.entity.R;
import com.ace.smart.entity.vo.PUserVo;
import com.ace.smart.service.LoginService;
import com.ace.smart.service.PRoleService;
import com.ace.smart.service.PUserService;
import com.ace.smart.util.PasswordUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/index")
@Controller
public class IndexController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private PUserService pUserService;
    @Autowired
    private PRoleService pRoleService;

    /**
     * @desc 左边菜单栏
     * @author zzh
     * @date 2018/6/1 8:44
     * @param
     * @return
     */
    @RequestMapping("/leftMenu")
    @ResponseBody
    public List<PPermission> leftMenu(){
        PUser pUser =  pUserService.findUserRole(loginService.getLoginUser().getUserLoginName());
        List<PPermission> list = pRoleService.userPPermission(pUser.getpRoles().getpPermissions());
        return list;
    }

    /**
     * @desc 个人用户信息
     * @author zzh
     * @date 2018/6/1 8:44
     * @param
     * @return
     */
    @RequestMapping("/userInfo")
    public String userInfo(Model model){
        PUserVo pUserVo = pUserService.selectUserAndImgByLoginName(loginService.getLoginName());
        model.addAttribute("pUser",pUserVo);
        return "user/userInfo";
    }

    @GetMapping("updatePwd")
    public String updatePwd(Model model) {
        model.addAttribute("pUser",loginService.getLoginName());
        return "user/changePwd";

    }

    /**
     * @desc 个人密码修改
     *       0: 修改成功
     *       1: 修改失败
     *       2: 原密码错误
     * @author zzh
     * @date 2018/6/1 9:59
     * @param   
     * @return 
     */
    @Log("修改密码")
    @PostMapping("updatePwd")
    @ResponseBody
    public R updatePwds( String oldpswd,  String pswd) {
        Map<String ,String> map = new HashMap<>();
        PUser pUser = loginService.getLoginUser();
        boolean isUpdate = pUserService.selectPswdByName(pUser.getId()+"",pUser.getUserLoginName(),oldpswd);
        if (isUpdate) {
            map.put("id",pUser.getId()+"");
            map.put("pswd", PasswordUtil.encryptPassword(pUser.getId()+"",pswd));
            int affect = pUserService.updatePass(map);
            return affect>0?R.ok():R.error();
        }
        return R.error("2","原密码错误");
    }
}
