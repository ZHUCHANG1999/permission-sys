package com.permission.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.authority.dao.RoleMapper;
import com.permission.authority.dao.UserMapper;
import com.permission.authority.entity.Role;
import com.permission.authority.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private UserMapper userMapper;
}