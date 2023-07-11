package com.cheng.common.utils.encrypt;


import java.util.Base64;

/**
 * @author: cheng
 * @time: 2022/7/24 17:19
 * @desc: Base64工具类
 */
public class Base64Util {
    /**
     * Base64解密
     * @param data 要解密的数据
     * @return 需要解密的字符串
     */
    public static byte[] decrypt(String data) {
        return Base64.getDecoder().decode(data);
    }


    /**
     * Base64 加密
     * @param data 要加密的字符数组
     * @return 加密后的字符串
     */
    public static String encrypt(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

}
