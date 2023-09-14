//package com.gaswell.schedule;
//
//import com.gaswell.pojo.RtuData;
//import com.gaswell.service.QueryDtuDataService;
//import com.gaswell.service.RtuDataRemoteService;
//import com.gaswell.service.RtuDataService;
//import com.gaswell.utils.CacheLoader;
//import com.gaswell.vo.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//public class QueryDtuDataTask {
//    @Autowired
//    private QueryDtuDataService queryDtuDataService;
//
//    @Autowired
//    private RtuDataService rtuDataService;
//
//    @Autowired
//    private RtuDataRemoteService rtuDataRemoteService;
//
////    // 每5分钟向所有设备发送一次查询全部数据指令
////    @Scheduled(cron = "0 */5 * * * ?")
////    public void execute(){
////        queryDtuDataService.queryBatchDtuData();
////    }
//
//
//    // 每5分钟从远程数据库同步一次数据
//    @Scheduled(cron = "0 */5 * * * ?")
//    public void execute() {
//
//        // 可在后端首次启动时判断
//        int count = (int) rtuDataService.selectCount().getData();
//        if(count == 0){
//            CacheLoader.isFirstSync = true;
//        }
//        else{
//            CacheLoader.isFirstSync = false;
//        }
//
//        // 首次同步，查询远程数据库所有数据同步到本地
//        if(CacheLoader.isFirstSync){
//            Result result = rtuDataRemoteService.selectAll();
//            List<RtuData> rtuDatas = (List<RtuData>) result.getData();
//            rtuDataService.insertBatch(rtuDatas);
//        }
//
//        // 非首次同步，查询本地数据库记录数countLocal，查询远程数据库记录数countRemote，查询远程数据库记录 limit countLocal, countRemote
//        if(!CacheLoader.isFirstSync){
//            int countLocal = (int)rtuDataService.selectCount().getData();
//            int countRemote = (int)rtuDataRemoteService.selectCount().getData();
//            if(countLocal != countRemote && countLocal < countRemote){
//                Result result = rtuDataRemoteService.selectByRange(countLocal, countRemote);
//                List<RtuData> rtuDatas = (List<RtuData>) result.getData();
//                rtuDataService.insertBatch(rtuDatas);
//            }
//        }
//    }
//}
