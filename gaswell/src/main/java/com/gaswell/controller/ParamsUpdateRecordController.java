package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.service.ParamsUpdateRecordServie;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paramsUpdate")
@Api(tags = "参数修改记录")
public class ParamsUpdateRecordController {
    @Autowired
    private ParamsUpdateRecordServie paramsUpdateRecordServie;
    @PostMapping("allData")
    @ApiOperation(value = "查询所有数据")
    @LogAnnotation(module = "参数修改记录", operator = "查询所有数据")
    public Result selectAll(String userName,int current,int size){
        return paramsUpdateRecordServie.selectAll(current,size);
    }
}
