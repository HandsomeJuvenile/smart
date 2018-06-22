package com.ace.smart.service;

import com.ace.smart.entity.PPermission;
import com.ace.smart.entity.PRole;
import com.ace.smart.entity.PRolePermission;
import com.ace.smart.entity.vo.PRoleVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PRoleService {
    int deleteByPrimaryKey(Long[] rId);

    int insert(PRole record);

    int insertSelective(PRoleVo record);

    PRole selectByPrimaryKey(Long rId);

    int updateByPrimaryKeySelective(PRoleVo record);

    int updateByPrimaryKey(PRole record);

    PageInfo selectAll(int currentPage, int pageSize,String rName);

    public int addRolePer(PRoleVo pRoleVo);

    List<PPermission> selectChecked(long rid);

    int update(PRoleVo record);

    List<PPermission> checked(List<PPermission> list,List<PRolePermission> pRolePermissionsList);

    List<PPermission> checkeds(List<PPermission> list,List<PPermission> pRolePermissionsList);

    List<PPermission> userPPermission(List<PPermission> list);
}
