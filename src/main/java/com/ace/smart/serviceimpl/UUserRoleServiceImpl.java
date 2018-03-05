package com.ace.smart.serviceimpl;

import com.ace.smart.entity.PUserVo;
import com.ace.smart.service.UUserRoleService;
import com.ace.smart.entity.UUserRole;
import com.ace.smart.mapper.UUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UUserRoleServiceImpl implements UUserRoleService {
    @Autowired
    private UUserRoleMapper userRoleMapper;

    @Transactional
    @Override
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

}
