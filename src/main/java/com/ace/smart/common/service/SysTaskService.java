package com.ace.smart.common.service;


import com.ace.smart.common.entity.SysTask;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface SysTaskService {

    int deleteByPrimaryKey(Long id);

    int insert(SysTask record);

    int insertSelective(SysTask record);

    SysTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysTask record);

    int updateByPrimaryKey(SysTask record);

    PageInfo selectAll(int currentPage, int pageSize,String name);

    int updateState(Map<String,String> map);

    int batchDelete(List<Long> list);

    List<SysTask> selectAll();

    void initSchedule();
}
