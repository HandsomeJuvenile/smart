package com.ace.smart.config.shiro;


import com.ace.smart.entity.URole;
import com.ace.smart.entity.UUser;
import com.ace.smart.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class MyRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private UserService userService;
    @Override
    public String getName() {
        return "myRealm";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;//获取用户输入的token
        long username = Long.parseLong(usernamePasswordToken.getUsername());
        UUser user = userService.findUserRole(username);// 查找数据库中的用户token
        LOGGER.info("验证当前Subject时获取到token---");
        if (user == null) {
            LOGGER.info("用户不存在");
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPswd(), ByteSource.Util.bytes(user.getId()+""),getName());
    }

    /**
     * 权限授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("权限配置");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UUser uUser =(UUser) principalCollection.getPrimaryPrincipal(); // 获取当前输入登录的用户信息
        if(uUser!=null){
            List<URole> rolesList = uUser.getRoles();
            if(rolesList!=null && rolesList.size()>0){
                for (URole uRole:rolesList) {
                    simpleAuthorizationInfo.addRole(uRole.getName()); // 权限名称
                   /* if (uRole.getuPermissions()!=null && uRole.getuPermissions().size()>0){
                        for (UPermission uPermission:uRole.getuPermissions()) {
                            simpleAuthorizationInfo.addStringPermission(uPermission.getUrl());  // 资源路径
                        }
                    }*/
                }
            }
        }
        return simpleAuthorizationInfo;
    }
}