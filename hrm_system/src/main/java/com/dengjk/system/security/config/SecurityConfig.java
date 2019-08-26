package com.dengjk.system.security.config;


import com.dengjk.common.utils.MD5Util;
import com.dengjk.system.security.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private LoginFailureHnadle loginFailureHnadle;

    @Autowired
    private LoginSucessHnadle loginSucessHnadle;

    @Autowired
    private RemberMeConfig remberMeConfig;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    /**
     * 配置认证用户信息和权限
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(new PasswordEncoder() {
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String encode = MD5Util.encode((String) rawPassword);
                encodedPassword = encodedPassword.replace("\r\n", "");
                boolean result = encodedPassword.equals(encode);
                return result;
            }

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }
        });

        /**
         * 账号添加权限
         *
         * auth.inMemoryAuthentication().withUser("admin").password("123456").authorities("showOrder","addOrder","updateOrder","deleteOrder","findUser");
         */
    }

    // 配置拦截请求资源
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * 如何权限控制 给每一个请求路径 分配一个权限名称 让后账号只要关联该名称，就可以有访问权限
         */
        http.rememberMe() /**开启记住用户信息功能*/
                /**注入一个TokenRepository*/
                .tokenRepository(remberMeConfig.persistentTokenRepository())
                /**设置token有效时长 10天*/
                .tokenValiditySeconds(864000)
                /**最终去认证用户实现登入*/
                .userDetailsService(myUserDetailService);
        http.formLogin()
                /**系统的登入页面*/
                .loginPage("/sys/security/login")
                /**登入的登入接口,告知系统验证账号和密码*/
                .loginProcessingUrl("/sys/security/login/form")
                /**访问指定页面，用户未登入，跳转至登入页面，如果登入成功，跳转至用户访问指定页面，用户访问登入页面，默认的跳转页面*/
                .defaultSuccessUrl("/sys/security/loginSuccess")
                /**登入成功后，跳转至指定页面*/
                //.successForwardUrl("/sys/security/loginSuccess")
                //.failureUrl("/login?error")
                .successHandler(loginSucessHnadle).failureHandler(loginFailureHnadle);
        http.logout()
                /**退出后让当前session失效*/
                .invalidateHttpSession(true)
                /**删除的cookie*/
                .deleteCookies("HRM_JSESSIONID")
                /**动手退出登入触发的地址*/
                .logoutUrl("/sys/security/logout")
                /**退出登入的处理*/
                .logoutSuccessHandler(logoutSuccessHandler);
        /**403无权限配置*/
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        http.authorizeRequests()
                .antMatchers("/showOrder").hasAnyAuthority("showOrder")
                .antMatchers("/addOrder").hasAnyAuthority("addOrder")
                .antMatchers("/findUser").hasAnyAuthority("findUser")
                .antMatchers("/login").permitAll()
                .antMatchers("/updateOrder").hasAnyAuthority("updateOrder")
                .antMatchers("/deleteOrder").hasAnyAuthority("deleteOrder")
                /**shiro整合的模块全部放开*/
                .antMatchers("/sys/permisson/**").permitAll()
                .antMatchers("/sys/role/**").permitAll()
                .antMatchers("/sys/user/**").permitAll()
                .antMatchers("/loginError/**").permitAll()
                .antMatchers("/sys/security/login").permitAll()
                /**配置删除必须要有删除的权限*/
                .antMatchers("/sys/security/deleteOrder").hasAnyAuthority("sys_user_delete")
                .antMatchers("/sys/security/updateOrder").hasAnyAuthority("sys_user_update")
                .antMatchers("/**").fullyAuthenticated()
                .and().csrf().disable();
        /***
         * 动态查询权限,遍历每个权限资源设置
         */
      /*  List<BsPermission> listPermission = permissionMapper.findAllPermission();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http
                .authorizeRequests();
        for (Permission permission : listPermission) {
            authorizeRequests.antMatchers(permission.getUrl()).hasAnyAuthority(permission.getPermTag());
        }
        authorizeRequests.antMatchers("/login").permitAll().antMatchers("/**").fullyAuthenticated().and().formLogin()
                .loginPage("/login").and().csrf().disable();*/

    }
}
