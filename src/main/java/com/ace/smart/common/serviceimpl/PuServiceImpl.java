package com.ace.smart.common.serviceimpl;

import com.ace.smart.common.entity.PUser;
import com.ace.smart.common.entity.PUserVo;
import com.ace.smart.common.entity.PuImg;
import com.ace.smart.common.mapper.PUserMapper;
import com.ace.smart.common.service.PUserService;
import com.ace.smart.common.service.PuImgService;
import com.ace.smart.common.service.UUserRoleService;
import com.ace.smart.common.util.CollectionUtil;
import com.ace.smart.common.util.DateUtil;
import com.ace.smart.common.util.IdGen;
import com.ace.smart.common.util.PasswordUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PuServiceImpl implements PUserService {
    private static final Logger logger = LoggerFactory.getLogger(PuServiceImpl.class);

    @Autowired
    private PUserMapper pUserMapper;
    @Autowired
    private UUserRoleService uUserRoleService;
    @Autowired
    private PuImgService puImgService;

    /**
     * 查询所有用户
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo selectAll(int currentPage,int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<PUser> list = pUserMapper.selectAll();
        if(list!=null && list.size()>0){
            PageInfo pageInfo = new PageInfo(list);
            return pageInfo;
        }
        return new PageInfo(new ArrayList<PUser>());
    }

    @Override
    public int countUser() {
        return pUserMapper.countUser();
    }

    /**
     * 根据条件查询用户
     * @param map
     * @return
     */
    @Override
    public List<PUser> selectByIf(Map<String,Object> map){
        if(!map.isEmpty()){
            List<PUser> pUsers = pUserMapper.selectByIf(map);
            return pUsers;
        }
        return null;
    }

    @Override
    public PUser selectByPrimaryKey(Long id) {
        return pUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加用户
     * @param pUser
     * @return
     */
    // TODO: 2018/6/4 事务
    @Transactional
    @Override
    public int insert(PUser pUser) {
        String pswd = pUser.getPswd();
        long id = IdGen.getAtomicCounter();
        pUser.setId(id);
        pUser.setStatus("0");
        pUser.setPswd(PasswordUtil.encryptPassword(id+"",pswd));
        pUser.setCreateTime(DateUtil.getCurrentDate());
        pUser.setLastLoginTime(DateUtil.getCurrentDate());
        int affect = pUserMapper.insert(pUser);;

        // 设置默认头像
        PuImg puImg = new PuImg();
        puImg.setUploadTime(DateUtil.getCurrentDate());
        puImg.setImgId(IdGen.getAtomicCounter()+"");
        puImg.setJpgurl("/img/defaultHeadImg.jpg");
        puImg.setType("0");
        puImg.setUserId(id+"");
        puImgService.insertSelective(puImg);
        return affect;
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        if(id==null){
            return 0;
        }
        return pUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchDelete(List<Long> list) {
        return !CollectionUtil.listIsNull(list)?0:pUserMapper.batchDelete(list);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(PUserVo record) {
        if (record.getPswd() != null && !record.getPswd().isEmpty()) {
            record.setPswd(PasswordUtil.encryptPassword(record.getId() + "", record.getPswd()));
        }
        int affect=pUserMapper.updateByPrimaryKeySelective(record);
        if (record.getrId() != null && record.getrId()!=0l) {
             uUserRoleService.deluserRole(record.getId());
        }
        uUserRoleService.insert(record);
        return affect;
    }

    @Override
    public int updateByPrimaryKey(PUser record) {
        return pUserMapper.updateByPrimaryKey(record);
    }

    /**
     * 验证用户添加的信息是否准确
     * @param pUser
     * @return
     */
    @Override
    public String validation(PUserVo pUser){
        boolean flag = true;
        Map<String,Object> userMap = new HashMap<String ,Object>();
        if(pUser!=null){
            if(pUser.getUserLoginName()!=null){
                userMap.put("userLoginName",pUser.getUserLoginName());
                flag = CollectionUtil.listIsExit(selectByIf(userMap));
                userMap.remove("userLoginName");
                if(!flag){
                    return "1";
                }
            }
            if(pUser.getPhone()!=null){
                userMap.put("phone",pUser.getPhone());
                flag = CollectionUtil.listIsExit(selectByIf(userMap));
                userMap.remove("phone");
                if(!flag){
                    return "2";
                }
            }
            /*if(pUser.getToken()!=null && pUser.getEmail()!=null){
                EmailContainer emailContainer = EmailContainer.getInstance();
                Map<String,String> emailMap = emailContainer.getEmailMap();
                if(emailMap.get(pUser.getEmail())!=null){
                    String emailToken = emailMap.get(pUser.getEmail());
                    flag = emailToken.equals(pUser.getToken());
                    if(!flag){
                        return "3";
                    }
                    emailMap.remove(pUser.getEmail());
                }else{
                    return "5";
                }
            }*/
        }
       return "true";
    }

    /**
     * 验证用户修改的信息是否准确
     * @param pUser
     * @return
     */
    @Override
    public String validationUpdate(PUserVo pUser){
        Assert.notNull(pUser,"PUser is null");
        PUser user = selectByLoginName(pUser.getUserLoginName());
        boolean flag = true;
        Map<String,Object> userMap = new HashMap<String ,Object>();
        if(!pUser.getPhone().equals(user.getPhone())){
            userMap.put("phone",pUser.getPhone());
            flag = CollectionUtil.listIsExit(selectByIf(userMap));
            userMap.remove("phone");
            if(!flag){
                return "2";
            }
        }
       /* if(!pUser.getToken().equals(user.getEmail())){
            EmailContainer emailContainer = EmailContainer.getInstance();
            Map<String,String> emailMap = emailContainer.getEmailMap();
            if(emailMap.get(pUser.getEmail())!=null){
                String emailToken = emailMap.get(pUser.getEmail());
                flag = emailToken.equals(pUser.getToken());
                if(!flag){
                    return "3";
                }
                emailMap.remove(pUser.getEmail());
            }else{
                return "5";
            }
        }*/
        return "true";
    }

    /**
     * 根据用户登录名查询
     * @param userLoginName
     * @return
     */
    @Override
    public PUser selectByLoginName(String userLoginName) {
        return (userLoginName!=null&&!"".equals(userLoginName))?pUserMapper.selectByLoginName(userLoginName):new PUser();
    }

    /**
     * 更新密码
     * @param map
     * @return
     */
    @Override
    public int updatePass(Map map) {
        Assert.notNull(map,"map is not null");
        if(map.containsKey("id") && map.get("id")!=null){
            return pUserMapper.updatePass(map);
        }
        return 0;
    }

    /**
     * @desc 登录的时候 查询用户权限
     * @author zzh
     * @date 2018/5/7 15:35  
     * @param username
     * @return pu
     */
    @Override
    public PUser findUserRole(String username) {
        if(username.isEmpty()){
            return null;
        }
        return pUserMapper.findUserRole(username);
    }

    /**
     * 查询用户信息和头像信息
     * @param username
     * @return
     */
    @Override
    public com.ace.smart.common.entity.vo.PUserVo selectUserAndImgByLoginName(String username) {
        return pUserMapper.selectUserAndImgByLoginName(username);
    }

    /**
     * @desc 修改密码前 查询旧密码是否正确
     * @author zzh
     * @date 2018/6/1 9:34
     * @param
     * @return
     */
    @Override
    public boolean selectPswdByName(String id,String username,String oldpswd) {
        String pswd = pUserMapper.selectPswdByName(username);
        if (pswd !=null && !pswd.isEmpty()) {
            if (pswd.equals(PasswordUtil.encryptPassword(id+"",oldpswd))) {
                return true;
            }
        }
        return false;
    }


}
