package com.dengjk.system.dao;

import com.dengjk.system.PePermissionApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Dengjk
 * @create 2019-04-17 21:05
 * @desc
 **/
public interface PermissionApiRepository extends JpaRepository<PePermissionApi, String>, JpaSpecificationExecutor<PePermissionApi> {
}
