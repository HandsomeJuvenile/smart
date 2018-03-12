package com.ace.smart.serviceimpl;

import com.ace.smart.container.EmailContainer;
import com.ace.smart.entity.PUser;
import com.ace.smart.entity.PUserVo;
import com.ace.smart.mapper.PUserMapper;
import com.ace.smart.service.PUserService;
import com.ace.smart.service.UUserRoleService;
import com.ace.smart.util.CollectionUtil;
import com.ace.smart.util.DateUtil;
import com.ace.smart.util.IdGen;
import com.ace.smart.util.PasswordUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PuServiceImpl implements PUserService {
    private static final Logger logger = LoggerFactory.getLogger(PuServiceImpl.class);

    @Autowired
    private PUserMapper pUserMapper;
    @Autowired
    private UUserRoleService uUserRoleService;

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
    @Override
    public int insert(PUser pUser) {
        long id = IdGen.getAtomicCounter();
        pUser.setId(IdGen.getAtomicCounter());
        pUser.setStatus("0");
        pUser.setPswd(PasswordUtil.encryptPassword(id+"",pUser.getPswd()));
        pUser.setCreateTime(DateUtil.getCurrentDate());
        pUser.setLastLoginTime(DateUtil.getCurrentDate());
        int affect = pUserMapper.insert(pUser);;
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
    public int updateByPrimaryKeySelective(PUserVo record) {
        record.setPswd(PasswordUtil.encryptPassword(record.getId()+"",record.getPswd()));
        int affect=pUserMapper.updateByPrimaryKeySelective(record);
        uUserRoleService.deluserRole(record.getId());
        uUserRoleService.insert(record);
        return affect;}

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

    @Override
    public int updatePass(Map map) {
        Assert.notNull(map,"map is not null");
        if(map.containsKey("id") && map.get("id")!=null){
            return pUserMapper.updatePass(map);
        }
        return 0;
    }
}
