package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qbb01;
import com.gaswell.service.IQbb01Service;
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
 *  气田开发综合月数据 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qbb01")
@Api(tags = "气田开发综合月数据 前端控制器")
public class Qbb01Controller {
    @Autowired
    private IQbb01Service iQbb01Service;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="气田开发综合月数据",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qbb01 qbb01){
        return iQbb01Service.insertOne(qbb01);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="气田开发综合月数据",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qbb01 qbb01){
        return iQbb01Service.updateOne(qbb01);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="气田开发综合月数据",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qbb01 qbb01) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQbb01Service.selectProperties(qbb01);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="气田开发综合月数据",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){
        return iQbb01Service.selectAll(current,size,jh);
    }

}
