package com.ace.smart.common.mapper;

import com.ace.smart.common.entity.PRole;
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