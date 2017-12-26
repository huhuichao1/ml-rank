package org.wltea.analyzer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据类型工具类
 * 
 * @author shouyilin
 *
 */
public class DataTypeUtil {

	public static Integer getInteger(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			return Integer.valueOf(StringUtil.trim(input));
		} catch (Exception e) {
		}
		return null;
	}

	public static Long getLong(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			return Long.valueOf(StringUtil.trim(input));
		} catch (Exception e) {
		}
		return null;
	}

	public static Float getFloat(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			return Float.valueOf(StringUtil.trim(input));
		} catch (Exception e) {
		}
		return null;
	}

	public static Double getDouble(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			return Double.valueOf(StringUtil.trim(input));
		} catch (Exception e) {
		}
		return null;
	}

	public static Boolean getBoolean(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		try {
			return Boolean.valueOf(StringUtil.trim(input));
		} catch (Exception e) {
		}
		return null;
	}

	public static Date getDate(String input, String dateTimePattern) {
		if (StringUtil.isEmpty(input, dateTimePattern)) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateTimePattern);
		try {
			return format.parse(StringUtil.trim(input));
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date getDate(String input, String[] dateTimePatterns) {
		if (StringUtil.isEmpty(input) || dateTimePatterns == null || dateTimePatterns.length == 0) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat();
		for (String dateTimePattern : dateTimePatterns) {
			if (StringUtil.isEmpty(dateTimePattern)) {
				continue;
			}
			format.applyPattern(dateTimePattern);
			try {
				return format.parse(StringUtil.trim(input));
			} catch (ParseException e) {
			}
		}
		return null;
	}

	private static Map<String, Integer> nums = new HashMap<String, Integer>();
	static {
		nums.put("一", 1);
		nums.put("二", 2);
		nums.put("三", 3);
		nums.put("四", 4);
		nums.put("五", 5);
		nums.put("六", 6);
		nums.put("七", 7);
		nums.put("八", 8);
		nums.put("九", 9);
		nums.put("十", 10);
	}

	public static Integer translateInteger(String input) {
		if (StringUtil.isEmpty(input)) {
			return null;
		}
		return nums.get(StringUtil.trim(input));
	}

}
