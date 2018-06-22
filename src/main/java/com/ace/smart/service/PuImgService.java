package com.ace.smart.service;

import com.ace.smart.entity.PuImg;

import java.util.List;

public interface PuImgService {

    int deleteByPrimaryKey(String imgId);

    int insert(PuImg record);

    int insertSelective(PuImg record);

    PuImg selectByPrimaryKey(String imgId);

    int updateByPrimaryKeySelective(PuImg record);

    int updateByPrimaryKey(PuImg record);

    List<PuImg> selectByUserIdAndType(PuImg puImg);

}
