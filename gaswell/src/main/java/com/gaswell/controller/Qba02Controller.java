package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qba02;
import com.gaswell.service.IQba02Service;
import com.gaswell.service.UserService;
import com.gaswell.vo.Qba01Vo;
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
 * 采气井月数据 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qba02")
@Api(tags = "采气井月数据 前端控制器")
public class Qba02Controller {
    @Autowired
    private IQba02Service iQba02Service;
@Autowired
private UserService userService;
    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="采气井月数据",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Qba02 qba02){
        return iQba02Service.insertOne(qba02);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="采气井月数据",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Qba02 qba02){
        return iQba02Service.updateOne(qba02);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="采气井月数据",operator="按条件查询")
    public Result selectList(String userName,@RequestBody Qba02 qba02,int sort) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return iQba02Service.selectProperties(qba02,sort,department);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="采气井月数据",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String jh,int sort){
        return iQba02Service.selectAll(current,size,jh,sort);
    }
    @PostMapping("selectByMutiProperties")
    @ApiOperation(value = "按条件查询(多选)")
    @LogAnnotation(module = "采气井月数据", operator = "按条件查询（多选）")
    public Result selectData(String userName, @RequestBody Qba01Vo qba01Vo,String date,int sort) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        Result result = iQba02Service.selectByMutiProperties(qba01Vo,date, sort,department);
        return result;
    }
    @PostMapping("finZyqm")
    @ApiOperation(value = "查询所有作业区名")
    @LogAnnotation(module = "采气井月数据", operator = "查询所有区名")
    public Result findQm(String userName) {
        return iQba02Service.findZyqm();
    }
    @PostMapping("findZm")
    @ApiOperation(value = "查询所有站名")
    @LogAnnotation(module = "采气井月数据", operator = "查询所有站名")
    public Result findZm(String userName) {
        return iQba02Service.findZm();
    }
    @PostMapping("findDm")
    @ApiOperation(value = "查询所有队名")
    @LogAnnotation(module = "采气井月数据", operator = "查询所有队名")
    public Result findDm(String userName) {
        return iQba02Service.findDm();
    }

    @PostMapping("findQkmc")
    @ApiOperation(value = "查询所有区块名称")
    @LogAnnotation(module = "采气井日数据", operator = "查询所有区块名称")
    public Result selectAll(String userName) {
        return iQba02Service.findQkmc();
    }

}
