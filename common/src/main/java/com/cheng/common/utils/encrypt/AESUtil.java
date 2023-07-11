package com.cheng.common.utils.encrypt;

import com.cheng.common.error.system.SystemErrorCodes;
import com.cheng.common.error.system.SystemException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author: cheng
 * @time: 2022/7/22 22:51
 * @desc: 对称加密 AES加密工具类，AES是可逆加密算法
 */
public class AESUtil {

    //加密算法
    private static final String AES = "AES";
    // 算法/模式/补码方式
    private static final String TRANSFORMATION = AES + "/CBC/PKCS5Padding";


    /**
     * 合并两个字节数组
     * @param a
     * @param b
     * @return
     */
    private static byte[]  join(byte[] a, byte[] b) {
        ByteBuffer buffer = ByteBuffer.allocate(a.length + b.length);
        buffer.put(a);
        buffer.put(b);
        return buffer.array();
    }

    /**
     * 使用指定key 加密content
     * @param content  要加密的内容
     * @param key  秘钥
     * @return  base64加密后的数据（前16位为加密数据，后16为随机初始化向量）
     * @throws Exception
     */
    public static String encrypt(String content, String key) throws Exception {
        SecureRandom random = SecureRandom.getInstanceStrong();
        // 随机生成16位的iv
        byte[] iv = random.generateSeed(16);
        System.out.println("iv length ===== " + iv.length + " data = " + Arrays.toString(iv));
        byte[] data = encrypt(content, key, iv);
        System.out.println("data length ===== " + data.length + " data = "+ Arrays.toString(data));
        return Base64Util.encrypt(join(data,iv));
    }

    /**
     * AES加密，指定16位秘钥及16位初始化向量
     * @param content  要加密的内容
     * @param key  16位秘钥
     * @param iv  16位初始化向量
     * @return  base64编码后的加密数据
     * @throws Exception
     */
    public static String encrypt(String content, String key, String iv) throws Exception {
        byte[] result = encrypt(content, key, iv.getBytes(StandardCharsets.UTF_8));
        return Base64Util.encrypt(result);
    }

    /**
     * AES加密
     *
     * @param content 加密文本
     * @param key     加密秘钥
     * @param iv      初始化向量
     * @return 加密后的内容
     */
    private static byte[] encrypt(String content, String key, byte[] iv) throws Exception {
        byte[] raw = key.getBytes(StandardCharsets.UTF_8);
        //将AES秘钥转为SecretKeySpec对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
        //根据加密算法获取加密器
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        //使用CBC模式，需要一个向量iv,可增加加密算法的强度
        IvParameterSpec ivParam = new IvParameterSpec(iv);
        // 初始化加密器，设置加密模式、秘钥和初始化向量
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParam);
        //加密数据
        return cipher.doFinal(content.getBytes());
    }

    /**
     * 解密
     *
     * @param data   解密文本
     * @param key     加密秘钥
     * @param iv      初始化向量
     * @return 解密后的内容
     */
    private static String decrypt(byte[] data, String key, byte[] iv) throws Exception {
        //秘钥
        byte[] raw = key.getBytes(StandardCharsets.UTF_8);
        // 秘钥转为SecretKeySpec对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
        // 获取解密器
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 初始化向量转为 IvParameterSpec对象
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        // 初始化解密器，设置解密模式、秘钥和初始化向量
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 执行解密
        byte[] origin = cipher.doFinal(data);
        // 解密后的字节数组转为字符串
        return new String(origin,StandardCharsets.UTF_8);
    }

    /**
     * 解密数据 加密数据中需要包含随机的初始化向量iv
     * @param data 加密后的数据
     * @param key 秘钥
     * @return
     */
     public static String decrypt(String data,String key) throws Exception{
        // 获取加密data
         byte[] encodeData = Base64Util.decrypt(data);
         if(encodeData.length < 16) {
             throw new SystemException(SystemErrorCodes.DECODE_ERROR);
         }
         // 获取加密数据和初始化向量
         int offset = encodeData.length - 16;
          ByteBuffer dataBuffer = ByteBuffer.allocate(offset);
          dataBuffer.put(encodeData,0,offset);
          ByteBuffer ivBuffer = ByteBuffer.allocate(16);
          ivBuffer.put(encodeData,offset,16);
         return decrypt(dataBuffer.array(),key,ivBuffer.array());
     }

     public static String decrypt(String data,String key,String iv) throws Exception{
         byte[] encodeData = Base64Util.decrypt(data);
         return decrypt(encodeData,key,iv.getBytes(StandardCharsets.UTF_8));
     }


}
