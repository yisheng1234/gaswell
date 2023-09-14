package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qaa04;
import com.gaswell.service.IQaa04Service;
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
 * 套管记录 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qaa04")
@Api(tags="套管记录 前端控制器")
public class Qaa04Controller {
    @Autowired
    private IQaa04Service iQaa04Service;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="套管记录 ",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qaa04 qaa04){
        return iQaa04Service.insertOne(qaa04);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="套管记录 ",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qaa04 qaa04){
        return iQaa04Service.updateOne(qaa04);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="套管记录 ",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qaa04 qaa04) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQaa04Service.selectProperties(qaa04);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="套管记录 ",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){
        return iQaa04Service.selectAll(current,size,jh);
    }

}
