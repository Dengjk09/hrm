package com.dengjk.system;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * 角色表(BS_ROLE)
 *
 * @author bianj
 * @version 1.0.0 2019-04-14
 */
@Entity
@Table(name = "bs_role")
@Data
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
public class BsRole implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -2907647530309063937L;

    /**
     * 主键
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 19)
    @GeneratedValue(generator = "role")
    @GenericGenerator(name = "role", strategy = "com.dengjk.common.utils.IdGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "dataCenterID", value = "20"),
                    @org.hibernate.annotations.Parameter(name = "workerId", value = "10")})
    private String id;

    /**
     * 手机号码
     */
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    /**
     * 角色描述
     */
    @Column(name = "DESCRITION", nullable = false, length = 255)
    private String descrition;

    /**
     * 企业id
     */
    @Column(name = "COMPANY_ID", nullable = true, length = 19)
    private Long companyId;

    /**
     * 角色和用户之间的关系多对多
     * mappedBy表示所有的关系交由Bsuser表中的roles字段维护
     */

    @ManyToMany(mappedBy = "roles")
    private Set<BsUser> users;


    /**
     * 角色和权限之前的关系
     */

    @ManyToMany
    /**解决json转换之间的死循环*/
    @JsonIgnore
    @JsonBackReference
    /**描述中间表之间的关系*/
    @JoinTable(
            name = "bs_role_permission", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
    private Set<BsPermission> permissions;

}