package com.ace.smart.common.service;

import com.ace.smart.common.entity.SysLog;
import com.github.pagehelper.PageInfo;

public interface SysLogService {

    int insert(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    Integer count();

    PageInfo selectAll(int currentPage,int pageSize);
}
