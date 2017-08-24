package cn.hejinyo.config;

import cn.hejinyo.shiro.realm.ModularRealm;
import cn.hejinyo.shiro.realm.CredentialsMatcher;
import cn.hejinyo.shiro.realm.StatelessAuthcTokenRealm;
import cn.hejinyo.shiro.realm.StatelessLoginTokenRealm;
import cn.hejinyo.shiro.subject.StatelessSubjectFactory;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/17 23:22
 * @Description :
 */

@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // SecurityManager 安全管理器 有多个Realm,可使用'realms'属性代替
        factoryBean.setSecurityManager(securityManager);

        // 注入自定义拦截器,自定义拦截器会导致拦截器链混乱，如/** 会拦截之前所有的拦截器链，所以放弃，使用springmvc的拦截器
        //factoryBean.getFilters().put("login", loginFilter());
        //factoryBean.getFilters().put("mnone", mnoneFilter());
        //factoryBean.getFilters().put("authc", authenticationFilter());

        // 拦截器链
       /* factoryBean.getFilterChainDefinitionMap().put("/login", "login");
        factoryBean.getFilterChainDefinitionMap().put("/druid/**", "anon");
        factoryBean.getFilterChainDefinitionMap().put("/wechat/**", "anon");
        factoryBean.getFilterChainDefinitionMap().put("/**", "authc");*/
        logger.debug("注入ShiroFilterFactoryBean成功");
        return factoryBean;
    }

    // SecurityManager 安全管理器 有多个Realm,可使用'realms'属性代替
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //禁用session 的subjectFactory
        securityManager.setSubjectFactory(subjectFactory());
        /*
        * 禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions
        * 所以需要配合context.setSessionCreationEnabled(false);
        */
        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);

        //自定义realms
        List<Realm> realms = new ArrayList<>();
        realms.add(statelessLoginRealm());
        realms.add(statelessAuthcRealm());
        securityManager.setRealms(realms);
        //自定义 ModularRealm
        securityManager.setAuthenticator(defaultModularRealm());
        //缓存管理器
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * 缓存管理器
     *
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
        return cacheManager;
    }


    /**
     * 凭证匹配器
     *
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        CredentialsMatcher matcher = new CredentialsMatcher(ehCacheManager());
        matcher.setHashIterations(2);
        matcher.setHashAlgorithmName("md5");
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    /**
     * Subject工厂,禁用了session
     *
     * @return
     */
    @Bean
    public StatelessSubjectFactory subjectFactory() {
        return new StatelessSubjectFactory();
    }

    /**
     * 配置使用自定义认证器，可以实现多Realm认证，并且可以指定特定Realm处理特定类型的验证
     *
     * @return
     */
    @Bean
    public ModularRealm defaultModularRealm() {
        ModularRealm authenticator = new ModularRealm();
        //配置认证策略，只要有一个Realm认证成功即可，并且返回所有认证成功信息
        List<Realm> realms = new ArrayList<>();
        realms.add(statelessLoginRealm());
        realms.add(statelessAuthcRealm());
        authenticator.setRealms(realms);
        return authenticator;
    }

    /**
     * 登录创建token的Realm
     *
     * @return
     */
    @Bean
    public StatelessLoginTokenRealm statelessLoginRealm() {
        StatelessLoginTokenRealm loginRealm = new StatelessLoginTokenRealm();
        //自定义凭证匹配器
        loginRealm.setCredentialsMatcher(credentialsMatcher());
        //登录不启用缓存，默认false
        loginRealm.setCachingEnabled(false);
        return loginRealm;
    }

    /**
     * token验证Realm
     *
     * @return
     */
    @Bean
    public StatelessAuthcTokenRealm statelessAuthcRealm() {
        StatelessAuthcTokenRealm authcRealm = new StatelessAuthcTokenRealm();
        authcRealm.setCachingEnabled(true);
        //启用授权缓存
        authcRealm.setAuthorizationCachingEnabled(true);
        authcRealm.setAuthorizationCacheName("authCache");
        return authcRealm;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 注解RequiresPermissions 需要此配置，否侧注解不生效，和上面aop搭配才有效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 在方法中 注入  securityManager ，进行代理控制,相当于调用SecurityUtils.setSecurityManager(securityManager)
     *
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager()});
        return factoryBean;
    }
}
