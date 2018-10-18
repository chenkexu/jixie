package com.qmwl.zyjx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 */
public class DateUtils {

    /**
     * 获得某月的天数
     *
     * @param year  int
     * @param month int
     * @return int
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if (month.equals("1") || month.equals("3") || month.equals("5")
                || month.equals("7") || month.equals("8") || month.equals("10")
                || month.equals("12")) {
            days = 31;
        } else if (month.equals("4") || month.equals("6") || month.equals("9")
                || month.equals("11")) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }
        return days;
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static String getYear() {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取月份
     *
     * @return
     */
    public static String getMonth() {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取日
     *
     * @return
     */
    public static String getDay() {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取格林威治时间(1970年至今的秒数)
     */
    public static long getGMTime1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/Greenwich"));
        String format = sdf.format(new Date());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date gmDate = null;
        try {
            gmDate = sdf1.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gmDate.getTime() / 1000;
    }

    /**
     * 获取格林威治时间 即1970年至今的秒数
     */
    public static long getGMTime2() {
        int round = (int) (System.currentTimeMillis() / 1000);
        return round;
    }

    /**
     * 格式到秒
     *
     * @param time
     * @return
     */
    public static String getMillon(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    /**
     * 格式到分
     *
     * @param time
     * @return
     */
    public static String getFen(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time);
    }

    /**
     * 格式到分,省去年份
     *
     * @param time
     * @return
     */
    public static String getFenRemoveY(long time) {
        return new SimpleDateFormat("MM-dd HH:mm").format(time);
    }

    /**
     * 格式到分,省去年份,月份,day
     *
     * @param time
     * @return
     */
    public static String getFenRemoveYandMandD(long time) {
        return new SimpleDateFormat("HH:mm").format(time);
    }

    /**
     * 根据规则获取当前时间应该显示的是什么
     *
     * @param time
     * @return
     */
    public static String getFormatTime(long time) {
        long currentTimeMillis = System.currentTimeMillis();
        // 如果不是今年
        if (!getIsYear(time)) {
            return getFen(time);
        }
        // 如果不是同一天
        else if (!isSameDate(time, currentTimeMillis)) {
            return getFenRemoveY(time);
        } else {
            if ((currentTimeMillis - time) / 1000 <= 60 && (currentTimeMillis - time) / 1000 >= 0) {
                return "刚刚";
            } else {
                return getFenRemoveYandMandD(time);
            }

        }

    }

    /**
     * 判断是否是今年
     *
     * @param time
     * @return
     */
    public static boolean getIsYear(long time) {
        String curTime = new SimpleDateFormat("yyyy").format(System.currentTimeMillis());
        String preTime = new SimpleDateFormat("yyyy").format(time);
        if (curTime.equals(preTime)) {
            return true;
        }

        return false;
    }

    /**
     * 是否同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(long date1, long date2) {
        long days1 = date1 / (1000 * 60 * 60 * 24);
        long days2 = date2 / (1000 * 60 * 60 * 24);
        return days1 == days2;
    }

    /**
     * 获取时间HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        // "\\s"以空格截断
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 获取当前时间的年月日时分秒
     *
     * @return
     */
    public static String current() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分" + second + "秒";
    }

    /**
     * 得到昨天的日期
     *
     * @return
     */
    public static String getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 得到明天的日期
     *
     * @return
     */
    public static String getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = sdf.format(calendar.getTime());
        return tomorrow;
    }

    /**
     * 时间转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 时间转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr1(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 时间转化为时间(几点)
     *
     * @param time
     * @return
     */
    public static String timeStampToTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(time * 1000);
        return date;
    }

    /**
     * 将日期格式转化为时间(秒数)
     *
     * @param time
     * @return
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 将日期格式转化为时间(秒数)
     *
     * @param time
     * @return
     */
    public static long getString2Date(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 判断是否大于当前时间
     *
     * @param time
     * @return
     */
    public static boolean judgeCurrTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sdf.parse(time);
            long t = date.getTime();
            long round = System.currentTimeMillis();
            if (t - round > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 判断是否大于当前时间
     *
     * @param time
     * @return
     */
    public static boolean judgeCurrTime(long time) {
        long round = System.currentTimeMillis();
        if (time - round > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较后面的时间是否大于前面的时间
     *
     * @param
     * @return
     */
    public static boolean judgeTime2Time(String time1, String time2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            // 转化为时间
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            // 获取秒数作比较
            long l1 = date1.getTime() / 1000;
            long l2 = date2.getTime() / 1000;
            if (l2 - l1 > 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 得到日期 yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String formatDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(time * 1000);
        return date;
    }

    /**
     * 得到时间 HH:mm:ss
     *
     * @param timeStamp
     * @return
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 将一个时间转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }

    public static String timeCha(long zongshijian, String xiaoshiStr, String fenzhongStr, String miaoStr) {
//        long curTime = System.currentTimeMillis();
//        if (time <= curTime) {
//            return "";
//        }
        if (zongshijian <= 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
//        long zongshijian = time - curTime;
        long hours = zongshijian / (60 * 60);
        long min = (zongshijian - hours * 60 * 60) / (60);
        long miao = (zongshijian - (60 * 60) * hours - (60) * min) / 1;
        if (hours < 10) {
            stringBuffer.append("0");
            stringBuffer.append(hours);
        } else {
            stringBuffer.append(hours);
        }
        stringBuffer.append(xiaoshiStr);
        if (min < 10) {
            stringBuffer.append("0");
            stringBuffer.append(min);
        } else {
            stringBuffer.append(min);
        }
        stringBuffer.append(fenzhongStr);
        if (miao < 10) {
            stringBuffer.append("0");
            stringBuffer.append(miao);
        } else {
            stringBuffer.append(miao);
        }
        stringBuffer.append(miaoStr);
        return stringBuffer.toString();
    }


    public static String timeCha(long zzongshijian, String tianStr, String xiaoshiStr, String fenzhongStr, String miaoStr) {
//        long curTime = System.currentTimeMillis();
//        if (time <= curTime) {
//            return "";
//        }
        if (zzongshijian <= 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
//        long zongshijian = time - curTime;
        long tian = zzongshijian / (24 * 60 * 60);

        long zongshijian = zzongshijian - tian * (24 * 60 * 60);
        long hours = zongshijian / (60 * 60);
        long min = (zongshijian - hours * 60 * 60) / (60);
        long miao = (zongshijian - (60 * 60) * hours - (60) * min) / 1;
        if (tian < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(tian);
        stringBuffer.append(tianStr);

        if (hours < 10) {
            stringBuffer.append("0");
            stringBuffer.append(hours);
        } else {
            stringBuffer.append(hours);
        }
        stringBuffer.append(xiaoshiStr);
        if (min < 10) {
            stringBuffer.append("0");
            stringBuffer.append(min);
        } else {
            stringBuffer.append(min);
        }
        stringBuffer.append(fenzhongStr);
        if (miao < 10) {
            stringBuffer.append("0");
            stringBuffer.append(miao);
        } else {
            stringBuffer.append(miao);
        }
        stringBuffer.append(miaoStr);
        return stringBuffer.toString();
    }

    /**
     * 获得当前时间差
     *
     * @param timeStamp
     * @return
     */
    public static int nowCurrentTime(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = timeStamp - curTime;
        return (int) time;
    }

    /**
     * 获取当前的时 -->flag==true 获取当前的分 -->flag==false
     *
     * @return
     */
    public static String nowCurrentPoint(boolean flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(System.currentTimeMillis());
        String[] split = date.split(":");
        String hour = null;
        String minute = null;
        if (flag) {
            if (split.length > 1) {
                hour = split[0];
                return hour;
            }
        } else {
            if (split.length > 1) {
                minute = split[1];
                return minute;
            }
        }
        return null;
    }

    /**
     * 将标准时间格式HH:mm:ss化为当前的时间差值
     *
     * @param str
     * @return
     */
    public static String StandardFormatStr(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(str);
            long timeStamp = d.getTime();

            long curTime = System.currentTimeMillis() / (long) 1000;
            long time = curTime - timeStamp / 1000;
            return time / 60 + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将一个时间转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * 日期变量转成对应的星期字符串
     *
     * @param date
     * @return
     */

    public static final int WEEKDAYS = 7;
    // 星期字符数组
    public static String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String DateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }
        return WEEK[dayIndex - 1];
    }

}
