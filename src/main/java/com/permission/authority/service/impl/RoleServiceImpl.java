package com.permission.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.permission.authority.dao.UserMapper;
import com.permission.authority.entity.Role;
import com.permission.authority.dao.RoleMapper;
import com.permission.authority.entity.User;
import com.permission.authority.entity.dto.UserRoleTo;
import com.permission.authority.entity.query.RoleQueryVo;
import com.permission.authority.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2025-04-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户查询角色列表
     */
    @Override
    public IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVo
            roleQueryVo) {
        //创建条件构造器
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        //角色名称
        queryWrapper.like(!ObjectUtils.isEmpty(roleQueryVo.getRoleName()),
                Role::getRoleName,roleQueryVo.getRoleName());
        //排序
        queryWrapper.orderByAsc(Role::getId);
        //根据用户ID查询用户信息
        User user = userMapper.selectById(roleQueryVo.getUserId());
        //如果用户不为空、且不是管理员，则只能查询自己创建的角色
        if(user!=null && !ObjectUtils.isEmpty(user.getIsAdmin()) &&
                user.getIsAdmin() !=1){
            queryWrapper.eq(Role::getCreateUser,roleQueryVo.getUserId());
        }
        return baseMapper.selectPage(page,queryWrapper);
    }

    /**
     * 保存角色权限关系
     */
    @Override
    public boolean saveRolePermission(Long roleId, List<Long> permissionIds) {
        //删除该角色对应的权限信息
        baseMapper.deleteRolePermission(roleId);
        //保存角色权限
        return baseMapper.saveRolePermission(roleId,permissionIds)>0;
    }

    @Override
    public boolean hasDistribution(Long id) {
        List<UserRoleTo> userRoleTo = baseMapper.hasDistributionByUser(id) ;
        if( userRoleTo.size() > 0){
            return true ;
        }
        return false;
    }

    /**
     * 根据用户ID查询该用户拥有的角色ID
     */
    @Override
    public List<Long> findRoleIdByUserId(Long userId) {
        return baseMapper.findRoleIdByUserId(userId);
    }

    /*
    * 检查角色是否被使用
    * */
    @Override
    public boolean hashRoleCount(Long id) {
        return baseMapper.getRoleCountByRoleId(id) > 0;
    }
}