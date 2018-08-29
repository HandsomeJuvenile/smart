package com.ace.smart.common.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 相当于shiroConfig的配置文件
 * 改变成java的配置方式
 */
@Configuration
public class ShiroConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfig.class);
    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    @Autowired
    private RedisSessionDao sessionDao;
    
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager")SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login"); //设置的是跳转到登录界面的url
        //shiroFilterFactoryBean.setSuccessUrl("/index");//设置登录成功后取得页面
        //filterChainDefinitionMap.put("/Sys/**", "authc");//authc 指定需要认证的url  如果没有登录就使用这个url  则会直接跳转到toLogin界面
        filterChainDefinitionMap.put("/area/**", "anon");
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/bootstrapswitch/**", "anon"); // anon 不用登录也可以访问
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/jQuery/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/layuicms/**", "anon");
        filterChainDefinitionMap.put("/sys/**", "anon");
        filterChainDefinitionMap.put("/user/**", "anon");
        filterChainDefinitionMap.put("/zTree/**", "anon");
        //filterChainDefinitionMap.put("/logout/**", "logout");
        filterChainDefinitionMap.put("/**", "authc");  // 顺序拦截 按照顺序 对所有的静态文件 不设置拦截后  其他的都需要登录才可以
        //配置记住我或认证通过可以访问的地址
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

   /* // 配置核心安全事务管理器  用于本地测试所使用的SecurityManager
    @Bean(name = "securityManager")
    public SecurityManager securityManager(){
        LOGGER.debug("------shiro-----已经加载");
        DefaultSecurityManager securityManager = new DefaultSecurityManager(myRealm());
        return securityManager;
    }*/

    // 配置核心安全事务管理器  用于Web所使用的SecurityManager
    @Bean(name = "securityManager")
    public SecurityManager securityWebManager(){
        LOGGER.debug("------shiro-----已经加载");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(myRealm());
        //securityManager.setRememberMeManager(rememberMeManager());
        // 自定义session管理 使用redis
        securityManager.setCacheManager(cacheManager());
        //securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    @Bean  //  此处需要添加该注解  否则realm中无法进行自动注入
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher()); //
        return myRealm;
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * thylem模板中使用shiro标签
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * cookie对象
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie remberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");//  cookie的名称
        simpleCookie.setMaxAge(259200); // 记住我cookie的过期时间30天  单位是秒
        simpleCookie.setHttpOnly(true);//如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击
        return simpleCookie;
    }

    /**
     * cookie管理对象
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(remberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * @desc 配置session
     * @author zzh
     * @date 2018/8/13 16:11
     * @param
     * @return
     */
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        // 加入缓存器
        manager.setCacheManager(cacheManager());
        manager.setSessionDAO(sessionDao);
        manager.setDeleteInvalidSessions(true);
        manager.setGlobalSessionTimeout(sessionDao.getExpireTime());
        manager.setSessionValidationSchedulerEnabled(true);//是否定时检查session
        return manager;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     * @return
     */
   /* @Bean
    public EhCacheManager ehCacheManager(){
        LOGGER.info("shiro的ehcache缓存缓存管理");
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
        return ehCacheManager;
    }*/

   public RedisManager redisManager(){
       RedisManager redisManager = new RedisManager();
       redisManager.setHost("192.168.1.107");
       redisManager.setPort(6379);
       redisManager.setExpire(60000);
       return redisManager;
   }

   public RedisCacheManager cacheManager(){
       RedisCacheManager redisCacheManager = new RedisCacheManager();
       redisCacheManager.setRedisManager(redisManager());
       return redisCacheManager;
   }
}
