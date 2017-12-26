package hhc.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huhuichao on 2017/8/28.
 */
public class TimeFormat {
	public static enum DateType {
		DATE_14("yyyyMMddHHmmss"), DATE_COMMON("yyyy-MM-dd HH:mm:ss"), DATE_UNIX("MM/dd/yyyy HH:mm:ss");

		String format;

		DateType(String format) {
			this.format = format;
		}

		public String getFormat() {
			return format;
		}

	}

	/**
	 * 把DateType类型转换成 14位数字
	 * 
	 * @param date
	 * @param type
	 * @return
	 */
	public static String convert2Date14(String date, DateType type) {
		String ret = null;
		SimpleDateFormat sdf = new SimpleDateFormat(type.getFormat());
		SimpleDateFormat date4 = new SimpleDateFormat(DateType.DATE_14.getFormat());
		Date smdate = null;
		try {
			smdate = sdf.parse(date);
			ret = date4.format(smdate);
			return ret;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ret;
		}

	}

	/**
	 * 
	 * @return
	 */
	public static String currentTimeDate14() {
		SimpleDateFormat date4 = new SimpleDateFormat(DateType.DATE_14.getFormat());
		Date date = new Date();
		// System.c
		return date4.format(date);
	}

	public static void main(String[] args) {
		System.out.println(convert2Date14("2017-01-02 03:03:33", DateType.DATE_COMMON));
		System.out.println(convert2Date14("11/28/2017 03:03:33", DateType.DATE_UNIX));
	}

}
