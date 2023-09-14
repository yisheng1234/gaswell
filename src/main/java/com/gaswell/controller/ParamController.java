//package com.gaswell.controller;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.pojo.Param;
//import com.gaswell.service.ParamService;
//import com.gaswell.vo.Result;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @author Lei Wang
// * @Date: 2022/04/17/ 17:55
// * @Blog leiwang.xyz
// * @Email ileiwang@live.com
// */
//@RestController
//@RequestMapping("/param")
//@Api(tags = "参数配置管理")
//public class ParamController {
//
//    @Autowired
//    private ParamService paramService;
//
//    @GetMapping("selectByPage")
//    @ApiOperation(value = "分页查询所有参数配置")
//    @LogAnnotation(module = "参数配置管理", operator = "分页查询所有参数配置")
//    public Result selectByPage(String userName, int current, int size) {
//        Page<Param> page = new Page<>(current, size);
//        paramService.page(page);
//        List<Param> list = page.getRecords();
//        return Result.success(list);
//    }
//
//    @GetMapping("selectAll")
//    @ApiOperation(value = "查询所有参数配置")
//    @LogAnnotation(module = "参数配置管理", operator = "查询所有参数配置")
//    public Result selectAll(String userName) {
//        List<Param> list = paramService.list();
//        return Result.success(list);
//    }
//
//    // 添加
//    @PostMapping("addOne")
//    @ApiOperation(value = "增加参数")
//    @LogAnnotation(module = "参数配置管理", operator = "增加参数")
//    public Result addOne(String userName, @RequestBody Param param) {
//        boolean result = paramService.save(param);
//        if (result) {
//            return Result.success("success");
//        } else {
//            return Result.fail(400, "fail");
//        }
//    }
//
//    // 按ID删除
//    @DeleteMapping("deleteOne/{id}")
//    @ApiOperation(value = "删除参数")
//    @LogAnnotation(module = "参数配置管理", operator = "删除参数")
//    public Result deleteOne(String userName, @PathVariable Integer id) {
//        return Result.success(paramService.removeById(id));
//    }
//
//    // 修改
//    @PostMapping("updataOne")
//    @ApiOperation(value = "修改参数")
//    @LogAnnotation(module = "参数配置管理", operator = "修改参数")
//    public Result updataOne(String userName, @RequestBody Param param) {
//        boolean result = paramService.updateById(param);
//        if (result) {
//            return Result.success("success");
//        } else {
//            return Result.fail(400, "fail");
//        }
//    }
//}
