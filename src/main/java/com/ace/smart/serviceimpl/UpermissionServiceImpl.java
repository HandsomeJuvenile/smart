package com.ace.smart.serviceimpl;

import com.ace.smart.entity.UPermission;
import com.ace.smart.mapper.UPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpermissionServiceImpl implements UPermissionMapper {
    @Autowired
    private UPermissionMapper uPermissionMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(UPermission record) {
        return uPermissionMapper.insert(record);
    }

    @Override
    public int insertSelective(UPermission record) {
        return 0;
    }

    @Override
    public UPermission selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(UPermission record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(UPermission record) {
        return 0;
    }
}
