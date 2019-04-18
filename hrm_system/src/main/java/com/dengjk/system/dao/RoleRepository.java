package com.dengjk.system.dao;

import com.dengjk.system.BsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

/**
 * @author Dengjk
 * @create 2019-04-14 19:14
 * @desc
 **/
public interface RoleRepository extends JpaRepository<BsRole, String>, JpaSpecificationExecutor<BsRole> {

    /**根据id批量查询*/
    Set<BsRole> findByIdIn(List<String> ids);

}
