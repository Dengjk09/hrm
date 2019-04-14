package com.dengjk.system.dao;

import com.dengjk.system.BsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Dengjk
 * @create 2019-04-14 19:14
 * @desc
 **/
public interface RoleRepository  extends JpaRepository<BsRole, Long>, JpaSpecificationExecutor<BsRole> {
}
