package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.Permission;
import com.gaswell.service.PermissionService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:44
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@RestController
@RequestMapping("/permission")
@Api(tags = "权限管理")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("selectAll")
    @ApiOperation(value = "查询所有权限(分页)")
    @LogAnnotation(module="权限管理",operator="查询所有权限")
    public Result selectAll( String userName,int current,int size){
        return permissionService.selectAll(current,size);
    }



    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询权限(分页)")
    @LogAnnotation(module="权限管理",operator="按条件查询权限")
    public Result selectByProperties(String userName, int current,int size,@RequestBody Permission permission) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return permissionService.selectProperties(current,size,permission);
    }

    @PostMapping("addOne")
    @ApiOperation(value = "增加一个权限")
    @LogAnnotation(module="权限管理",operator="增加一个权限")
    public Result addOne(String userName, @RequestBody Permission permission){
        return permissionService.insertOne(permission);
    }

    @PostMapping("insertBatch")
    @ApiOperation(value = "批量增加一个权限")
    @LogAnnotation(module="权限管理",operator="批量增加一个权限")
    public Result insertBatch(String userName, @RequestBody List<Permission> list){
        return permissionService.insertBatch(list);
    }

    @PutMapping("updataOne")
    @ApiOperation(value = "修改一个权限")
    @LogAnnotation(module="权限管理",operator="修改一个权限")
    public Result updataOne(String userName, @RequestBody Permission permission){
        return permissionService.updateOne(permission);
    }

    @DeleteMapping("deleteOne/{id}")
    @ApiOperation(value = "删除一个权限")
    @LogAnnotation(module="权限管理",operator="删除一个权限")
    public Result deleteOne(String userName, @PathVariable Integer id){
        return Result.success(permissionService.removeById(id));
    }

}
