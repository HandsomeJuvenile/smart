package com.ace.smart.mapper;

import com.ace.smart.entity.PUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PUserMapper {
    int deleteByPrimaryKey(Long id);

    int batchDelete(List<Long> list);

    int insert(PUser record);

    int insertSelective(PUser record);

    PUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PUser record);

    int updateByPrimaryKey(PUser record);

    List<PUser> selectAll();

    int countUser();

    List<PUser> selectByIf(Map<String,Object> map);

    PUser selectByLoginName(String userLoginName);
}