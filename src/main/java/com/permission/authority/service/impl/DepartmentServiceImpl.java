package com.permission.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.permission.authority.dao.UserMapper;
import com.permission.authority.entity.Department;
import com.permission.authority.dao.DepartmentMapper;
import com.permission.authority.entity.User;
import com.permission.authority.entity.query.DepartmentQueryVo;
import com.permission.authority.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.authority.utils.DepartmentTree;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Resource
    private UserMapper userMapper;
    /**
     * 查询部门列表
     *
     * @param departmentQueryVo
     * @return
     */
    @Override
    public List<Department> findDepartmentList(DepartmentQueryVo
                                                       departmentQueryVo) {
        //创建条件构造器对象
        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper<Department>();
        //部门名称
        queryWrapper.like(!ObjectUtils.isEmpty(departmentQueryVo.getDepartmentName())
                ,Department::getDepartmentName,departmentQueryVo.getDepartmentName());
//        queryWrapper.like(Department::getDepartmentName,departmentQueryVo.getDepartmentName());
        //排序
        queryWrapper.orderByAsc(Department::getOrderNum);
        //查询部门列表
        List<Department> departmentList = baseMapper.selectList(queryWrapper);

        //生成部门树
        List<Department> departmentTree =
                DepartmentTree.makeDepartmentTree(departmentList, 0L);
        return departmentTree;
    }
    /**
     * 查询上级部门列表
     */
    @Override
    public List<Department> findParentDepartment() {
        //创建条件构造器对象
        QueryWrapper<Department> queryWrapper = new QueryWrapper<Department>();
        //排序
        queryWrapper.orderByAsc("order_num");
        //查询部门列表
        List<Department> departmentList = baseMapper.selectList(queryWrapper);
        //创建部门对象
        Department department = new Department();
        department.setId(0L);
        department.setDepartmentName("顶级部门");
        department.setPid(-1L);
        departmentList.add(department);
        //生成部门树列表
        List<Department> departmentTree =
                DepartmentTree.makeDepartmentTree(departmentList, -1L);
        //返回部门列表
        return departmentTree;
    }

    /**
     * 判断部门下是否有子部门
     */
    @Override
    public boolean hasChildrenOfDepartment(Long id) {
        //创建条件构造器对象
        QueryWrapper<Department> queryWrapper = new QueryWrapper<Department>();
        queryWrapper.eq("pid",id);
        //如果数量大于0，表示存在
        if(baseMapper.selectCount(queryWrapper)>0){
            return true;
        }
        return false;
    }


    /**
     * 判断部门下是否存在用户
     */
    @Override
    public boolean hasUserOfDepartment(Long id) {
        //创建条件构造器对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("department_id", id);
        //如果数量大于0，表示存在
        if (userMapper.selectCount(queryWrapper) > 0) {
            return true;
        }
        return false;
    }
}
