package com.dengjk.system.service.impl;

import com.dengjk.common.enums.PermissionTypeEnum;
import com.dengjk.common.exception.MyException;
import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import com.dengjk.common.utils.SnowflakeIdWorker;
import com.dengjk.system.BsPermission;
import com.dengjk.system.PePermissionApi;
import com.dengjk.system.PePermissionMenu;
import com.dengjk.system.PePermissionPoint;
import com.dengjk.system.dao.PermissionApiRepository;
import com.dengjk.system.dao.PermissionMenuRepository;
import com.dengjk.system.dao.PermissionPointRepository;
import com.dengjk.system.dao.PermissionRepository;
import com.dengjk.system.service.PermissionService;
import com.dengjk.system.service.UserService;
import com.dengjk.system.vo.PermissionAddVo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Dengjk
 * @create 2019-04-14 19:23
 * @desc
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private SnowflakeIdWorker idGenerator;

    @Autowired
    private PermissionPointRepository permissionPointRepository;

    @Autowired
    private PermissionApiRepository permissionApiRepository;

    @Autowired
    private PermissionMenuRepository permissionMenuRepository;

    @Autowired
    private UserService userService;


    /**
     * 添加权限,权限和资源是一一对应的关系,添加的时候要根据类型判断是那种资源
     * 1为菜单 2 为功能  3 为api
     *
     * @param permissionAddVo
     * @return
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public Result addOrUpdatePermission(PermissionAddVo permissionAddVo) {
        /**根据类型取出*/
        Integer type = permissionAddVo.getType();
        BsPermission bsPermission = null;
        if (StringUtils.isEmpty(permissionAddVo.getId())) {
            bsPermission = new BsPermission();
            BeanUtils.copyProperties(permissionAddVo, bsPermission);
            bsPermission.setId(idGenerator.nextId() + "");
        } else {
            bsPermission = new BsPermission();
            BeanUtils.copyProperties(permissionAddVo, bsPermission);
        }
        /**保存明细*/
        switch (type) {
            case 1:
                PePermissionMenu pePermissionMenu = permissionAddVo.getPePermissionMenu();
                pePermissionMenu.setId(bsPermission.getId());
                permissionMenuRepository.save(pePermissionMenu);
                break;
            case 2:
                PePermissionPoint pePermissionPoint = permissionAddVo.getPePermissionPoint();
                pePermissionPoint.setId(bsPermission.getId());
                permissionPointRepository.save(pePermissionPoint);
                break;
            case 3:
                PePermissionApi pePermissionApi = permissionAddVo.getPePermissionApi();
                pePermissionApi.setId(bsPermission.getId());
                permissionApiRepository.save(pePermissionApi);
                break;
        }
        permissionRepository.save(bsPermission);
        return ResultUtil.success("保存成功");
    }


    @Override
    public Result findPermissionById(String id) {
        Optional<BsPermission> byId = permissionRepository.findById(id);
        if (byId.isPresent()) {
            BsPermission bsPermission = byId.get();
            PermissionAddVo permissionAddVo = new PermissionAddVo();
            BeanUtils.copyProperties(bsPermission, permissionAddVo);
            Integer type = bsPermission.getType();
            switch (type) {
                case 1:
                    permissionAddVo.setPePermissionMenu(permissionMenuRepository.findById(bsPermission.getId()).get());
                    break;
                case 2:
                    permissionAddVo.setPePermissionPoint(permissionPointRepository.findById(bsPermission.getId()).get());
                    break;
                case 3:
                    permissionAddVo.setPePermissionApi(permissionApiRepository.findById(bsPermission.getId()).get());
                    break;
            }
            return ResultUtil.success(permissionAddVo);
        }
        return ResultUtil.success(null);
    }


    /**
     * 根据类型查询权限集合
     *
     * @param type
     * @param id
     */
    @Override
    public Object findPermissionDel(Integer type, String id) {
        switch (type) {
            case 1:
                return permissionMenuRepository.findById(id).get();
            case 2:
                return permissionPointRepository.findById(id).get();
            case 3:
                return permissionApiRepository.findById(id).get();
        }
        throw new MyException("未找到对应的类型");
    }


    /**
     * 获取权限树状结构
     *
     * @return
     */
    @Override
    public Result findPermisMenuTree() {
        /**查询出所有的菜单资源*/
        Set<BsPermission> menuPermis = permissionRepository.findByType(PermissionTypeEnum.menu.getCode());
        /**把所有的菜单构建成一棵层级树 ,先根据深度排序,在根据排序值排序*/
        Set<BsPermission> menuPermisSort = menuPermis.stream().sorted(Comparator.comparing(BsPermission::getDepth).thenComparing(BsPermission::getSort)).collect(Collectors.toSet());
        /**首层结构*/
        List<BsPermission> firstPermis = menuPermisSort.stream().filter(x -> x.getDepth().equals(1)).sorted(Comparator.comparing(BsPermission::getSort)).collect(Collectors.toList());
        firstPermis.forEach(x -> {
            Set<BsPermission> nextPermis = menuPermis.stream().filter(y -> StringUtils.equals(x.getId(), y.getPid())).collect(Collectors.toSet());
            userService.recursionFindPerm(nextPermis);
            x.setNextBsPermission(nextPermis);
        });
        return ResultUtil.success(firstPermis);
    }


    @Override
    public Result deletePermissionById(String id) {
        return null;
    }


    /**
     * 权限更新
     *
     * @param permissionAddVo
     * @return
     */
    @Override
    public Result upatePermission(PermissionAddVo permissionAddVo) {
        return null;
    }
}
