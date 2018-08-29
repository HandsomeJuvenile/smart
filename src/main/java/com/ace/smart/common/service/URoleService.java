package com.ace.smart.common.service;

import com.ace.smart.common.entity.URole;

import java.util.List;

public interface URoleService {
    int deleteByPrimaryKey(Long id);

    int insert(URole record);

    int insertSelective(URole record);

    URole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(URole record);

    int updateByPrimaryKey(URole record);

    List<URole> selectAllRole();
}
