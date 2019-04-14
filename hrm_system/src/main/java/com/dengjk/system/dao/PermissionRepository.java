package com.dengjk.system.dao;

import com.dengjk.system.BsPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Dengjk
 * @create 2019-04-14 19:14
 * @desc
 **/
public interface PermissionRepository extends JpaRepository<BsPermission, String>, JpaSpecificationExecutor<BsPermission> {
}
