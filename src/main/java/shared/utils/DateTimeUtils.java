package shared.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DateTimeUtils.class);

    public static final String DATE = "yyyy-MM-dd";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_US = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_TIME_US_NEW = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_TIME_NEW = "yyyyMMddHHmmssSSS";
    public static final String DATE_YYYY_MM_DD = "yyyyMMdd";
    public static final String DATE_HH_MM = "HH:mm";
    public static final String DATE_MM_DD = "MM-dd";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(DATE_TIME);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(DATE);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_NEW = DateTimeFormat.forPattern(DATE_TIME_NEW);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YYYY_MM_DD = DateTimeFormat.forPattern(DATE_YYYY_MM_DD);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_HH_MM = DateTimeFormat.forPattern(DATE_HH_MM);

    public static final long ONE_SECOND_MILLISECONDS = 1000;
    public static final long ONE_HOUR_MILLISECONDS = 60 * 60 * 1000;
    public static final long ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    public static final long ONE_MONTH_MILLISECONDS = 30 * 24 * 60 * 60 * 1000;
    
    public static final int MILLI_SECONDS_LENGTH = 13;
    public static final int SECONDS_LENGTH = 10;
    public static final int ZONE_OFFSET_HOURS = 8;

    /**
     * 单位秒
     */
    public static long timestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转时间戳 单位秒
     */
    public static long timestamp(String dateTime) {
        DateTime time = DateTime.parse(dateTime, DATE_TIME_FORMATTER);
        return time.getMillis() / 1000L;
    }

    /**
     * 将时间字符串转为时间戳（单位：毫秒）
     */
    public static long timestamp(String dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        DateTime time = DateTime.parse(dateTime, formatter);
        return time.getMillis();
    }

    /**
     * 将时间字符串转为时间戳（单位：毫秒）
     * NOTE: 先使用pattern格式解析，如果解析失败则使用exceptionPattern
     */
    public static long timestamp(String dateTime, String pattern, String exceptionPattern) {
        DateTime time;
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
            time = DateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(exceptionPattern);
            time = DateTime.parse(dateTime, formatter);
        }
        return time.getMillis();
    }

    /**
     * 返回值单位秒
     */
    public static long timestamp(Date date) {
        if (date == null) {
            return 0L;
        }
        return date.getTime() / 1000L;
    }

    public static Date formatSecondUnitToDate(long time) {
        return new Date(time * 1000);
    }

    public static Date stringToDate(String date) {
        return DATE_FORMATTER.parseDateTime(date).toDate();
    }

    public static Date stringToDateTime(String date) {
        return DATE_TIME_FORMATTER.parseDateTime(date).toDate();
    }

    /**
     * @param time s
     * @return yyyy-MM-dd hh:mm:ss
     */
    public static String formatSecondUnitToString(long time) {
        return formatToString(time * 1000, DATE_TIME);
    }

    /**
     * @param time s
     * @return yyyy-MM-dd hh:mm:ss
     */
    public static String formatSecondUnitToString(Long time) {
        return time == null ? null : formatToString(time * 1000, DATE_TIME);
    }

    /**
     * @param time s
     */
    public static String formatSecondUnitToString(long time, String pattern) {
        return formatToString(time * 1000, pattern);
    }

    /**
     * @param time    ms
     * @param pattern eg:yyyy-MM-dd
     */
    public static String formatToString(long time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.print(time);
    }

    /**
     * @param time ms
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatToString(long time) {
        return formatToString(time, DATE_TIME);
    }

    /**
     * 返回当前日期  eg:2016-01-01
     */
    public static String currentDate() {
        return DATE_FORMATTER.print(System.currentTimeMillis());
    }

    /**
     * 返回当前时间 eg:2016-01-01 12:12:12
     */
    public static String currentDateTime() {
        return DATE_TIME_FORMATTER.print(System.currentTimeMillis());
    }

    public static String currentDateBegin() {
        return DATE_FORMATTER.print(System.currentTimeMillis()) + " 00:00:00";
    }

    public static String currentDateEnd() {
        return DATE_FORMATTER.print(System.currentTimeMillis()) + " 23:59:59";
    }

    /**
     * 某一天的开始
     *
     * @param date 单位为毫秒
     */
    public static String dateBegin(long date) {
        return DATE_FORMATTER.print(date) + " 00:00:00";
    }

    /**
     * 某一天的结束
     *
     * @param date 单位为毫秒
     */
    public static String dateEnd(long date) {
        return DATE_FORMATTER.print(date) + " 23:59:59";
    }

    /**
     * 获取距离1970-01-01间隔多少天的时间戳
     *
     * @param days 天数
     * @return timestamp s
     */
    public static long getTimestampByDays(final int days) {
        return getTimestampByDays(0, days);
    }

    /**
     * 获取距离data间隔多少天的时间戳
     *
     * @param data timestamp s
     * @param days 天数
     */
    public static long getTimestampByDays(final long data, final int days) {
        DateTime dateTime = new DateTime(data * 1000);
        return dateTime.plusDays(days).getMillis() / 1000L;
    }

    public static long timeUnitChange(long souTime, TimeUnit souUnit, TimeUnit targetUnit) {
        if (souUnit == targetUnit) {
            return souTime;
        }
        return targetUnit.convert(souTime, souUnit);
    }

    /**
     * @return 单位秒
     */
    public static long increaseDays(String since, int days) {
        return increaseDays(since, days, DATE_FORMATTER);
    }

    /**
     * @return 单位秒
     */
    public static long increaseDays(long timestamp, int days) {
        return increaseDays(new DateTime(timestamp * 1000L), days).getMillis() / 1000L;
    }

    /**
     * @return 单位秒
     */
    public static long increaseDays(String since, int days, DateTimeFormatter formatter) {
        DateTime dt = DateTime.parse(since, formatter);
        return increaseDays(dt, days).getMillis() / 1000L;
    }

    public static DateTime increaseDays(DateTime since, int days) {
        if (days > 0) {
            return since.plusDays(days);
        } else {
            return since.minusDays(-days);
        }
    }

    public static Date increaseDays(Date since, int days) {
        DateTime dt = new DateTime(since.getTime());
        return increaseDays(dt, days).toDate();
    }

    public static String format(String datetime, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dt = DateTime.parse(datetime, DATE_TIME_FORMATTER);
        return dt.toString(formatter);
    }

    /**
     * 将秒转换成 x天x小时x分x秒
     */
    public static String formatTime(long s) {
        int mi = 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = s / dd;
        long hour = (s - day * dd) / hh;
        long minute = (s - day * dd - hour * hh) / mi;
        long second = s - day * dd - hour * hh - minute * mi;

        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("天");
        }
        if (hour > 0) {
            sb.append(hour).append("小时");
        }
        if (minute > 0) {
            sb.append(minute).append("分");
        }
        if (second >= 0) {
            sb.append(second).append("秒");
        }
        return sb.toString();
    }

    public static Duration duration(Date from, Date to) {
        return Duration.ofSeconds(timestamp(to) - timestamp(from));
    }

    public static int currentIntegerDate() {
        Calendar calendar = Calendar.getInstance();
        return integerDate(calendar);
    }

    private static int integerDate(Calendar calendar) {
        return calendar.get(Calendar.YEAR) * 10000
                + (calendar.get(Calendar.MONTH) + 1) * 100
                + calendar.get(Calendar.DATE);
    }

    /**
     * @param time ms
     */
    public static int integerDate(long time) {
        String date = formatToString(time, DATE_YYYY_MM_DD);
        return Integer.parseInt(date);
    }

    public static long integerDateToMillis(int time) {
        return DATE_TIME_FORMATTER_YYYY_MM_DD.parseMillis(String.valueOf(time));
    }

    /**
     * 获取今天0点时间
     */
    public static Date getTodayEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取昨天0点
     */
    public static Date getYesterdayEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得明天0点时间
     */
    public static Date getTomorrowEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前时间字符串。格式：yyyyMMddHHmmssSSS
     */
    public static String getCompactDateTime() {
        return DATE_TIME_FORMATTER_NEW.print(System.currentTimeMillis());
    }

    /**
     * 判断时间是否为当天
     * 单位：秒
     */
    public static boolean isToday(long timestamp) {
        String today = formatToString(System.currentTimeMillis(), DATE);
        String date = formatSecondUnitToString(timestamp, DATE);
        return today.equals(date);
    }

    /**
     * 得到当前时间的前N小时
     */
    public static long getBeforeByHourTime(int ihour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - ihour);
        return calendar.getTimeInMillis() / 1000L;
    }

    /**
     * 得到当前时间的前N天
     *
     * @param days 天数
     * @return 13位时间戳
     */
    public static long getTimestampBeforeDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取过去第几天的日期
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        return calendar.getTime();
    }

    /**
     * 给定一个时间周期，判断时间date是否在此周期段内。
     * 例如给定周期 2:00~21:00 ，则
     * 03:00 -> true
     * 23:00 -> false
     * 例如给定周期 21:00~02:00 ，该周期跨了一天，则
     * 03:00 -> false
     * 23:00 -> true
     */
    public static boolean inPeriod(Date now, String startPattern, String endPattern) {
        String print = DATE_TIME_FORMATTER_HH_MM.print(now.getTime());
        now = DATE_TIME_FORMATTER_HH_MM.parseDateTime(print).toDate();
        Date beginTime = DATE_TIME_FORMATTER_HH_MM.parseDateTime(startPattern).toDate();
        Date endTime = DATE_TIME_FORMATTER_HH_MM.parseDateTime(endPattern).toDate();

        if (beginTime.getTime() < endTime.getTime()) {
            return now.getTime() >= beginTime.getTime() && now.getTime() <= endTime.getTime();
        } else if (endTime.getTime() == beginTime.getTime()) {
            return true;
        } else {
            // 跨天的周期切割成两段 startPattern-23:59 , 00:00-endPattern 进行比较
            Calendar newStart = Calendar.getInstance();
            newStart.setTime(beginTime);
            newStart.set(Calendar.HOUR_OF_DAY, 0);
            newStart.set(Calendar.MINUTE, 0);
            newStart.set(Calendar.SECOND, 0);
            long newBeginTime = newStart.getTime().getTime();

            Calendar newEnd = Calendar.getInstance();
            newEnd.setTime(endTime);
            newEnd.set(Calendar.HOUR_OF_DAY, 23);
            newEnd.set(Calendar.MINUTE, 59);
            newEnd.set(Calendar.SECOND, 59);
            long newEndTime = newEnd.getTime().getTime();

            return (now.getTime() >= beginTime.getTime() && now.getTime() <= newEndTime)
                    || (now.getTime() >= newBeginTime && now.getTime() <= endTime.getTime());
        }
    }

    /**
     * 校验时间格式（仅格式符合hh:mm）
     */
    private static boolean checkHHMM(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        try {
            @SuppressWarnings("unused")
            Date t = dateFormat.parse(time);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * 校验时间格式HH:mm（精确:HH区间必须在[00,23]，mm区间必须在[00,59]）
     */
    public static boolean checkTime(String time) {
        if (checkHHMM(time)) {
            String[] temp = time.split(":");
            if ((temp[0].length() == 2 || temp[0].length() == 1) && temp[1].length() == 2) {
                int h, m;
                try {
                    h = Integer.parseInt(temp[0]);
                    m = Integer.parseInt(temp[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                return h >= 0 && h < 24 && m >= 0 && m <= 59;
            }
        }
        return false;
    }

    public static Long timestampToSecs(Long timeInMillis) {
        return timeInMillis == null ? null : timeInMillis / ONE_SECOND_MILLISECONDS;
    }

    /**
     * 秒转为毫秒
     */
    public static Long timestampToMillis(Long timeInSecs) {
        return timeInSecs == null ? null : timeInSecs * ONE_SECOND_MILLISECONDS;
    }

    /**
     * 获取输入时间戳的前minushours的天数和小时数
     *
     * @param timestamp  毫秒时间戳  若为0就是当前时间
     * @param minusHours 前n小时  若为0就是当前小时
     */
    public static int getMinusHour(Long timestamp, int minusHours) {
        LocalDateTime localDateTime;
        if (timestamp == 0) {
            localDateTime = LocalDateTime.now();
        } else {
            localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        }
        LocalDateTime minusHoursDateTime = localDateTime.minusHours(minusHours);
        return minusHoursDateTime.getDayOfMonth() * 100 + minusHoursDateTime.getHour();
    }

    /**
     * 当前时间距离当天0点的毫秒数
     *
     * @param timestamp 毫秒时间戳  若为0就是当前时间
     */
    public static long getTimeHours(long timestamp) {
        // 将时间戳转换为Instant对象
        Instant instant = Instant.ofEpochMilli(timestamp);
        // 获取Instant对应的本地时间，即当前时区的时间
        LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        // 获取小时数并返回
        return dateTime.getHour();
    }

    /**
     * 根据时间戳解析成时间
     *
     * @param timestamp 时间戳
     * @return date
     */
    public static Date parseDate(Long timestamp) {
        if (null == timestamp) {
            return null;
        }

        if (String.valueOf(timestamp).length() == MILLI_SECONDS_LENGTH) {
            return new Date(timestamp);
        } else if (String.valueOf(timestamp).length() == SECONDS_LENGTH) {
            return formatSecondUnitToDate(timestamp);
        }
        return null;
    }

    /**
     * 获取当前日期的0点时间戳和23:59:59时间戳（13位）
     */
    public static long[] rangeOfToday() {
        try {
            LocalDateTime startLocalDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime endLocalDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            long startTimeStamp = localDateTimeToTimeStamp(startLocalDateTime);
            long endTimeStamp = localDateTimeToTimeStamp(endLocalDateTime);
            return new long[]{startTimeStamp, endTimeStamp};
        } catch (Exception e) {
            LOG.error("failed to get range of today", e);
            return null;
        }
    }

    /**
     * LocalDateTime 转 13位时间戳
     */
    private static long localDateTimeToTimeStamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now();
        }
        return localDateTime.toInstant(ZoneOffset.ofHours(ZONE_OFFSET_HOURS)).toEpochMilli();
    }

}
