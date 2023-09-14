package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qaa091;
import com.gaswell.service.IQaa091Service;
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
 * 射孔井段数据 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qaa091")
@Api(tags="射孔井段数据 前端控制器")
public class Qaa091Controller {
    @Autowired
    private IQaa091Service iQaa091Service;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="射孔井段数据",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qaa091 qaa091){
        return iQaa091Service.insertOne(qaa091);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="射孔井段数据",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qaa091 qaa091){
        return iQaa091Service.updateOne(qaa091);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="射孔井段数据",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qaa091 qaa091) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQaa091Service.selectProperties(qaa091);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="射孔井段数据",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){

        return iQaa091Service.selectAll(current,size,jh);
    }

}
