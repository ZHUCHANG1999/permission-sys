package com.permission.authority.dao;

import com.permission.authority.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.permission.authority.entity.dto.UserRoleTo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2025-04-05
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 删除角色权限关系
     */
    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    void deleteRolePermission(Long roleId);

    /**
     * 保存角色权限关系
     */
    int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);


    List<UserRoleTo> hasDistributionByUser(Long id);

    /**
     * 根据用户ID查询该用户拥有的角色ID
     */
    @Select("select role_id from `sys_user_role` where user_id = #{userId}")
    List<Long> findRoleIdByUserId(Long userId);


    @Select("select count(1) from `sys_user_role` where user_id = #{userId}")
    int getRoleCountByRoleId(Long id);
}
