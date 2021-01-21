package com.dengjk.system.service.impl;

import com.dengjk.common.exception.LoginErrorException;
import com.dengjk.common.utils.JwtUtils;
import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import com.dengjk.system.BsPermission;
import com.dengjk.system.BsRole;
import com.dengjk.system.BsUser;
import com.dengjk.system.dao.PermissionRepository;
import com.dengjk.system.dao.RoleRepository;
import com.dengjk.system.dao.UserRepository;
import com.dengjk.system.service.PermissionService;
import com.dengjk.system.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private PermissionRepository permissionRepository;


    @Autowired
    private PermissionService permissionService;

    /**
     * 新增用户
     *
     * @param bsUser
     * @return
     */
    @Override
    public Result addUser(BsUser bsUser) {
        /**秘密加密*/
        String password = new Md5Hash(bsUser.getPassword(), bsUser.getMobile(), 3).toString();
        bsUser.setPassword(password);
        userRepository.save(bsUser);
        return ResultUtil.success("添加成功");
    }


    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public Result findUserById(String id) {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        List list = principals.asList();
        BsUser bsUser = (BsUser) principals.getPrimaryPrincipal();
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        return ResultUtil.success(userRepository.findById(id));
    }


    @Override
    public Result deleteUserById(String id) {
        userRepository.deleteById(id);
        return ResultUtil.success("删除成功");
    }


    /**
     * @param dataMap
     * @return
     */
    @Override
    public Result assignRoles(Map<String, Object> dataMap) {
        String userId = (String) dataMap.get("userId");
        List<String> roles = (List<String>) dataMap.get("roleIds");
        /**根据id查出用户信息*/
        BsUser bsUser = userRepository.findById(userId).get();
        Set<BsRole> rolesDb = bsUser.getRoles();
        Set<BsRole> byIdIn = roleRepository.findByIdIn(roles);
        /**把角色设置到用户*/
        bsUser.setRoles(byIdIn);
        userRepository.save(bsUser);
        return ResultUtil.success("保存成功");
    }

    /**
     * 用于请求登入
     *
     * @param dataMap
     * @return
     */
    @Override
    public Result userLogin(Map<String, Object> dataMap) throws LoginErrorException {
        String mobile = (String) dataMap.get("mobile");
        String password = (String) dataMap.get("password");
        BsUser allByMobile = userRepository.findAllByMobile(mobile);
        if (allByMobile == null) {
            throw new LoginErrorException("用户不存在,请先注册!!!");
        }
        if (!StringUtils.equals(allByMobile.getPassword(), password)) {
            throw new LoginErrorException("密码有误,请重新登入!!!");
        }
        /**生成jwt*/
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("companyId", allByMobile.getCompanyId());
        String jwt = jwtUtils.createJwt(allByMobile.getId(), allByMobile.getUserName(), userMap);
        return ResultUtil.success(jwt);
    }


    /**
     * 通过shiro-token获取用户信息
     *
     * @param authorization
     * @return
     */
    @Override
    public Result userInfoByShiro(String authorization) {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        BsUser bsUser = (BsUser) principals.getPrimaryPrincipal();
        return this.getUserInfo(bsUser.getId());
    }

    /**
     * 通过jwt-token获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result userInfo(String token) throws LoginErrorException {
        token = StringUtils.remove(token, "Bearer ");
        Claims claims = jwtUtils.parseJwt(token);
        return getUserInfo(claims.getId());
    }


    public Result getUserInfo(String id) {
        Optional<BsUser> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            /**渲染权限明细*/
            Set<BsRole> roles = byId.get().getRoles();
            roles.forEach(x -> {
                Set<BsPermission> permissions = x.getPermissions();
                /**并且要通过当前权限去查询是否有下级权限-如果有pid的话,说明他一定是其他权限的下级权限*/
                Set<BsPermission> pidIsNull = permissions.stream().filter(y -> StringUtils.isEmpty(y.getPid())).collect(Collectors.toSet());
                pidIsNull.forEach(y -> {
                    Set<BsPermission> byPid = permissionRepository.findByPid(y.getId());
                    this.recursionFindPerm(byPid);
                    y.setNextBsPermission(byPid);
                });
                x.setPermissions(pidIsNull);
            });
            return ResultUtil.success(byId.get());
        }
        return null;
    }


    /**
     * 递归获取权限列表-分了层级
     *
     * @param bsPermissions
     */
    @Override
    public void recursionFindPerm(Set<BsPermission> bsPermissions) {
        bsPermissions.forEach(z -> {
            Set<BsPermission> byPi = permissionRepository.findByPid(z.getId());
            byPi = byPi.stream().sorted(Comparator.comparing(x -> x.getSort())).collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(byPi)) {
                return;
            }
            z.setNextBsPermission(byPi);
            this.recursionFindPerm(byPi);
        });
    }

    /**
     * 使用shiro模拟用户登入
     * <p>
     * 登入的用户名和明细会委托给securityManager 我们再去使用,reale域进行用户的认证和授权->MyRealm
     *
     * @param mobile
     * @param password
     * @return
     */
    @Override
    public Result loginByShiro(String mobile, String password) throws LoginErrorException {
        try {
            /**对用于的密码进行加密-md5  mobile手机号码是盐,  "3"的意思是循环加密多少次*/
            password = new Md5Hash(password, mobile, 3).toString();
            /**获取UsernamePasswordToken对象*/
            UsernamePasswordToken userToken = new UsernamePasswordToken(mobile, password);
            /**获取Subject对象*/
            Subject subject = SecurityUtils.getSubject();
            String sessionId = (String) subject.getSession().getId();
            log.info("sessionId:" + sessionId);
            /**使用subject进行登入*/
            userToken.setRememberMe(true);
            subject.login(userToken);
            return ResultUtil.success(sessionId);
        } catch (AuthenticationException e) {
            throw new LoginErrorException("登入失败,请检查用户名和秘密");
        }
    }
}
