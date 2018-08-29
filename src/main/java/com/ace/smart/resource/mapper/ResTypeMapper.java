package com.ace.smart.resource.mapper;

import com.ace.smart.resource.entity.ResType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResTypeMapper {
    int deleteByPrimaryKey(Long resId);

    int insert(ResType record);

    int insertSelective(ResType record);

    ResType selectByPrimaryKey(Long resId);

    int updateByPrimaryKeySelective(ResType record);

    int updateByPrimaryKey(ResType record);

    List<ResType> selectAll();
}