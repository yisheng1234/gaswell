package com.gaswell.utils;

import io.netty.channel.ChannelId;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lei Wang
 * @Date: 2022/01/15/ 14:08
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 记录实时读取数据指令发送记录
public class QueryDataList {

    // 保存设备ID(即SIM卡号/手机号/硬件ID)和对应的网络连接
    // 发送记录后保存到queryDataList
    // 接收到响应后从queryDataList中删除
    // public static Map<String, ChannelId> queryDataList = new ConcurrentHashMap<>();

    // 记录实时读取数据指令的设备ID和起止地址
    // 接收到数据后，从queryPositionList中删除
    // eg:（602042504521:VW4000）
    public static Map<String, String> queryPositionList = new ConcurrentHashMap<>();

    // 保存当前发送的指令查询的是哪项数据，值为"1"、"2"、"3"
    public static String current = null;
    // 保存全部指令查询出的数据
    public static Map<String,String> map = new HashMap<>();


    // 保存指令查询内容（实时数据）
    // 如查询设备001的28004号地址，则存{"001","28004"}
    // 收到响应后，将返回值存到queryValue中，同时从queryLog中删除
    // 如果再查询设备001的28005号地址，需要查看queryLog中是否含有键为001的值，如果有，则等待，如果没有，则发送指令
    // 多个设备可以同时发送指令（如设备001发送A指令后，继续向设备002发送发送A指令），单个设备的指令只能逐一发送（如发送指令A，收到响应后，再发送指令B）
    public static Map<String,String> queryLog = new HashMap<>();
    // 保存指令查询返回的值
    // 如查询设备001的28004号地址，返回值为1，则存{"001",{"28004",1}}
    public static Map<String,Map<String,Integer>> queryValue = new HashMap<>();

    // 保存指令查询内容（设备参数）
    public static Map<String,String> paramQueryLog = new HashMap<>();
    public static Map<String,Map<String,Integer>> paramQueryValue = new HashMap<>();

}
