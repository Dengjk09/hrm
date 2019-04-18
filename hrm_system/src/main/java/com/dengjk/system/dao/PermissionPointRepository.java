package com.dengjk.system.dao;

import com.dengjk.system.PePermissionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Dengjk
 * @create 2019-04-17 21:06
 * @desc
 **/
public interface PermissionPointRepository extends JpaRepository<PePermissionPoint, String>, JpaSpecificationExecutor<PePermissionPoint> {
}
