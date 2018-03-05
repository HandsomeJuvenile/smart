package com.ace.smart.mapper;

import com.ace.smart.entity.TsMenuinfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TsMenuinfoMapper {
    int deleteByPrimaryKey(String menuId);

    int insert(TsMenuinfo record);

    int insertSelective(TsMenuinfo record);

    TsMenuinfo selectByPrimaryKey(String menuId);

    int updateByPrimaryKeySelective(TsMenuinfo record);

    int updateByPrimaryKey(TsMenuinfo record);

    public List<TsMenuinfo> selectByUpMenuId(String menu_Id);
}