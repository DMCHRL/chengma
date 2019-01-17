package com.chengma.devplatform.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具
 *
 * @author huangqr@midea.com.cn:
 * @version 创建时间：2016年7月16日
 * 类说明
 */
public class StringUtil {

    private static Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");
    private static Pattern UNDERLINE_PATTERN = Pattern.compile("_(\\w)");
    private static Pattern CHINESE_CHAR_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 内容全部替换：不区分大小写
     *
     * @param input       需要替换的内容
     * @param regex       替换规则
     * @param replacement 替换内容
     * @return 返回替换后的结果
     */
    public static String replaceAll(String input, String regex, String replacement) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        return m.replaceAll(replacement);
    }

    /**
     * 字符串去空格
     *
     * @param str 字符串
     * @return 返回去空后的结果
     */
    public static String getStr(String str) {
        if (null == str || "".equals(str) || "\"\"".equals(str) || "''".equals(str)) {
            return null;
        }
        return str.trim();
    }

    /**
     * 判断字符串类型
     *
     * @param str 字符串
     * @return 1-字符串 2-纯数字 0-空
     */
    public static String getType(String str) {
        if (null != str && !"".equals(str)) {
            str = str.trim();
            boolean flag = false;
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    flag = true;
                }
            }
            if (flag) {
                return "1";
            }
            return "2";
        }
        return "0";
    }

    /**
     * 驼峰命名法转下划线命名法
     *
     * @param str 字符串
     * @return 返回转换后的结果
     */
    public static String humpToLine(String str) {
        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean isMatched = false;
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            isMatched = true;
        }
        if (!isMatched) {
            return str;
        } else {
            matcher.appendTail(sb);
        }
        return sb.toString();
    }

    /**
     * 下划线命名法转驼峰命名法
     *
     * @param str 字符串
     * @return 转换后的结果
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = UNDERLINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 附件上传产生的随机文件名
     *
     * @param sLen 文件名长度
     * @return 随机文件名
     */
    public static String randomId(int sLen) {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
                .getInstance().getTime());
        String base = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer temp = new StringBuffer();
        temp.append(date);
        for (int i = 0; i < sLen; i++) {
            int p = (int) (Math.random() * 37);
            if (p > 35) {
                p = 35;
            }
            temp.append(base.substring(p, p + 1));
        }
        return temp.toString();
    }

    /**
     * 文件扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExpandName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        int len = fileName.length();
        return fileName.substring(index, len);
    }

    /**
     * 将对象转换为String
     *
     * @param obj javaBean
     * @return 转换后的结果
     */
    public static String toString(Object obj) {
        String rtStr = "";
        if (obj == null) {
            return rtStr;
        } else {
            rtStr = obj.toString();
        }
        return rtStr.trim();
    }

    /**
     * 判断是否是中文
     *
     * @param str 字符串
     * @return 判断结果
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Matcher m = CHINESE_CHAR_PATTERN.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 将“,”隔开的字符串转成sql中in表达式可用的格式
     *
     * @param vals 字符串 例如 abc,def
     * @return 'abc','def'
     */
    public static String toSqlInVal(String vals) {
        StringBuffer result = new StringBuffer();
        result.append("'").append(vals.replaceAll("," , "','")).append("'");
        return result.toString();
    }

    /**
     * 将属性名转成set方法格式
     *
     * @param field 属性名 例如name
     * @return setName
     */
    public static String fieldToSetMethod(String field) {
        String first = field.substring(0, 1).toUpperCase();
        return "set" + first + field.substring(1);
    }

    /**
     * 将属性名转成get方法格式
     *
     * @param field 属性名 例如name
     * @return getName
     */
    public static String fieldToGetMethod(String field) {
        String first = field.substring(0, 1).toUpperCase();
        return "get" + first + field.substring(1);
    }

    /**
     * 在字符串前面补0
     * 如果指定长度小于等于字符串，则直接输出字符串
     *
     * @param str 字符串
     * @param bit 长度
     * @return 处理后的结果
     */
    public static String fitZero(String str, int bit) {
        int strLen = str.length();
        int gapLen = bit - strLen;
        if (gapLen <= 0) {
            return str;
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < gapLen; i++) {
            result.append("0");
        }
        result.append(str);
        return result.toString();
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^(?:0|\\-?(?:0\\.\\d*[1-9]|[1-9]\\d*(?:\\.\\d*[1-9])?))$");
        if (null == str) {
            return false;
        }
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

}
