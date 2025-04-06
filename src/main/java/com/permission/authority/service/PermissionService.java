package com.permission.authority.service;

import com.permission.authority.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.permission.authority.entity.dto.RolePermissionVo;
import com.permission.authority.entity.query.PermissionQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2025-04-05
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户id查询权限带单列表
     */
    List<Permission> findPermissionListByUserId(Long userId);

    /**
     * 查询菜单列表
     */
    List<Permission> findPermissionList(PermissionQueryVo permissionQueryVo);

    /**
     * 查询上级菜单列表
     */
    List<Permission> findParentPermissionList();

    /**
     * 检查菜单是否有子菜单
     */
    boolean hasChildrenOfPermission(Long id);

    /**
     * 查询分配权限树列表
     */
    RolePermissionVo findPermissionTree(Long userId, Long roleId);
}
