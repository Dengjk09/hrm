package com.dengjk.system.dao;

import com.dengjk.system.BsPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Dengjk
 * @create 2019-04-14 19:14
 * @desc
 **/
public interface PermissionRepository extends JpaRepository<BsPermission, String>, JpaSpecificationExecutor<BsPermission> {

    Set<BsPermission> findByIdIn(List<String> ids);

    /**
     * 根据类型和id查询所有的权限
     */
    Set<BsPermission> findAllByPidInAndType(List<String> ids, Integer type);

    /**
     * 通过父id查询
     */
    Set<BsPermission> findByPid(String pid);

    /**
     * 通过权限类型查询
     */
    Set<BsPermission> findByType(Integer type);


}
