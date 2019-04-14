package com.dengjk.system;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源按钮表(PE_PERMISSION_POINT)
 * 
 * @author bianj
 * @version 1.0.0 2019-04-14
 */
@Entity
@Table(name = "PE_PERMISSION_POINT")
@Data
public class PePermissionPoint implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -988139032444996948L;

    /** 主键 */
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 19)
    private Long id;

    /** 按钮图标 */
    @Column(name = "POINT_CLASS", nullable = false, length = 255)
    private String pointClass;

    /** 排序号 */
    @Column(name = "POINT_ICON", nullable = false, length = 255)
    private String pointIcon;

    /** 按钮状态 */
    @Column(name = "POINT_STATUS", nullable = false, length = 255)
    private String pointStatus;

    /** 按钮描述 */
    @Column(name = "DESCRITION", nullable = false, length = 255)
    private String descrition;

}