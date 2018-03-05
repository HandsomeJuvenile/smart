package com.ace.smart.mapper;

import com.ace.smart.entity.URole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface URoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(URole record);

    int insertSelective(URole record);

    URole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(URole record);

    int updateByPrimaryKey(URole record);

    List<URole> selectAllRole();
}