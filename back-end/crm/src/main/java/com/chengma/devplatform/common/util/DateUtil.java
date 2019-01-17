package com.chengma.devplatform.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具
 *
 * @author huangqr@midea.com.cn:
 * @version 创建时间：2016年7月22日
 * 类说明
 */
public class DateUtil {

    public static String TIME_FORMAT = "yyyyMMddHHmmss";

    private final static String[] DATE_PATTERN = new String[]{
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd"
    };

    /**
     * 判断是否日期大小
     *
     * @param currentDate  当前需要比较的日期
     * @param previousDate 被比较的日期
     * @return -1表示小于，0表示相等，1表示大于
     */
    public static int compareDate(Date currentDate, Date previousDate) {
        return currentDate.compareTo(previousDate);
    }

    /**
     * 日期转换：字符串转换为Date类型
     *
     * @param date 格式为：yyyy-MM-dd或yyyy-MM-dd HH:mm:ss的日期字符串
     * @return Date
     */
    public static Date parse(String date) {
        try {
            return DateUtils.parseDate(date, DATE_PATTERN);
        } catch (ParseException e) {
            try {
                throw new Exception("日期格式错误", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 日期转换：Date转换为格式为yyyy-MM-dd的日期字符串
     *
     * @param toFormat 日期
     * @return 日期字符串
     */
    public static String formatSimpleDate(Date toFormat) {
        return new SimpleDateFormat(DATE_PATTERN[1]).format(toFormat);
    }

    /**
     * 日期转换：Date转换为格式为yyyy-MM-dd HH:mm:ss的日期字符串
     *
     * @param toFormat Date
     * @return 日期字符串
     */
    public static String format(Date toFormat) {
        return new SimpleDateFormat(DATE_PATTERN[0]).format(toFormat);
    }

    /**
     * 日期转换：Date转换为格式为yyyyMMddHHmmss的日期字符串
     *
     * @param toFormat Date
     * @return 日期字符串
     */
    public static String formatDate(Date toFormat) {
        return new SimpleDateFormat(TIME_FORMAT).format(toFormat);
    }

    /**
     * 日期转换：字符串转换为Date类型
     *
     * @param date 格式为：yyyy-MM-dd的日期字符串
     * @return Date
     */
    public static Date dateFormatYMD(String date) {
        return dateFormat(date, DATE_PATTERN[1]);
    }

    /**
     * 日期转换：字符串转换为Date类型
     *
     * @param date 格式为：yyyy-MM-dd HH:mm:ss的日期字符串
     * @return Date
     */
    public static Date dateFormatYMDHMS(String date) {
        return dateFormat(date, DATE_PATTERN[0]);
    }

    /**
     * 日期转换：字符串转换为Date类型
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return Date
     */
    private static Date dateFormat(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            try {
                throw new Exception("日期格式错误：" + e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 日期转换：字符串转换为Date类型
     * 可把UTC格式的日期字符串转成GMT日期
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return Date
     */
    public static Date toGMT(String date, String format) {
        char flag = 'Z';
        if (date.indexOf(flag) != -1) {
            date = date.replace("Z", " UTC");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            try {
                return df.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
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
     * @param time   长整型数字
     * @param format 格式为：yyyyMMddHHmmss，或其他
     * @return String
     */
    public static String convert2String(long time, String format) {
        if (time > 0L) {
            if (StringUtils.isBlank(format)) {
                format = TIME_FORMAT;
            }
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date date = new Date(time);
            return sf.format(date);
        }
        return "";
    }

    /**
     * 获取当年的第一天
     *
     * @return Date
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     *
     * @return Date
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    public static boolean isTranDate(String date){
        Date bdate = dateFormatYMDHMS(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        int week=cal.get(Calendar.DAY_OF_WEEK)-1;
        String temp="";
        if(week ==6 || week==0||week==1){//0代表周日，6代表周六
            if(week==6){
                temp = formatSimpleDate(bdate);
                temp=temp+" 04:00:00"; // 周六凌晨4点收盘
                Date t=dateFormatYMDHMS(temp);
                if(bdate.before(t)){
                    return false;
                }
            }

            if(week==1){
                temp = format(bdate);
                Date t= dateFormatYMD(temp);
                temp = formatSimpleDate(t)+" 08:00:00"; // 周1早上8点开盘
                t = dateFormatYMDHMS(temp);
                if(t.before(bdate)){
                    return false;
                }
            }

            return true;
        }
        return false;
    }

}
