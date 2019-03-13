package com.harrymark.wechatapp.frientcommon.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 想不到更多的了，遇到需要的再补充
 * Created by haoweima on 2019/1/24.
 */
public class DateUtils {

    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY_MM_DD_HH_MM_SS_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 获取某日期的年份
     *
     * @param date
     * @return
     */
    public static Integer getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取某日期的月份
     *
     * @param date
     * @return
     */
    public static Integer getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取某日期的日数
     *
     * @param date
     * @return
     */
    public static Integer getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);//获取日
    }

    /**
     * 更改日期字符串格式
     *
     * @param dateStr
     * @param sourceFormat
     * @param targetFormat
     * @return
     * @throws Exception
     */
    public static String changeFormat(String dateStr, String sourceFormat, String targetFormat) throws Exception {
        DateFormat df1 = new SimpleDateFormat(sourceFormat);
        Date date = df1.parse(dateStr);
        DateFormat df2 = new SimpleDateFormat(targetFormat);
        String result = df2.format(date);
        return result;
    }

    /**
     * Date转String
     *
     * @param date
     * @param targetFormat
     * @return
     */
    public static String date2String(Date date, String targetFormat) {
        DateFormat df = new SimpleDateFormat(targetFormat);
        String result = df.format(date);
        return result;
    }

    /**
     * String转Date
     *
     * @param dateStr
     * @param sourceFormat
     * @return
     * @throws Exception
     */
    public static Date string2Date(String dateStr, String sourceFormat) throws Exception {
        DateFormat df = new SimpleDateFormat(sourceFormat);
        Date date = df.parse(dateStr);
        return date;
    }

    /**
     * 获取date偏移offset天日期
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date getOffsetDate(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    /**
     * 获取date偏移offset天日期
     *
     * @param dateStr
     * @param offset
     * @param sourceFormat
     * @return
     */
    public static Date getOffsetDate(String dateStr, int offset, String sourceFormat) throws Exception {
        return getOffsetDate(string2Date(dateStr, sourceFormat), offset);
    }

    /**
     * 获取date偏移offset天日期
     *
     * @param date
     * @param offset
     * @param targetFormat
     * @return
     */
    public static String getOffsetString(Date date, int offset, String targetFormat) {
        return date2String(getOffsetDate(date, offset), targetFormat);
    }

    /**
     * 获取date偏移offset天日期
     *
     * @param dateStr
     * @param offset
     * @param sourceFormat
     * @param targetFormat
     * @return
     * @throws Exception
     */
    public static String getOffsetString(String dateStr, int offset, String sourceFormat, String targetFormat) throws Exception {
        return date2String(getOffsetDate(dateStr, offset, sourceFormat), targetFormat);
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date getDayBeginTime(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date getDayEndTime(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static Long getDistanceDays(String str1, String str2, String format1, String format2) throws Exception {
        DateFormat df1 = new SimpleDateFormat(format1);
        DateFormat df2 = new SimpleDateFormat(format2);

        long time1 = df1.parse(str1).getTime();
        long time2 = df2.parse(str2).getTime();

        long diff = (time1 < time2) ? time2 - time1 : time1 - time2;
        return diff / (1000 * 60 * 60 * 24);
    }

}
