package com.ace.smart.common.serviceimpl;

import com.ace.smart.common.entity.DictionaryType;
import com.ace.smart.common.mapper.DictionaryTypeMapper;
import com.ace.smart.common.service.DictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DictionaryTypeServiceImpl implements DictionaryTypeService{
    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        if (id == null) return 0;
        return dictionaryTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DictionaryType record) {
        if (record.getId()==null || StringUtils.isEmpty(record.getName())) return 0;
        return dictionaryTypeMapper.insert(record);
    }

    @Override
    public DictionaryType selectByPrimaryKey(Integer id) {
        return dictionaryTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(DictionaryType record) {
        if (record.getId()==null || StringUtils.isEmpty(record.getName())) return 0;
        return dictionaryTypeMapper.updateByPrimaryKeySelective(record);
    }

}
