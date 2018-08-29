package com.ace.smart.resource.service.impl;

import com.ace.smart.common.util.CollectionUtil;
import com.ace.smart.resource.entity.ResType;
import com.ace.smart.resource.mapper.ResTypeMapper;
import com.ace.smart.resource.service.ResTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResTypeServiceImpl implements ResTypeService {
    @Autowired
    private ResTypeMapper resTypeMapper;

    @Override
    public int insert(ResType record) {
        return resTypeMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKey(ResType record) {
        return StringUtils.isEmpty(record.getResName())?0:resTypeMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(Long resId) {
        if ( resId != null && resId != 0l) {
            return resTypeMapper.deleteByPrimaryKey(resId);
        }
        return 0;
    }

    @Override
    public PageInfo selectAll(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<ResType> list = resTypeMapper.selectAll();
        return CollectionUtil.listIsNull(list)?new PageInfo(list):new PageInfo(new ArrayList<ResType>());
    }

    public Integer get(){
        return 0;
    }
}
