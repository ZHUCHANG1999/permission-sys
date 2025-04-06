package com.permission.authority.controller;


import com.permission.authority.entity.Permission;
import com.permission.authority.entity.query.PermissionQueryVo;
import com.permission.authority.service.PermissionService;
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
@RequestMapping("/api/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 查询菜单列表
     */
    @GetMapping("/list")
    public Result getMenuList(PermissionQueryVo permissionQueryVo) {
        //查询菜单列表
        List<Permission> permissionList =
                permissionService.findPermissionList(permissionQueryVo);
        //返回数据
        return Result.ok(permissionList);
    }

    /**
     * 查询上级菜单列表
     */
    @GetMapping("/parent/list")
    public Result getParentList() {
        //查询上级菜单列表
        List<Permission> permissionList =
                permissionService.findParentPermissionList();
        //返回数据
        return Result.ok(permissionList);
    }

    /**
     * 根据id查询菜单信息
     */
    @GetMapping("/{id}")
    public Result getMenuById(@PathVariable Long id) {
        return Result.ok(permissionService.getById(id));
    }

    /**
     * 添加菜单
     */
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission) {
        if (permissionService.save(permission)) {
            return Result.ok().message("菜单添加成功");
        }
        return Result.error().message("菜单添加失败");
    }

    /**
     * 修改菜单
     */
    @PutMapping("/update")
    public Result update(@RequestBody Permission permission) {
        if (permissionService.updateById(permission)) {
            return Result.ok().message("菜单修改成功");
        }
        return Result.error().message("菜单修改失败");
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        if (permissionService.removeById(id)) {
            return Result.ok().message("菜单删除成功");
        }
        return Result.error().message("菜单删除失败");
    }

    /**
     * 检查菜单下是否有子菜单
     *
     * @param id
     * @return
     */
    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id) {
        //判断菜单是否有子菜单
        if (permissionService.hasChildrenOfPermission(id)) {
            return Result.exist().message("该菜单下有子菜单，无法删除");
        }
        return Result.ok();
    }

}
