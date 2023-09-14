package com.gaswell.nettyDemo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lei Wang
 * @Date: 2022/01/14/ 23:38
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */

// （3）实时控制
public class test5 {
    public static void main(String[] args) {

        // 开关量类型
        Map<String, Integer> map = new HashMap<>();
        map.put("VW4022", 10);
        map.put("VW4024", 11);
        map.put("VW4026", 12);

        byte[] bytes = new byte[8];

        // PLC的ModBus地址
        bytes[0] = 0x02;

        // 功能码，含义为写PLC寄存器
        bytes[1] = 0x06;

        // 数据的起始地址（10-12）
        int start = 10;
        byte[] b_start = Utils.intToByteArray2(start);
        System.out.println(b_start.length);
        Utils.printHexString(b_start);

        bytes[2] = b_start[0];
        bytes[3] = b_start[1];

        // 写入的数据（0、1）
        int code = 1;
        byte[] b_code = Utils.intToByteArray2(code);
        System.out.println(b_code.length);
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
        bytes[6] = b_crc[1]; // bytes[6] = (byte) 0x68;
        bytes[7] = b_crc[0]; // bytes[7] = (byte) 0x3B;
        Utils.printHexString(bytes);

        // 检验
        String s = "0206000A0001683B";
        byte[] bytes1 = Utils.str2Bcd(s);
        Utils.printHexString(bytes1);

    }
}
