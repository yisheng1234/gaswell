package com.gaswell.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lei Wang
 * @Date: 2022/01/15/ 9:28
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 保存TCP网络连接
// 一个硬件设备ID（4G SIM卡号?）对应一个TCP网络连接ChannelId
    @Component
public class CacheLoader {

    // 保存TCP连接对象
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 保存设备ID和对应的TCP连接对象
    public static Map<String, ChannelId> channelMapSC = new ConcurrentHashMap<>();
    // 保存TCP连接对象和对应的设备ID
    public static Map<ChannelId, String> channelMapCS = new ConcurrentHashMap<>();
    // 保存设备ID和设备IP
    public static Map<String, String> deviceIdAndIp = new ConcurrentHashMap<>();

    // 保存UDP连接对象
    // 该连接对象可以向所有设备发送UDP指令
    public static Channel udpChannel = null;

    // 每个设备开启一个线程，进行多个地址的逐一查询
    public static Map<String, Thread> threadMap = new ConcurrentHashMap<>();

    // 判断deviceIdAndIp中的设备ID判断是否存在
    public static boolean isDeviceIdExist(String deviceId) {
        return deviceIdAndIp.containsKey(deviceId);
    }

    // 判断deviceIdAndIp中的设备IP和deviceIp是否相等
    public static boolean isDeviceIpEqual(String deviceId, String deviceIp) {
        return deviceIdAndIp.get(deviceId).equals(deviceIp);
    }

    // 将设备ID、设备IP、Channel添加到缓存
    public static void addToCache(Channel channel, String deviceId) {
        // 从Channel中获得设备IP
        String deviceIp = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
        // 判断设备ID是否存在
        boolean isDeviceIdExist = isDeviceIdExist(deviceId);
        if (!isDeviceIdExist) { // 如果设备ID不存在，则添加到缓存
            CacheLoader.channelGroup.add(channel);
            CacheLoader.channelMapSC.put(deviceId, channel.id());
            CacheLoader.channelMapCS.put(channel.id(), deviceId);
            CacheLoader.deviceIdAndIp.put(deviceId, deviceIp);

        } else { // 如果设备ID存在，判断设备IP和deviceId是否相等
            boolean isDeviceIpEqual = isDeviceIpEqual(deviceId, deviceIp);
            if(isDeviceIpEqual){ // 如果相等，说明已注册，且设备IP未变化，无需操作
            }
            else{ // 如果不相等，说明已注册，且设备IP已变化，需先移除，再重新添加
                // 删除旧连接
                ChannelId channelId = CacheLoader.channelMapSC.get(deviceId);
                CacheLoader.channelGroup.remove(channelId);
                CacheLoader.channelMapSC.remove(deviceId);
                CacheLoader.channelMapCS.remove(channelId);
                CacheLoader.deviceIdAndIp.remove(deviceId);
                // 添加新连接
                CacheLoader.channelGroup.add(channel);
                CacheLoader.channelMapSC.put(deviceId, channel.id());
                CacheLoader.channelMapCS.put(channel.id(), deviceId);
                CacheLoader.deviceIdAndIp.put(deviceId, deviceIp);
            }
        }

    }

    // 根据channel将连接信息从缓存删除
    public static void deleteFromCache(Channel channel) {
        String deviceId = CacheLoader.channelMapCS.get(channel.id());
        CacheLoader.channelGroup.remove(channel.id());
        CacheLoader.channelMapSC.remove(deviceId);
        CacheLoader.channelMapCS.remove(channel.id());
        CacheLoader.deviceIdAndIp.remove(deviceId);
    }

    // 根据deviceId将连接信息从缓存删除
    public static void deleteFromCache(String deviceId) {
        ChannelId channelId = CacheLoader.channelMapSC.get(deviceId);
        CacheLoader.channelGroup.remove(channelId);
        CacheLoader.channelMapSC.remove(deviceId);
        CacheLoader.channelMapCS.remove(channelId);
        CacheLoader.deviceIdAndIp.remove(deviceId);
    }


    // 将连接添加到缓存
    public static void addChannel(Channel channel, String equipmentId) {
        // 检查是否存在
        // 若equipmentId已存在，删除旧连接，添加新连接
        if (CacheLoader.channelMapSC.containsKey(equipmentId)) {
            ChannelId channelId = CacheLoader.channelMapSC.get(equipmentId);
            // 删除旧连接
            CacheLoader.channelGroup.remove(channelId);
            CacheLoader.channelMapCS.remove(channelId);
            CacheLoader.channelMapSC.remove(equipmentId);

            // 添加新连接
            CacheLoader.channelGroup.add(channel);
            CacheLoader.channelMapCS.put(channel.id(), equipmentId);
            CacheLoader.channelMapSC.put(equipmentId, channel.id());
            // System.out.println(channel.remoteAddress());
            // System.out.println("已存在设备，更新完毕！");
        } else {
            CacheLoader.channelMapSC.put(equipmentId, channel.id());
            CacheLoader.channelMapCS.put(channel.id(), equipmentId);
            CacheLoader.channelGroup.add(channel);
            // System.out.println("已保存TCP网络连接！");
        }
    }

    // 从缓存中移除连接
    public static void removeChannel(Channel channel) throws IOException {
        if (CacheLoader.channelMapCS.get(channel.id()) == null) {
            // System.out.println("未注册的设备或已离线！");
        } else {
            String equipmentId = CacheLoader.channelMapCS.get(channel.id());

//            // 从数据库在线设备表中删除
//            String url = "http://127.0.0.1:10001/api/deleteOnlineDevice?deviceNumber="
//                    + equipmentId;
//            HttpClient.sendGetRequest(url);
//            System.out.println("该设备已移出在线设备列表！");

            // 从连接组中删除连接
            CacheLoader.channelGroup.remove(channel.id());
            CacheLoader.channelMapSC.remove(CacheLoader.channelMapCS.get(channel.id()));
            CacheLoader.channelMapCS.remove(channel.id());

            // System.out.println("该设备已下线： " + equipmentId);
        }
    }

    // 标记是否首次同步远程数据库
    public static boolean isFirstSync = true;

}
