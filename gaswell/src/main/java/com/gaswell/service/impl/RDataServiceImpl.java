package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gaswell.mapper.RDataMapper;
import com.gaswell.service.RDataService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("mysql")
public class RDataServiceImpl implements RDataService {
    @Autowired
    private RDataMapper rDataMapper;
    @Override
    public Result findLast(String jh) {
        return Result.success(rDataMapper.findLast(jh));
    }
}
