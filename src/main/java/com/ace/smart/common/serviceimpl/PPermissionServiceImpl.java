package com.ace.smart.common.serviceimpl;

import com.ace.smart.common.mapper.PPermissionMapper;
import com.ace.smart.common.entity.PPermission;
import com.ace.smart.common.entity.vo.PPermissionVo;
import com.ace.smart.common.service.PPermissionService;
import com.ace.smart.common.util.DateUtil;
import com.ace.smart.common.util.IdGen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PPermissionServiceImpl implements PPermissionService{
    @Autowired
    private PPermissionMapper pPermissionMapper;

    /**
     * 删除权限
     * @param menuId
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long menuId) {
        if (menuId==null){
            return 0;
        }
        int affect = pPermissionMapper.deleteByPrimaryKey(menuId);
        return affect>0?affect:0;
    }

    /**
     * 插入权限
     * @param record
     * @return
     */
    @Override
    public int insert(PPermission record) {
        if(record!=null && !record.getName().isEmpty()){
            record.setMenuId(IdGen.getAtomicCounter());
            record.setUpdateTime(DateUtil.getCurrentDate());
            record.setCreateTime(DateUtil.getCurrentDate());
            record.setStatus("1");
            record.setLevel(1);
            return pPermissionMapper.insert(record);
        }
        return 0;
}

    /**
     *
     * @param menuId
     * @return
     */
    @Override
    public PPermission selectByPrimaryKey(Long menuId) {
        return null;
    }

    /**
     * 根据传来的权限有选择的更新
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(PPermission record) {
        if(record!=null && record.getMenuId()!=null){
            if("".equals(record.getParentId()) || record.getParentId() == null){
                record.setParentId("0");
            }
            record.setUpdateTime(DateUtil.getCurrentDate());
            return  pPermissionMapper.updateByPrimaryKeySelective(record);
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKey(PPermission record) {
        if(record==null || record.getMenuId()==null ){
            return 0;
        }
        int affect = pPermissionMapper.updateByPrimaryKey(record);
        return  affect>0?affect:0;
    }

    @Override
    public PageInfo selectAll(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<PPermission> permissionList = pPermissionMapper.selectAll();
        PageInfo pageInfo = new PageInfo(permissionList);
        return pageInfo;
    }

    /**
     * 递归对菜单进行分级别
     * @param menuId
     * @return
     */
    @Override
    public List<PPermission> sortMenu(long menuId) {
        List<PPermission> permissionList = pPermissionMapper.selectByParent(menuId);
        for (PPermission pPermission : permissionList) {
            pPermission.setChildren(sortMenu(pPermission.getMenuId()));
        }
        return permissionList;
    }

    /**
     * 查询所有菜单 并且支持菜单搜索 和跳转到子菜单
     * @param currentPage
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo selectAllByParetnId(int currentPage, int pageSize, HashMap<String ,Object> map) {
        PageHelper.startPage(currentPage,pageSize);
        List<PPermissionVo> permissionList = pPermissionMapper.selectAllByParetnId(map);
        PageInfo pageInfo = new PageInfo(permissionList);
        return pageInfo;
    }

    /**
     * 查询子菜单
     * @param menuId
     * @return
     */
    @Override
    public List<PPermission> sonNumber(Long menuId) {
        return pPermissionMapper.selectByParent(menuId);
    }

    /**
     * 更新菜单的状态
     * @param map
     * @return
     */
    @Override
    public int updateState(Map<String, Object> map) {
        Assert.notNull(map.get("menuId"),"菜单id不可以为空");
        Assert.notNull(map.get("status"),"菜单状态不可以为空");
        return pPermissionMapper.updateState(map);
    }

    /**
     * 查询所有启用的菜单
     * @return
     */
    @Override
    public List<PPermission> selectAllMenu() {
        List<PPermission> list = pPermissionMapper.selectAllMenu();
        List<PPermission> sortList = this.sortMenu(list,"0");
        return sortList;
    }

    /**
     * 一次性将所有的数据查询出来 然后进行分类
     * 1. 首先对第一个元素查询 得到父级id  遍历集合  找到这个元素对应的父级  如果没有那么就是1级菜单
     * 但是 会出现问题  假如说
     * 2级某个菜单  找到了父级  但是他的子菜单 就没办法再找到他了
     *
     * 2. 先遍历得到一级菜单 放入到一个新的集合中去
     *    找到
     *
     * 3. 先一条条的确定完成
     *    先找到第一个父节点 然后在找到他的所有子节点
     *    如何完成无限极菜单呢?
     *    1. 首选遍历查找根节点 的一个元素
     *    2. 然后根据根节点的menuID  去查找他的二级菜单 并将得到的菜单组成list集合存入进去
     *    3. 在循环遍历二级菜单  得到他们的menuID  去进行操作
     *
     *    首先第一个循环  是一级菜单
     * 4.
     * @return
     */
    @Override
    public List<PPermission> sortMenu(List<PPermission> list,String id) {
        List<PPermission> newlist = new ArrayList<>();
        for (PPermission pPermission:list) {
            // 一级菜单
            if (pPermission.getParentId().equals(id)) {
                long menuId = pPermission.getMenuId();
                for (PPermission pPermissionTwo : list) {
                    if (Long.parseLong(pPermissionTwo.getParentId()) == menuId ) {
                        // 二级菜单
                        if (pPermission.getChildren() == null || pPermission.getChildren().size()<1) {
                            pPermission.setChildren(new ArrayList<PPermission>());
                        }
                        pPermission.getChildren().add(pPermissionTwo);
                        sortMenu(list,pPermissionTwo.getMenuId()+"");
                    }
                }
                newlist.add(pPermission);
            }
        }
        return newlist;
    }

}
