package com.gaswell.nettyDemo;

import com.gaswell.utils.ByteUtils;

/**
 * @author Lei Wang
 * @Date: 2022/05/20/ 16:34
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class test06 {

    public static void main(String[] args) {
        byte[] deviceId = new byte[]{0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x39,0x30,0x31};
        System.out.println(ByteUtils.bytesToHexString(deviceId));
        System.out.println(ByteUtils.bcd2Str(deviceId));
        System.out.println(ByteUtils.bcd2Str2(deviceId));
        System.out.println(new String(deviceId));// 0x30-0x39代表0-9，转为String即为数字

        System.out.println("----------------------------");
        byte ip1 = (byte) 0xFF;
        int ip = ip1 & 0xff;
        System.out.println(ip);


        System.out.println("----------------------------");
        byte[] port = new byte[]{0x77, 0x05};
        System.out.println(ByteUtils.bytesToHexString(port));
        // System.out.println(ByteUtils.byteArrayToInt(port)); //报错
        System.out.println(ByteUtils.byteArrayToInt2(port));

        System.out.println("----------------------------");
        int a = 1;
        ByteUtils.printHexString(ByteUtils.intToByteArray2(a));

        byte b = 0x01;
        System.out.println(b+1);

        System.out.println("----------------------------");
        String dev = "1234";
        ByteUtils.printHexString(dev.getBytes());


    }
}
