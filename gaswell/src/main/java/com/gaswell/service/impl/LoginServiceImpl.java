package com.gaswell.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.gaswell.pojo.User;
import com.gaswell.service.LoginService;
import com.gaswell.service.UserService;
import com.gaswell.utils.DESUtils;
import com.gaswell.utils.JWTUtils;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.LoginParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@DS("mysql")
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String slat="gaswell";
    @Override
    public Result login(LoginParams loginParams) {
        String passWord=loginParams.getPassword();
        String userName=loginParams.getUserName();

//        passWord= DigestUtils.md5Hex(passWord+slat);
        passWord= DESUtils.encryptBasedDes(passWord);
        loginParams.setPassword(passWord);
        if(StringUtils.isBlank(passWord) || StringUtils.isBlank(userName)){
            return Result.fail(111,"用户名和密码不能为空");
        }
        User user=userService.selectByNameAndPwd(loginParams);

        if(user==null){
            return  Result.fail(111,"用户名密码错误");
        }
        //id唯一，生成的token也唯一

        String token= JWTUtils.createToken(user.getUser_id());
        //在redis中设置30天过期时间
        redisTemplate.opsForValue().set("token_"+token, JSON.toJSONString(user),30, TimeUnit.DAYS);
        return Result.success(token);
    }
    /*检验token是否合法，以及redis中是否存储此token信息 ，并根据token返回user*/
    @Override
    public User checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap==null){
            return null;
        }
        String userJson=redisTemplate.opsForValue().get("token_"+token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }

        User user=JSON.parseObject(userJson,User.class);
        return user;
    }
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}
