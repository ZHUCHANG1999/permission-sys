package com.permission.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.authority.dao.UserMapper;
import com.permission.authority.entity.Permission;
import com.permission.authority.dao.PermissionMapper;
import com.permission.authority.entity.User;
import com.permission.authority.entity.dto.RolePermissionVo;
import com.permission.authority.entity.query.PermissionQueryVo;
import com.permission.authority.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.authority.utills.MenuTree;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private UserMapper userMapper;
}
