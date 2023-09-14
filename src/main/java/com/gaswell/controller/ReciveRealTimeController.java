//package com.gaswell.controller;
//
//import cn.hutool.log.Log;
//import cn.hutool.log.LogFactory;
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.utils.ByteUtils;
//import com.gaswell.utils.CacheLoader;
//import com.gaswell.utils.CommandBuilder;
//import com.gaswell.utils.QueryDataList;
//import com.gaswell.vo.Result;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelId;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/activereaddata")
//@Api(tags = "实时读取数据")
//public class ReciveRealTimeController {
//
//    static Log log = LogFactory.get(CommandController.class);
//
//    // http://localhost:8888/activereaddata/queryAllData?userName=wang&deviceId=602042504521
//    // 一次性查询7项数据
//    @GetMapping("/queryAllData")
//    @ApiOperation("发送实时读取全部数据指令")
//    @LogAnnotation(module = "发送命令", operator = "发送实时读取全部数据指令")
//    public Result queryData(@RequestParam("userName") String userName, @RequestParam("deviceId") String deviceId) {
//        // 判断设备ID是否在线
//        if (!CacheLoader.channelMapSC.containsKey(deviceId)) { // 不在线
//            log.info("设备不在线");
//            return Result.fail(444, "设备不在线");
//        } else { // 在线
//            ChannelId channelId = CacheLoader.channelMapSC.get(deviceId);
//            ByteBuf byteBuf = Unpooled.buffer();
//            // Utils.printHexString(CommandBuilder.queryDataCommand(plcPosition, dataNum));
//            byteBuf.writeBytes(CommandBuilder.queryAllDataCommand(deviceId));
//            // 将指令发送到设备
//            CacheLoader.channelGroup.find(channelId).writeAndFlush(byteBuf);
//            String hexString = ByteUtils.bytesToHexString(CommandBuilder.queryAllDataCommand(deviceId));
//            // 保存设备ID和数据起始地址
//            QueryDataList.queryPositionList.put(deviceId, "alldata");
////            System.out.println(deviceId);
//            log.info("实时读取数据指令已发送给设备：{}", deviceId);
//            log.info("指令内容：{}", hexString);
//            return Result.success("实时读取数据指令已发送给设备：" + deviceId);
//        }
//    }
//
//    // 查询从itemName开始的dataNum条数据
//    // http://localhost:8888/activereaddata/querySingleData?userName=wang&itemName=泵出口压力&dataNum=1&deviceId=602042504521
//    @GetMapping("/querySingleData")
//    @ApiOperation("发送实时读取单条数据指令")
//    @LogAnnotation(module = "发送命令", operator = "发送实时读取单条数据指令")
//    public Result querySingleData(@RequestParam("userName") String userName, @RequestParam("itemName") String itemName, @RequestParam("dataNum") int dataNum, @RequestParam("deviceId") String deviceId) {
//        // 判断设备ID是否在线
//        if (!CacheLoader.channelMapSC.containsKey(deviceId)) { // 不在线
//            log.info("设备不在线");
//            return Result.fail(444, "设备不在线");
//        } else { // 在线
//            ChannelId channelId = CacheLoader.channelMapSC.get(deviceId);
//            ByteBuf byteBuf = Unpooled.buffer();
//            byteBuf.writeBytes(CommandBuilder.queryAllDataCommand(deviceId));
//            // 将指令发送到设备
//            CacheLoader.channelGroup.find(channelId).writeAndFlush(byteBuf);
//            String hexString = ByteUtils.bytesToHexString(CommandBuilder.querySingleDataCommand(itemName, dataNum,"001"));
//            // 保存设备ID和查询的数据项
//            QueryDataList.queryPositionList.put(deviceId,itemName);
////            System.out.println(deviceId);
//            log.info("实时读取数据指令已发送给设备：{}", deviceId);
//            log.info("指令内容：{}", hexString);
//            return Result.success("实时读取数据指令已发送给设备：" + deviceId);
//        }
//    }
//
//    @GetMapping("/test")
//    public void test() {
//
//        String hexString1 = ByteUtils.bytesToHexString(CommandBuilder.queryAllDataCommand("1111"));
//        log.info("指令内容1：{}", hexString1);
//
//        String hexString2 = ByteUtils.bytesToHexString(CommandBuilder.querySingleDataCommand("泵出口压力", 1, "1111"));
//        log.info("指令内容2：{}", hexString2);
//
//        String hexString3 = ByteUtils.bytesToHexString(CommandBuilder.changeOpenValveTimeCommand(4116, 7200,"1111"));
//        log.info("指令内容3：{}", hexString3);
//
//    }
//
//}
