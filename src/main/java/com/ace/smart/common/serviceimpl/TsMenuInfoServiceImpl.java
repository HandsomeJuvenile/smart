package com.ace.smart.common.serviceimpl;

import com.ace.smart.common.mapper.TsMenuinfoMapper;
import com.ace.smart.common.util.CollectionUtil;
import com.ace.smart.common.entity.TsMenuinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TsMenuInfoServiceImpl implements TsMenuinfoMapper {
    @Autowired
    private TsMenuinfoMapper tsMenuinfoMapper;

    @Override
    public int deleteByPrimaryKey(String menuId) {
        return 0;
    }

    @Override
    public int insert(TsMenuinfo record) {
        return 0;
    }

    @Override
    public int insertSelective(TsMenuinfo record) {
        return 0;
    }

    @Override
    public TsMenuinfo selectByPrimaryKey(String menuId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(TsMenuinfo record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(TsMenuinfo record) {
        return 0;
    }

    /**
     * 查询某菜单下的子菜单
     * @param menu_Id
     * @return
     */
    @Override
    public  List<TsMenuinfo> selectByUpMenuId(String menu_Id) {
        return tsMenuinfoMapper.selectByUpMenuId(menu_Id);
    }


    /**
     * 使用递归完成无限极分类
     * @param menuId
     * @return
     */
    public List<TsMenuinfo> sysMenuItems(String menuId){
        List<TsMenuinfo> tsMenuinfoList = selectByUpMenuId(menuId);
        CollectionUtil.listIsNull(tsMenuinfoList);
            for (TsMenuinfo tsMenuinfo : tsMenuinfoList) {
                tsMenuinfo.setTsMenuinfoList(sysMenuItems(tsMenuinfo.getMenuId()));
            }
        return tsMenuinfoList;
    }
}
