//package com.gaswell.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.pojo.ControlValveLog;
//import com.gaswell.service.ControlValveLogService;
//import com.gaswell.vo.Result;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.util.Strings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @author Lei Wang
// * @Date: 2022/03/30/ 15:15
// * @Blog leiwang.xyz
// * @Email ileiwang@live.com
// */
//@Slf4j
//@RestController
//@RequestMapping("/controlValveLog")
//@Api(tags = "阀门控制日志模块")
//public class ControlValveLogController {
//    @Autowired
//    ControlValveLogService controlValveLogService;
//
//    // 无条件分页查询
//    @GetMapping("selectAll")
//    @ApiOperation(value = "查询所有日志(分页)")
//    @LogAnnotation(module="阀门控制日志模块",operator="查询所有日志")
//    public Result selectAll(String userName, int current, int size){
//        Page<ControlValveLog> page = new Page<>(current,size);
//        controlValveLogService.page(page);
//        List<ControlValveLog> list = page.getRecords();
//        return Result.success(list);
//    }
//
//    // 有条件分页查询
//    @GetMapping("selectByProperties")
//    @ApiOperation(value = "按条件查询日志(分页)")
//    @LogAnnotation(module="阀门控制日志模块",operator="按条件查询日志")
//    public Result selectByProperties(String userName, int current, int size, @RequestBody ControlValveLog controlValveLog){
//        LambdaQueryWrapper<ControlValveLog> lqw = new LambdaQueryWrapper<ControlValveLog>();
//        lqw.eq(controlValveLog.getId()!=0,ControlValveLog::getId, controlValveLog.getId());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getDeviceId()), ControlValveLog::getDeviceId,controlValveLog.getDeviceId());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getPlcPosition()), ControlValveLog::getPlcPosition,controlValveLog.getPlcPosition());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getStatus()), ControlValveLog::getStatus,controlValveLog.getStatus());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getControlTime()), ControlValveLog::getControlTime,controlValveLog.getControlTime());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getResult()), ControlValveLog::getResult,controlValveLog.getResult());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getCommand()), ControlValveLog::getCommand,controlValveLog.getCommand());
//        IPage<ControlValveLog> page=new Page<>(current,size);
//        controlValveLogService.page(page, lqw);
//        List<ControlValveLog> list = page.getRecords();
//        return Result.success(list);
//    }
//
//    // 按ID删除一条记录
//    @DeleteMapping("deleteOne/{id}")
//    @ApiOperation(value = "删除一条日志")
//    @LogAnnotation(module="阀门控制日志模块",operator="删除一条日志")
//    public Result deleteOne(String userName, @PathVariable Integer id){
//        return Result.success(controlValveLogService.removeById(id));
//    }
//
//    // 按ID批量删除
//    @DeleteMapping("deleteBatch")
//    @ApiOperation(value = "批量删除日志")
//    @LogAnnotation(module="阀门控制日志模块",operator="批量删除日志")
//    public Result deleteOne(String userName, @RequestParam List<Integer> ids){
//        return Result.success(controlValveLogService.removeByIds(ids));
//    }
//
//    // 按条件删除
//    @DeleteMapping("deleteByProperties")
//    @ApiOperation(value = "按条件删除日志")
//    @LogAnnotation(module="阀门控制日志模块",operator="按条件删除日志")
//    public Result deleteByProperties(String userName, @RequestBody ControlValveLog controlValveLog){
//        LambdaQueryWrapper<ControlValveLog> lqw=new LambdaQueryWrapper<>();
//        lqw.eq(controlValveLog.getId()!=0,ControlValveLog::getId, controlValveLog.getId());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getDeviceId()), ControlValveLog::getDeviceId,controlValveLog.getDeviceId());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getPlcPosition()), ControlValveLog::getPlcPosition,controlValveLog.getPlcPosition());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getStatus()), ControlValveLog::getStatus,controlValveLog.getStatus());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getControlTime()), ControlValveLog::getControlTime,controlValveLog.getControlTime());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getResult()), ControlValveLog::getResult,controlValveLog.getResult());
//        lqw.eq(Strings.isNotEmpty(controlValveLog.getCommand()), ControlValveLog::getCommand,controlValveLog.getCommand());
//        return Result.success(controlValveLogService.remove(lqw));
//    }
//}
