package cn.hejinyo.config;

import cn.hejinyo.shiro.cache.RedisCacheManager;
import cn.hejinyo.shiro.filter.StatelessAuthcFilter;
import cn.hejinyo.shiro.filter.URLFilter;
import cn.hejinyo.shiro.realm.CredentialsMatcher;
import cn.hejinyo.shiro.realm.ModularRealm;
import cn.hejinyo.shiro.realm.StatelessAuthcTokenRealm;
import cn.hejinyo.shiro.realm.StatelessLoginTokenRealm;
import cn.hejinyo.shiro.subject.StatelessSubjectFactory;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/17 23:22
 * @Description :
 */

@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    /**
     * EhCache缓存管理器
     *
     * @return
     */
   /* @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
        return cacheManager;
    }*/

    /**
     * Redis缓存管理器，保留，还不太会用，生存时间不会设置
     *
     * @return
     */
   /* @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }*/

    // SecurityManager 安全管理器 有多个Realm,可使用'realms'属性代替
    @Bean("securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //禁用session 的subjectFactory
        securityManager.setSubjectFactory(new StatelessSubjectFactory());
        //禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions,所以需要配合context.setSessionCreationEnabled(false);
        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        //自定义realms
        List<Realm> realms = new ArrayList<>();
        realms.add(statelessLoginRealm());
        realms.add(statelessAuthcRealm());
        securityManager.setRealms(realms);
        //自定义 ModularRealm
        securityManager.setAuthenticator(defaultModularRealm(realms));
        //缓存管理器
        //securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 注入自定义拦截器,注意拦截器自注入问题
        Map<String, Filter> filters = new HashMap<>();
        filters.put("url", new URLFilter());
        filters.put("authc", authcFilter());
        factoryBean.setFilters(filters);

        // 拦截器链
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login/**", "anon");
        filterMap.put("/logout", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/wechat/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/**", "url,authc");
        factoryBean.setFilterChainDefinitionMap(filterMap);

        logger.debug("注入ShiroFilterFactoryBean成功");
        return factoryBean;
    }

    /**
     * 凭证匹配器
     *
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        CredentialsMatcher matcher = new CredentialsMatcher();
        matcher.setHashIterations(2);
        matcher.setHashAlgorithmName("md5");
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    /**
     * 配置使用自定义认证器，可以实现多Realm认证，并且可以指定特定Realm处理特定类型的验证
     *
     * @return
     */
    @Bean
    public ModularRealm defaultModularRealm(List<Realm> realms) {
        ModularRealm authenticator = new ModularRealm();
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
        //loginRealm.setCachingEnabled(false);
        return loginRealm;
    }

    /**
     * token验证Realm
     *
     * @return
     */
    @Bean
    public StatelessAuthcTokenRealm statelessAuthcRealm() {
        //authcRealm.setCachingEnabled(true);
        //启用授权缓存
        //authcRealm.setAuthorizationCachingEnabled(true);
        //authcRealm.setAuthorizationCacheName("authCache");
        return new StatelessAuthcTokenRealm();
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

    @Bean
    public StatelessAuthcFilter authcFilter() {
        return new StatelessAuthcFilter();
    }

    /**
     * 解决自定义拦截器混乱问题
     *
     * @param authcFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean registrationBean(StatelessAuthcFilter authcFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(authcFilter);
        registration.setEnabled(false);//取消自动注册功能 Filter自动注册,不会添加到FilterChain中.
        return registration;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean("lifecycleBeanPostProcessor")
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
     * 注解RequiresPermissions 需要此配置，否侧注解不生效，和上面aop搭配才有效,这里会出问题，导致controller失效，还没有解决方案
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
