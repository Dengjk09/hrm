package com.dengjk.system.security.service;

import com.dengjk.system.BsUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 构建一个spring-security所需的用户信息
 */
@Data
public class MyUserDetail implements UserDetails {

    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 启用状态,0
     */
    private Boolean enableState;
    private String id;
    private String username;
    private String realname;
    private String password;
    private Date createDate;
    private Date lastLoginTime;


    /**
     * 用户是否已经启用
     */
    private boolean enabled = true;

    /**
     * 账户是否未过期,过期无法验证
     */
    private boolean accountNonExpired = true;

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    private boolean accountNonLocked = true;

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    private boolean credentialsNonExpired = true;

    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();


    /**
     * 提供指定初始化值的构造
     *
     * @param user
     * @param authorities
     */
    public MyUserDetail(BsUser user, List<GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.password = user.getPassword();
        this.mobile = user.getMobile();
        this.username=user.getMobile();
        this.mobile = mobile;
    }

    /**
     * 权限列表
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
