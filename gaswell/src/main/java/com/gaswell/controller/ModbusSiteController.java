package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.service.ModbusSiteService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("modbussite")
@Api(tags = "组态图参数控制")
public class ModbusSiteController {
    @Autowired
    private ModbusSiteService modbusSiteService;
    @ApiOperation("查询所有数据")
    @PostMapping("/all")
    @LogAnnotation(module = "MODBUS日志",operator = "查询所有数据")
    public Result selectAll(String userName){
        return modbusSiteService.selectAll();
    }
}
