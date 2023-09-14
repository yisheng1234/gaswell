package com.gaswell.controller;

import com.gaswell.service.RegisterService;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.RegisterParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "注册")
@RestController
@RequestMapping("/register")
@Slf4j
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @ApiOperation("用户注册")
    @PostMapping("register")
//    @LogAnnotation(module = "登录",operator = "登录")
    public Result register(@RequestBody  RegisterParams registerParams){
        return registerService.register(registerParams);
    }
}
