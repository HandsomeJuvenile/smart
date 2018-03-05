package com.ace.smart.serviceimpl;

import com.ace.smart.mapper.TsMenuinfoMapper;
import com.ace.smart.util.CollectionUtil;
import com.ace.smart.entity.TsMenuinfo;
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
     * 迭代  缺点 : 只可以有3级菜单
     * @param menuId
     * @return
     */
    public List<TsMenuinfo> sysMenuItemsIter(String menuId){
        List<TsMenuinfo> tsMenuinfoList = selectByUpMenuId("000000000000000");
        int count = 0;
        CollectionUtil.listIsNull(tsMenuinfoList);
        for (TsMenuinfo tsMenuinfo:tsMenuinfoList) {
            List<TsMenuinfo> tsSonMenuinfoList = selectByUpMenuId(tsMenuinfo.getMenuId());
            tsMenuinfo.setTsMenuinfoList(tsSonMenuinfoList);
            for (TsMenuinfo tsSonMeninfo :tsSonMenuinfoList){
                tsSonMeninfo.setTsMenuinfoList(selectByUpMenuId(tsSonMeninfo.getMenuId()));
            }
        }
        return tsMenuinfoList;
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
