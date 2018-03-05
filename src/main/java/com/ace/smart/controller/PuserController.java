package com.ace.smart.controller;

import com.ace.smart.container.EmailContainer;
import com.ace.smart.email.SendEmail;
import com.ace.smart.entity.Email;
import com.ace.smart.entity.PRole;
import com.ace.smart.entity.PUser;
import com.ace.smart.entity.PUserVo;
import com.ace.smart.mapper.PRoleMapper;
import com.ace.smart.service.PUserService;
import com.ace.smart.service.UUserRoleService;
import com.ace.smart.util.CollectionUtil;
import com.ace.smart.util.IdGen;
import com.example.smart.entity.*;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Sys/user")
public class PuserController {
    private static final Logger logger = LoggerFactory.getLogger(PuserController.class);

    @Autowired
    private PUserService pUserService;
    @Autowired
    private PRoleMapper pRoleMapper;
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private UUserRoleService uUserRoleService;

    /**
     * 查询所有用户
     * @param model
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/show")
    public  String show(Model model, @RequestParam(value = "currentPage", required = false)Integer currentPage, @RequestParam(value = "pageSize",required = false)Integer pageSize){
        if(currentPage==null){
            currentPage = 1;
        }
        if(pageSize==null){
            pageSize = 10;
        }
        PageInfo pageInfo = pUserService.selectAll(currentPage,pageSize);
        List<PUser> pUsers = pageInfo.getList();
        if(pUsers!=null && pUsers.size()>0){
            model.addAttribute("pageInfos",pageInfo);
        }
        return "/user/index";
    }

    @RequestMapping("/toCreate")
    public String toCreate(Model model){
        List<PRole> list = pRoleMapper.selectAll("");
        model.addAttribute("pRole",list);
        return "/user/addUser";
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create(@RequestBody @Validated PUserVo pUser, BindingResult bindingResult){
        checkDTOParams(bindingResult);
        String validation = pUserService.validation(pUser);
        if(!"true".equals(validation)){
            return validation;
        }
        if(pUser!=null){
            int count = pUserService.insert(pUser);
            uUserRoleService.insert(pUser);
            if(count>0 ){
                logger.info(pUser.getUserLoginName()+"用户添加成功");
                return "0";
            }else {
                return "4";
            }
        }
        return "4";
    }

    private void checkDTOParams(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //throw new 带验证码的验证错误异常
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String id){
        long idl = 0l;
        if (id!=null && !id.equals("")){
            idl = Long.parseLong(id);
            int affect = pUserService.deleteByPrimaryKey(idl);
            if (affect > 0){
                logger.info(id+"用户删除成功");
                return "true";
            }
        }
        return "false";
    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(@RequestParam(value="id") String[] id) {
        List<Long> longs = CollectionUtil.strToList(id);

        System.out.println();
        if (id != null && !"".equals(Arrays.asList(id))) {
            int affect = pUserService.batchDelete(longs);
            if (affect > 0) {
                return "1";
            }
            return "0";
        }
        return "0";
    }

        @RequestMapping("/sendEmail")
        @ResponseBody
        public String sendEmail (String recive){
            String token = IdGen.uuid();
            EmailContainer emailContainer = EmailContainer.getInstance();
            Map<String, String> emailMap = emailContainer.getEmailMap();
            emailMap.put(recive, token);
            Email email = new Email();
            email.setRecive(recive);
            email.setToken(token);
            sendEmail.sendRegisterEmail(email);
            return "true";
        }

        @RequestMapping("/toUpdate/{id}")
        public String toupdate(@PathVariable(value = "id")long id,Model model){
            List<PRole> list = pRoleMapper.selectAll("");
            model.addAttribute("pRole",list);
            if(id!=0){
                PUser pUser = pUserService.selectByPrimaryKey(id);
                if(pUser!=null){
                    model.addAttribute("pUser",pUser);
                }else {
                    model.addAttribute("pUser",new PUser());
                }
            }
            return "/user/update";
        }

        @RequestMapping("/update")
        @ResponseBody
        public String update(@RequestBody @Validated PUserVo pUser){
            if(pUser!=null){
                String validation = pUserService.validationUpdate(pUser);
                if(!"true".equals(validation)){
                    return validation;
                }
                pUserService.updateByPrimaryKeySelective(pUser);
                logger.info(pUser.getUserLoginName()+"信息发生修改");
                return "0";
            }
            return "nodata";
        }

}
