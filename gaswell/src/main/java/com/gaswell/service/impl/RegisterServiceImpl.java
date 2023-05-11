package com.gaswell.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.gaswell.pojo.User;
import com.gaswell.service.DepartmentService;
import com.gaswell.service.RegisterService;
import com.gaswell.service.RoleService;
import com.gaswell.service.UserService;
import com.gaswell.utils.DESUtils;
import com.gaswell.utils.JWTUtils;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.RegisterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@DS("mysql")
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserService userService;
    private static final String slat="gaswell";
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RoleService roleService;

    @Override
    public Result register(RegisterParams registerParams) {
        User user = userService.selectUserByuserName(registerParams.getUserName());
        if(user!=null){
            return  Result.fail(111,"用户名已存在");
        }

        int departmentId = departmentService.fineByName(registerParams.getDepartment());
        int roleId=roleService.findByName(registerParams.getRoleName());
        User newUser=new User();

        newUser.setUser_department_id(departmentId);
        newUser.setUser_name(registerParams.getUserName());
        newUser.setUser_realname(registerParams.getRealName());
//        System.out.println(registerParams.getPassWord());
//        newUser.setUser_password(DigestUtils.md5Hex(registerParams.getPassWord()+slat));
        newUser.setUser_password(DESUtils.encryptBasedDes(registerParams.getPassWord()));

        //默认设置成职员的id
        newUser.setUser_role_id(roleId);
//        System.out.println(newUser.getUser_password());
        userService.save(newUser);
//        System.out.println(newUser.getUser_id());
        String token = JWTUtils.createToken(newUser.getUser_id());
                                          //将对象转为string类型.       过期时间一天
        redisTemplate.opsForValue().set("token_"+token, JSON.toJSONString(newUser),30, TimeUnit.DAYS);
        return Result.success(token);
    }

}
