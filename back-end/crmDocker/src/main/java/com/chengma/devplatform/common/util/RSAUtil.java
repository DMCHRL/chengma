package com.chengma.devplatform.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密解密工具
 * Created by Administrator on 2017/11/6.
 */
public class RSAUtil {
    private static final String ENCRYPTION_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 生成密钥
     *
     * @return 密钥集
     * @throws Exception 异常
     */
    public static Map<String, Object> initKey() throws Exception {
        /* 初始化密钥生成器 */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyPairGenerator.initialize(1024);

        /* 生成密钥 */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("PublicKey", publicKey);
        keyMap.put("PrivateKey", privateKey);
        return keyMap;
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥集
     * @return 公钥
     * @throws Exception 异常
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("PublicKey");
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥集
     * @return 私钥
     * @throws Exception 异常
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("PrivateKey");
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 加密
     *
     * @param data      原文
     * @param keyString 密钥
     * @param isPublic  是否公钥
     * @return 密文
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] data, String keyString, boolean isPublic) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = RSAUtil.getKeyFactory(keyAndFactoryMap);
        Key key = RSAUtil.getKey(keyAndFactoryMap);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data      密文
     * @param keyString 密钥
     * @param isPublic  是否公钥
     * @return 原文
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] data, String keyString, boolean isPublic) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = RSAUtil.getKeyFactory(keyAndFactoryMap);
        Key key = RSAUtil.getKey(keyAndFactoryMap);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 生成钥匙
     */
    private static Map<String, Object> generateKeyAndFactory(String keyString, boolean isPublic) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(keyString);

        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        Key key = null;
        if (isPublic) {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            key = keyFactory.generatePublic(x509KeySpec);
        } else {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            key = keyFactory.generatePrivate(pkcs8KeySpec);
        }

        Map<String, Object> keyAndFactoryMap = new HashMap<String, Object>(2);
        keyAndFactoryMap.put("key", key);
        keyAndFactoryMap.put("keyFactory", keyFactory);

        return keyAndFactoryMap;
    }

    /**
     * 从指定对象中获取钥匙
     */
    private static Key getKey(Map<String, Object> map) {
        if (map.get("key") == null) {
            return null;
        }
        return (Key) map.get("key");
    }

    /**
     * 从指定对象中获取钥匙工厂
     */
    private static KeyFactory getKeyFactory(Map<String, Object> map) {
        if (map.get("keyFactory") == null) {
            return null;
        }
        return (KeyFactory) map.get("keyFactory");
    }

    /**
     * 对信息生成数字签名（用私钥）
     *
     * @param data      信息
     * @param keyString 密钥
     * @return 数字签名
     * @throws Exception 异常
     */
    public static String sign(byte[] data, String keyString) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, false);
        Key key = RSAUtil.getKey(keyAndFactoryMap);

        PrivateKey privateKey = (PrivateKey) key;

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名（用公钥）
     *
     * @param data      信息
     * @param keyString 密钥
     * @param sign      数字签名
     * @return 校验结果
     * @throws Exception 异常
     */
    public static boolean verify(byte[] data, String keyString, String sign)
            throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, true);
        Key key = RSAUtil.getKey(keyAndFactoryMap);

        PublicKey publicKey = (PublicKey) key;

        // 取公钥匙对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));
    }
}
