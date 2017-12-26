package hhc.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonDay {



	static String initDate = "2016-09-26 00:00:00";
	static int initIntTime = 1474992000-3600*24*2;
	public static int intDay = 3600*24;
	

	
	
	public static int getNowTime()
	{
		return initIntTime+secondBetween();
	}
	
	public static int getStartTime()
	{
		return  (secondBetween()/intDay)*3600*24+initIntTime;
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		
		String date = "2016-10-26 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date smdate = null;
		try {
			smdate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(CommonDay.getTime(smdate));
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int secondBetween()  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date smdate = null;
		try {
			smdate = sdf.parse(initDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date nowDate = new Date();
		try {
			smdate = sdf.parse(sdf.format(smdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(nowDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	
	
	
	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int getTime(Date nowDate)  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date smdate = null;
		try {
			smdate = sdf.parse(initDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Date nowDate = new Date();
		try {
			smdate = sdf.parse(sdf.format(smdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(nowDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000);

		return Integer.parseInt(String.valueOf(between_days))+initIntTime;
	}
	
	
	
	public static String getDay()  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		Date nowDate = new Date();
		return sdf.format(nowDate);
		//return nowDate.toString();
		//return Integer.parseInt(String.valueOf(between_days));
	}


}
