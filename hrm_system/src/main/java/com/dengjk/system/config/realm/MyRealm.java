package com.dengjk.system.config.realm;

import com.dengjk.system.BsUser;
import com.dengjk.system.dao.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Dengjk
 * @create 2019-04-28 15:09
 * @desc 自定义realm域 继承自AuthorizingRealm重写两个方法
 **/
public class MyRealm extends AuthorizingRealm {


    @Autowired
    private UserRepository userRepository;


    public void setName(String name) {
        super.setName("myRealm");
    }

    /**
     * 认证方法
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /**获取在service登入时候传进来的用户名和密码*/
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        String username = userToken.getUsername();
        String password = new String(userToken.getPassword());
        BsUser user = userRepository.findAllByMobile(username);
        if (user != null && StringUtils.equals(password, user.getPassword())) {
            /**构造一个简单的AuthenticationInfo返回*/
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, username);
            return info;
        }
        /**返回一个空会抛一个异常,在loginByShiro方法中就可以try住*/
        return null;
    }


    /**
     * 授权方法
     * <p>
     * 把所有的角色和权限赋值到SimpleAuthorizationInfo中去
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /**获取认证之后的用户信息*/
        BsUser user = (BsUser) principalCollection.getPrimaryPrincipal();
        user = userRepository.findAllByMobile(user.getMobile());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        /**把用户的角色和权限赋值到 SimpleAuthenticationInfo对象中去*/
        Set<String> roles = user.getRoles().stream().map(x -> x.getName()).collect(Collectors.toSet());
        Set<String> permis = user.getRoles().stream().map(x -> x.getPermissions())
                .flatMap(x -> x.stream()).map(x -> x.getCode()).collect(Collectors.toSet());
        info.setRoles(roles);
        info.setStringPermissions(permis);
        return info;
    }
}
