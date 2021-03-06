package com.ace.smart.common.mapper;

import com.ace.smart.common.entity.PuImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PuImgMapper {
    int deleteByPrimaryKey(String imgId);

    int insert(PuImg record);

    int insertSelective(PuImg record);

    PuImg selectByPrimaryKey(String imgId);

    int updateByPrimaryKeySelective(PuImg record);

    int updateByPrimaryKey(PuImg record);

    List<PuImg> selectByUserIdAndType(PuImg puImg);
}