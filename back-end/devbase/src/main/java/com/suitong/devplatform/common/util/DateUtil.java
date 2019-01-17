package com.suitong.devplatform.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/** 
 * @author huangqr@midea.com.cn: 
 * @version 创建时间：2016年7月22日 
 * 类说明 
 */
public abstract class DateUtil {

	public static String TIME_FORMAT = "yyyyMMddHHmmss";

	private final static String[] DATE_PATTERN = new String[]{
			"yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd"
	};
	/**
	 * 判断是否日期大小
	 * @param currentDate 当前需要比较的日期
	 * @param previousDate 被比较的日期
	 * @return -1表示小于，0表示相等，1表示大于
	 */
	public static  int compareDate(Date currentDate,Date previousDate){
		return currentDate.compareTo(previousDate);
	}
	
	/**
	 * 日期转换：字符串转换为Date类型
	 * @param date 格式为：yyyy-MM-dd或yyyy-MM-dd HH:mm:ss的日期字符串
	 * @return Date
	 */
	public static Date parse(String date){
		try {
			return DateUtils.parseDate(date,DATE_PATTERN);
		} catch (ParseException e) {
			try {
				throw new Exception("日期格式错误",e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 日期转换：Date转换为格式为yyyy-MM-dd的日期字符串
	 * @param toFormat 日期
	 * @return 日期字符串
	 */
	public static String formatSimpleDate(Date toFormat){
		return new SimpleDateFormat(DATE_PATTERN[1]).format(toFormat);
	}

	/**
	 * 日期转换：Date转换为格式为yyyy-MM-dd HH:mm:ss的日期字符串
	 * @param toFormat Date
	 * @return 日期字符串
	 */
	public static String format(Date toFormat){
		return new SimpleDateFormat(DATE_PATTERN[0]).format(toFormat);
	}

	/**
	 * 日期转换：字符串转换为Date类型
	 * @param date 格式为：yyyy-MM-dd的日期字符串
	 * @return
	 */
	public static Date dateFormatYMD(String date){
		DateFormat df = new SimpleDateFormat(DATE_PATTERN[1]);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			try {
				throw new Exception("日期格式错误："+e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 日期转换：字符串转换为Date类型
	 * @param date 格式为：yyyy-MM-dd HH:mm:ss的日期字符串
	 * @return
	 */
	public static Date dateFormatYMDHMS(String date){
		DateFormat df = new SimpleDateFormat(DATE_PATTERN[0]);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			try {
				throw new Exception("日期格式错误："+e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static Date toGMT(String date, String format){
		if(date.indexOf("Z") != -1){
			date = date.replace("Z", " UTC");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
			try {
				return df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			SimpleDateFormat df = new SimpleDateFormat(format);
			try {
				return df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 *
	 * @param time
	 * @param format
	 * @return
	 */
	public static String convert2String(long time, String format) {
		if (time > 0l) {
			if (StringUtils.isBlank(format))
				format = TIME_FORMAT;
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);
			return sf.format(date);
		}
		return "";
	}

}
