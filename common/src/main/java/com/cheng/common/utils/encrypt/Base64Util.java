package com.cheng.common.utils.encrypt;


import org.bouncycastle.util.encoders.Base64;

import java.util.Arrays;

/**
 * @author: cheng
 * @time: 2022/7/24 17:19
 * @desc: Base64工具类
 */
public class Base64Util {
    /**
     * Base64解密
     * @return 需要解密的字符串
     */
    public static byte[] decrypt(String key) {
        return Base64.decode(key);
    }


    /**
     * Base64 加密
     * @param key 要加密的字符数组
     * @return 加密后的字符串
     */
    public static String encrypt(byte[] key){
        return Arrays.toString(Base64.encode(key));
    }

}
