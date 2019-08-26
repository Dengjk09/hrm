package com.dengjk.system.security.service;

import com.dengjk.system.BsPermission;
import com.dengjk.system.BsRole;
import com.dengjk.system.BsUser;
import com.dengjk.system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    /**
     * 查询用户信息,校验用户信息,赋值权限
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**根据用户查询用户信息*/
        BsUser bsUser = userRepository.findAllByMobile(username);
        Assert.notNull(bsUser, "用户名不存在,请检查!!!");
        /**根据用户查询用户对应权限*/
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Set<BsRole> roles = bsUser.getRoles();
        for (BsRole permission : roles) {
            Set<BsPermission> permissions = permission.getPermissions();
            for (BsPermission bsPermission : permissions) {
                authorities.add(new SimpleGrantedAuthority(bsPermission.getCode()));
            }
        }
        MyUserDetail myUserDetail = new MyUserDetail(bsUser, authorities);
        return myUserDetail;
    }
}
