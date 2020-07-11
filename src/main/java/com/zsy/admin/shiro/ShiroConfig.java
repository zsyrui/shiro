package com.zsy.admin.shiro;

import com.zsy.admin.filter.CORSAuthenticationFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Classname LoginController
 * @Description TODO
 * @Date 2020/3/21 16:05
 * @Created by yuan1
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCacheManager(cacheManager());
        return shiroRealm;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //设置遇到未登录、未授权等情况时候，请求这些地址，返回相应的错误
        shiroFilter.setLoginUrl("/shiroError/login");
        shiroFilter.setUnauthorizedUrl("/shiroError/unauthorized");

        //拦截器，配置访问权限 必须是LinkedHashMap，因为它必须保证有序。滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        // 配置不会被拦截的链接 顺序判断
        filterMap.put("/login", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/reg", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/webjars/**", "anon");

        //剩余的请求shiro都拦截
        filterMap.put("/**/*", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterMap);


        //自定义拦截器
        Map<String, Filter> customFilterMap = new LinkedHashMap<>();
        customFilterMap.put("corsAuthenticationFilter", new CORSAuthenticationFilter());
        shiroFilter.setFilters(customFilterMap);

        return shiroFilter;
    }
    /**
     * securityManager 核心配置
     * 安全控制层
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //如果不是前后端分离，则不必设置下面的sessionManager
        securityManager.setSessionManager(sessionManager());

        //使用自定义的cacheManager
        securityManager.setCacheManager(cacheManager());

        //设置realm（推荐放到最后，不然某些情况会不生效）
        securityManager.setRealm(shiroRealm());



        return securityManager;
    }
    /**
     * 密码加解密规则
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();

        //设置散列算法：这里使用的MD5算法
        credentialsMatcher.setHashAlgorithmName("md5");

        //散列次数，好比散列2次，相当于md5(md5(xxxx))
        credentialsMatcher.setHashIterations(2);

        return credentialsMatcher;
    }
    /**
     * 开启shiro 的AOP注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    //自定义sessionManager
    @Bean
    public SessionManager sessionManager(){

        ShiroSession sessionManager = new ShiroSession();

        //超时时间，默认 30分钟，会话超时；方法里面的单位是毫秒
        //customSessionManager.setGlobalSessionTimeout(20000);

        //配置session持久化
        sessionManager.setSessionDAO(redisSessionDAO());

        return sessionManager;
    }

    /**
     * 配置redisManager
     *
     */
    public RedisManager getRedisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("139.155.31.185");
        redisManager.setPort(20000);
        redisManager.setPassword("123456");
        redisManager.setExpire(1800);

        return redisManager;
    }


    /**
     * 配置具体cache实现类
     * @return
     */
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(getRedisManager());

        //设置过期时间，单位是秒，20s
        return redisCacheManager;
    }


    /**
     * 自定义session持久化
     * @return
     */
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());

        //设置sessionid生成器
        //redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());

        return redisSessionDAO;
    }


    /**
     * 管理shiro一些bean的生命周期 即bean初始化 与销毁
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     *  api controller 层面
     *  加入注解的使用，不加入这个AOP注解不生效(shiro的注解 例如 @RequiresGuest)
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


    /**
     *  用来扫描上下文寻找所有的Advistor(通知器),
     *  将符合条件的Advisor应用到切入点的Bean中，需要在LifecycleBeanPostProcessor创建后才可以创建
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


}
