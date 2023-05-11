package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qaa02;
import com.gaswell.service.IQaa02Service;
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
 * 钻井地质信息 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qaa02")
@Api(tags="钻井地质信息 前端控制器")
public class Qaa02Controller {
    @Autowired
    private IQaa02Service iQaa02Service;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="钻井地质信息",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qaa02 qaa02){
        return iQaa02Service.insertOne(qaa02);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="钻井地质信息",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qaa02 qaa02){
        return iQaa02Service.updateOne(qaa02);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="钻井地质信息",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qaa02 qaa02) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQaa02Service.selectProperties(qaa02);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="钻井地质信息",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){
        return iQaa02Service.selectAll(current,size,jh);
    }

}
