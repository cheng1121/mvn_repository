package com.cheng.common.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: cheng
 * @time: 2022/7/22 22:51
 * @desc: 对称加密 AES加密工具类
 */
public class AESUtil {

    //加密算法
    private static final String AES = "AES";
    // 算法/模式/补码方式
    private static final String TRANSFORMATION = AES+"/CBC/PKCS5Padding";

    /**
     * 加密
     * @param content 加密文本
     * @param key 加密秘钥，appSecret的前16位
     * @param iv 初始化向量,appSecret的后16位
     * @return 加密后的内容
     */
    public static String encrypt(String content,String key,String iv) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw,AES);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        //使用CBC模式，需要一个向量iv,可增加加密算法的强度
        IvParameterSpec ivParam = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParam);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return Base64Util.encrypt(encrypted); //base64加密
    }

    /**
     * 解密
     * @param content 加密文本
     * @param key 加密秘钥，appSecret的前16位
     * @param iv 初始化向量,appSecret的后16位
     * @return 解密后的内容
     */
    public static String decrypt(String content,String key,String iv) throws Exception{
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw,AES);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
        byte[] encrypted = Base64Util.decrypt(content);
        byte[] original = cipher.doFinal(encrypted); //获取原始数据
        return new String(original);
    }


}
