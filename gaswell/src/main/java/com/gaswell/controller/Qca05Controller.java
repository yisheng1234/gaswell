package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qca05;
import com.gaswell.service.IQca05Service;
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
 * 压力恢复测试解释数据 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qca05")
@Api(tags = "压力恢复测试解释数据 前端控制器")
public class Qca05Controller {
    @Autowired
    private IQca05Service iQca05Service;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="压力恢复测试解释数据",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qca05 qca05){
        return iQca05Service.insertOne(qca05);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="压力恢复测试解释数据",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qca05 qca05){
        return iQca05Service.updateOne(qca05);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="压力恢复测试解释数据",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qca05 qca05) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQca05Service.selectProperties(qca05);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="压力恢复测试解释数据",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){
        return iQca05Service.selectAll(current,size,jh);
    }

}
