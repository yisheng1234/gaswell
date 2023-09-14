package com.gaswell.utils;

import com.gaswell.pojo.ControlValveLog;
import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lei Wang
 * @Date: 2022/01/15/ 14:09
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 记录实时控制数据指令发送记录
public class ControlValveList {

    // 保存设备ID(即SIM卡号/手机号/硬件ID)和对应的网络连接
    // 发送命令后保存到controlValveList
    // 接收到响应后从controlValveList中删除
    // public static Map<String, ChannelId> controlValveList = new ConcurrentHashMap<>();

    // 记录实时控制数据指令的设备ID和指令内容HexString
    // 接收到数据后，从queryPositionList中删除
    // eg:{602042504521:0206000A0001683B}
    public static Map<String, String> controlCommandList = new ConcurrentHashMap<>();

    // 记录实时控制数据指令的设备ID和控制指令记录
    // 接收到数据后，从queryPositionList中删除
    // eg:{602042504521:ControlValveLog controlValveLog}
    public static Map<String, ControlValveLog> controlCommandLogList = new ConcurrentHashMap<>();
}
