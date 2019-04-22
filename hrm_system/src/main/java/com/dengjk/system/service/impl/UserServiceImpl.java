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
import com.google.common.collect.Sets;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
@Service
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
     * 通过jwt-token获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result userInfo(String token) throws LoginErrorException {
        token = StringUtils.remove(token, "Bearer ");
        Claims claims = jwtUtils.parseJwt(token);
        Optional<BsUser> byId = userRepository.findById(claims.getId());
        if (byId.isPresent()) {
            /**渲染权限明细*/
            Set<BsRole> roles = byId.get().getRoles();
            roles.forEach(x -> {
                Set<BsPermission> permissions = x.getPermissions();
                permissions.forEach(y -> {
                    String pid = y.getPid();
                    if(StringUtils.isNotEmpty(pid)){
                        /**要去寻找他们的上一级*/
                        BsPermission allByPid = permissionRepository.findByPid(pid);
                        allByPid.setNextBsPermission(Sets.newHashSet(y));
                        if(StringUtils.isNotEmpty(allByPid.getPid())){
                            BsPermission allByPid2 = permissionRepository.findByPid(pid);
                            allByPid2.setNextBsPermission(Sets.newHashSet(allByPid));
                            BeanUtils.copyProperties(y,allByPid2);
                        }
                    }
                    y.setPermissionDel(permissionService.findPermissionDel(y.getType(), y.getId()));
                });
            });
            return ResultUtil.success(byId.get());
        }
        return ResultUtil.notExist("或者失败");
    }






}
