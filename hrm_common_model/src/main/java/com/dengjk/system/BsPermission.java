package com.dengjk.system;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限表(BS_PERMISSION)
 *
 * @author bianj
 * @version 1.0.0 2019-04-14
 */
@Entity
@Table(name = "BS_PERMISSION")
@Data
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
public class BsPermission implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -2110915478369429498L;

    /**
     * 主键
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 19)
   /* @GeneratedValue(generator = "permission")
    @GenericGenerator(name = "permission", strategy = "com.dengjk.common.utils.IdGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "dataCenterID", value = "20"),
                    @org.hibernate.annotations.Parameter(name = "workerId", value = "10")})*/
    private String id;

    /**
     * 手机号码
     */
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    /**
     * 权限类型 1为菜单 2 为功能  3 为api
     */
    @Column(name = "TYPE", nullable = false, length = 10)
    private Integer type;

    /**
     * 权限标志
     */
    @Column(name = "CODE", nullable = true, length = 11)
    private String code;

    /**
     * 权限描述
     */
    @Column(name = "DESCRITION", nullable = false, length = 255)
    private String descrition;

    /**
     * 父级id（权限属于哪个菜单或者按钮）
     */
    @Column(name = "PID", nullable = true, length = 11)
    private String pid;

    /**是否企业可见*/
    @Column(name = "en_visible")
    private Integer enVisible;

}