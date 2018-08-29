package com.ace.smart.resource.service;


import com.ace.smart.resource.entity.ResType;
import com.github.pagehelper.PageInfo;

public interface ResTypeService {

    int insert(ResType record);

    int updateByPrimaryKey(ResType record);

    int deleteByPrimaryKey(Long resId);

    PageInfo selectAll(int currentPage, int pageSize);

}
