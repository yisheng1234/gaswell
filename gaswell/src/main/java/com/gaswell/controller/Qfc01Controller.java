package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qfc01;
import com.gaswell.service.IQfc01Service;
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
 * 现场天然气分析数据 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qfc01")
@Api(tags = "现场天然气分析数据 前端控制器")
public class Qfc01Controller {
    @Autowired
    private IQfc01Service iQfc01Service;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="现场天然气分析数据",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qfc01 qfc01){
        return iQfc01Service.insertOne(qfc01);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="现场天然气分析数据",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qfc01 qfc01){
        return iQfc01Service.updateOne(qfc01);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="现场天然气分析数据",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qfc01 qfc01) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQfc01Service.selectProperties(qfc01);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="现场天然气分析数据",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){
        return iQfc01Service.selectAll(current,size,jh);
    }

}
