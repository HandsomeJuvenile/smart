package com.ace.smart.mapper;

import com.ace.smart.entity.PRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PRoleMapper {
    int deleteByPrimaryKey(Long[] rId);

    int insert(PRole record);

    int insertSelective(PRole record);

    PRole selectByPrimaryKey(Long rId);

    int updateByPrimaryKeySelective(PRole record);

    int updateByPrimaryKey(PRole record);

    List<PRole> selectAll(String rName);

}