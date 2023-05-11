//package com.gaswell.controller;
//
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.pojo.RealTimeData;
//import com.gaswell.service.RealTimeDataService;
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
//@RequestMapping("/realtimedata")
//@Api(tags = "周期读取数据")
//public class RealTimeDataController {
//    @Autowired
//    private RealTimeDataService realTimeDataService;
//    @PostMapping("latestdata")
//    @ApiOperation("读取所有井最新数据")
//    @LogAnnotation(module = "周期读取数据",operator = "读取最新数据")
//    public Result findLatestData(String userName){
//        return  realTimeDataService.findLatestData();
//    }
//    @PostMapping("showdata")
//    @ApiOperation("数据展示")
//    @LogAnnotation(module = "周期读取数据",operator = "数据展示")
//    public Result showData(String userName,  String ywbh, String properties,  String date){
//        return realTimeDataService.showData(ywbh,properties,date);
//    }
//    @PostMapping("deletedata")
//    @ApiOperation("删除数据")
//    @LogAnnotation(module = "周期读取数据",operator = "删除数据")
//    public Result deleteData(String userName,  String ywbh, String date, Integer id){
//        return  realTimeDataService.deleteData(ywbh,date,id);
//    }
//
//    @PostMapping("showonrdaydata")
//    @ApiOperation("查询一天的数据")
//    @LogAnnotation(module = "周期读取数据",operator = "查询一天的数据")
//    public Result findOneDayData(String userName,String ywjh,String day){
//        return realTimeDataService.findOneDayData(ywjh,day);
//    }
//    @PostMapping("selectData")
//    @ApiOperation("查询某个时间范围的数据(油压，套压，井口温度)")
//    @LogAnnotation(module = "周期读取数据", operator = "查询某个时间范围数据(油压，套压，井口温度)")
//    public Result selectData(String userName,String properties,String ywbh,String date){
//        return realTimeDataService.selectData(ywbh,date,properties);
//    }
//    @PostMapping("selectDataByDate")
//    @ApiOperation("查询某个时间范围的数据")
//    @LogAnnotation(module = "周期读取数据", operator = "查询某个时间范围数据")
//    public Result selectDataByDate(String userName,String ywbh,String date){
//        return realTimeDataService.selectDataByDate(ywbh,date);
//    }
//    @PostMapping("updatedata")
//    @ApiOperation("修改数据")
//    @LogAnnotation(module = "周期读取数据",operator = "修改数据")
//    public Result updateDataById(String userName, @RequestBody RealTimeData realTimeData,String ywjh,String date){
//        return realTimeDataService.updateOne(realTimeData,ywjh,date);
//    }
//    @PostMapping("WorkingCondition")
//    @ApiOperation("工况诊断")
//    @LogAnnotation(module = "周期读取数据",operator = "工况诊断")
//    public Result WorkingCondition(String userName,String properties,int number,String ywjh) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        return realTimeDataService.workingCondition(properties,number,ywjh);
//    }
//    @PostMapping("JYPolice")
//    @ApiOperation("积液")
//    @LogAnnotation(module = "周期读取数据",operator = "报警")
//    public Result JYPolice(String userName,String ywbh){
//        return realTimeDataService.JYPolice(ywbh);
//    }
//    @PostMapping("DDPolice")
//    @ApiOperation("冻堵")
//    @LogAnnotation(module = "周期读取数据",operator = "冻堵")
//    public Result DDPolice(String userName,String ywbh){
//        return  realTimeDataService.DDPolice(ywbh);
//    }
//    @PostMapping("SHWPolice")
//    @ApiOperation("水合物")
//    @LogAnnotation(module = "周期读取数据",operator = "水合物")
//    public Result SHWPolice(String userName,String ywbh){
//        return realTimeDataService.SHWPolice(ywbh);
//    }
//    @RequestMapping("alldata")
//    @ApiOperation("查询所有数据")
//    @LogAnnotation(module = "周期读取数据",operator = "查询所有数据")
//    public Result allData(String userName,String ywbh,int current,int size){
//        return  realTimeDataService.selectALLData(ywbh,current,size);
//    }
//
//}
