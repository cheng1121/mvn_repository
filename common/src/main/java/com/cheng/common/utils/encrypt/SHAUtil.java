package com.cheng.common.utils.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author: cheng
 * @date: 2023/6/23 14:56
 * @desc: SHA加密工具类
 */
public class SHAUtil {

    private static final String SHA_256_ALGORITHM = "SHA-256";

    /**
     * SHA-256加密
     *
     * @param data 数据
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        // 获取SHA-256算法实例
        MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM);
        //计算散列值
        byte[] digest = messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
        //将byte数组转为16进制字符串
        StringBuilder builder = new StringBuilder();
        for (byte b : digest) {
            builder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }
        return builder.toString();

    }

    /**
     * 加盐的SHA256加密
     *
     * @param data 数据
     * @param salt 岩
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String salt) throws Exception {
        String finalData = data + salt;
        return encrypt(finalData);

    }

    /**
     * 多次哈希的加密
     *
     * @param data  数据
     * @param count 哈希次数
     * @return
     */
    public static String encrypt(String data, int count) throws Exception {
        String encodeData = data;
        if (count > 0) {
            for (int i = 0; i < count; ++i) {
                encodeData = encrypt(encodeData);
            }
        } else {
            encodeData = encrypt(encodeData);
        }
        return encodeData;
    }

    /**
     * 加盐并多次哈希
     *
     * @param data
     * @param salt
     * @param count
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String salt, int count) throws Exception {
        String encodeData = data;
        if (count > 0) {
            for (int i = 0; i < count; ++i) {
                encodeData = encrypt(encodeData + salt);
            }
        } else {
            encodeData = encrypt(encodeData + salt);
        }

        return encodeData;
    }


}
