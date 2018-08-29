package com.ace.smart.common.mapper;

import com.ace.smart.common.entity.UUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UUserMapper {
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