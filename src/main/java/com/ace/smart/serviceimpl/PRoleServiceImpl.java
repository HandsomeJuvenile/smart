package com.ace.smart.serviceimpl;

import com.ace.smart.util.CollectionUtil;
import com.ace.smart.entity.PPermission;
import com.ace.smart.entity.PRole;
import com.ace.smart.entity.PRolePermission;
import com.ace.smart.entity.PUser;
import com.ace.smart.entity.vo.PRoleVo;
import com.ace.smart.mapper.PRoleMapper;
import com.ace.smart.service.PPermissionService;
import com.ace.smart.service.PRolePermissionService;
import com.ace.smart.service.PRoleService;
import com.ace.smart.util.DateUtil;
import com.ace.smart.util.IdGen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
public class PRoleServiceImpl implements PRoleService{
    @Autowired
    private PRoleMapper pRoleMapper;
    @Autowired
    private PRolePermissionService pRolePermissionService;
    @Autowired
    private PPermissionService pPermissionService;

    @Override
    public int deleteByPrimaryKey(Long[] rId) {
        Assert.notNull(rId,"not null");
        return pRoleMapper.deleteByPrimaryKey(rId);
    }

    @Override
    public int insert(@Validated PRole recor) {
        return 0;
    }

    /**
     * 事务去添加权限
     * 事务还未完成!!!
     * @param record
     * @return
     */
    @Transactional
    @Override
    public int insertSelective(PRoleVo record) {
        Assert.notNull(record.getrName(),"添加角色名不可以为空");
        Long rid = IdGen.getAtomicCounter();
        record.setrId(rid);
        record.setCreateTime(DateUtil.getCurrentDate());
        int affectRole = pRoleMapper.insert(record);
        int affectRolePP = pRolePermissionService.insert(record);
        return affectRole>0?affectRole:0;
    }

    @Override
    public PRole selectByPrimaryKey(Long rId) {
        Assert.notNull(rId,"not null");
        return pRoleMapper.selectByPrimaryKey(rId);
    }

    /**
     * 修改单个权限表的记录
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(@Validated PRoleVo record) {
        return pRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PRole record) {
        return 0;
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public PageInfo selectAll(int currentPage, int pageSize,String rName) {
        PageHelper.startPage(currentPage,pageSize);
        List<PRole> list = pRoleMapper.selectAll(rName);
        return CollectionUtil.listIsNull(list)?new PageInfo(list):new PageInfo(new ArrayList<PUser>());
    }

    @Override
    public int addRolePer(PRoleVo pRoleVo) {
        return this.insertSelective(pRoleVo);
    }

    /**
     * 在所有的菜单中勾选出权限已选中的菜单
     * @param rid
     * @return
     */
    @Override
    public List<PPermission> selectChecked(long rid) {
        List<PPermission> list = pPermissionService.sortMenu(0);
        List<PRolePermission> pRolePermissions = pRolePermissionService.selectRolePperm(rid);
        List<PPermission> permissionList = checked(list,pRolePermissions);
        return permissionList;
    }

    @Transactional
    @Override
    public int update(PRoleVo record) {
        this.updateByPrimaryKeySelective(record);
        pRolePermissionService.delete(record.getrId());
        int aff = pRolePermissionService.insert(record);
        return aff;
    }

    /**
     * 递归已经分类好的菜单  设置勾选
     * @param list
     * @param pRolePermissionsList
     * @return
     */
    private List<PPermission> checked(List<PPermission> list,List<PRolePermission> pRolePermissionsList){
        for (PPermission pPermission: list) {
            for (PRolePermission pRolePermission:pRolePermissionsList) {
                if (pRolePermission.getPid().equals(pPermission.getMenuId()) ) {
                    pPermission.setChecked(true);
                    if (pPermission.getChildren()!=null) {
                        checked(pPermission.getChildren(),pRolePermissionsList);
                    }
                }
            }
        }
        return list;
    }

}