package com.ace.smart.service;

import com.ace.smart.entity.URole;

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
