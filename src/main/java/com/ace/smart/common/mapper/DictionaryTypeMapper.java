package com.ace.smart.common.mapper;

import com.ace.smart.common.entity.DictionaryType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictionaryTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DictionaryType record);

    int insertSelective(DictionaryType record);

    DictionaryType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictionaryType record);

    int updateByPrimaryKey(DictionaryType record);
}