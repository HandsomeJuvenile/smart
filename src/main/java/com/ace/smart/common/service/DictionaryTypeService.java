package com.ace.smart.common.service;

import com.ace.smart.common.entity.DictionaryType;

public interface DictionaryTypeService {

    int deleteByPrimaryKey(Integer id);

    int insert(DictionaryType record);

    DictionaryType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictionaryType record);
}
