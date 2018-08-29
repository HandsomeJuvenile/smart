package com.ace.smart.common.mapper;

import com.ace.smart.common.entity.DictionaryItem;

public interface DictionaryItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DictionaryItem record);

    int insertSelective(DictionaryItem record);

    DictionaryItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictionaryItem record);

    int updateByPrimaryKey(DictionaryItem record);
}