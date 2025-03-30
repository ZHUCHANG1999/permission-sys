package com.permission.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.authority.dao.UserMapper;
import com.permission.authority.entity.User;
import com.permission.authority.entity.query.UserQueryVo;
import com.permission.authority.service.FileService;
import com.permission.authority.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private FileService fileService;

    /*
    * 根据用户名查询用户信息
    */
    @Override
    public User findUserByUserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,userName);
        return baseMapper.selectOne(queryWrapper);
    }

}