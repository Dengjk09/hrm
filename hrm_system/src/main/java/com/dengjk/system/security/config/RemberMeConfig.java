package com.dengjk.system.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * spring-security 的记住登入信息,多久是可以免登入的
 * <p>
 * 在用户登陆一次以后，系统会记住用户一段时间，在这段时间不管服务重启多少次 ,用户不用反复登陆就可以使用我们的系统。
 * <p>
 * 1.在用户第一次登入时候,如果认证成功了的话 会调用 RemeberMeService->TokenRepository,在当前的数据源下自动建立一个库(persistent_logins)。
 * 同时会将spring生成的token写入到数据库,并把token存入到浏览器的cookie中
 * <p>
 * 2.就算这个时候服务被重启了session失效了,用户再次请求需要授权的接口,会之前进入到RememberMeAuthenticationFilter 会去读Coookie中的
 * token 通过token->okenRepository去数据库查询用户信息   最终调用UserDetailsService认证用户实现登入
 */
@Configuration
public class RemberMeConfig {

    @Autowired
    private DruidDataSource druidDataSource;

    /**
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(druidDataSource);
        return jdbcTokenRepository;
    }
}
