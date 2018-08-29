package com.ace.smart.common.mapper;

import com.ace.smart.common.entity.UUserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UUserRoleMapper {
    int insert(UUserRole record);

    int insertSelective(UUserRole record);

    int deluserRole(Long id);

    UUserRole selectByPrimaryId(Long id);
}