CREATE TABLE `company` (
`id` BIGINT ( 20 ) NOT NULL COMMENT '主键id',
`name` VARCHAR ( 200 ) DEFAULT NULL COMMENT '公司名称',
`version` VARCHAR ( 200 ) DEFAULT NULL COMMENT '版本',
`renewalDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '续期时间',
PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE bs_user (
id BIGINT ( 20 ) PRIMARY KEY COMMENT '主键',
mobile VARCHAR ( 20 ) NOT NULL COMMENT '手机号码',
userName VARCHAR ( 255 ) NOT NULL COMMENT '用户名称',
password VARCHAR ( 255 ) NOT NULL COMMENT '用户名称',
enable_state TINYINT ( 1 ) DEFAULT '1' COMMENT '启用状态,0',
create_time DATETIME DEFAULT NULL COMMENT '创建时间',
department_id BIGINT ( 20 ) DEFAULT NULL COMMENT '部门id',
time_of_entry DATETIME DEFAULT NULL COMMENT '入职日期',
form_of_employment INT ( 1 ) DEFAULT NULL COMMENT '聘用形式',
work_number VARCHAR ( 20 ) DEFAULT NULL COMMENT '员工工号',
form_of_management VARCHAR ( 8 ) DEFAULT NULL COMMENT '管理形式',
work_city VARCHAR ( 16 ) DEFAULT NULL COMMENT '工作城市',
work_address VARCHAR ( 255 ) DEFAULT NULL COMMENT '工作地址',
correction_time DATETIME DEFAULT NULL COMMENT '转正时间',
in_service_status INT ( 1 ) DEFAULT NULL COMMENT '在职状态,1,在职 2.离职',
company_id BIGINT ( 20 ) DEFAULT NULL COMMENT '企业id',
company_name VARCHAR ( 225 ) DEFAULT NULL COMMENT '企业id',
department_name VARCHAR ( 225 ) DEFAULT NULL COMMENT '部门名称'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 comment '用户表';



CREATE TABLE bs_role (
id BIGINT ( 20 ) PRIMARY KEY COMMENT '主键',
NAME VARCHAR ( 255 ) NOT NULL COMMENT '手机号码',
descrition VARCHAR ( 255 ) NOT NULL COMMENT '角色描述',
company_id BIGINT ( 20 ) DEFAULT NULL COMMENT '企业id'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 comment '角色表';




CREATE TABLE bs_permission (
id BIGINT ( 20 ) PRIMARY KEY COMMENT '主键',
NAME VARCHAR ( 255 ) NOT NULL COMMENT '手机号码',
type INT ( 1 ) NOT NULL COMMENT '权限类型 1为菜单 2 为功能  3 为api',
CODE VARCHAR ( 11 ) DEFAULT NULL COMMENT '权限标志',
descrition VARCHAR ( 255 ) NOT NULL COMMENT '权限描述',
pid VARCHAR ( 11 ) DEFAULT NULL COMMENT '父级id（权限属于哪个菜单或者按钮）',
en_visible INT ( 1 ) DEFAULT NULL COMMENT '企业可见性 0不可见,1可见'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT '权限表';




CREATE TABLE pe_permission_menu (
id BIGINT ( 20 ) PRIMARY KEY COMMENT '主键',
menu_icon VARCHAR ( 255 ) NOT NULL COMMENT '菜单图标',
menu_order VARCHAR ( 255 ) NOT NULL COMMENT '排序号',
descrition VARCHAR ( 255 ) NOT NULL COMMENT '菜单描述'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4  comment '资源菜单表';



CREATE TABLE pe_permission_point (
id BIGINT ( 20 ) PRIMARY KEY COMMENT '主键',
point_class VARCHAR ( 255 ) NOT NULL COMMENT '按钮图标',
point_icon VARCHAR ( 255 ) NOT NULL COMMENT '排序号',
point_status VARCHAR ( 255 ) NOT NULL COMMENT '按钮状态',
descrition VARCHAR ( 255 ) NOT NULL COMMENT '按钮描述'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4  comment '资源按钮表';



CREATE TABLE pe_permission_api (
id BIGINT ( 20 ) PRIMARY KEY COMMENT '主键',
api_url VARCHAR ( 500 ) NOT NULL COMMENT '链接',
api_method VARCHAR ( 255 ) NOT NULL COMMENT '请求类型',
api_level VARCHAR ( 255 ) NOT NULL COMMENT '权限等级(1 为通用接口,2为需要认证接口)',
descrition VARCHAR ( 255 ) NOT NULL COMMENT 'api描述'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT '资源api表';



