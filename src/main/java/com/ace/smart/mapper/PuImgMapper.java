package com.ace.smart.mapper;

import com.ace.smart.entity.PuImg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PuImgMapper {
    int deleteByPrimaryKey(String imgId);

    int insert(PuImg record);

    int insertSelective(PuImg record);

    PuImg selectByPrimaryKey(String imgId);

    int updateByPrimaryKeySelective(PuImg record);

    int updateByPrimaryKey(PuImg record);
}