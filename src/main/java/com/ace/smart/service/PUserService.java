package com.ace.smart.service;

import com.ace.smart.entity.PUser;
import com.ace.smart.entity.PUserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface PUserService {
    int deleteByPrimaryKey(Long id);

    int batchDelete(List<Long> list);

    int insert(PUser record);

    PUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PUserVo record);

    int updateByPrimaryKey(PUser record);

    PageInfo selectAll(int currentPage, int pageSize);

    int countUser();

    List<PUser> selectByIf(Map<String,Object> map);

    String validation(PUserVo pUser);

    String validationUpdate(PUserVo pUser);

    PUser selectByLoginName(String userLoginName);

    int updatePass(Map map);
}
