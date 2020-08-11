package com.manage.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 */
public class DateUtils {
	public static String YYYY = "yyyy";

	public static String YYYY_MM = "yyyy-MM";

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M");

	public static SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-M-d");

	/**
	 * 获取当前Date型日期
	 * 
	 * @return Date() 当前日期
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 取得年
	 * 
	 * @param date
	 * @return
	 */
	public static final int getYYYY(Date date) {
		return Integer.valueOf(parseDateToStr("yyyy", date));
	}

	public static final int getMM(Date date) {
		return Integer.valueOf(parseDateToStr("MM", date));
	}

	public static final int getDD(Date date) {
		return Integer.valueOf(parseDateToStr("dd", date));
	}

	public static final Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final Date parseStrToDate(String ts) {
		try {
			return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static final String dateTime() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyyMMdd");
	}

	/**
	 * 获取未来 第 past 天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取未来 第 past 天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static Date getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		return today;
	}

	/**
	 * 返回当前日期前C个月的月与年
	 * 
	 * @param c
	 *            当前月份前C个月，c<=12
	 * @return
	 */
	public static Map<String, String> getPastMonth(int c) {
		String byear = null;
		String eyear = null;
		String bmonth = null;
		String emonth = null;

		Date now = new Date();
		int a = DateUtils.getMM(now) - c;
		if (a >= 0) {
			byear = String.valueOf(DateUtils.getYYYY(now));
			eyear = String.valueOf(DateUtils.getYYYY(now));
			bmonth = String.valueOf(a + 1);
			emonth = String.valueOf(DateUtils.getMM(now));
		} else {
			byear = String.valueOf(DateUtils.getYYYY(now) - 1);
			eyear = String.valueOf(DateUtils.getYYYY(now));
			bmonth = String.valueOf(12 + a + 1);
			emonth = String.valueOf(DateUtils.getMM(now));
		}

		Map<String, String> r = new HashMap<String, String>();
		r.put("byear", byear);
		r.put("eyear", eyear);
		r.put("bmonth", bmonth);
		r.put("emonth", emonth);
		return r;
	}

	public static String getLast12Months(int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -i);
		Date m = c.getTime();
		return sdf.format(m);
	}

	public static String getLast12Days(int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -i);
		Date m = c.getTime();
		return sdfm.format(m);
	}

	/**
	 * 获得当前月之前几个月的列表,格式为:2018-1,2018-2
	 * 
	 * @param j
	 * @return
	 */
	public static List<String> getListMonths(int j) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < j; i++) {
			//String m = getLast12Months(i + 1);
			String m = getLast12Months(i);
			list.add(0, m);//.add(m);
		}
		return list;
	}

	/**
	 * 获得月份段的月份的列表,格式为:2018-1,2018-2
	 * 
	 * @param beginMonth
	 *            2015-6
	 * @param endMonth
	 *            2015-9
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getListMonths(String beginMonth, String endMonth) throws ParseException {
		Date d1 = sdf.parse(beginMonth);// 定义起始日期
		Date d2 = sdf.parse(endMonth);// 定义结束日期

		Calendar dd = Calendar.getInstance();// 定义日期实例
		dd.setTime(d1);// 设置日期起始时间

		List<String> list = new ArrayList<String>();
		while (dd.getTime().before(d2) || dd.getTime().equals(d2)) {// 判断是否到结束日期
			String str = sdf.format(dd.getTime());
			list.add(str);
			dd.add(Calendar.MONTH, 1); // 进行当前日期月份加1
		}
		return list;
	}

	/**
	 * 获得当前一d之前几天的列表,格式为:2018-1-1,2018-1-2
	 * 
	 * @param j
	 * @return
	 */
	public static List<String> getListDays(int j) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < j; i++) {
			String m = getLast12Days(i + 1);
			list.add(m);
		}
		return list;
	}

	public static String subTime(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
		return days + "d " + hours + "h " + minutes + "m";

	}
	
	public static int getDaysOfMonth(int month,int year){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static void main(String[] args) throws ParseException {
//		Date d1 = DateUtils.parseStrToDate("2018-09-12 14:01:53");
//		String a = subTime(DateUtils.getNowDate(), d1);
//		System.out.println(a);
//		System.out.println(DateUtils.getDaysOfMonth(2,2015));
		System.out.println(DateUtils.parseDateToStr(YYYY_MM_DD, (DateUtils.getPastDate(-1))));
		//System.out.println(getFetureDate(0));
	}
}
