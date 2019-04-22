package com.dengjk.system.service.impl;

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
import com.dengjk.system.vo.PermissionAddVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

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
