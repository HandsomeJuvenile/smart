package com.ace.smart.mapper;

import com.ace.smart.entity.UPermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UPermission record);

    int insertSelective(UPermission record);

    UPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UPermission record);

    int updateByPrimaryKey(UPermission record);
}