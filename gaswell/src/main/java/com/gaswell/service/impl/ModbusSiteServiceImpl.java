package com.gaswell.service.impl;

import com.gaswell.mapper.ModbusSiteMapper;
import com.gaswell.service.ModbusSiteService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModbusSiteServiceImpl implements ModbusSiteService {
    @Autowired
    private ModbusSiteMapper modbusSiteMapper;
    @Override
    public Result selectAll() {
        return Result.success(modbusSiteMapper.selectList(null));
    }
}
