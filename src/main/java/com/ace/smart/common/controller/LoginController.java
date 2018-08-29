package com.ace.smart.common.controller;

import com.ace.smart.common.annotation.Log;
import com.ace.smart.common.entity.PUser;
import com.ace.smart.common.entity.SysLog;
import com.ace.smart.common.entity.vo.PUserVo;
import com.ace.smart.common.service.LoginService;
import com.ace.smart.common.config.shiro.MyRealm;
import com.ace.smart.common.service.PUserService;
import com.ace.smart.common.service.SysLogService;
import com.ace.smart.common.util.IPUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private LoginService loginService;
    @Autowired
    private PUserService pUserService;
    @Autowired
    private SysLogService sysLogService;


    @GetMapping("login")
    public String toLogin(){
        return "login";
    }

    /**
     * @desc 0: 登录失败
     *       1: 登录成功
     *       2.账号或者密码错误
     *       3. 账号被锁定
     *       4. 登录错误次数过多
     * @author zzh
     * @date 2018/5/30 14:42
     * @param   
     * @return 
     */
    @Log("登录")
    @ResponseBody
    @PostMapping("/login")
    public String login(@PathParam("userLoginName")String userLoginName,@PathParam("pswd") String pswd,
            RedirectAttributes redirectAttributes, boolean rememberMe, Model model){
        boolean isLogin = true;
        UsernamePasswordToken token = new UsernamePasswordToken(userLoginName,pswd,rememberMe);
        //获得到subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + userLoginName + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + userLoginName + "]进行登录验证..验证通过");
        }catch(UnknownAccountException uae){
            isLogin = false;
            logger.info("对用户[" + userLoginName + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
            return "2";
        }catch(IncorrectCredentialsException ice){
            isLogin = false;
            logger.info("对用户[" + userLoginName + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
            return "2";
        }catch(LockedAccountException lae){
            isLogin = false;
            logger.info("对用户[" + userLoginName + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
            return "3";
        }catch(ExcessiveAttemptsException eae){
            isLogin = false;
            logger.info("对用户[" + userLoginName + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
            return "3";
        }catch(AuthenticationException ae){
            isLogin = false;
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + userLoginName + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
            return "2";
        }
        if(isLogin == false) {//出错了，返回登录页面
            token.clear();
            return "0";
        } else {//登录成功
            logger.info("用户[" + userLoginName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            //  更新用户的登录时间等
            Session session = currentUser.getSession();
            session.setAttribute("user",loginService.getLoginUser());
            return "1";
        }
    }

    /**
     * @desc 此处是将用户表与图片表联合起来的 图片表如果有两个  那么这个用户就会查询出来两条数据 报错
     * @author zzh
     * @date 2018/5/31 15:38
     * @param
     * @return
     */
    @Log("访问首页")
    @RequestMapping("/index")
    public String index(Model model){
        PUserVo pUserVo = pUserService.selectUserAndImgByLoginName(loginService.getLoginName());
        model.addAttribute("pUser",pUserVo);
        return "home";
    }

    @RequestMapping("/currentUser")
    @ResponseBody
    public  PUser getCurrentUser(){
        PUser pUser = loginService.getLoginUser();
        if (pUser == null) {
            return new PUser();
        }
        return pUser;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        PUser pUser =  subject.getPrincipals().oneByType(PUser.class);
        subject.logout();
        // 日志记录
        SysLog sysLog = new SysLog();
        sysLog.setOperation("退出");
        sysLog.setMethod("com.ace.smart.common.controller.LoginController.logout()");
        sysLog.setUsername(pUser.getNickname());
        sysLog.setUserId(pUser.getId());
        sysLog.setIp(IPUtils.getIpAddr(request));
        sysLog.setParams("");
        sysLogService.insert(sysLog);
        return "login";
    }
}
