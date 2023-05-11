//package com.gaswell.controller;
//
//import cn.hutool.log.Log;
//import cn.hutool.log.LogFactory;
//import com.alibaba.fastjson.JSONObject;
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.handler.ReciveCycleDataHandler;
//import com.gaswell.pojo.AlgorithmRecords;
//import com.gaswell.service.AlgorithmRecordsService;
//import com.gaswell.utils.CacheLoader;
//import com.gaswell.utils.CommandBuilder;
//import com.gaswell.utils.QueryDataList;
//import com.gaswell.vo.Result;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelId;
//import io.netty.channel.socket.DatagramPacket;
//import io.netty.util.CharsetUtil;
//import io.swagger.annotations.ApiOperation;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//    @Autowired
//    AlgorithmRecordsService algorithmRecordsService;
//
//    static Log log = LogFactory.get(TestController.class);
//
//    @GetMapping("predict")
//    public Result test01(@RequestParam("userName") String userName, @RequestParam("type") String type) throws IOException {
//        Socket socket = new Socket("127.0.0.1", 16666);
//        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        PrintStream ps = new PrintStream(socket.getOutputStream());
//
//        AlgorithmRecords algorithmRecords = new AlgorithmRecords();
//
//        algorithmRecords.setAlgorithm_type(type);
//        Result result = algorithmRecordsService.selectByTypeHightest(algorithmRecords);
//        AlgorithmRecords records = (AlgorithmRecords) result.getData();
//
//        String s = "";
//        s += records.getAlgorithm_type();
//        s += ";";
//        s += records.getDatabase_condition_type();
//        s += ";";
//        s += records.getDatabase_condition();
//        s += ";";
//        s += records.getUser_id();
//        s += ";";
//        s += records.getAlgorithm_name();
//        s += ";";
//
//        String param = records.getAlgorithm_parameter();
//
////        List<String> params = Arrays.stream(param.split(",")).collect(Collectors.toList());
////        for (int i = 0; i < params.size()-1 ; i++) {
////            System.out.println(params.get(i));
////        }
//
//        String p = "";
//        if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_sfjy")){
//            // TODO 从实时数据RealTimeData获取
//            p = "10,10,10,10,10,10";
//        }
//        else if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_sfdd")){
//            p = "10,10,10,10,10,10";
//        }
//        else if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_sfjk")){
//            p = "10,10,10,10,10,10";
//        }
//        else if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_ppjzl")){
//            p = "10,10,10,10,10,10";
//        }
//        else if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_jcjzl")){
//            p = "10,10,10,10,10,10";
//        }
//        s += p;
//        ps.print(s);
//        log.info(s);
//        String str = br.readLine();
//        log.info(str);
//        // true|1   or   true|2   or  false|reason
//        String[] split = str.split("|");
//        // TODO 给RealTimeData设置大数据分析结果
//
//        return Result.success(str);
//    }
//
//    @GetMapping("/sendCommand")
//    public Result sendCommand(@RequestParam("deviceId") String deviceId){
//
//        // 设置初始值
//        QueryDataList.map.clear();
//        QueryDataList.current = null;
//
//        // 假设发送testCommand(1)、testCommand(2)、testCommand(3)共3条指令给硬件
//        // 硬件接收后，返回同样长度的响应给服务器，同时将1、2、3加3，将对应的位置设置为4、5、6
//        // 全部查询完毕后，前端收到map：{"1":"4","2":"5","3":"6"}
//        ChannelId channelId = CacheLoader.channelMapSC.get(deviceId);
//        ByteBuf byteBuf = Unpooled.buffer();
//        byteBuf.writeBytes(CommandBuilder.testCommand(1));
//        // 发送指令1
//        String current = "1";
//        QueryDataList.current = current;
//        // 将指令发送到设备
//        CacheLoader.channelGroup.find(channelId).writeAndFlush(byteBuf);
//        // 阻塞，直到返回数据
//        while(true){
//            // 接收到数据
//            if(QueryDataList.map.get(current)!=null){
//                break;
//            }
//        }
//
//        // 发送指令2
//        byteBuf = Unpooled.buffer();
//        byteBuf.writeBytes(CommandBuilder.testCommand(2));
//        current = "2";
//        QueryDataList.current = current;
//        // 将指令发送到设备
//        CacheLoader.channelGroup.find(channelId).writeAndFlush(byteBuf);
//        // 阻塞，直到返回数据
//        while(true){
//            // 接收到数据
//            if(QueryDataList.map.get(current)!=null){
//                break;
//            }
//        }
//
//        // 发送指令3
//        byteBuf = Unpooled.buffer();
//        byteBuf.writeBytes(CommandBuilder.testCommand(3));
//        current = "3";
//        QueryDataList.current = current;
//        // 将指令发送到设备
//        CacheLoader.channelGroup.find(channelId).writeAndFlush(byteBuf);
//        // 阻塞，直到返回数据
//        while(true){
//            // 接收到数据
//            if(QueryDataList.map.get(current)!=null){
//                break;
//            }
//        }
//
//        // 返回全部数据
//        return Result.success(QueryDataList.map);
//
//    }
//
//    // 查询设备指定的地址，每次只查询一个值，收到响应后，才能继续发送下一条指令，否则提示请等待
//    @GetMapping("/sendCommandSingle")
//    public Result sendCommand(@RequestParam("deviceId") String deviceId,@RequestParam("position") String position){
//
//        if(QueryDataList.queryLog.get(deviceId)!=null){
//            return Result.success("上次查询指令未返回数据，请稍后再试");
//        }
//        else{
//            ChannelId channelId = CacheLoader.channelMapSC.get(deviceId);
//            ByteBuf byteBuf = Unpooled.buffer();
//            byteBuf.writeBytes(CommandBuilder.testCommand2(Integer.valueOf(position)));
//            // 保存发送指令内容
//            QueryDataList.queryLog.put(deviceId,position);
//            // 将指令发送到设备
//            CacheLoader.channelGroup.find(channelId).writeAndFlush(byteBuf);
//            // 阻塞，直到返回数据
//            while(true){
//                // 接收到数据
//                if(QueryDataList.queryLog.get(deviceId) == null){
//                    break;
//                }
//            }
//            // 返回数据
//            return Result.success(QueryDataList.queryValue.get(deviceId).get(position));
//        }
//    }
//
//    // ****************************************以上为TCP版本****************************************
//    // ****************************************以下为UDP版本****************************************
//
//    // 向指定IP和端口发送数据
//    @GetMapping("/sendUDPCommand")
//    public Result sendUDPCommand(@RequestParam("ip") String ip, @RequestParam("port") Integer port){
//
//        CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8),
//                new InetSocketAddress(ip, port)));
//        return Result.success("ok");
//
//    }
//
//    // 同时向多个IP和端口发送查询所有数据指令（循环遍历）
//    @GetMapping("/sendUDPCommands")
//    public Result sendUDPCommand(){
//
//        // 真实IP和端口，需要根据设备ID，从数据库获取
//        // Map<String, Integer> devices = new HashMap<>();
//
//        List<Integer>  devices = new ArrayList<>();
//
//        devices.add(60033);
//        devices.add(60034);
//        devices.add(60035);
//
//        for (int i = 0; i < devices.size(); i++) {
//            Integer port = devices.get(i);
//            ByteBuf heapBuf = Unpooled.buffer();
//            heapBuf.writeBytes(CommandBuilder.queryAllDataCommand("1234"));
//            CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
//        }
//        return Result.success("ok");
//    }
//
//    // 同时向多个IP和端口发送查询所有数据指令（每个设备开一个线程）
//    @GetMapping("/sendUDPCommandsThread")
//    public Result sendUDPCommandsThread(){
//
//        // 真实IP和端口，需要根据设备ID，从数据库获取
//        // Map<String, Integer> devices = new HashMap<>();
//
//        List<Integer>  devices = new ArrayList<>();
//
//        devices.add(60033);
//        devices.add(60034);
//        devices.add(60035);
//
//        for (int i = 0; i < devices.size(); i++) {
//            Integer port = devices.get(i);
//            Thread thread = new Thread(new Runnable() {
//                @SneakyThrows
//                @Override
//                public void run() {
//                    ByteBuf heapBuf = Unpooled.buffer();
//                    heapBuf.writeBytes(CommandBuilder.queryAllDataCommand(port+""));
//                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
//                }
//            });
//            CacheLoader.threadMap.put(port+"",thread);
//            thread.start();
//        }
//        return Result.success(CacheLoader.threadMap.keySet());
//    }
//
//    // 同时向多个IP和端口发送逐一查询数据指令
//    // （每个设备开一个线程，在每个线程内，逐一发送指令查询多条数据，不等硬件返回上一条指令的响应，就发送下一条指令）
//    @GetMapping("/sendUDPCommandSingleThread")
//    public Result sendUDPCommandSingleThread(){
//
//        // 真实IP和端口，需要根据设备ID，从数据库获取
//        // Map<String, Integer> devices = new HashMap<>();
//
//        List<Integer>  devices = new ArrayList<>();
//
//        devices.add(60033);
//        devices.add(60034);
//        devices.add(60035);
//
//        Map<String, Integer> map1 = new HashMap<>();
//        map1.put("泵出口压力", 27984);
//        map1.put("储液罐液位", 27986);
//        map1.put("储液罐体积", 27988);
//        map1.put("可燃气体浓度", 27990);
//        map1.put("太阳能电池板电压", 27992);
//        map1.put("蓄电池电压", 27994);
//        map1.put("流量", 27996);
//
//        for (int i = 0; i < devices.size(); i++) {
//            // 设备ID设为端口号
//            // 地址为本地127.0.0.1
//            // 端口号为devices里的值
//            Integer port = devices.get(i);
//            Thread thread = new Thread(new Runnable() {
//                @SneakyThrows
//                @Override
//                public void run() {
//                    // 遍历查询每个地址
//                    for (String key : map1.keySet()) {
//                        ByteBuf heapBuf = Unpooled.buffer();
//                        QueryDataList.queryLog.put(port+"", map1.get(key)+"");
//                        heapBuf.writeBytes(CommandBuilder.querySingleDataCommand(key,1,port+""));
//                        CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
//                    }
//                }
//            });
//            CacheLoader.threadMap.put(port+"", thread);
//            thread.start();
//        }
//        return Result.success(CacheLoader.threadMap.keySet());
//    }
//
//    // 同时向多个IP和端口发送逐一查询数据指令
//    // （每个设备开一个线程，在每个线程内，逐一发送指令查询多条数据，等硬件返回上一条指令的响应后，再发送下一条指令）
//    @GetMapping("/sendUDPCommandSingleWaitThread")
//    public Result sendUDPCommandSingleWaitThread(){
//
//        // 真实IP和端口，需要根据设备ID，从数据库获取
//        // Map<String, Integer> devices = new HashMap<>();
//
//        // 测试时，IP为127.0.0.1，设备ID和端口号都为devices中的端口值，
//        List<Integer> devices = new ArrayList<>();
//
//        // 模拟3台硬件设备，该端口从串口调试助手动态获取
//        devices.add(59960);
//        devices.add(61187);
//        devices.add(61188);
//
//        Map<String, Integer> map1 = new HashMap<>();
//        map1.put("泵出口压力", 27984);
//        map1.put("储液罐液位", 27986);
//        map1.put("储液罐体积", 27988);
//        map1.put("可燃气体浓度", 27990);
//        map1.put("太阳能电池板电压", 27992);
//        map1.put("蓄电池电压", 27994);
//        map1.put("流量", 27996);
//
//        // 每次批量发送命令时，需先把QueryDataList清空
//        QueryDataList.queryLog.clear();
//        QueryDataList.queryValue.clear();
//
//        // 每个设备开启一个线程，在每个线程中，每间隔5s钟发送一条查询指令（参数可调）
//        // 多条指令返回后的结果存储到QueryDataList.queryValue中
//        // 线程结束后，将QueryDataList.queryValue中的数据存储到数据库中
//        for (int i = 0; i < devices.size(); i++) {
//
//            Integer port = devices.get(i);
//            Thread thread = new Thread(new Runnable() {
//                @SneakyThrows
//                @Override
//                public void run() {
//
//                    ByteBuf heapBuf = Unpooled.buffer();
//
//                    // 查询泵出口压力
//                    QueryDataList.queryLog.put(port+"", "27984");
//                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("泵出口压力",1,port+""));
//                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
//
//                    // 死循环等待，内存占用大，不可行
////                    while(true){
////                        if(QueryDataList.queryLog.get(port+"")==null){
////                            break;
////                        }
////                    }
//
//                    // 假设发送指令后5s内能得到响应
//                    Thread.sleep(5000);
//
//                    // 储液罐液位
//                    heapBuf = Unpooled.buffer();
//                    QueryDataList.queryLog.put(port+"", "27986");
//                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("储液罐液位",1,port+""));
//                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
////                    while(true){
////                        if(QueryDataList.queryLog.get(port+"")==null){
////                            break;
////                        }
////                    }
//                    Thread.sleep(5000);
//
//                    // 储液罐体积
//                    heapBuf = Unpooled.buffer();
//                    QueryDataList.queryLog.put(port+"", "27988");
//                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("储液罐体积",1,port+""));
//                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
////                    while(true){
////                        if(QueryDataList.queryLog.get(port+"")==null){
////                            break;
////                        }
////                    }
//                    Thread.sleep(5000);
//
//                    // 可燃气体浓度
//                    heapBuf = Unpooled.buffer();
//                    QueryDataList.queryLog.put(port+"", "27990");
//                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("可燃气体浓度",1,port+""));
//                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
////                    while(true){
////                        if(QueryDataList.queryLog.get(port+"")==null){
////                            break;
////                        }
////                    }
//                    Thread.sleep(5000);
//
//                    // 太阳能电池板电压
//                    heapBuf = Unpooled.buffer();
//                    QueryDataList.queryLog.put(port+"", "27992");
//                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("太阳能电池板电压",1,port+""));
//                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
////                    while(true){
////                        if(QueryDataList.queryLog.get(port+"")==null){
////                            break;
////                        }
////                    }
//                    Thread.sleep(5000);
//
//                    // 蓄电池电压
//                    heapBuf = Unpooled.buffer();
//                    QueryDataList.queryLog.put(port+"", "27994");
//                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("蓄电池电压",1,port+""));
//                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
////                    while(true){
////                        if(QueryDataList.queryLog.get(port+"")==null){
////                            break;
////                        }
////                    }
//                    Thread.sleep(5000);
//
//                    // 流量
//                    heapBuf = Unpooled.buffer();
//                    QueryDataList.queryLog.put(port+"", "27996");
//                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("流量",1,port+""));
//                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress("127.0.0.1", port)));
////                    while(true){
////                        if(QueryDataList.queryLog.get(port+"")==null){
////                            break;
////                        }
////                    }
//                    Thread.sleep(5000);
//
//                    // 查询指令记录已清空，查询完成
//                    if(QueryDataList.queryLog.get(port+"")==null){
//                        // 保存到数据库
//                        System.out.println(port+" 实时数据获取完成!");
//                        System.out.println(QueryDataList.queryValue.get(port+""));
//                    }
//                    // 查询指令记录未清空，最后一次查询仍未返回
//                    else{
//                        System.out.println(port+" 实时数据获取不完整!");
//                        System.out.println(QueryDataList.queryValue.get(port+""));
//                    }
//                }
//            });
//            CacheLoader.threadMap.put(port+"", thread);
//            thread.start();
//        }
//        return Result.success(CacheLoader.threadMap.keySet(),"已开始向所有设备发送指令");
//    }
//
//}
