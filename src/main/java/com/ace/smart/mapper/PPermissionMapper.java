package com.ace.smart.mapper;

import com.ace.smart.entity.PPermission;
import com.ace.smart.entity.vo.PPermissionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface PPermissionMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(PPermission record);

    int insertSelective(PPermission record);

    PPermission selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(PPermission record);

    int updateByPrimaryKey(PPermission record);

    List<PPermission> selectAll();

    List<PPermission> selectByParent(Long menu_id);

    List<PPermissionVo> selectAllByParetnId(HashMap<String ,Object> map);

    int updateState(Map<String, Object> map);

    List<PPermission> selectAllMenu();
}