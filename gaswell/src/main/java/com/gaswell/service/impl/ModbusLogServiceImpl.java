package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaswell.mapper.ModbusLogMapper;
import com.gaswell.pojo.ModbusLog;
import com.gaswell.service.ModbusLogService;
import com.gaswell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("mysql")
public class ModbusLogServiceImpl implements ModbusLogService {
    @Autowired
    private ModbusLogMapper modbusLogMapper;
    @Override
    public Result selectAll(int current,int size) {
        Page<ModbusLog> page=new Page<>(current,size);
        modbusLogMapper.selectPage(page,null);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());
    }
}
