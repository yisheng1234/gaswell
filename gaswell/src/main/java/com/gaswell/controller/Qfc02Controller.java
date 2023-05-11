package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qfc02;
import com.gaswell.service.IQfc02Service;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 *  地层水分析数据 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qfc02")
@Api(tags = "地层水分析数据 前端控制器")
public class Qfc02Controller {
    @Autowired
    private IQfc02Service iQfc02Service;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="地层水分析数据",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qfc02 qfc02){
        return iQfc02Service.insertOne(qfc02);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="地层水分析数据",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qfc02 qfc02){
        return iQfc02Service.updateOne(qfc02);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="地层水分析数据",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qfc02 qfc02) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQfc02Service.selectProperties(qfc02);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="地层水分析数据",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){
        return iQfc02Service.selectAll(current,size,jh);
    }

}
