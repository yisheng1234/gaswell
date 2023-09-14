package com.gaswell.nettyDemo;

/**
 * @author Lei Wang
 * @Date: 2022/01/14/ 23:33
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class test0 {
    public static void main(String[] args) {
        // byte b = new Byte("65");
        // byte b = 0x41;
        byte b = (byte) 'A';
        System.out.println(b);
        System.out.println(Utils.byteToBit(b));

        byte[] bytes = {0x02, 0x06, 0x00, 0x0A, 0x00, 0x01};
        System.out.println(Utils.byteToBits(bytes));
        System.out.println(Utils.addCrc(bytes));

        System.out.println("1------------------------------");

        // 字符串转byte[]
        String ascii = "20220113115021, 0:23912, 1:119, M0:00100015001403E807D00BB8";
        byte[] bytes1 = ascii.getBytes();
        System.out.println(bytes1);
        System.out.println(new String(bytes1));
        Utils.printHexString(bytes1);

        System.out.println("2------------------------------");

        String s = "00100015001403E807D00BB8";
        // 字符串转BCD码
        byte[] bytes2 = Utils.str2Bcd(s);
        System.out.println(bytes2);
        Utils.printHexString(bytes2);

        // BCD码反转
        Utils.printHexString(Utils.bytesReverse(bytes2));

        // BCD码转字符串
        System.out.println(Utils.bcd2Str(bytes2));
        System.out.println(Utils.bytesToHexString(bytes2));

        String sss = ":;<=>?";
        // byte[] bytes3 = {0x3A,0x3B,0x3C,0x3D,0x3E,0x3F};
        byte[] bytes3 = sss.getBytes();
        System.out.println(Utils.bcd2Str(bytes3));
        Utils.printHexString(bytes3);
        System.out.println(new String(bytes3));

        System.out.println("3------------------------------");
        // "2022"的16进制字节数组
        String s2022 = "2022";
        byte[] b2022 = s2022.getBytes(); // byte[] b2022 = {0x32,0x30,0x32,0x32};
        Utils.printHexString(b2022);
        String s1 = new String(b2022);
        System.out.println(s1);

        System.out.println("4------------------------------");
        // byte b2 = (byte)'2';
        byte b2 = 0x32;
        // 打印byte的二进制字符串
        System.out.println(Utils.byteToBit(b2));

        // 打印byte的十进制字符串
        System.out.println(b2);
        System.out.println(Byte.toString(b2));
        System.out.println("" + b2);


        // 打印byte的ASCII格式
        char c2 = (char) b2;
        System.out.println(c2);
        String ss = String.valueOf(c2);
        System.out.println(ss);
        System.out.println(new String(new byte[]{b2}));
        System.out.println(Character.toString(c2));

        // int转byte[]
        int a = 1000;
        byte[] bytes4 = Utils.intToByteArray(a);
        // byte[]转int
        int i_num = Utils.byteArrayToInt(bytes4);
        System.out.println(i_num);

        System.out.println("5------------------------------");

        // byte[]转Hex String
        byte[] bytes5 = {0x00,0x01,0x10,0x11,0x1A, 0x3A};
        System.out.println(Utils.bcd2Str2(bytes5));
        System.out.println(Utils.bcd2Str(bytes5));
        System.out.println(Utils.bytesToHexString(bytes5));

        // Hex String转byte[]
        String s2 = "000110111A3A";
        byte[] bytes6 = Utils.hexStringToBytes(s2);
        Utils.printHexString(bytes6);
        byte[] bytes7 = Utils.str2Bcd(s2);
        Utils.printHexString(bytes7);
    }
}
