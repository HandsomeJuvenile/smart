package com.ace.smart.service;

import com.ace.smart.entity.PUser;
import com.ace.smart.entity.PuImg;
import com.ace.smart.entity.UUser;

public interface LoginService {

    public PUser getLoginUser();

    public String getLoginName();

    public PUser login(PUser pUser);
}
