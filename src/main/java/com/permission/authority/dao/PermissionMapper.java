package com.permission.authority.dao;

import com.permission.authority.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2025-04-05
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findPermissionListByUserId(Long userId);

    /**
     * 根据角色ID查询权限列表
     */
    List<Permission> findPermissionListByRoleId(Long roleId);
}
