package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.service.DiagnosisService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags="数据诊断结果")
@RequestMapping("/diagnosis")
public class DiagnosisController {
    @Autowired
    private DiagnosisService diagnosisService;
    @PostMapping("latestdata")
    @ApiOperation("读取所有井最新数据")
    @LogAnnotation(module = "RTU实时生产数据",operator = "读取所有井最新数据")
    public Result findLatestData(String userName){
        return  diagnosisService.findLatestData();
    }
}
