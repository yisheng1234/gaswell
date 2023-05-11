//package com.gaswell.controller;
//
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.pojo.ReciveCycleData;
//import com.gaswell.service.ReciveCycleDataService;
//import com.gaswell.vo.Result;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.lang.reflect.InvocationTargetException;
//
//@RestController
//@RequestMapping("/cycledata")
//@Api(tags = "周期读取数据(老)")
//public class CycleDataController {
//    @Autowired
//    private ReciveCycleDataService reciveCycleDataService;
//    @PostMapping("latestdata")
//    @ApiOperation("读取所有井最新数据最新数据")
//    @LogAnnotation(module = "周期读取数据",operator = "读取最新数据")
//    public Result findLatestData(String userName){
//        return  reciveCycleDataService.findLatestData();
//    }
//    @PostMapping("showdata")
//    @ApiOperation("数据展示")
//    @LogAnnotation(module = "周期读取数据",operator = "数据展示")
//    public Result showData(String userName,  String ywbh, String properties,  String date){
//        return reciveCycleDataService.showData(ywbh,properties,date);
//    }
//    @PostMapping("deletedata")
//    @ApiOperation("删除数据")
//    @LogAnnotation(module = "周期读取数据",operator = "删除数据")
//    public Result deleteData(String userName,  String ywbh, String date, Integer id){
//        return  reciveCycleDataService.deleteData(ywbh,date,id);
//    }
//
//    @PostMapping("showonrdaydata")
//    @ApiOperation("查询一天的数据")
//    @LogAnnotation(module = "周期读取数据",operator = "查询一天的数据")
//    public Result findOneDayData(String userName,String ywjh,String day){
//        return reciveCycleDataService.findOneDayData(ywjh,day);
//    }
//    @PostMapping("selectData")
//    @ApiOperation("查询某个时间范围的数据")
//    @LogAnnotation(module = "周期读取数据", operator = "查询某个时间范围数据")
//    public Result selectData(String userName,String properties,String ywbh,String date){
//        return reciveCycleDataService.selectData(ywbh,date,properties);
//    }
//    @PostMapping("updatedata")
//    @ApiOperation("修改数据")
//    @LogAnnotation(module = "周期读取数据",operator = "修改数据")
//    public Result updateDataById(String userName, @RequestBody ReciveCycleData reciveCycleData, String ywjh, String date){
//        return reciveCycleDataService.updateOne(reciveCycleData,ywjh,date);
//    }
//    @PostMapping("WorkingCondition")
//    @ApiOperation("工况诊断")
//    @LogAnnotation(module = "周期读取数据",operator = "工况诊断")
//    public Result WorkingCondition(String userName,String properties,int number,String ywjh) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        return reciveCycleDataService.workingCondition(properties,number,ywjh);
//    }
//
//}
