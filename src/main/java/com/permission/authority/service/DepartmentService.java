package com.permission.authority.service;

import com.permission.authority.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.permission.authority.entity.query.DepartmentQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2025-04-05
 */
public interface DepartmentService extends IService<Department> {
    /**
     * 查询部门列表
     */
    List<Department> findDepartmentList(DepartmentQueryVo departmentQueryVo);


    /**
     * 查询上级部门列表
     */
    List<Department> findParentDepartment();

    /**
     * * 判断部门下是否有子部门
     */
    boolean hasChildrenOfDepartment(Long id);

    /**
     * * 判断部门下是否存在用户
     */
    boolean hasUserOfDepartment(Long id);
}
