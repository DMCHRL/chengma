package com.chengma.devplatform.common.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 加密解密工具
 * Created by Administrator on 2017/11/1.
 */
public class EncryptUtils {

    private static final String HEX_NUM_STR = "0123456789abcdef";
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static final Integer SALT_LENGTH = 12;

    /**
     * MD5加密
     *
     * @param input 原文
     * @return 密文
     */
    public static String encryptMD5(String input) {
        return encryptMD5(input, null);
    }

    /**
     * md5加密
     *
     * @param origin      原文
     * @param charsetName 指定编码，例如：utf-8
     * @return 密文
     */
    public static String encryptMD5(String origin, String charsetName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                origin = byteArrayToHexString(md.digest(origin.getBytes()));
            } else {
                origin = byteArrayToHexString(md.digest(origin.getBytes(charsetName)));
            }
        } catch (Exception exception) {
            throw new RuntimeException("MD5加密错误！");
        }
        return origin;
    }

    /**
     * 校验MD5
     *
     * @param input    原文
     * @param dbSource 密文
     * @return boolean
     */
    public static boolean checkMD5(String input, String dbSource) {
        String inputMD5 = encryptMD5(input);
        return dbSource.equalsIgnoreCase(inputMD5);
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex 16进制字符串
     * @return 16进制字节数组
     */
    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUM_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUM_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * 将16进制字节数组转换成字符串
     *
     * @param b 16进制字节数组
     * @return 16进制字符串
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将单个16进制字节转换成字符串
     *
     * @param b 16进制字节
     * @return 16进制字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * 验证口令是否合法
     *
     * @param input    原文
     * @param dbSource 密文
     * @return boolean
     * @throws NoSuchAlgorithmException     没有这样的算法
     * @throws UnsupportedEncodingException 不支持的编码
     */
    public static boolean checkComplexMD5(String input, String dbSource)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //将16进制字符串格式口令转换成字节数组
        byte[] pwdInDb = hexStringToByte(dbSource);
        //声明盐变量
        byte[] salt = new byte[SALT_LENGTH];
        //将盐从数据库中保存的口令字节数组中提取出来
        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
        //创建消息摘要对象
        MessageDigest md = MessageDigest.getInstance("MD5");
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        md.update(input.getBytes("UTF-8"));
        //生成输入口令的消息摘要
        byte[] digest = md.digest();
        //声明一个保存数据库中口令消息摘要的变量
        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
        //取得数据库中口令的消息摘要
        System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
        //比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
        return Arrays.equals(digest, digestInDb);
    }


    /**
     * 获得加密后的16进制形式口令
     *
     * @param input 原文
     * @return 密文
     * @throws NoSuchAlgorithmException     没有这样的算法
     * @throws UnsupportedEncodingException 不支持的编码
     */
    public static String getComplexMD5(String input)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //声明加密后的口令数组变量
        byte[] pwd = null;
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量
        byte[] salt = new byte[SALT_LENGTH];
        //将随机数放入盐变量中
        random.nextBytes(salt);

        //声明消息摘要对象
        MessageDigest md = null;
        //创建消息摘要
        md = MessageDigest.getInstance("MD5");
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        md.update(input.getBytes("UTF-8"));
        //获得消息摘要的字节数组
        byte[] digest = md.digest();

        //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + SALT_LENGTH];
        //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
        //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteArrayToHexString(pwd);
    }

    /**
     * SHA-1加密
     *
     * @param decrypt 原文
     * @return 密文
     */
    public static String encodeSHA1(String decrypt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decrypt.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 校验SHA-1
     *
     * @param input    原文
     * @param dbSource 密文
     * @return boolean
     */
    public static boolean checkSHA1(String input, String dbSource) {
        String inputSHA1 = encodeSHA1(input);
        return dbSource.equalsIgnoreCase(inputSHA1);
    }

    /**
     * AES加密
     *
     * @param content  原文
     * @param password 加密密码
     * @return 加密字节数组
     */
    public static byte[] encryptAES(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param content  加密字节数组
     * @param password 解密密码
     * @return 原文字节数组
     */
    public static byte[] decryptAES(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
