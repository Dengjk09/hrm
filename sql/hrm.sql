
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bs_permission
-- ----------------------------
DROP TABLE IF EXISTS `bs_permission`;
CREATE TABLE `bs_permission`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
  `type` int(1) NOT NULL COMMENT '权限类型 1为菜单 2 为功能  3 为api',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标志',
  `descrition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限描述',
  `pid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父级id（权限属于哪个菜单或者按钮）',
  `en_visible` int(1) DEFAULT NULL COMMENT '企业可见性 0不可见,1可见',
  `depth` int(11) DEFAULT NULL COMMENT '当前层级的深度',
  `sort` int(11) DEFAULT 1 COMMENT '排序值,用于当前层级菜单的排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_permission
-- ----------------------------
INSERT INTO `bs_permission` VALUES (568923852987736064, '员工管理', 1, 'sys_user_manager', '员工管理查看的考勤记录页面', '', 0, 1, 1);
INSERT INTO `bs_permission` VALUES (568924320187064320, '个人首页', 1, 'sys_user_first', '个人首页', '', 0, 1, 3);
INSERT INTO `bs_permission` VALUES (568926725180006400, '个人首页删除api ', 1, 'sys_user_delete', '个人首页-下级', '568924320187064320', 0, 2, 1);
INSERT INTO `bs_permission` VALUES (568926725180006402, '个人首页删除api ', 1, 'sys_user_delete', '个人首页-下下级', '568926725180006400', 0, 3, 1);
INSERT INTO `bs_permission` VALUES (568926725180006403, '个人首页删除api ', 1, 'sys_user_delete', '个人首页-下下下级', '568926725180006402', 0, 4, 1);
INSERT INTO `bs_permission` VALUES (568926725180006404, '个人首页update', 1, 'sys_user_delete', '个人首页-下级-平', '568924320187064320', 0, 2, 1);
INSERT INTO `bs_permission` VALUES (568926725180006405, '个人首页删除api ', 1, 'sys_user_delete', '个人首页-下下下下级', '568926725180006403', 0, 5, 1);
INSERT INTO `bs_permission` VALUES (568926725180006406, '个人首页update', 1, 'sys_user_update', '个人首页-下级-平-下级', '568926725180006404', 0, 2, 1);
INSERT INTO `bs_permission` VALUES (568926725180006407, '个人首页删除api ', 1, 'sys_user_delete', '个人首页-下下下下级-平级', '568926725180006403', 0, 5, 2);

-- ----------------------------
-- Table structure for bs_role
-- ----------------------------
DROP TABLE IF EXISTS `bs_role`;
CREATE TABLE `bs_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `descrition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色描述',
  `company_id` bigint(20) DEFAULT NULL COMMENT '企业id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_role
-- ----------------------------
INSERT INTO `bs_role` VALUES (568861382882664448, '管理员', '管理员角色', 123);
INSERT INTO `bs_role` VALUES (568861641255985152, '普通员工', '给普通员工用', 123);
INSERT INTO `bs_role` VALUES (568861799922311168, '测试专用', '给测试人员专用', 123);

-- ----------------------------
-- Table structure for bs_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `bs_role_permission`;
CREATE TABLE `bs_role_permission`  (
  `role_id` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_id` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `FKk5sad5jvaedrk18eil8vmc3xi`(`permission_id`) USING BTREE,
  INDEX `FKoud2xgcyw8vgjwrr1ocia9boj`(`role_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bs_role_permission
-- ----------------------------
INSERT INTO `bs_role_permission` VALUES ('568861382882664448', '568923852987736064');
INSERT INTO `bs_role_permission` VALUES ('568861382882664448', '568924320187064320');
INSERT INTO `bs_role_permission` VALUES ('568861382882664448', '568926725180006406');

-- ----------------------------
-- Table structure for bs_user
-- ----------------------------
DROP TABLE IF EXISTS `bs_user`;
CREATE TABLE `bs_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `enable_state` tinyint(1) DEFAULT 1 COMMENT '启用状态,0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `time_of_entry` datetime DEFAULT NULL COMMENT '入职日期',
  `form_of_employment` int(1) DEFAULT NULL COMMENT '聘用形式',
  `work_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工工号',
  `form_of_management` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '管理形式',
  `work_city` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工作城市',
  `work_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工作地址',
  `correction_time` datetime DEFAULT NULL COMMENT '转正时间',
  `in_service_status` int(1) DEFAULT NULL COMMENT '在职状态,1,在职 2.离职',
  `company_id` bigint(20) DEFAULT NULL COMMENT '企业id',
  `company_name` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业id',
  `department_name` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_user
-- ----------------------------
INSERT INTO `bs_user` VALUES (568858382021730304, '17620157876', '邓先生', '0b0e8cbd584c89df505ec0486d5bc5a2', 1, '2019-04-19 17:58:00', 1, '2019-04-19 17:58:00', 0, '123', '1', '广州', '白云', '2019-04-19 17:58:00', 0, 12, '一维公司', '技术部');
INSERT INTO `bs_user` VALUES (568858722158813184, '17620157878', '李先生', '0b0e8cbd584c89df505ec0486d5bc5a2', 1, '2019-04-19 17:58:00', 2, '2019-04-19 17:58:00', 0, '124', '1', '广州', '黄埔', '2019-04-19 17:58:00', 0, 13, '小米公司', '销售部');

-- ----------------------------
-- Table structure for bs_user_role
-- ----------------------------
DROP TABLE IF EXISTS `bs_user_role`;
CREATE TABLE `bs_user_role`  (
  `user_id` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `FKt02skrrs4e8kni7n48iog25ww`(`role_id`) USING BTREE,
  INDEX `FKexoeik0s2bpcfrfa4sql4eli8`(`user_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bs_user_role
-- ----------------------------
INSERT INTO `bs_user_role` VALUES ('568858382021730304', '568861641255985152');
INSERT INTO `bs_user_role` VALUES ('568858382021730304', '568861382882664448');
INSERT INTO `bs_user_role` VALUES ('568858382021730304', '568926725180006400');
INSERT INTO `bs_user_role` VALUES ('568858382021730304', '568861382882664448');

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司名称',
  `version` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '版本',
  `renewalDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '续期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pe_permission_api
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_api`;
CREATE TABLE `pe_permission_api`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `api_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '链接',
  `api_auth_type` int(11) DEFAULT NULL COMMENT 'shiro的权限是否需要认证(anon-1,authc-2,perm-3)',
  `api_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求类型',
  `api_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限等级(1 为通用接口,2为需要认证接口)',
  `descrition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'api描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源api表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pe_permission_api
-- ----------------------------
INSERT INTO `pe_permission_api` VALUES (568926725180006400, '/sys/user/userInfoByShiro', 3, 'GET', '2', '登入接口');

-- ----------------------------
-- Table structure for pe_permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_menu`;
CREATE TABLE `pe_permission_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `menu_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单图标',
  `menu_order` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '排序号',
  `descrition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pe_permission_menu
-- ----------------------------
INSERT INTO `pe_permission_menu` VALUES (568923852987736064, '前端之定义图标标识', '1', '员工管理查看的考勤记录页面');
INSERT INTO `pe_permission_menu` VALUES (568924320187064320, '前端个人自定义图标标识', '1', '员工的个人首页');
INSERT INTO `pe_permission_menu` VALUES (568926725180006400, '图标', '3', '个人明细');

-- ----------------------------
-- Table structure for pe_permission_point
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_point`;
CREATE TABLE `pe_permission_point`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `point_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '按钮图标',
  `point_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '排序号',
  `point_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '按钮状态',
  `descrition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '按钮描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源按钮表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('17620157876', '0QKc49iNDV3s964SUj9isg==', 'zZ+xhT0vZF+sJPhzctPRYA==', '2019-08-26 15:30:09');

SET FOREIGN_KEY_CHECKS = 1;
