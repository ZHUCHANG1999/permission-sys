package com.permission.authority.controller;


import com.permission.authority.entity.Department;
import com.permission.authority.entity.query.DepartmentQueryVo;
import com.permission.authority.service.DepartmentService;
import com.permission.authority.utils.Result;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2025-04-05
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    /**
     * 查询部门列表
     */
    @GetMapping("/list")
    public Result list(DepartmentQueryVo departmentQueryVo){
        //调用查询部门列表方法
        List<Department> departmentList =
                departmentService.findDepartmentList(departmentQueryVo);
        //返回数据
        return Result.ok(departmentList);
    }

    /**
     * 查询上级部门列表
     */
    @GetMapping("/parent/list")
    public Result getParentDepartment(){
        //调用查询上级部门列表方法
        List<Department> departmentList =
                departmentService.findParentDepartment();
        //返回数据
        return Result.ok(departmentList);
    }

    /**
     * 添加部门
     */
    @PostMapping("/add")
    public Result add(@RequestBody Department department){
        if(departmentService.save(department)){
            return Result.ok().message("部门添加成功");
        }
        return Result.error().message("部门添加失败");
    }

    /**
     * 修改部门
     */
    @PutMapping("/update")
    public Result update(@RequestBody Department department){
        if(departmentService.updateById(department)){
            return Result.ok().message("部门修改成功");
        }
        return Result.error().message("部门修改失败");
    }

    /**
     * 查询某个部门下是否存在子部门
     */
    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id){
        //调用查询部门下是否存在子部门的方法
        if(departmentService.hasChildrenOfDepartment(id)){
            return Result.exist().message("该部门下存在子部门，无法删除");
        }
        //调用查询部门下是否存在用户的方法
        if(departmentService.hasUserOfDepartment(id)){
            return Result.exist().message("该部门下存在用户，无法删除");
        }
        return Result.ok();
    }


    /**
     * 删除部门
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        if(departmentService.removeById(id)){
            return Result.ok().message("部门删除成功");
        }
        return Result.error().message("部门删除失败");
    }
}
