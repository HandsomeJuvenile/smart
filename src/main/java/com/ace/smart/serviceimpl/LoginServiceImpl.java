package com.ace.smart.serviceimpl;

import com.ace.smart.entity.PUser;
import com.ace.smart.entity.PuImg;
import com.ace.smart.entity.UUser;
import com.ace.smart.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public PUser getLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        PUser pUser = subject.getPrincipals().oneByType(PUser.class);
        return pUser==null?new PUser():pUser;
    }

    @Override
    public String getLoginName() {
        return getLoginUser().getUserLoginName();
    }

    @Override
    public PUser login(PUser pUser) {

        return null;
    }

}
