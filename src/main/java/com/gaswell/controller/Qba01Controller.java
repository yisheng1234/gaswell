package com.gaswell.controller;


import com.gaswell.common.log.LogAnnotation;
import com.gaswell.entity.Qba01;
import com.gaswell.mapper.Qba01Mapper;
import com.gaswell.service.IQba01Service;
import com.gaswell.service.UserService;
import com.gaswell.vo.Qba01Vo;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 采气井日数据 前端控制器
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/qba01")
@Api(tags = "采气井日数据 前端控制器")
public class Qba01Controller {
    @Autowired
    private IQba01Service iQba01Service;
    @Autowired
    private UserService userService;
    @Autowired
    private Qba01Mapper qba01Mapper;

    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module = "采气井日数据", operator = "添加一条数据")
    public Result insertOne(String userName, @RequestBody Qba01 qba01) {
        return iQba01Service.insertOne(qba01);
    }


    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module = "采气井日数据", operator = "修改一条数据")
    public Result updateOne(String userName, @RequestBody Qba01 qba01) {
        return iQba01Service.updateOne(qba01);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module = "采气井日数据", operator = "按条件查询")
    public Result selectList(String userName, @RequestBody Qba01 qba01,int sort) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        Result result = iQba01Service.selectProperties(qba01, sort,department);
        return result;
    }
    @PostMapping("selectByMutiProperties")
    @ApiOperation(value = "按条件查询(多选)")
    @LogAnnotation(module = "采气井日数据", operator = "按条件查询（多选）")
    public Result selectData(String userName, @RequestBody Qba01Vo qba01Vo,String date,int sort) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        Result result = iQba01Service.selectByMutiProperties(qba01Vo,date,sort,department);
        return result;
    }
    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module = "采气井日数据", operator = "查询所有数据")
    public Result selectAll(String userName, int current, int size, String jh,int sort) {
        return iQba01Service.selectAll(current, size, jh,sort);
    }
    @PostMapping("findQm")
    @ApiOperation(value = "查询所有区名")
    @LogAnnotation(module = "采气井日数据", operator = "查询所有区名")
    public Result findQm(String userName) {
        return iQba01Service.findQm();
    }
    @PostMapping("findZm")
    @ApiOperation(value = "查询所有站名")
    @LogAnnotation(module = "采气井日数据", operator = "查询所有站名")
    public Result findZm(String userName) {
        return iQba01Service.findZm();
    }
    @PostMapping("findDm")
    @ApiOperation(value = "查询所有队名")
    @LogAnnotation(module = "采气井日数据", operator = "查询所有队名")
    public Result findDm(String userName) {
        return iQba01Service.findQm();
    }
    @PostMapping("findZyq")
    @ApiOperation(value = "查询所有作业区")
    @LogAnnotation(module = "采气井日数据", operator = "查询所有作业区")
    public Result findZyq(String userName) {
        return iQba01Service.findZyq();
    }
    @PostMapping("findQkmc")
    @ApiOperation(value = "查询所有区块名称")
    @LogAnnotation(module = "采气井日数据", operator = "查询所有区块名称")
    public Result selectAll(String userName) {
        return iQba01Service.findQkmc();
    }
    /**
     * 使用cursor查询全部数据
     * @return
     */
    @GetMapping("cursorgetAll")
    @Transactional
    public Result test() {
        List list = new LinkedList<>();
//        Object o=null;
        try (Cursor<Qba01> cursor = iQba01Service.streamQuery()) {
            cursor.forEach(t -> {
                list.add(t);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(list);
    }

}
