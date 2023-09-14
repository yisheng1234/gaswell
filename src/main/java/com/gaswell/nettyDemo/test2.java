package com.gaswell.nettyDemo;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2022/01/14/ 21:33
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */

// （1）间隔读取数据
public class test2 {
    public static void main(String[] args) throws UnsupportedEncodingException {

        // 模拟接收到的数据
        byte[] bytes = {0x32, 0x30, 0x32, 0x32, 0x30, 0x31, 0x31, 0x33, 0x31, 0x31, 0x35, 0x30, 0x32, 0x31, 0x2C, 0x20, 0x30, 0x3A, 0x32, 0x33, 0x39, 0x31, 0x32, 0x2C, 0x20, 0x31, 0x3A, 0x31, 0x31, 0x39, 0x2C, 0x20, 0x4D, 0x30, 0x3A, 0x30, 0x30, 0x31, 0x30, 0x30, 0x30, 0x31, 0x35, 0x30, 0x30, 0x31, 0x34, 0x30, 0x33, 0x45, 0x38, 0x30, 0x37, 0x44, 0x30, 0x30, 0x42, 0x42, 0x38, 0x0D, 0x0A};
        int length = bytes.length;
        System.out.println(length);

        // System.out.println(new String(bytes, "UTF-8"));
        String ascii = new String(bytes);
        System.out.println(ascii);

        String[] strings = ascii.split(", ");
        for (String s : strings) {
            System.out.println(s);
        }
        String m0 = strings[3].substring(3);
        int m0_len = m0.length();

        for (int i = 0; i < m0_len / 4; i++) {
            String s1 = m0.substring(4 * i, 4 * i + 4);
            System.out.println(s1);
            BigInteger bigInteger = new BigInteger(s1, 16);
            System.out.println(bigInteger.intValue());
        }
    }
}
