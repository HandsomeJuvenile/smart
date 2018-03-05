package com.ace.smart.serviceimpl;

import com.ace.smart.entity.UUser;
import com.ace.smart.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public UUser getLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        UUser uUser = subject.getPrincipals().oneByType(UUser.class);
        return uUser;
    }

    @Override
    public String getLoginName() {
        return getLoginUser().getId()+"";
    }

}
