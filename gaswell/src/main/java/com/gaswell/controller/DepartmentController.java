package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.Department;
import com.gaswell.service.DepartmentService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("department")
@Api(tags = "部门管理")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;



    @GetMapping("selectAll")
    @ApiOperation(value = "查询所有部门(分页)")
    @LogAnnotation(module="部门管理",operator="查询所有部门")
    public Result selectAll( String userName,int current,int size){
        return departmentService.selectAll(current,size);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询部门(分页)")
    @LogAnnotation(module="权限管理",operator="按条件查询权限")
    public Result selectByProperties(String userName, int current,int size,@RequestBody Department department) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return departmentService.selectProperties(current,size,department);
    }

    @PostMapping("addOne")
    @ApiOperation(value = "增加一个部门")
    @LogAnnotation(module="部门管理",operator="增加一个部门")
    public Result addOne(String userName, @RequestBody Department department){
        return departmentService.insertOne(department);
    }

//    @PostMapping("insertBatch")
//    @ApiOperation(value = "批量增加一个权限")
//    @LogAnnotation(module="权限管理",operator="批量增加一个权限")
//    public Result insertBatch(Integer uid,String userName, @RequestBody List<Permission> list){
//        return permissionService.insertBatch(list);
//    }

    @PutMapping("updataOne")
    @ApiOperation(value = "修改一个部门")
    @LogAnnotation(module="权限管理",operator="修改一个权限")
    public Result updataOne(String userName, @RequestBody Department department){
        return departmentService.updateOne(department);
    }

    @DeleteMapping("deleteOne/{id}")
    @ApiOperation(value = "删除一个部门")
    @LogAnnotation(module="权限管理",operator="删除一个权限")
    public Result deleteOne(String userName, @PathVariable Integer id){
        return Result.success(departmentService.removeById(id));
    }
}
