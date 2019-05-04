package com.dengjk.system;/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.8.0
 */

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源api表(PE_PERMISSION_API)
 * 
 * @author bianj
 * @version 1.0.0 2019-04-14
 */
@Entity
@Table(name = "PE_PERMISSION_API")
@Data
public class PePermissionApi implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2115570036056677115L;

    /** 主键 */
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 19)
    private String id;

    /** 链接 */
    @Column(name = "API_URL", nullable = false, length = 500)
    private String apiUrl;

    /**
     * 权限类型是否需要认证
     */
    @Column(name = "api_auth_type")
    private Integer apiAuthType;

    /** 请求类型 */
    @Column(name = "API_METHOD", nullable = false, length = 255)
    private String apiMethod;

    /** 权限等级(1 为通用接口,2为需要认证接口) */
    @Column(name = "API_LEVEL", nullable = false, length = 255)
    private String apiLevel;

    /** api描述 */
    @Column(name = "DESCRITION", nullable = false, length = 255)
    private String descrition;


}