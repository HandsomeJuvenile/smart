package com.ace.smart.common.config.shiro;

import com.ace.smart.common.dao.RedisDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;

@Service
public class RedisSessionDao extends AbstractSessionDAO{

    // Session 超时时间
    private int expireTime = 60000;
    @Autowired
    private RedisDao redisDao;

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * @desc 创建session
     * @author zzh
     * @date 2018/8/13 15:07  
     * @param   
     * @return 
     */
    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("=========doCreate===========");
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session,sessionId);
        redisDao.setValue(session.getId(),session,expireTime);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
            return null;
        //return (Session)redisTemplate.opsForValue().get(serializable);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || StringUtils.isEmpty(session.getId())) {
            return;
        }
        session.setTimeout(expireTime);
        redisDao.setValue(session.getId(),session,expireTime);
    }

    @Override
    public void delete(Session session) {
         if (session == null) {
             return;
         }
        redisDao.delete(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return (Collection<Session>) redisDao.getAllKeys();
    }
}
