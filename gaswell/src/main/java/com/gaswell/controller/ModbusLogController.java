package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.service.ModbusLogService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "MODBUS日志")
public class ModbusLogController {
    @Autowired
    private ModbusLogService modbusLogService;
    @ApiOperation("查询所有数据")
    @PostMapping ("/all")
    @LogAnnotation(module = "MODBUS日志",operator = "查询所有数据")
    public Result selectAll(String userName,int current,int size){
        return modbusLogService.selectAll( current,size);
    }
}
