package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gaswell.mapper.DeviceInfoMapper;
import com.gaswell.pojo.DeviceInfo;
import com.gaswell.service.QueryDtuDataService;
import com.gaswell.utils.CacheLoader;
import com.gaswell.utils.CommandBuilder;
import com.gaswell.utils.QueryDataList;
import com.gaswell.vo.Result;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DS("mysql")
public class QueryDtuDataServiceImpl implements QueryDtuDataService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;


    @Override
    public Result queryBatchDtuData() {
        List<DeviceInfo> deviceInfos = deviceInfoMapper.selectList(null);
        for (DeviceInfo deviceInfo : deviceInfos) {
            // 设备ID
            String deviceId = deviceInfo.getDeviceId();
            // IP地址
            String ip = deviceInfo.getDeviceIp();
            // 端口号
            String port = deviceInfo.getDevicePort();
            // 每个设备一个线程
            Thread thread = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    ByteBuf heapBuf = Unpooled.buffer();
                    heapBuf.writeBytes(CommandBuilder.queryAllDataCommand(deviceId));
                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                }
            });
            CacheLoader.threadMap.put(deviceId, thread);
            thread.start();
        }
        return Result.success(CacheLoader.threadMap.keySet(),"批量指令发送完成");
    }

    @Override
    public Result queryDtuData(String deviceId) {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deviceId", deviceId);
        DeviceInfo deviceInfo = deviceInfoMapper.selectOne(queryWrapper);
        // IP地址
        String ip = deviceInfo.getDeviceIp();
        // 端口号
        String port = deviceInfo.getDevicePort();
        // 每个设备一个线程
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ByteBuf heapBuf = Unpooled.buffer();
                heapBuf.writeBytes(CommandBuilder.queryAllDataCommand(deviceId));
                CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
            }
        });
        CacheLoader.threadMap.put(deviceId, thread);
        thread.start();
        return Result.success(CacheLoader.threadMap.keySet(),"单条指令发送完成");
    }

    @Override
    public Result queryBatchDtuDataSingle() {

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("泵出口压力", 27984);
        map1.put("储液罐液位", 27986);
        map1.put("储液罐体积", 27988);
        map1.put("可燃气体浓度", 27990);
        map1.put("太阳能电池板电压", 27992);
        map1.put("蓄电池电压", 27994);
        map1.put("流量", 27996);

        // 每次批量发送命令时，需先把QueryDataList清空
        QueryDataList.queryLog.clear();
        QueryDataList.queryValue.clear();

        List<DeviceInfo> deviceInfos = deviceInfoMapper.selectList(null);
        for (DeviceInfo deviceInfo : deviceInfos) {
            // 设备ID
            String deviceId = deviceInfo.getDeviceId();
            // IP地址
            String ip = deviceInfo.getDeviceIp();
            // 端口号
            String port = deviceInfo.getDevicePort();
            // 每个设备一个线程
            Thread thread = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {

                    ByteBuf heapBuf = Unpooled.buffer();

                    // 查询泵出口压力
                    QueryDataList.queryLog.put(deviceId, "27984");
                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("泵出口压力",1,deviceId));
                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                    // 假设发送指令后5s内能得到响应
                    Thread.sleep(5000);

                    // 储液罐液位
                    heapBuf = Unpooled.buffer();
                    QueryDataList.queryLog.put(deviceId, "27986");
                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("储液罐液位",1,deviceId));
                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                    Thread.sleep(5000);

                    // 储液罐体积
                    heapBuf = Unpooled.buffer();
                    QueryDataList.queryLog.put(deviceId, "27988");
                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("储液罐体积",1,deviceId));
                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                    Thread.sleep(5000);

                    // 可燃气体浓度
                    heapBuf = Unpooled.buffer();
                    QueryDataList.queryLog.put(deviceId, "27990");
                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("可燃气体浓度",1,deviceId));
                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                    Thread.sleep(5000);

                    // 太阳能电池板电压
                    heapBuf = Unpooled.buffer();
                    QueryDataList.queryLog.put(deviceId, "27992");
                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("太阳能电池板电压",1,deviceId));
                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));

                    Thread.sleep(5000);

                    // 蓄电池电压
                    heapBuf = Unpooled.buffer();
                    QueryDataList.queryLog.put(deviceId, "27994");
                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("蓄电池电压",1,deviceId));
                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                    Thread.sleep(5000);

                    // 流量
                    heapBuf = Unpooled.buffer();
                    QueryDataList.queryLog.put(deviceId, "27996");
                    heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("流量",1,deviceId));
                    CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                    Thread.sleep(5000);

                    // 查询指令记录已清空，查询完成
                    if(QueryDataList.queryLog.get(deviceId)==null){
                        // 保存到数据库
                        System.out.println(deviceId+" 实时数据获取完成!");
                        System.out.println(QueryDataList.queryValue.get(deviceId));
                    }
                    // 查询指令记录未清空，最后一次查询仍未返回
                    else{
                        System.out.println(deviceId+" 实时数据获取不完整!");
                        System.out.println(QueryDataList.queryValue.get(deviceId));
                    }
                }
            });
            CacheLoader.threadMap.put(deviceId, thread);
            thread.start();
        }
        return Result.success(CacheLoader.threadMap.keySet(),"批量指令发送完成");
    }

    @Override
    public Result queryDtuDataSingle(String deviceId) {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deviceId", deviceId);
        DeviceInfo deviceInfo = deviceInfoMapper.selectOne(queryWrapper);
        // IP地址
        String ip = deviceInfo.getDeviceIp();
        // 端口号
        String port = deviceInfo.getDevicePort();
        // 每个设备一个线程
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                ByteBuf heapBuf = Unpooled.buffer();

                // 查询泵出口压力
                QueryDataList.queryLog.put(deviceId, "27984");
                heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("泵出口压力",1,deviceId));
                CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                // 假设发送指令后5s内能得到响应
                Thread.sleep(5000);

                // 储液罐液位
                heapBuf = Unpooled.buffer();
                QueryDataList.queryLog.put(deviceId, "27986");
                heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("储液罐液位",1,deviceId));
                CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                Thread.sleep(5000);

                // 储液罐体积
                heapBuf = Unpooled.buffer();
                QueryDataList.queryLog.put(deviceId, "27988");
                heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("储液罐体积",1,deviceId));
                CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                Thread.sleep(5000);

                // 可燃气体浓度
                heapBuf = Unpooled.buffer();
                QueryDataList.queryLog.put(deviceId, "27990");
                heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("可燃气体浓度",1,deviceId));
                CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                Thread.sleep(5000);

                // 太阳能电池板电压
                heapBuf = Unpooled.buffer();
                QueryDataList.queryLog.put(deviceId, "27992");
                heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("太阳能电池板电压",1,deviceId));
                CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));

                Thread.sleep(5000);

                // 蓄电池电压
                heapBuf = Unpooled.buffer();
                QueryDataList.queryLog.put(deviceId, "27994");
                heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("蓄电池电压",1,deviceId));
                CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                Thread.sleep(5000);

                // 流量
                heapBuf = Unpooled.buffer();
                QueryDataList.queryLog.put(deviceId, "27996");
                heapBuf.writeBytes(CommandBuilder.querySingleDataCommand("流量",1, deviceId));
                CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
                Thread.sleep(5000);

                // 查询指令记录已清空，查询完成
                if(QueryDataList.queryLog.get(deviceId)==null){
                    // 保存到数据库
                    System.out.println(deviceId+" 实时数据获取完成!");
                    System.out.println(QueryDataList.queryValue.get(deviceId));
                }
                // 查询指令记录未清空，最后一次查询仍未返回
                else{
                    System.out.println(deviceId+" 实时数据获取不完整!");
                    System.out.println(QueryDataList.queryValue.get(deviceId));
                }
            }
        });
        CacheLoader.threadMap.put(deviceId, thread);
        thread.start();
        return Result.success(CacheLoader.threadMap.keySet(),deviceId+"：逐一查询指令已发送");
    }

    @Override
    public Result readSetting(String deviceId, Integer address) {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deviceId", deviceId);
        DeviceInfo deviceInfo = deviceInfoMapper.selectOne(queryWrapper);
        // IP地址
        String ip = deviceInfo.getDeviceIp();
        // 端口号
        String port = deviceInfo.getDevicePort();

        ByteBuf heapBuf = Unpooled.buffer();

        QueryDataList.paramQueryLog.put(deviceId, String.valueOf(address));
        heapBuf.writeBytes(CommandBuilder.readSetting(deviceId, address));
        CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
        // 等待硬件返回数据
        while(QueryDataList.paramQueryLog.get(deviceId) != null && QueryDataList.paramQueryLog.get(deviceId).equals(String.valueOf(address))){

        }

        return Result.success(QueryDataList.paramQueryValue.get(deviceId), deviceId+"：查询参数指令已发送");

    }

    @Override
    public Result writeSetting(String deviceId, Integer address, Integer content) {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deviceId", deviceId);
        DeviceInfo deviceInfo = deviceInfoMapper.selectOne(queryWrapper);
        // IP地址
        String ip = deviceInfo.getDeviceIp();
        // 端口号
        String port = deviceInfo.getDevicePort();

        ByteBuf heapBuf = Unpooled.buffer();

        QueryDataList.paramQueryLog.put(deviceId, String.valueOf(address));
        heapBuf.writeBytes(CommandBuilder.writeSetting(deviceId, address, content));
        CacheLoader.udpChannel.writeAndFlush(new DatagramPacket(heapBuf, new InetSocketAddress(ip, Integer.parseInt(port))));
        return Result.success(deviceId+":写入参数指令已发送");
    }


}
