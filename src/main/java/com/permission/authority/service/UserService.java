package com.permission.authority.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.permission.authority.entity.User;
import com.permission.authority.entity.query.UserQueryVo;

import java.util.List;

public interface UserService extends IService<User> {

    /*
    * 根据用户名查询信息
    * */
    public User findUserByUserName(String userName) ;

}