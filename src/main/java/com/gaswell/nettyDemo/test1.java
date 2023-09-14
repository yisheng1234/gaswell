package com.gaswell.nettyDemo;

import java.math.BigInteger;

/**
 * @author Lei Wang
 * @Date: 2022/01/14/ 20:33
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class test1 {
    public static void main(String[] args) {

        String s = "00100015001403E807D00BB8";

        byte[] bytes = Utils.str2Bcd(s);
        System.out.println(bytes.length);
        Utils.printHexString(bytes);

        int length = s.length();

        for (int i = 0; i < length / 4; i++) {
            String s1 = s.substring(4 * i, 4 * i + 4);
            System.out.println(s1);
            BigInteger bigInteger = new BigInteger(s1, 16);
            System.out.println(bigInteger.intValue());
        }


        // int转十六进制字符串
        System.out.println(Integer.toHexString(10));
        // int转二进制字符串
        System.out.println(Integer.toBinaryString(1000));
    }
}
