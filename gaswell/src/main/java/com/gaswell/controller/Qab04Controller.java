package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qab04;
import com.gaswell.service.IQab04Service;
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
 * 气藏流体性质数据 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qab04")
@Api(tags = "气藏流体性质数据 前端控制器")
public class Qab04Controller {
    @Autowired
    private IQab04Service iQab04Service;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="气藏流体性质数据",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qab04 qab04){
        return iQab04Service.insertOne(qab04);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="气藏流体性质数据",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qab04 qab04){
        return iQab04Service.updateOne(qab04);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="气藏流体性质数据",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qab04 qab04) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQab04Service.selectProperties(qab04);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="气藏流体性质数据",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){
        return iQab04Service.selectAll(current,size,jh);
    }

}
