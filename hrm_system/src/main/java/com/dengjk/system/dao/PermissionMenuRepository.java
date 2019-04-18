package com.dengjk.system.dao;

import com.dengjk.system.PePermissionMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Dengjk
 * @create 2019-04-17 21:06
 * @desc
 **/
public interface PermissionMenuRepository extends JpaRepository<PePermissionMenu, String>, JpaSpecificationExecutor<PePermissionMenu> {
}
