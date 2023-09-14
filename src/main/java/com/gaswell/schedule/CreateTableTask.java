//package com.gaswell.schedule;
//
//import com.gaswell.mapper.RealTimeDataMapper;
////import com.gaswell.mapper.ReciveCycleDataMapper;
//import com.gaswell.pojo.Qj;
//import com.gaswell.service.QjService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class CreateTableTask {
//    @Autowired
//    private QjService qjService;
////    @Autowired
////    private ReciveCycleDataMapper reciveCycleDataMapper;
//    @Autowired
//    private RealTimeDataMapper realTimeDataMapper;
//    //秒 分 时 每月中的第几天 那个月      *每个月     ？占位符无意义
//    @Scheduled(cron = "1 0 0 1 * ?")  //每月一号 00:00:01执行一次
//    public void execute(){
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
//        String date=sdf.format(new Date());
//
//        List<Qj> qjs = qjService.selectAll();
//        for (Qj qj : qjs) {
//            // String tableName_rcd="recivecycledata_"+qj.getYwbh()+"_"+date;
//
//            // 例如：ws5_202203，存储实时生产数据，数据可修改
//            String tableName = qj.getYwbh() + "_" + date;
////            reciveCycleDataMapper.createTable(tableName);
//            realTimeDataMapper.createTable(tableName);
//            // 例如：ws5_202203_backup，存储原始实时生产数据，数据不可修改
//            String tableName_backup = qj.getYwbh() + "_" + date + "_backup";
//            realTimeDataMapper.createTable_backup(tableName_backup);
//        }
//    }
//}
//

