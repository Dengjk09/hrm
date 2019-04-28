package com.dengjk.system;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * BS_USER
 *
 * @author bianj
 * @version 1.0.0 2019-04-14
 * 在Hibernate中可以利用@DynamicInsert和@DynamicUpdate生成动态SQL语句，即在插入和修改数据的时候,语句中只包括要插入或者修改的字段。
 * @LastModifiedDate @CreatedDate自己更新时间
 */
@Entity
@Table(name = "BS_USER")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@Getter
@Setter
public class BsUser implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -8523632798814446393L;

    /**
     * 主键
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 19)
    @GeneratedValue(generator = "user")
    @GenericGenerator(name = "user", strategy = "com.dengjk.common.utils.IdGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "dataCenterID", value = "20") ,
                    @org.hibernate.annotations.Parameter(name = "workerId", value = "10")})
    private String id;

    /**
     * 手机号码
     */
    @Column(name = "MOBILE", nullable = false, length = 20)
    private String mobile;

    /**
     * 用户名称
     */
    @Column(name = "userName", nullable = false, length = 255)
    private String userName;


    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * 启用状态,0
     */
    @Column(name = "ENABLE_STATE", nullable = true)
    private Boolean enableState;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME", nullable = true)
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    /**
     * 部门id
     */
    @Column(name = "DEPARTMENT_ID", nullable = true, length = 19)
    private Long departmentId;

    /**
     * 入职日期
     */
    @Column(name = "TIME_OF_ENTRY", nullable = true)
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime timeOfEntry;

    /**
     * 聘用形式
     */
    @Column(name = "FORM_OF_EMPLOYMENT", nullable = true, length = 10)
    private Integer formOfEmployment;

    /**
     * 员工工号
     */
    @Column(name = "WORK_NUMBER", nullable = true, length = 20)
    private String workNumber;

    /**
     * 管理形式
     */
    @Column(name = "FORM_OF_MANAGEMENT", nullable = true, length = 8)
    private String formOfManagement;

    /**
     * 工作城市
     */
    @Column(name = "WORK_CITY", nullable = true, length = 16)
    private String workCity;

    /**
     * 工作地址
     */
    @Column(name = "WORK_ADDRESS", nullable = true, length = 255)
    private String workAddress;

    /**
     * 转正时间
     */
    @Column(name = "CORRECTION_TIME", nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime correctionTime;

    /**
     * 在职状态,1,在职 2.离职
     */
    @Column(name = "IN_SERVICE_STATUS", nullable = true, length = 10)
    private Integer inServiceStatus;

    /**
     * 企业id
     */
    @Column(name = "COMPANY_ID", nullable = true, length = 19)
    private Long companyId;

    /**
     * 企业id
     */
    @Column(name = "COMPANY_NAME", nullable = true, length = 225)
    private String companyName;

    /**
     * 部门名称
     */
    @Column(name = "DEPARTMENT_NAME", nullable = true, length = 225)
    private String departmentName;

    /**
     * 用户和角色之间的多对多关系
     */
    @ManyToMany()
    /**解决json转换之间的死循环*/
    @JsonIgnoreProperties(value = { "users" })
    /**描述中间表之间的关系*/
    @JoinTable(
        name = "bs_user_role", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}
    )
    private Set<BsRole> roles;

}