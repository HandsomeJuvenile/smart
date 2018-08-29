package com.ace.smart.common.service;

import com.ace.smart.common.entity.PUserVo;
import com.ace.smart.common.entity.UUserRole;

public interface UUserRoleService {

    int insert(PUserVo pUserVo);

    int insertSelective(UUserRole record);

    int deluserRole(Long id);

    UUserRole selectByPrimaryId(Long id);
}
