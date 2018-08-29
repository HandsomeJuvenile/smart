package com.ace.smart.common.service;

import com.ace.smart.common.entity.PRolePermission;
import com.ace.smart.common.entity.vo.PRoleVo;

import java.util.List;

public interface PRolePermissionService {

    int insert(PRoleVo record);

    int insertSelective(PRolePermission record);

    List<PRolePermission> selectRolePperm(long rid);

    int delete(long rid);

}
