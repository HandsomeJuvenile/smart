package com.ace.smart.common.mapper;


import com.ace.smart.common.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    Integer count();

    List<SysLog> selectAll();
}