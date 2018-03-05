package com.ace.smart.controller;

import com.ace.smart.service.LoginService;
import com.ace.smart.config.shiro.MyRealm;
import com.ace.smart.entity.UUser;
import com.ace.smart.service.URoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private URoleService uRoleService;
    @Autowired
    private LoginService loginService;
    @RequestMapping("/login")
    public String login(@Valid UUser user, RedirectAttributes redirectAttributes,boolean rememberMe, Model model){
        boolean isLogin = true;        String username = user.getId()+"";
        UsernamePasswordToken token = new UsernamePasswordToken(username,user.getPswd(),rememberMe);
        //获得到subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        }catch(UnknownAccountException uae){
            isLogin = false;
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        }catch(IncorrectCredentialsException ice){
            isLogin = false;
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        }catch(LockedAccountException lae){
            isLogin = false;
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            isLogin = false;
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            isLogin = false;
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        if(isLogin == false) {//出错了，返回登录页面
            token.clear();
            return "redirect:/toLogin";
        } else {//登录成功
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            //  更新用户的登录时间  等
            Session session = currentUser.getSession();
            session.setAttribute("user",loginService.getLoginUser());
            return "redirect:/roll";
        }

    }

    @RequestMapping("/roll")
    public String roll(Model model){
       /* UUser uUser= loginService.getLoginUser();
        if(uUser!=null){
            model.addAttribute("uUsers",uUser);
        }*/
        return "blank";
    }

    @RequestMapping("/roll/index")
    public String index(Model model){
       /* UUser uUser= loginService.getLoginUser();
        if(uUser!=null){
            model.addAttribute("uUsers",uUser);
        }*/
        return "roll/hello";
    }
}
