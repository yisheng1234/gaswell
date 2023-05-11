package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.service.IQaa01Service;
import com.gaswell.service.QueryDtuDataService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lei Wang
 * @Date: 2022/05/20/ 13:07
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 设置参数
@RestController("device")
@Api(tags="设备参数设置 前端控制器")
public class DeviceController {

    @Autowired
    private QueryDtuDataService queryDtuDataService;

    @GetMapping("readSetting")
    @ApiOperation(value = "读取设备参数")
    @LogAnnotation(module="设备管理",operator="读取设备参数")
    Result readSetting(@RequestParam("deviceId") String deviceId, @RequestParam("address") Integer address) {
        return queryDtuDataService.readSetting(deviceId, address);
    }

    @GetMapping("writeSetting")
    @ApiOperation(value = "写入设备参数")
    @LogAnnotation(module="设备管理",operator="写入设备参数")
    Result writeSetting(@RequestParam("deviceId") String deviceId, @RequestParam("address") Integer address, @RequestParam("content") Integer content){
        return queryDtuDataService.writeSetting(deviceId, address, content);
    }


}
