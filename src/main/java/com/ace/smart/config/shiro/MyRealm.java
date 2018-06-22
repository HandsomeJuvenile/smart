package com.ace.smart.config.shiro;


import com.ace.smart.dao.RedisDao;
import com.ace.smart.entity.PPermission;
import com.ace.smart.entity.PUser;
import com.ace.smart.entity.URole;
import com.ace.smart.entity.UUser;
import com.ace.smart.service.PPermissionService;
import com.ace.smart.service.PUserService;
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

    @Override
    public String getName() {
        return "myRealm";
    }
    @Autowired
    private PUserService pUserService;
    @Autowired
    private RedisDao redisDao;

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
        String username = usernamePasswordToken.getUsername();
        PUser user = null;

        // 看redis 里面是否存储了想要的数据


        // 没有 则开始相redis 里面存放数据 但是这样的话 第一次登陆会非常慢

        if(username!=null && !username.isEmpty()) {
            if (redisDao.isExistsKey(username)) {
                user = (PUser) redisDao.getValue(username);// 查找redis中的用户token
            } else {
                user = pUserService.selectByLoginName(username);// 查找数据库中的用户token
            }
        }
        if (user == null || user.getId()==null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        return new SimpleAuthenticationInfo(user,user.getPswd(),ByteSource.Util.bytes(user.getId()+""),this.getName());
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
        PUser uUser =(PUser) principalCollection.getPrimaryPrincipal(); // 获取当前输入登录的用户信息
        if(uUser!=null){
            //  对权限授权

            List<PPermission> pPermissions = pUserService.findUserRole(uUser.getUserLoginName()).getpRoles().getpPermissions();
            for (PPermission pPermission:pPermissions) {

            }
        }
        return simpleAuthorizationInfo;
    }
}