package com.ace.smart.service;

import com.ace.smart.entity.SysLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysLogService {

    int insert(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    Integer count();

    PageInfo selectAll(int currentPage,int pageSize);
}
