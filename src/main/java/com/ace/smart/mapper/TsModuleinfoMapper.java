package com.ace.smart.mapper;

import com.ace.smart.entity.TsModuleinfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TsModuleinfoMapper {
    int deleteByPrimaryKey(String moduleId);

    int insert(TsModuleinfo record);

    int insertSelective(TsModuleinfo record);

    TsModuleinfo selectByPrimaryKey(String moduleId);

    int updateByPrimaryKeySelective(TsModuleinfo record);

    int updateByPrimaryKey(TsModuleinfo record);
}