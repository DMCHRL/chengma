package com.suitong.devplatform.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @author huangqr@midea.com.cn: 
 * @version 创建时间：2016年7月16日 
 * 类说明 
 */
public class StringUtil {
	
	private static Pattern humpPattern = Pattern.compile("[A-Z]"); 
	
	/**
	 * 内容全部替换：不区分大小写
	 * @param input 需要替换的内容
	 * @param regex 替换规则
	 * @param replacement 替换内容  
	 * @return 返回替换后结果
	 */
	 public static String replaceAll(String input, String regex, String replacement) {  
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(input);  
        String result = m.replaceAll(replacement);  
        return result;  
	 }  

	 /**
	  * 字符串去空格
	  * @param str
	  * @return
	  */
	 public static String getStr(String str){
		 if(null==str||str.equals("")||str.equals("\"\"")||str.equals("''")){
			 return null;
		 }	
		 return str.trim();
	 }
	 
	/**
	 * 根据字符串类型 1-字符串 2-纯数字 0-空
	 * 
	 * @param str
	 * @return
	 */
	public static String getType(String str) {
		if (null != str && !str.equals("")) {
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
	 * @param str
	 * @return
	 */ 
    public static String humpToLine(String str){  
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
		boolean isMatched = false;
        while(matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
			isMatched = true;
		}
		if (!isMatched) {
			return str;
		}else {
			matcher.appendTail(sb);
		}
        return sb.toString();
    }  
    
    /**
     * 下划线命名法转驼峰命名法
     * @param str
     * @return
     */
    public static String lineToHump(String str){  
        str = str.toLowerCase();  
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);  
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());  
        }
        matcher.appendTail(sb);  
        return sb.toString();  
    }

	/**
	 * <p>
	 * 说明:附件上传产生的随机文件名
	 * </p>
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

	// 文件扩展名
	public static String getExpandName(String fileName) {
		int index = fileName.lastIndexOf(".");
		if(index == -1) {
			return null;
		}
		int len = fileName.length();
		String name = fileName.substring(index, len);
		return name;
	}

	// 将对象转换为String
	public static String toString(Object obj) {
		String rtStr = "";
		if (obj == null) {
			return rtStr;
		} else {
			rtStr = obj.toString();
		}
		return rtStr.trim();
	}

	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	public static String toSqlInVal(String vals) {
		StringBuffer result = new StringBuffer();
		result.append("'").append(vals.replaceAll(",", "','")).append("'");
		return result.toString();
	}

	public static String fieldToSetMethod(String field) {
		String first = field.substring(0, 1).toUpperCase();
		return "set" + first + field.substring(1);
	}

	public static String fieldToGetMethod(String field) {
		String first = field.substring(0, 1).toUpperCase();
		return "get" + first + field.substring(1);
	}

	public static String fitZero(String str, int bit) {
		int strLen = str.length();
		int gapLen = bit - strLen;
		if(gapLen <= 0) {
			return str;
		}
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < gapLen; i++) {
			result.append("0");
		}
		result.append(str);
		return result.toString();
	}

}
