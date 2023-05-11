package com.gaswell.service;

import com.gaswell.pojo.User;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.LoginParams;

public interface LoginService {

    Result login(LoginParams loginParams);
    User checkToken(String token);
    Result logout(String token) ;
}
