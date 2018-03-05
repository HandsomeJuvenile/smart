package com.ace.smart.serviceimpl;

import com.ace.smart.entity.PRolePermission;
import com.ace.smart.entity.vo.PRoleVo;
import com.ace.smart.mapper.PRolePermissionMapper;
import com.ace.smart.service.PRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class PRolePermissionServiceImpl implements PRolePermissionService{
    @Autowired
    private PRolePermissionMapper pRolePermissionMapper;


    @Override
    public int insert(PRoleVo record) {
        String pid = record.getPpers();
        int affect = 0;
        if (pid.contains("on")) {
            pid = pid.substring(3,pid.length());
        }
        if (pid != null && !pid.isEmpty()) {
            String [] pids = pid.split(",");
            for (String str:pids) {
                PRolePermission pRolePermission = new PRolePermission();
                pRolePermission.setRid(record.getrId());
                pRolePermission.setPid(Long.parseLong(str));
                affect += pRolePermissionMapper.insert(pRolePermission);
            }
        }
        return affect;
    }

    @Override
    public int insertSelective(PRolePermission record) {
        return 0;
    }

    @Override
    public List<PRolePermission> selectRolePperm(long rid) {
        return pRolePermissionMapper.selectRolePperm(rid);
    }

    @Override
    public int delete(long rid) {
        Assert.notNull(rid,"not null");
        return pRolePermissionMapper.delete(rid);
    }
}
