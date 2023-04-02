package com.cheng.common.utils.encrypt;

import com.google.common.collect.Maps;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * @author: cheng
 * @time: 2022/7/22 22:35
 * @desc: 非对称加密 RSA加密工具类
 */
public class RSAUtil {

    private final static Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 加密方式
     */
    private final static String RSA = "RSA";


    /**
     * 签名算法
     */
    private static final String RSA_SIGNATURE = "MD5withRSA";
    /**
     * 公钥算法
     */
    private static final String RSA_PUBLIC_KEY = "RSAPublicKey";

    /**
     * 私钥算法
     */

    private static final String RSA_PRIVATE_KEY = "RSAPrivateKey";

    private static final String transformation = "RSA/None/PKS1Padding";

    private static final String PROVIDER = "BC";


    /**
     * 注册BouncyCastle 提供的算法库
     */
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 初始化秘钥
     * 1. 生成公钥
     * 2. 生成私钥
     *
     * @return
     */
    public static Map<String, Object> init() {
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            //公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            //私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            //将秘钥封装到map
            map = Maps.newHashMap();
            map.put(RSA_PUBLIC_KEY, publicKey);
            map.put(RSA_PRIVATE_KEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }

        return map;
    }

    /**
     * 公钥加密
     *
     * @param data 数据
     * @param key  公钥
     * @return
     */
    public static String encryptByPublicKey(String data, String key) {
        String result = null;
        try {
            byte[] bytes = Base64Util.decrypt(key);
            //获取公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            //对数据加密
            Cipher cipher = Cipher.getInstance(transformation, PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encode = cipher.doFinal(data.getBytes());

            result = Base64Util.encrypt(encode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * 私钥解密
     * @param data 公钥加密后的数据
     * @param key 私钥
     * @return
     */
    public static String decryptByPrivateKey(String data,String key){
        String result = null;
        try {
            byte[] bytes = Base64Util.decrypt(key);
            //取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            //对数据解密
            Cipher cipher = Cipher.getInstance(transformation,PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            //先Base64解密
            byte[] decoded = Base64Util.decrypt(data);
            //私钥解密
            result = new String(cipher.doFinal(decoded));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return result ;
    }

    /**
     * 获取公钥
     * @param map
     * @return
     */
    public static String getPublicKey(Map<String,Object> map ){
        Key key = (Key) map.get(RSA_PUBLIC_KEY);
       return Base64Util.encrypt(key.getEncoded());
    }

    /**
     * 获取私钥
     */

    public static String getPrivateKey(Map<String,Object> map){
        Key key = (Key) map.get(RSA_PRIVATE_KEY);
        return Base64Util.encrypt(key.getEncoded());
    }

    /**
     * 用私钥生成对数据生成数字签名
     */
    public static String sign(byte[] data,String privateKey){
        String str = "";
        try {
            byte[] bytes = Base64Util.decrypt(privateKey);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(RSA);
            PrivateKey key = factory.generatePrivate(pkcs8EncodedKeySpec);
            //用私钥生成数字签名
            Signature signature = Signature.getInstance(RSA_SIGNATURE);
            signature.initSign(key);
            signature.update(data);
            str = Base64Util.encrypt(signature.sign());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return str;
    }

    /**
     * 校验数字签名
     * @param data 加密后的数据
     * @param publicKey 公钥
     * @param sign 数字签名
     * @return true成功 false失败
     */
    public static boolean verify(byte[] data,String publicKey,String sign){
        boolean result = false;
        try {
            byte[] bytes = Base64Util.decrypt(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(RSA);
            PublicKey key = factory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(RSA_SIGNATURE);
            signature.initVerify(key);
            signature.update(data);
            result = signature.verify(Base64Util.decrypt(sign));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return result;

    }


}
