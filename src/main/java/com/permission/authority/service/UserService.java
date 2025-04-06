package com.permission.authority.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.authority.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.permission.authority.entity.query.UserQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2025-04-05
 */
public interface UserService extends IService<User> {

    /*
    * 根据用户名查询信息
    * */
    public User findUserByUserName(String userName) ;

    /**
     * 分页查询用户信息
     */
    IPage<User> findUserListByPage(IPage<User> page, UserQueryVo userQueryVo);

//    /**
//     * 删除用户信息
//     */
//    boolean deleteById(Long id);

    /**
     * 分配角色
     */
    boolean saveUserRole(Long userId, List<Long> roleIds);
}