CREATE TABLE `sys_department` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门编号',
                                  `department_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
                                  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门电话',
                                  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门地址',
                                  `pid` bigint NOT NULL COMMENT '所属部门编号',
                                  `parent_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属部门名称',
                                  `order_num` int DEFAULT NULL COMMENT '排序',
                                  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除 1-已删除)',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;