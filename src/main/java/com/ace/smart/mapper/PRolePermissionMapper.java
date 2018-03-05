package com.ace.smart.mapper;

import com.ace.smart.entity.PRolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PRolePermissionMapper {
    int insert(PRolePermission record);

    int insertSelective(PRolePermission record);

    List<PRolePermission> selectRolePperm(long rid);

    int delete(long rid);
}