package com.ace.smart.common.serviceimpl;

import com.ace.smart.common.mapper.URoleMapper;
import com.ace.smart.common.entity.URole;
import com.ace.smart.common.service.URoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UroleServiceImpl implements URoleService{
    @Autowired
    private URoleMapper uRoleMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(URole record) {
        return uRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(URole record) {
        return 0;
    }

    @Override
    public URole selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(URole record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(URole record) {
        return 0;
    }

    @Override
    public List<URole> selectAllRole() {
        return uRoleMapper.selectAllRole();
    }
}
