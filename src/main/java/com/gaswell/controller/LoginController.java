package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.service.LoginService;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.LoginParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录")
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("login")
    @ApiOperation("登录")
    @LogAnnotation(module = "登录",operator = "登录")
    public Result login(String userName,@RequestBody LoginParams loginParams){
        return  loginService.login(loginParams);
    }
}
