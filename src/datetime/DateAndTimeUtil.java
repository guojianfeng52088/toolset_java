package datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description 来不及解释了，快上车！！！
 * @Author pc
 * @Date 2019/10/04 21:17
 */
public class DateAndTimeUtil {

    public static final String SM_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DFT_FORMAT = "yyyy-MM-dd";
    public static final String SDF_FORMAT = "yyyyMMddHHmmss";
    public static final String CDF_FORMAT = "yyyy年MM月dd日";
    public static final String HMS_FORMAT = "HH:mm:ss";
    public static final String YMD_FORMAT = "yyyyMMdd";

    public static SimpleDateFormat SM = new SimpleDateFormat(SM_FORMAT);
    public static SimpleDateFormat DFT = new SimpleDateFormat(DFT_FORMAT);
    public static SimpleDateFormat SDF = new SimpleDateFormat(SDF_FORMAT);
    public static SimpleDateFormat CDF = new SimpleDateFormat(CDF_FORMAT);
    public static SimpleDateFormat HMS = new SimpleDateFormat(HMS_FORMAT);
    public static SimpleDateFormat YMD = new SimpleDateFormat(YMD_FORMAT);

    /**
     * @param format 时间和日期格式
     * @Description 获取当前日期和时间（自定义格式）
     * @Author guojianfeng
     * @Date 2019/10/04 21:25
     * @Return
     */
    public static String getCurrentDateTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * @param
     * @Description 获取当前日期（格式：yyyy-MM-dd）
     * @Author guojianfeng
     * @Date 2019/10/04 21:23
     * @Return
     */
    public static String getCurrentDate() {
        return getCurrentDateTime(DFT_FORMAT);
    }

    /**
     * @param
     * @Description 获取当前时间（格式：HH:mm:ss）
     * @Author guojianfeng
     * @Date 2019/10/04 21:27
     * @Return
     */
    public static String getCurrentTime() {
        return getCurrentDateTime(HMS_FORMAT);
    }

    /**
     * @param
     * @Description 获取当前的日期和时间（格式：yyyy-MM-dd HH:mm:ss）
     * @Author guojianfeng
     * @Date 2019/10/04 21:29
     * @Return
     */
    public static String getCurrentDateTime() {
        return getCurrentDateTime(SM_FORMAT);
    }


    /**
     * @param
     * @Description 获取昨天的日期（格式：yyyy-MM-dd）
     * @Author guojianfeng
     * @Date 2019/10/04 21:30
     * @Return
     */
    public static String getYesterday() {
        SimpleDateFormat df = new SimpleDateFormat(DFT_FORMAT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return df.format(c.getTime());
    }


    /**
     * @param amount 天数，往前算的话，传负数
     * @Description 获取当前日期相加后的日期（格式：yyyy-MM-dd）
     * @Author guojianfeng
     * @Date 2019/10/04 21:32
     * @Return
     */
    public static String addDay(int amount) {
        SimpleDateFormat df = new SimpleDateFormat(DFT_FORMAT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, amount);
        return df.format(c.getTime());
    }


    /**
     * @param
     * @Description 获取明天的日期（格式：yyyy-MM-dd）
     * @Author guojianfeng
     * @Date 2019/10/04 21:33
     * @Return
     */
    public static String getTomorrow() {
        SimpleDateFormat df = new SimpleDateFormat(DFT_FORMAT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return df.format(c.getTime());
    }


    /**
     * @param date String类型的日期
     * @Description 把String转换成日期
     * @Author guojianfeng
     * @Date 2019/10/04 21:35
     * @Return
     */
    public static Date convertStringToDate(String date) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(getFormat(date));
        return df.parse(date);
    }


    /**
     * @param date   需要转换的日期date
     * @param format 转换后的格式
     * @Description 把日期类型转换成指定格式的String串
     * @Author guojianfeng
     * @Date 2019/10/04 21:36
     * @Return
     */
    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    /**
     * @param value  转换前的值
     * @param format 转换的格式
     * @Description 日期时间字符串格式转换
     * @Author guojianfeng
     * @Date 2019/10/04 21:37
     * @Return
     */
    public static String dateFormat(String value, String format) {
        try {
            Date date = convertStringToDate(value);
            return convertDateToString(date, format);
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * @param date   待转换的date对象
     * @param format 转换的格式
     * @Description Date类型转指定格式String
     * @Author guojianfeng
     * @Date 2019/10/04 21:39
     * @Return
     */
    public static String dateFormat(Date date, String format) {
        try {
            return convertDateToString(date, format);
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * @param type   间隔的单位：yyyy-年，MM-月，dd-日，HH-小时，mm-分钟，ss-秒，不填默认为日
     * @param sdate1 日期1
     * @param sdate2 日期2
     * @Description 计算两个日期的间隔（yyyy MM dd HH mm ss）
     * @Author guojianfeng
     * @Date 2019/10/04 21:40
     * @Return
     */
    public static int dateDiff(String type, String sdate1, String sdate2) throws Exception {
        Date date1 = new SimpleDateFormat(getFormat(sdate1)).parse(sdate1);
        Date date2 = new SimpleDateFormat(getFormat(sdate2)).parse(sdate2);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        if ("yyyy".equals(type)) {//年
            return yearDiff;
        } else if ("MM".equals(type)) {//月
            int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
            return monthDiff;
        } else {
            long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
            long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
            if ("HH".equals(type)) {//小时
                return (int) ((ldate2 - ldate1) / (3600000));
            } else if ("mm".equals(type)) {//分钟
                return (int) ((ldate2 - ldate1) / (60000));
            } else if ("ss".equals(type)) {//秒
                return (int) ((ldate2 - ldate1) / (1000));
            } else {//日
                return (int) ((ldate2 - ldate1) / (3600000 * 24));
            }
        }
    }


    /**
     * @param type  间隔的单位：yyyy-年，MM-月，dd-日，HH-小时，mm-分钟，ss-秒，不填默认为日
     * @param sdate String型日期
     * @param num   间隔数量
     * @Description 计算日期加上一段间隔之后的新日期
     * @Author guojianfeng
     * @Date 2019/10/04 21:41
     * @Return
     */
    public static String dateAdd(String type, String sdate, int num) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(getFormat(sdate));
        Date date = df.parse(sdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if ("yyyy".equals(type)) {
            cal.add(Calendar.YEAR, num);
        } else if ("MM".equals(type)) {
            cal.add(Calendar.MONTH, num);
        } else if ("HH".equals(type)) {
            cal.add(Calendar.HOUR, num);
        } else if ("mm".equals(type)) {
            cal.add(Calendar.MINUTE, num);
        } else if ("ss".equals(type)) {
            cal.add(Calendar.SECOND, num);
        } else {
            cal.add(Calendar.DATE, num);
        }
        return df.format(cal.getTime());
    }


    /**
     * @param date 当前日期
     * @Description 获取当前日期的当天开始时间
     * @Author guojianfeng
     * @Date 2019/10/04 21:42
     * @Return
     */
    public static String dateToStartTime(String date) {
        return date + " 00:00:00";
    }

    /**
     * @param date 当前日期
     * @Description 获取当天日期的结束时间
     * @Author guojianfeng
     * @Date 2019/10/04 21:44
     * @Return
     */
    public static String dateToEndTime(String date) {
        return date + " 23:59:59";
    }


    /**
     * @param date 日期
     * @Description 获取日期格式，只支持"yyyy-MM-dd HH:mm:ss"、"yyyy-MM-dd"、"MM/dd/yyyy HH:mm:ss"、"MM/dd/yyyy"四种格式。
     * @Author guojianfeng
     * @Date 2019/10/04 21:21
     * @Return
     */
    private static String getFormat(String date) throws Exception {
        int x1 = date.indexOf("-");
        int x2 = date.indexOf("/");
        int x3 = date.indexOf(":");
        if (x1 != -1 && x3 != -1) {
            return "yyyy-MM-dd HH:mm:ss";
        } else if (x1 != -1 && x3 == -1) {
            return "yyyy-MM-dd";
        } else if (x2 != -1 && x3 != -1) {
            return "MM/dd/yyyy HH:mm:ss";
        } else if (x2 != -1 && x3 != -1) {
            return "MM/dd/yyyy";
        } else {
            throw new Exception("日期格式错误：" + date);
        }
    }
}
