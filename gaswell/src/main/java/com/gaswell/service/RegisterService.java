package com.gaswell.service;

import com.gaswell.vo.Result;
import com.gaswell.vo.params.RegisterParams;

public interface RegisterService {
    Result register(RegisterParams registerParams);
}
