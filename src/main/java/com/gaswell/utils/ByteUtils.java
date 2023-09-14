package com.gaswell.utils;

import java.util.Arrays;

/**
 * @author Lei Wang
 * @Date: 2021/07/30/ 20:44
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class ByteUtils {

    // byte转二进制字符串
    public static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public static int byteToBits(byte[] b) {
        int re = 0;
        for (byte t : b
        ) {
            String byteString = byteToBit(t);
            char[] byteGroup = byteString.toCharArray();
            int u = 0;
            for (char p : byteGroup
            ) {
                u += Integer.valueOf(p);
            }
            re += u;
            re %= 256;
        }
        return re;
    }

    // byte[]转十六进制字符串
    public static void printHexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        System.out.println();
    }

    // 累加和校验
    public static byte addCrc(byte[] bytes) {
        byte result = 0x00;
        for (byte tem : bytes) {
            result += tem;
        }
        return result;
    }

    // 字符串转BCD码
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    // BCD码转字符串反转
    public static byte[] bytesReverse(byte[] arr) {
        // 遍历数组
        for (int i = 0; i < arr.length / 2; i++) {
            // 交换元素
            byte temp = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = arr[i];
            arr[i] = temp;
        }
        // 返回反转后的结果
        return arr;
    }

    // BCD码转字符串
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    public static String bcd2Str2(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString();
    }

    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    // 打印byte[]为十六进制字符串
    // 与printHexString、bcd2Str、bcd2Str2函数功能相似
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    // 将十六进制字符串转化为byte[]
    // 与str2Bcd函数功能相似
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    // CRC-16 MODBUS校验码计算
    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }

    /**
     * int到byte[] 由高位到低位
     *
     * @param i 需要转换为byte数组的整行值。
     * @return byte数组
     */
    // 将int转化为byte[]，数组长度为4
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    // 将int转化为byte[]，数组长度为2
    public static byte[] intToByteArray2(int i) {
        byte[] result = new byte[2];
        result[0] = (byte) ((i >> 8) & 0xFF);
        result[1] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * byte[]转int
     *
     * @param bytes 需要转换成int的数组
     * @return int值
     */
    // 将byte[]转化为int，数组长度为4
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (3 - i) * 8;
            value += (bytes[i] & 0xFF) << shift;
        }
        return value;
    }

    // 将byte[]转化为int，数组长度为2
    public static int byteArrayToInt2(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (1 - i) * 8;
            value += (bytes[i] & 0xFF) << shift;
        }
        return value;
    }

    // 判断CRC校验码是否正确
    public static boolean isCrcRight(byte[] bytes) {
        int len = bytes.length;
        byte[] b_jy = new byte[len - 2];
        for (int i = 0; i < len - 2; i++) {
            b_jy[i] = bytes[i];
        }
        String crc = getCRC(b_jy);
        byte[] b_crc = str2Bcd(crc);
        if (b_crc.length == 2 && b_crc[0] == bytes[len - 1] && b_crc[1] == bytes[len - 2]) {
            return true;
        }
        return false;
    }

    // 判断两个byte[]是否相等
    public static boolean isByteEquals(byte[] b1, byte[] b2) {
        return Arrays.equals(b1, b2);
    }

    // 判断byte[]是否以回车换行(ASCII为0D 0A)结束
    public static boolean isEndWithEnter(byte[] bytes) {
        int length = bytes.length;
        if (bytes[length - 2] == 0x0D && bytes[length - 1] == 0x0A) {
            return true;
        }
        return false;
    }

    // 判断byte是否为数字
    public static boolean isNumber(byte b) {
        if (b >= (byte) 0x30 && b <= (byte) 0x39) {
            return true;
        } else {
            return false;
        }
    }

    // 判断bytes是否为A+手机号码
    public static boolean isRegistration(byte[] bytes) {
        if (bytes.length != 12) {
            return false;
        } else {
            for (int i = 1; i < bytes.length; i++) {
                if (!isNumber(bytes[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    // 判断bytes是否为手机号码
    public static boolean isPhoneNumber(byte[] bytes) {
        if (bytes.length != 11) {
            return false;
        } else {
            for (int i = 0; i < bytes.length; i++) {
                if (!isNumber(bytes[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    // 判断bytes是否为BREAKDOWN
    public static boolean isBreakDown(byte[] bytes) {
        if (bytes.length != 9) {
            return false;
        } else {
            if (bytes[0] == 0x42 && bytes[1] == 0x52 && bytes[2] == 0x45 && bytes[3] == 0x41 && bytes[4] == 0x4B && bytes[5] == 0x44 && bytes[6] == 0x4F && bytes[7] == 0x57 && bytes[8] == 0x4E) {
                return true;
            } else {
                return false;
            }
        }
    }
}
