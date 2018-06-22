package com.ace.smart.serviceimpl;

import com.ace.smart.entity.PUser;
import com.ace.smart.entity.SysLog;
import com.ace.smart.mapper.SysLogMapper;
import com.ace.smart.service.LoginService;
import com.ace.smart.service.SysLogService;
import com.ace.smart.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService{
    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private LoginService loginService;

    @Override
    public int insert(SysLog record) {
        record.setGmtCreate(DateUtil.getCurrentDate());
        if (record.getUserId() == null ) {
            PUser pUser = loginService.getLoginUser();
            if (pUser == null) {
                record.setUserId(-1l);
                record.setUsername("获取用户信息为空");
            }
            record.setUserId(pUser.getId());
            record.setUsername(pUser.getNickname());
        }
        return sysLogMapper.insert(record);
    }

    @Override
    public SysLog selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public Integer count() {
        return sysLogMapper.count();
    }

    @Override
    public PageInfo selectAll(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<SysLog> list = sysLogMapper.selectAll();
        if(list!=null && list.size()>0){
            PageInfo pageInfo = new PageInfo(list);
            return pageInfo;
        }
        return new PageInfo(new ArrayList<PUser>());
    }
}
