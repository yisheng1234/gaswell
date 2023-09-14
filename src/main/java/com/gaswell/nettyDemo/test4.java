package com.gaswell.nettyDemo;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @author Lei Wang
 * @Date: 2022/01/14/ 22:41
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class test4 {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 模拟接收到的数据
        byte[] bytes = {0x02, 0x03, 0x04, 0x00, 0x10, 0x00, 0x15, 0x09, 0x39};
        int length = bytes.length;
        System.out.println(length);

        // byte[]转为字符串
        String ascii = new String(bytes, "UTF-8");
        System.out.println(ascii); // 乱码，0x00-1x1F非可打印字符

        // 检查校验码是否正确
        System.out.println(Utils.isCrcRight(bytes));

        // byte转为int，获取字节数
        byte b_num = bytes[2];
        int i_num = Integer.valueOf(Byte.toString(b_num));
        System.out.println(i_num);

        // 获取的字节数与实际字节数相等
        System.out.println(i_num == length - 5);

        // 复制数据
        byte[] b_data = new byte[length - 5];
        for (int i = 0; i < i_num; i++) {
            b_data[i] = bytes[i + 3];
        }

//        // 乱码，0x00-1x1F非可打印字符
//        String s_data = new String(b_data);
//        System.out.println(s_data);
//        int len_data = s_data.length();
//
//        for (int i = 0; i < len_data / 4; i++) {
//            String s1 = s_data.substring(4 * i, 4 * i + 4);
//            System.out.println(s1);
//            BigInteger bigInteger = new BigInteger(s1, 16);
//            System.out.println(bigInteger.intValue());
//        }

        // 从b_data解析获取到的数据
        // byte数组转int
        // 数组长度为4
        byte[] b2i = new byte[4];
        b2i[0] = 0x00;
        b2i[1] = 0x00;
        b2i[2] = b_data[0];
        b2i[3] = b_data[1];
        int res = Utils.byteArrayToInt(b2i);
        System.out.println(res);

        // byte数组转int
        // 数组长度为2
        byte[] b2i_2 = new byte[2];
        b2i_2[0] = b_data[0];
        b2i_2[1] = b_data[1];
        int res_2 = Utils.byteArrayToInt2(b2i_2);
        System.out.println(res_2);

        // int转byte数组
        // 数组长度为4
        byte[] i2b = Utils.intToByteArray(16);
        Utils.printHexString(i2b);

        // int转byte数组
        // 数组长度为2
        byte[] i2b_2 = Utils.intToByteArray2(16);
        Utils.printHexString(i2b_2);
    }
}
