package com.ace.smart.service;

import com.ace.smart.entity.PUserVo;
import com.ace.smart.entity.UUserRole;

public interface UUserRoleService {

    int insert(PUserVo pUserVo);

    int insertSelective(UUserRole record);

    int deluserRole(Long id);

    UUserRole selectByPrimaryId(Long id);
}
