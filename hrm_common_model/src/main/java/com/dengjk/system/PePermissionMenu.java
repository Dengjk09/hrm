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
 * 资源菜单表(PE_PERMISSION_MENU)
 * 
 * @author bianj
 * @version 1.0.0 2019-04-14
 */
@Entity
@Table(name = "PE_PERMISSION_MENU")
@Data
public class PePermissionMenu implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4256833286762915963L;

    /** 主键 */
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 19)
    private Long id;

    /** 菜单图标 */
    @Column(name = "MENU_ICON", nullable = false, length = 255)
    private String menuIcon;

    /** 排序号 */
    @Column(name = "MENU_ORDER", nullable = false, length = 255)
    private String menuOrder;

    /** 菜单描述 */
    @Column(name = "DESCRITION", nullable = false, length = 255)
    private String descrition;


}