package com.dengjk.system.config;

import com.dengjk.common.enums.PermAuthTypeEnum;
import com.dengjk.common.enums.PermissionTypeEnum;
import com.dengjk.system.BsPermission;
import com.dengjk.system.PePermissionApi;
import com.dengjk.system.config.realm.MyRealm;
import com.dengjk.system.dao.PermissionApiRepository;
import com.dengjk.system.dao.PermissionRepository;
import com.dengjk.system.service.impl.MySessionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Dengjk
 * @create 2019-04-28 16:00
 * @desc springboot整合shiro配置
 * <p>
 * 配置权限鉴定的两种方式：
 * 1.过滤器拦截ShiroFilterFactoryBean
 * 2.使用注解来配置  权限：1.@ RequiresPermissions("sys_user_delete") 角色：2.@RequiresRoles("管理员")
 * <p>
 * 往容器中注入四个bean
 * 1.创建自定义的realm
 * 2.配置安全管理器  securityManager
 * 3.配置shiro的过滤器工厂
 * 4.开启shiro对注解支持
 **/
@Configuration
@Slf4j
@EnableCaching
public class ShiroConfig {


    @Value("${spring.redis.host}")
    private String redistHost;

    @Value("${spring.redis.database}")
    private String dataBase;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private String port;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionApiRepository permissionApiRepository;

    /**
     * 往容器中注入自定义的realm域
     *
     * @return
     */
    @Bean
    public Realm getRealm() {
        return new MyRealm();
    }


    /**
     * 配置安全管理器  securityManager
     *
     * @param realm
     * @return
     */
    @Bean
    public SecurityManager getSecurityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        /**
         * 设置redis-session缓存
         */
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    /**
     * 要过滤的类型:
     * （1）认证过滤器：anon、authcBasic、auchc、user、logout
     * （2）授权过滤器：perms、roles、ssl、rest、port
     * 类型解释:
     * （1）anon：匿名过滤器，表示通过了url配置的资源都可以访问，例：“/statics/**=anon”表示statics目录下所有资源都能访问
     * （2）authc：基于表单的过滤器，表示通过了url配置的资源需要登录验证，否则跳转到登录，例：“/unauthor.jsp=authc”如果用户没有登录访问unauthor.jsp则直接跳转到登录
     * （3）authcBasic：Basic的身份验证过滤器，表示通过了url配置的资源会提示身份验证，例：“/welcom.jsp=authcBasic”访问welcom.jsp时会弹出身份验证框
     * （4）perms：权限过滤器，表示访问通过了url配置的资源会检查相应权限，例：“/statics/**=perms["user:add:*,user:modify:*"]“表示访问statics目录下的资源时只有新增和修改的权限
     * （5）port：端口过滤器，表示会验证通过了url配置的资源的请求的端口号，例：“/port.jsp=port[8088]”访问port.jsp时端口号不是8088会提示错误
     * （6）rest：restful类型过滤器，表示会对通过了url配置的资源进行restful风格检查，例：“/welcom=rest[user:create]”表示通过restful访问welcom资源时只有新增权限
     * （7）roles：角色过滤器，表示访问通过了url配置的资源会检查是否拥有该角色，例：“/welcom.jsp=roles[admin]”表示访问welcom.jsp页面时会检查是否拥有admin角色
     * （8）ssl：ssl过滤器，表示通过了url配置的资源只能通过https协议访问，例：“/welcom.jsp=ssl”表示访问welcom.jsp页面如果请求协议不是https会提示错误
     * （9）user：用户过滤器，表示可以使用登录验证/记住我的方式访问通过了url配置的资源，例：“/welcom.jsp=user”表示访问welcom.jsp页面可以通过登录验证或使用记住我后访问，否则直接跳转到登录
     * （10）logout：退出拦截器，表示执行logout方法后，跳转到通过了url配置的资源，例：“/logout.jsp=logout”表示执行了logout方法后直接跳转到logout.jsp页面
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean filterFactoryBean(SecurityManager securityManager) {
        /**创建shiro过滤器工厂*/
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        /**设置安全管理器*/
        factoryBean.setSecurityManager(securityManager);
        /**设置过滤器集合*/
        Map<String, String> permisMap = new ConcurrentHashMap<>();
        /**设置跳转页面(登入的页面和失败的页面,登入成功之后的页面)*/
        factoryBean.setLoginUrl("/loginError?code=1");
        factoryBean.setUnauthorizedUrl("/loginError?code=2");
        /**放开权限,所有都可以访问*/
        /**map的k是要过滤的地址,map的v是要过滤的类型*/
        permisMap.put("/sys/user/index", "anon");
        permisMap.put("sys/user/login", "anon");
        permisMap.put("/loginError", "anon");
        permisMap.put("/sys/user/loginByShiro", "anon");
        /**
         * permisMap.put("/sys/user/**", "roles[管理员]");
         * permisMap.put("/sys/user/**", "perms[sys_user_delete]");
         */
        /**加载数据库配置动态的设置权限*/
        this.dynamicPermis(permisMap);
        /**拦截所有放在最后*/
        permisMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(permisMap);
        return factoryBean;
    }


    /**
     * 开启shiro对注解的支持
     * <p>
     * ==========使用注解如果权限不匹配,不会跳转到UnauthorizedUrl,而是直接抛异常,我们使用全局异常处理,响应结果 authorizationHandler
     * <p>
     * 两个注解,加在controller方法上,表示该接口必须要有该权限
     * <p>
     * 1.@ RequiresPermissions("sys_user_delete")
     * 2.@RequiresRoles("管理员")
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor attributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }


    /**
     * 配置shiro redisManager
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redistHost);
        redisManager.setDatabase(15);
        redisManager.setPort(Integer.parseInt(port));
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     *
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        log.info("创建RedisCacheManager...");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     *
     * @return
     */
    @Bean
    public ShiroRedisSessionDao redisSessionDAO() {
        /*RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());*/
        /**自己实现sessionDao,使用redisTemplate存储数据,value的序列化必须是jdk的字节实现 redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());*/
        ShiroRedisSessionDao redisSessionDao = new ShiroRedisSessionDao(redisTemplate);
        return redisSessionDao;
    }


    /**
     * cookie管理对象;记住我功能
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //cookieRememberMeManager.setCookie(remeberMeCookie());
        /**rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)*/
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }


    /***
     * session管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        /**设置session超时时间-单位毫秒*/
        int ttl = 1000 * 60 * 60 * 24;
        sessionManager.setGlobalSessionTimeout(ttl);
        /**隔多久检查一次session的有效期*/
        sessionManager.setSessionValidationInterval(ttl);
        sessionManager.setDeleteInvalidSessions(true);
        /**删除过期的session*/
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setCacheManager(redisCacheManager());
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }


    /**
     * 设置cookie
     *
     * @return
     */
    public Cookie sessionIdCookie() {
        Cookie sessionIdCookie = new SimpleCookie("hrm");
        sessionIdCookie.setMaxAge(86400);
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setPath("/");
        sessionIdCookie.setName("hrm");
        return sessionIdCookie;
    }


    public SimpleCookie remeberMeCookie() {
        SimpleCookie scookie = new SimpleCookie("rememberMe");
        scookie.setMaxAge(86400 * 30);
        scookie.setHttpOnly(true);
        scookie.setPath("/");
        return scookie;
    }

    /***
     * 动态查询数据库,添加权限
     * @param permisMap
     */
    private void dynamicPermis(Map<String, String> permisMap) {
        Set<BsPermission> permissions = permissionRepository.findByType(PermissionTypeEnum.api.getCode());
        Set<String> apiId = permissions.stream().map(x -> x.getId()).collect(Collectors.toSet());
        List<PePermissionApi> permissionApis = permissionApiRepository.findAllById(apiId);
        permissionApis.stream().filter(x -> StringUtils.isNotEmpty(x.getApiUrl())).forEach(x -> {
            String permType = PermAuthTypeEnum.getPermAuthTypeEnum(x.getApiAuthType()).getName();
            if (x.getApiAuthType() == 3 || x.getApiAuthType() == 4) {
                /**如果是设置权限或者角色,就要特殊处理*/
                BsPermission bsPermission = permissions.stream().filter(y -> StringUtils.equals(y.getId(), x.getId())).findFirst().orElse(null);
                permisMap.put(x.getApiUrl(), String.format(permType, bsPermission.getCode()));
            } else {
                permisMap.put(x.getApiUrl(), permType);
            }
        });
    }
}