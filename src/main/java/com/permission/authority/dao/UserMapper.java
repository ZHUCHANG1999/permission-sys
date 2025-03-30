package com.permission.authority.dao;

import com.permission.authority.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhuc
 * @since 2025-03-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 删除用户角色关系
     */
    @Delete("delete from sys_user_role where user_id=#{userId}")
    int deleteUserRole(Long userId);

    /**
     * 保存用户角色关系
     */
    int saveUserRole(@Param("userId") Long userId, @Param("roleIds")List<Long> roleIds);
}