package com.gaswell.controller;

import com.gaswell.service.RDataService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rdata")
@Api(tags = "设备实时信息")
public class RDataController {
    @Autowired
    private RDataService rDataService;
    @PostMapping("findLast")
    @ApiOperation(value = "根据时间查找最新设备数据")
    public Result findLast(String jh) {
        return rDataService.findLast(jh);
    }
}
