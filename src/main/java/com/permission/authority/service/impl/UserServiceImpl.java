package com.permission.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.permission.authority.entity.User;
import com.permission.authority.dao.UserMapper;
import com.permission.authority.entity.query.UserQueryVo;
import com.permission.authority.service.FileService;
import com.permission.authority.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.authority.utils.SystemConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 分页查询用户信息
     */
    @Override
    public IPage<User> findUserListByPage(IPage<User> page, UserQueryVo userQueryVo)
    {
        //创建条件构造器对象
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        //部门编号
        queryWrapper.eq(!ObjectUtils.isEmpty(userQueryVo.getDepartmentId()),
                User::getDepartmentId,userQueryVo.getDepartmentId());
        //用户名
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getUsername()),
                User::getUsername,userQueryVo.getUsername());
        //真实姓名
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getRealName()),
                User::getRealName,userQueryVo.getRealName());
        //电话
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getPhone()),
                User::getPhone,userQueryVo.getPhone());
        //查询并返回数据
        return baseMapper.selectPage(page,queryWrapper);
    }


//    @Override
//    @Transactional(rollbackFor = RuntimeException.class)
//    public boolean deleteById(Long id) {
//        //查询
//        User user = baseMapper.selectById(id);
//        //删除用户角色关系
//        baseMapper.deleteUserRole(id);
//        //删除用户
//        if (baseMapper.deleteById(id) > 0) {
//            //判断用户是否存在
//            if (user != null && !ObjectUtils.isEmpty(user.getAvatar())
//                    && !user.getAvatar().equals(SystemConstants.DEFAULT_AVATAR)) {
//                //删除文件
//                fileService.deleteFile(user.getAvatar());
//            }
//            return true;
//        }
//        return false;
//    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveUserRole(Long userId,  List<Long> roleIds) {
        //删除该用户对应的角色信息
        baseMapper.deleteUserRole(userId);
        //保存用户角色信息
        return baseMapper.saveUserRole(userId,roleIds)>0;
    }

}