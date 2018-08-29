package com.ace.smart.common.service;

import com.ace.smart.common.entity.PPermission;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PPermissionService {
    int deleteByPrimaryKey(Long menuId);

    int insert(PPermission record);

    PPermission selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(PPermission record);

    int updateByPrimaryKey(PPermission record);

    PageInfo selectAll(int currentPage, int pageSize);

    List<PPermission> sortMenu(long menuId);

    PageInfo selectAllByParetnId(int currentPage, int pageSize, HashMap<String ,Object> map);

    List<PPermission> sonNumber(Long menuId);

    int updateState(Map<String, Object> map);

    List<PPermission> selectAllMenu();

    List<PPermission> sortMenu(List<PPermission> list,String id);
}
