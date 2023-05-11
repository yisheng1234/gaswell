package com.gaswell.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gaswell.mapper.EquitMentMapper;
import com.gaswell.mapper.ModbusSiteMapper;
import com.gaswell.nettyDemo.Utils;
import com.gaswell.pojo.ModbusSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lei Wang
 * @Date: 2022/01/15/ 13:12
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */

// 指令构造
    @Component
public class CommandBuilder {
    @Autowired
    private   ModbusSiteMapper modbusSiteMapper;
    @Autowired
    private EquitMentMapper equitMentMapper;
    public  byte[] rtuCommand(String param,String data ){
        LambdaQueryWrapper<ModbusSite> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ModbusSite::getParamName,param);
        ModbusSite modbusSite = modbusSiteMapper.selectOne(lqw);
        String site=modbusSite.getRtu();
//        LambdaQueryWrapper<EquipMent> lqw1=new LambdaQueryWrapper<>();
//        lqw1.eq(EquipMent::getJh,jh);
//        EquipMent equipMent = equitMentMapper.selectOne(lqw1);
//        String equipSite=equipMent.getModbussite();
        Integer equipSite=1;
        byte[] bytes=new byte[8];
//        主机地址
        bytes[0]=0x01;
//        功能码  写入单个寄存器
        bytes[1]=0x06;
//        地址
        Integer site_I=Integer.valueOf(site);
        byte[] b_site = Utils.intToByteArray2(site_I);
        bytes[2]=b_site[0];
        bytes[3]=b_site[1];
//        数据
        Integer data_I=Integer.valueOf(data);
        byte[] b_data = Utils.intToByteArray2(data_I);
        bytes[4]=b_data[0];
        bytes[5]=b_data[1];
        byte[] crc_b=new byte[6];
        for(int i=0 ;i<6;i++){
            crc_b[i]=bytes[i];
        }
        String crc=Utils.getCRC(crc_b);
        byte[] b_crc=Utils.str2Bcd(crc);
        bytes[6]=b_crc[1];
        bytes[7]=b_crc[0];
        Utils.printHexString(bytes);
        return bytes;
    }










    // 构造实时读取数据指令
    // plcPosition为PLC存储位置(VW4000-VW4026)
    // dateNum为读取数据的个数(0-14)
    public static byte[] queryDataCommand(String plcPosition, int dataNum) {

        // {PLC存储位置:ModBus站内地址}
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("VW4000", 0);
        map1.put("VW4002", 1);
        map1.put("VW4004", 2);
        map1.put("VW4006", 3);
        map1.put("VW4008", 4);
        map1.put("VW4010", 5);
        map1.put("VW4012", 6);
        map1.put("VW4014", 7);
        map1.put("VW4016", 8);
        map1.put("VW4018", 9);
        map1.put("VW4020", 10);
        map1.put("VW4022", 11);
        map1.put("VW4024", 12);

        byte[] bytes = new byte[8];

        // PLC的ModBus地址
        bytes[0]=0x02;
        // 功能码，含义为读PLC寄存器
        bytes[1] = 0x03;

        // 数据的起始地址（0-12）
        int start = map1.get(plcPosition);
        byte[] b_start = Utils.intToByteArray(start);
        Utils.printHexString(b_start);
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];


        // 读取数据的个数（0-14）
        int num = dataNum;
        byte[] b_num = Utils.intToByteArray(num);

        String hex = Integer.toHexString(num);
        System.out.println("读取数据个数");
        Utils.printHexString(b_num);

        // 读取数据的个数
        bytes[4] = b_num[2];
        bytes[5] = b_num[3];

        // 复制校验数据
        byte[] b_jy = new byte[6];
        for (int i = 0; i < 6; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);
//        System.out.println(crc);// 38c4

        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);

        bytes[6] = b_crc[1]; // b_crc[1] = (byte) 0xC4;
        bytes[7] = b_crc[0]; // b_crc[0] = (byte) 0x38;
        Utils.printHexString(bytes);

        return bytes;
    }

    // 构造实时控制数据指令
    // plcPosition为PLC存储位置(VW4022、VW4024、VW4026)
    // status为开关状态（ON、OFF）
    public static byte[] controlValveCommand(String plcPosition, String status) {
        System.out.println("-----------------------");
        // {PLC存储位置:ModBus站内地址}
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("VW4020", 10);
        map1.put("VW4022", 11);
        map1.put("VW4024", 12);

        // {开关状态:状态码}
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("ON", 1);
        map2.put("OFF", 0);


        byte[] bytes = new byte[8];

        // PLC的ModBus地址
        bytes[0] = 0x02;

        // 功能码，含义为写PLC寄存器
        bytes[1] = 0x06;

        // 数据的起始地址（10-12）
        //  position=10 ---> 00 0A
        int start = map1.get(plcPosition);
        byte[] b_start = Utils.intToByteArray2(start);
        Utils.printHexString(b_start);
        bytes[2] = b_start[0];
        bytes[3] = b_start[1];

        // 写入的数据（0、1）
        int code = map2.get(status);
        byte[] b_code = Utils.intToByteArray2(code);
        //  status=0  -->  00 01
        Utils.printHexString(b_code);
        bytes[4] = b_code[0];
        bytes[5] = b_code[1];

        // 复制校验数据
        byte[] b_jy = new byte[6];
        for (int i = 0; i < 6; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);
        System.out.println(crc);//3b68

        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);
        bytes[6] = b_crc[1]; // b_crc[1] = (byte) 0x68;
        bytes[7] = b_crc[0]; // b_crc[0] = (byte) 0x3B;
        Utils.printHexString(bytes);
        return bytes;
    }

    // *************************************以上为实验室设备指令*************************************

    // 构造实时读取全部数据指令
    public static byte[] queryAllDataCommand(String deviceId) {

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("泵出口压力", 27984);
        map1.put("储液罐液位", 27986);
        map1.put("储液罐体积", 27988);
        map1.put("可燃气体浓度", 27990);
        map1.put("太阳能电池板电压", 27992);
        map1.put("蓄电池电压", 27994);
        map1.put("流量", 27996);

        byte[] bytes = new byte[8];

        // 设备地址01
        bytes[0]=0x01;
        // 读取功能码
        bytes[1] = 0x04;

        // 读取地址
        Integer start = map1.get("泵出口压力");
        byte[] b_start = Utils.intToByteArray(start);
        Utils.printHexString(b_start);
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];


        // 数据长度
        int num = 7;
        byte[] b_num = Utils.intToByteArray(num);

        String hex = Integer.toHexString(num);
        System.out.println("读取数据个数");
        Utils.printHexString(b_num);

        // 读取数据的个数
        bytes[4] = b_num[2];
        bytes[5] = b_num[3];

        // 校验码
        byte[] b_jy = new byte[6];
        for (int i = 0; i < 6; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);
//        System.out.println(crc);// 38c4

        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);

        bytes[6] = b_crc[1]; // b_crc[1] = (byte) 0xC4;
        bytes[7] = b_crc[0]; // b_crc[0] = (byte) 0x38;
        Utils.printHexString(bytes);

        // return buildTCPMsg(bytes,deviceId);
        return buildUDPMsg(bytes, deviceId);
    }

    // 构造实时读取任意条数据指令
    // itemName：查询的起始地址（如：泵出口压力）
    // dataNum：查询的数据个数
    public static byte[] querySingleDataCommand(String itemName, Integer dataNum, String deviceId) {

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("泵出口压力", 27984);
        map1.put("储液罐液位", 27986);
        map1.put("储液罐体积", 27988);
        map1.put("可燃气体浓度", 27990);
        map1.put("太阳能电池板电压", 27992);
        map1.put("蓄电池电压", 27994);
        map1.put("流量", 27996);

        byte[] bytes = new byte[8];

        // 设备地址01
        bytes[0] = 0x01;
        // 读取功能码
        bytes[1] = 0x04;

        // 读取地址
        Integer start = map1.get(itemName);
        byte[] b_start = Utils.intToByteArray(start);
        // Utils.printHexString(b_start);
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];


        // 数据长度
        int num = dataNum;
        byte[] b_num = Utils.intToByteArray(num);

        String hex = Integer.toHexString(num);
        //System.out.println("读取数据个数");
        //Utils.printHexString(b_num);

        // 读取数据的个数
        bytes[4] = b_num[2];
        bytes[5] = b_num[3];

        // 校验码
        byte[] b_jy = new byte[6];
        for (int i = 0; i < 6; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);
//        System.out.println(crc);// 38c4

        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);

        bytes[6] = b_crc[1]; // b_crc[1] = (byte) 0xC4;
        bytes[7] = b_crc[0]; // b_crc[0] = (byte) 0x38;
        // Utils.printHexString(bytes);

        // return buildTCPMsg(bytes,deviceId);
        return buildUDPMsg(bytes,deviceId);
    }

    // 构造修改开阀时间指令
    // address：寄存器地址
    // content：写入内容
    public static byte[] changeOpenValveTimeCommand(Integer address, Integer content, String deviceId) {

        byte[] bytes = new byte[13];

        // 设备地址01
        bytes[0] = 0x01;
        // 多个字写入功能码
        bytes[1] = 0x04;

        // 写入地址
        byte[] b_start = Utils.intToByteArray(address);
        Utils.printHexString(b_start);
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];

        // 写入寄存器的个数
        bytes[4] = 0x00;
        bytes[5] = 0x02;

        // 写入的字节数
        bytes[6] = 0x04;

        // 写入内容
        byte[] b_content = Utils.intToByteArray(content);

        System.out.println("写入内容");
        Utils.printHexString(b_content);

        // 读取数据的个数
        bytes[7] = b_content[0];
        bytes[8] = b_content[1];
        bytes[9] = b_content[2];
        bytes[10] = b_content[3];

        // 校验码
        byte[] b_jy = new byte[11];
        for (int i = 0; i < 11; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);
//        System.out.println(crc);// 38c4

        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);

        bytes[11] = b_crc[1]; // b_crc[1] = (byte) 0xC4;
        bytes[12] = b_crc[0]; // b_crc[0] = (byte) 0x38;
        Utils.printHexString(bytes);

        // return buildTCPMsg(bytes,deviceId);
        return buildUDPMsg(bytes,deviceId);
    }

    // 构造心跳响应报文
    // heartBeat：心跳报文
    public static byte[] heartBeatResponseCommand(byte[] heartBeat) {
        byte[] bytes = new byte[16];
        bytes[0] = heartBeat[0];
        bytes[1] = (byte) 0x81;
        bytes[2] = heartBeat[2];
        bytes[3] = heartBeat[3];
        for (int i = 4; i <= 14; i++) {
            bytes[i] = heartBeat[i];
        }
        bytes[15] = heartBeat[heartBeat.length-1];
        return bytes;
    }

    // 构造下线响应报文
    // offline：下线响应报文
    public static byte[] offlineResponseCommand(byte[] offline) {
        byte[] bytes = new byte[16];
        bytes[0] = offline[0];
        bytes[1] = (byte) 0x02;
        bytes[2] = offline[2];
        bytes[3] = offline[3];
        for (int i = 4; i <= 14; i++) {
            bytes[i] = offline[i];
        }
        bytes[15] = offline[offline.length-1];
        return bytes;
    }

    public static byte[] loginResponseCommand(byte[] login) {
        byte[] bytes = new byte[16];
        bytes[0] = login[0];
        bytes[1] = (byte) 0x83;
        bytes[2] = 0x00;
        bytes[3] = 0x10;
        for (int i = 4; i <= 14; i++) {
            bytes[i] = login[i];
        }
        bytes[15] = login[login.length-1];
        return bytes;
    }

    // 模拟发送指令
    public static byte[] testCommand(Integer integer) {
        byte[] bytes = new byte[7];
        bytes[0] = (byte) 0xAA;
        bytes[1] = (byte) 0x01;
        bytes[2] = (byte) 0x02;
        bytes[3] = (byte) 0x03;
        bytes[4] = (byte) 0x04;
        byte[] bytes1 = ByteUtils.intToByteArray2(integer);
        bytes[5] = bytes1[0];
        bytes[6] = bytes1[1];
        return bytes;
    }

    // 模拟发送指令
    public static byte[] testCommand2(Integer integer) {
        byte[] bytes = new byte[7];
        bytes[0] = (byte) 0xBB;
        bytes[1] = (byte) 0x01;
        bytes[2] = (byte) 0x02;
        bytes[3] = (byte) 0x03;
        bytes[4] = (byte) 0x04;
        byte[] bytes1 = ByteUtils.intToByteArray2(integer);
        bytes[5] = bytes1[0];
        bytes[6] = bytes1[1];
        return bytes;
    }

    // 一次性读取年/月/日/时/分/秒
    public static byte[] readDateAll(String deviceId){
        byte[] bytes = new byte[8];

        // 设备地址01
        bytes[0]=0x01;
        // 读取功能码
        bytes[1] = 0x04;

        // 读取地址
        Integer start = 28004;
        byte[] b_start = Utils.intToByteArray(start);
        Utils.printHexString(b_start);
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];

        // 数据长度
        int num = 6;
        byte[] b_num = Utils.intToByteArray(num);

        // 读取数据的个数
        bytes[4] = b_num[2];
        bytes[5] = b_num[3];

        // 校验码
        byte[] b_jy = new byte[6];
        for (int i = 0; i < 6; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);
        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);
        bytes[6] = b_crc[1];
        bytes[7] = b_crc[0];
        Utils.printHexString(bytes);
        // return buildTCPMsg(bytes,deviceId);
        return buildUDPMsg(bytes,deviceId);
    }

    // 逐一读取年/月/日/时/分/秒
    public static byte[] readDateSingle(String name, String deviceId) {

        byte[] bytes = new byte[8];
        // 设备地址01
        bytes[0]=0x01;
        // 读取功能码
        bytes[1] = 0x04;
        // 读取地址
        Integer start = 0;
        if(name.equals("year")){
            start = 28004;
            // QueryDataList.date_current.put(deviceId,"year");
        }
        else if(name.equals("month")){
            start = 28005;
            // QueryDataList.date_current.put(deviceId,"month");
        }
        else if(name.equals("day")){
            start = 28006;
            // QueryDataList.date_current.put(deviceId,"day");
        }
        else if(name.equals("hour")){
            start = 28007;
            // QueryDataList.date_current.put(deviceId,"hour");
        }
        else if(name.equals("minute")){
            start = 28008;
            // QueryDataList.date_current.put(deviceId,"minute");
        }
        else if(name.equals("second")){
            start = 28009;
            // QueryDataList.date_current.put(deviceId,"second");
        }

        byte[] b_start = Utils.intToByteArray(start);
        Utils.printHexString(b_start);
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];

        // 数据长度
        int num = 1;
        byte[] b_num = Utils.intToByteArray(num);

        // 读取数据的个数
        bytes[4] = b_num[2];
        bytes[5] = b_num[3];

        // 校验码
        byte[] b_jy = new byte[6];
        for (int i = 0; i < 6; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);
        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);

        bytes[6] = b_crc[1];
        bytes[7] = b_crc[0];
        Utils.printHexString(bytes);
        // return buildTCPMsg(bytes,deviceId);
        return buildUDPMsg(bytes,deviceId);
    }

    // 构造TCP服务器下发数据报文
    // bytes为数据体内容
    public static byte[] buildTCPMsg(byte[] bytes,String deviceId){
        int length = bytes.length;
        byte[] full = new byte[16+length];

        full[0] = (byte) 0x7B;
        full[1] = (byte) 0x89;
        full[2] = ByteUtils.intToByteArray2(length+16)[0];
        full[3] = ByteUtils.intToByteArray2(length+16)[1];
        byte[] device = deviceId.getBytes();
        int device_length = device.length;
        if(device_length>=11){
            for (int i = 0; i < 11; i++) {
                full[4+i] = device[i];
            }
        }
        else {
            for (int i = 0; i < device_length; i++) {
                full[4+i] = device[i];
            }
            for (int j = 0; j < 11 - device_length; j++) {
                full[4+device_length+j] = 0x00;
            }
        }

        for (int i = 0; i < length; i++) {
            full[15+i] = bytes[i];
        }

        full[15+length] = 0x7B;
        return full;
    }

    // 构造UDP服务器下发数据报文
    // bytes为数据体内容
    public static byte[] buildUDPMsg(byte[] bytes, String deviceId){
        int length = bytes.length;
        byte[] full = new byte[16+length];

        full[0] = (byte) 0x7B;
        full[1] = (byte) 0x89;
        full[2] = (byte) 0x00;
        full[3] = (byte) 0x10;
        byte[] device = deviceId.getBytes();
        int device_length = device.length;
        if(device_length>=11){
            for (int i = 0; i < 11; i++) {
                full[4+i] = device[i];
            }
        }
        else {
            for (int i = 0; i < device_length; i++) {
                full[4+i] = device[i];
            }
            for (int j = 0; j < 11 - device_length; j++) {
                full[4+device_length+j] = 0x00;
            }
        }
        full[15] = 0x7B;

        for (int i = 0; i < length; i++) {
            full[16+i] = bytes[i];
        }
        return full;
    }

    // 从指定设备指定地址读取数据（设备参数）
    public static byte[] readSetting(String deviceId, Integer address){
        byte[] bytes = new byte[8];

        // 设备地址01
        bytes[0] = 0x01;
        // 读取功能码
        bytes[1] = 0x04;

        // 读取地址
        Integer start = address;
        byte[] b_start = Utils.intToByteArray(start);
        // Utils.printHexString(b_start);
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];

        // 数据长度
        int num = 1;
        byte[] b_num = Utils.intToByteArray(num);

        // 读取数据的个数
        bytes[4] = b_num[2];
        bytes[5] = b_num[3];

        // 校验码
        byte[] b_jy = new byte[6];
        for (int i = 0; i < 6; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);

        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);

        bytes[6] = b_crc[1]; // b_crc[1] = (byte) 0xC4;
        bytes[7] = b_crc[0]; // b_crc[0] = (byte) 0x38;

        return buildUDPMsg(bytes,deviceId);

    }

    // 向指定设备指定地址写入指定数据（设备参数）
    public static byte[] writeSetting(String deviceId, Integer address, Integer content){
        byte[] bytes = new byte[13];

        // 设备地址01
        bytes[0] = 0x01;
        // 多个字写入功能码
        bytes[1] = 0x04;

        // 写入地址
        byte[] b_start = Utils.intToByteArray(address);
        Utils.printHexString(b_start);
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];

        // 写入寄存器的个数
        bytes[4] = 0x00;
        bytes[5] = 0x02;

        // 写入的字节数
        bytes[6] = 0x04;

        // 写入内容
        byte[] b_content = Utils.intToByteArray(content);

        // 读取数据的个数
        bytes[7] = b_content[0];
        bytes[8] = b_content[1];
        bytes[9] = b_content[2];
        bytes[10] = b_content[3];

        // 校验码
        byte[] b_jy = new byte[11];
        for (int i = 0; i < 11; i++) {
            b_jy[i] = bytes[i];
        }

        // 计算CRC校验码
        String crc = Utils.getCRC(b_jy);

        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);

        bytes[11] = b_crc[1]; // b_crc[1] = (byte) 0xC4;
        bytes[12] = b_crc[0]; // b_crc[0] = (byte) 0x38;

        return buildUDPMsg(bytes,deviceId);
    }

}
