package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.Role;
import com.gaswell.service.RolePermissionService;
import com.gaswell.service.RoleService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:44
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Api(tags="角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("selectAll")
    @ApiOperation(value = "查询所有角色（分页）")
    @LogAnnotation(module="角色管理",operator="查询所有角色")
    public Result selectAll(String userName,int currentPage,int size){
        return roleService.selectAll(currentPage,size);
    }


    @PostMapping("selectByPropertiesPage")
    @ApiOperation(value = "按条件查询角色(分页)")
    @LogAnnotation(module="角色管理",operator="按条件分页查询角色")
    public Result selectByPropertiesPage(String userName,  Integer current,  Integer size,@RequestBody Role role){
//        IPage<Role> page = roleService.selectByPropertiesPage(current,size,role);
//        if(page.getPages() < current)
//            page = roleService.selectByPropertiesPage((int)page.getPages(),size,role);
//        return Result.success(page);
        return roleService.selectByPropertiesPage(current, size, role);
    }

    @PostMapping("addOne")
    @ApiOperation(value = "增加一个角色")
    @LogAnnotation(module="角色管理",operator="增加一个角色")
    public Result addOne(String userName, @RequestBody Role role){
        boolean flag = roleService.save(role);
        if(flag)
          return Result.success(roleService.save(role));
        else
          return Result.fail(200,"角色名称或角色种类重复！请确认后重新输入。");
    }

    @PutMapping("updataOne")
    @ApiOperation(value = "修改一个角色")
    @LogAnnotation(module="角色管理",operator="修改一个角色")
    public Result updataOne(String userName, @RequestBody Role role){
        return Result.success(roleService.updateById(role));
    }

    @DeleteMapping("deleteOne/{id}")
    @ApiOperation(value = "删除一个角色")
    @LogAnnotation(module="角色管理",operator="删除一个角色")
    public Result deleteOne(String userName, @PathVariable Integer id){
        return Result.success(roleService.removeById(id));
    }
    @GetMapping("addPermissionForRole")
    @ApiOperation(value = "为角色增加权限")
    @LogAnnotation(module="角色管理",operator="为角色增加权限")
    public Result addPermissionForRole(String userName, int roleId, @RequestParam ArrayList<Integer> permissions){
        return rolePermissionService.addPermissionForRole(roleId,permissions);
    }
    @GetMapping("deletePermissionForRole")
    @ApiOperation(value = "为角色删除权限")
    @LogAnnotation(module="角色管理",operator="为角色删除权限")
    public Result deletePermissionForRole(String userName,int roleId,int permissionId){
        return rolePermissionService.deletePermissionForRole(roleId,permissionId);
    }
}
