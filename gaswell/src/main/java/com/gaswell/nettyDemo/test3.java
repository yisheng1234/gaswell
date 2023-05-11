package com.gaswell.nettyDemo;

/**
 * @author Lei Wang
 * @Date: 2022/01/14/ 21:51
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */

// （2）实时读取
public class test3 {

    public static void main(String[] args) {

        // 数据的起始地址（0-12）
        int start = 0;
        byte[] b_start = Utils.intToByteArray(start);
        System.out.println(b_start.length);
        Utils.printHexString(b_start);

        // 读取数据的个数（0-14）
        int num = 2;
        byte[] b_num = Utils.intToByteArray(num);
        System.out.println(b_num.length);
        Utils.printHexString(b_num);

        String hex = Integer.toHexString(num);
        System.out.println(hex);

        byte[] bytes = new byte[8];

        // PLC的ModBus地址
        bytes[0] = 0x02;

        // 功能码，含义为读PLC寄存器
        bytes[1] = 0x03;

        // 数据的起始地址
        bytes[2] = b_start[2];
        bytes[3] = b_start[3];

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
        System.out.println(crc);// 38c4

        // 字符串转十六进制
        byte[] b_crc = Utils.str2Bcd(crc);

        bytes[6] = b_crc[1]; // bytes[6] = (byte) 0xC4;
        bytes[7] = b_crc[0]; // bytes[7] = (byte) 0x38;
        Utils.printHexString(bytes);

        // 检验
        String s = "020300000002C438";
        byte[] bytes1 = Utils.str2Bcd(s);
        Utils.printHexString(bytes1);

    }
}
