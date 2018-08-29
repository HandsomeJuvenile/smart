package com.ace.smart.common.mapper;

import com.ace.smart.common.entity.SysTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysTask record);

    int insertSelective(SysTask record);

    SysTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysTask record);

    int updateByPrimaryKey(SysTask record);

    List<SysTask> selectAll(String name);

    int updateState(Map<String,String> map);

    int batchDelete(List<Long> list);

}