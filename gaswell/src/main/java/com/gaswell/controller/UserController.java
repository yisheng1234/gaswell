package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.User;
import com.gaswell.service.UserService;
import com.gaswell.utils.DESUtils;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:44
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("selectAll")
    @ApiOperation(value = "查询所有用户(分页)")
    @LogAnnotation(module = "用户管理",operator = "查询所有用户")
    public Result selectAll(String userName,int current,int size){
        return userService.selectAll(current,size);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询用户")
    @LogAnnotation(module = "用户管理",operator = "按条件查询用户")
    public Result selectByProperties( String userName, @RequestBody User user){
        return Result.success(userService.selectByProperties(user));
    }

    @PostMapping("selectByPropertiesPage/{current}/{size}")
    @ApiOperation(value = "按条件分页查询用户")
    @LogAnnotation(module = "用户管理",operator = "按条件分页查询用户")
    public Result selectByPropertiesPage( String userName,@PathVariable Integer current, @PathVariable Integer size, @RequestBody User user){
        return userService.selectByPropertiesPage(current,size,user);
    }

    @PostMapping
    @ApiOperation(value = "按用户名查询用户权限")
    @LogAnnotation(module = "用户管理",operator = "按用户名查询用户权限")
    public Result selectPermissionByUserName(String userName,Integer current,Integer size){
        return userService.selectPermissionByuserName();
    }
    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除用户")
    @LogAnnotation(module = "用户管理",operator = "删除用户")
    public Result delete( String userName, @PathVariable Integer id){
        return Result.success(userService.removeById(id));
    }

    @DeleteMapping("deleteByType/{id}")
    @ApiOperation(value = "按种类删除用户")
    @LogAnnotation(module = "用户管理",operator = "按种类删除用户")
    public Result deleteByType( String userName, @PathVariable Integer id){
        return Result.success(userService.removeByRole(id));
    }

    @DeleteMapping("deleteByDepartment/{id}")
    @ApiOperation(value = "按部门删除用户")
    @LogAnnotation(module = "用户管理",operator = "按部门删除用户")
    public Result deleteByDepartment( String userName, @PathVariable Integer id){
        return Result.success(userService.removeByDepartment(id));
    }

    @PostMapping("addOne")
    @ApiOperation("增加一个用户")
    @LogAnnotation(module = "用户管理",operator = "增加一个用户")
    public Result addOne( String userName, @RequestBody User user){
        User user1 = userService.selectUserByuserName(user.getUser_name());
        if(user1!=null){
            return  Result.fail(111,"用户名已存在");
        }
        user.setUser_password(DESUtils.encryptBasedDes(user.getUser_password()));
        return Result.success(userService.save(user));
    }

    @PostMapping("updataOne")
    @ApiOperation("修改一个用户")
    @LogAnnotation(module = "用户管理",operator = "修改一个用户")
    public Result updataOne(String userName, @RequestBody User user){
        return userService.updateUser(user);
    }

    @PostMapping("currentUser")
    @ApiOperation("根据token获取用户信息")
    public Result getUserInfoByToken(@RequestHeader("Authorization") String token){
        return userService.getCurrentUser(token);
    }
}
