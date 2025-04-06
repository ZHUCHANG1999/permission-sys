package com.permission.authority.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.authority.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.permission.authority.entity.query.RoleQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2025-04-05
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户查询角色列表
     */
    IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVo roleQueryVo);

    /**
     * 保存角色权限关系
     */
    boolean saveRolePermission(Long roleId, List<Long> permissionIds);

    boolean hasDistribution(Long id);

    /**
     * 根据用户ID查询该用户拥有的角色ID
     *
     */
    List<Long> findRoleIdByUserId(Long userId);


    /*
    * 检查角色是否被使用
    * */
    boolean hashRoleCount(Long id);
}
