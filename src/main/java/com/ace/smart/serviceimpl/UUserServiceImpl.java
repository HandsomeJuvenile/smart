package com.ace.smart.serviceimpl;

import com.ace.smart.mapper.UUserMapper;
import com.ace.smart.entity.UUser;
import com.ace.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UUserServiceImpl implements UserService {
    @Autowired
    private UUserMapper uUserMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(UUser uuser) {
        return uUserMapper.insert(uuser);
    }

    @Override
    public int insertSelective(UUser record) {
        return 0;
    }

    @Override
    public UUser selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(UUser record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(UUser record) {
        return 0;
    }

    @Override
    public UUser findUserRole(long id) {
        return uUserMapper.findUserRole(id);
    }

    @Override
    public UUser checkUsername(long id) {
        UUser uUser = uUserMapper.checkUsername(id);
        if(uUser!=null){
            return uUser;
        }
        return null;
    }

    @Override
    public List<UUser> selectAllUser() {
        return uUserMapper.selectAllUser();
    }
}