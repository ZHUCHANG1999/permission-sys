CREATE TABLE `sys_permission` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限编号',
                                  `label` varchar(50) DEFAULT NULL COMMENT '权限名称',
                                  `parent_id` bigint DEFAULT NULL COMMENT '父权限ID',
                                  `parent_name` varchar(50) DEFAULT NULL COMMENT '父权限名称',
                                  `code` varchar(50) DEFAULT NULL COMMENT '授权标识符',
                                  `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
                                  `name` varchar(50) DEFAULT NULL COMMENT '路由名称',
                                  `url` varchar(100) DEFAULT NULL COMMENT '授权路径',
                                  `type` tinyint DEFAULT NULL COMMENT '权限类型(0-目录 1-菜单 2-按钮)',
                                  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                  `order_num` int DEFAULT NULL COMMENT '排序',
                                  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除，1-已删除)',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;