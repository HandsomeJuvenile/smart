package com.ace.smart.common.serviceimpl;

import com.ace.smart.common.entity.PUserVo;
import com.ace.smart.common.service.UUserRoleService;
import com.ace.smart.common.entity.UUserRole;
import com.ace.smart.common.mapper.UUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UUserRoleServiceImpl implements UUserRoleService {
    @Autowired
    private UUserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public int insert(PUserVo pUserVo) {
        int affetct = 0;
        UUserRole uUserRole = new UUserRole();
        uUserRole.setUid(pUserVo.getId());
        uUserRole.setRid(pUserVo.getrId());
        affetct += userRoleMapper.insert(uUserRole);
        return affetct;
    }

    @Override
    public int insertSelective(UUserRole record) {
        return 0;
    }

    /**
     * 移除权限
     * @param id
     */

    @Override
    @Transactional
    public int deluserRole(Long id) {
        return userRoleMapper.deluserRole(id);
    }

    @Override
    public UUserRole selectByPrimaryId(Long id) {
        return userRoleMapper.selectByPrimaryId(id);
    }

}
