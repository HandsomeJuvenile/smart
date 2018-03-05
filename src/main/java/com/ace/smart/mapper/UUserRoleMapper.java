package com.ace.smart.mapper;

import com.ace.smart.entity.UUserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UUserRoleMapper {
    int insert(UUserRole record);

    int insertSelective(UUserRole record);

    int deluserRole(Long id);
}