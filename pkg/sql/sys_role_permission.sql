CREATE TABLE `sys_role_permission` (
                                       `role_Id` bigint NOT NULL COMMENT '角色ID',
                                       `permission_Id` bigint NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
