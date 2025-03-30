package com.permission.authority.config.security.service;

import com.permission.authority.entity.User;
import com.permission.authority.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
* 用户认证处理器类
* */
@Component
public class CustomerUserDetailService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        // 调用根据用户查询用户信息的方法
        User user = userService.findUserByUserName(username);
        // 判断对象是否为空
        if (user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return user;
    }

}
