package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qaa01;
import com.gaswell.service.IQaa01Service;
import com.gaswell.service.UserService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * 气井基础信息 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qaa01")
@Api(tags="气井基础信息 前端控制器")
public class Qaa01Controller {
    @Autowired
    private IQaa01Service iQaa01Service;
    @Autowired
    private UserService userService;
    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="气井基础信息",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qaa01 qaa01){
        return iQaa01Service.insertOne(qaa01);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="气井基础信息",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qaa01 qaa01){
        return iQaa01Service.updateOne(qaa01);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="气井基础信息",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qaa01 qaa01) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return iQaa01Service.selectProperties(qaa01);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="气井基础信息",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh){
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return iQaa01Service.selectAll(current,size,jh,department);
    }

    @GetMapping("/selectQTMC")
    @ApiOperation(value = "按照区名和站名查询井号")
    @LogAnnotation(module="气井基础信息",operator="查询所有气田名称")
    public Result selectJh(String userName,String qm,String zm){
        return iQaa01Service.selectJh(qm,zm);
    }

    @GetMapping("/selectQM")
    @ApiOperation(value = "查询所有区名")
    @LogAnnotation(module="气井基础信息",operator="查询所有区名")
    public Result selectQM(String userName){
        return iQaa01Service.selectQM();
    }

    @GetMapping("/selectZM")
    @ApiOperation(value = "按区名查询站名")
    @LogAnnotation(module="气井基础信息",operator="查询所有站名")
    public Result selectZM(String userName,String qm){
        return iQaa01Service.selectZM(qm);
    }

}
