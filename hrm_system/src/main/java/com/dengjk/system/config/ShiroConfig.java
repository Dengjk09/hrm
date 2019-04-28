package com.dengjk.system.config;

import com.dengjk.system.config.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class ShiroConfig {


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
        /**设置跳转页面(登入的页面和失败的页面,登入成功之后的页面)*/
        factoryBean.setLoginUrl("/loginError?code=1");
        factoryBean.setUnauthorizedUrl("/loginError?code=2");
        /**设置过滤器集合*/
        Map<String, String> permisMap = new ConcurrentHashMap<>();
        /**放开权限,所有都可以访问*/
        permisMap.put("/sys/user/index", "anon");
        permisMap.put("/sys/user/loginByShiro", "anon");
        permisMap.put("/sys/user/**", "authc");
        /**
         * permisMap.put("/sys/user/**", "roles[管理员]");
         * permisMap.put("/sys/user/**", "perms[sys_user_delete]");
         *
         */
        /**map的k是要过滤的地址,map的v是要过滤的类型*/
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
}
