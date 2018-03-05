package com.ace.smart.service;

import com.ace.smart.entity.UUser;

import java.util.List;

public interface UserService {
    int deleteByPrimaryKey(Long id);

    int insert(UUser record);

    int insertSelective(UUser record);

    UUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);

    UUser findUserRole(long id);

    UUser checkUsername(long id);

    List<UUser> selectAllUser();

}
