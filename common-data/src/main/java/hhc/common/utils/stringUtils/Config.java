package hhc.common.utils.stringUtils;

import java.io.IOException;
import java.util.Properties;

public class Config {

	private static Properties p = new Properties();


	/**
	 * 加载配置文件
	 */
	public static synchronized void load(String propPath) {

		try {
			if (p.size() == 0) {
				p.load(Config.class.getResourceAsStream(propPath));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static String getProperty(String name) {
		return p.getProperty(name);
	}

	/**
	 * getProperty方法的简写
	 *
	 * @param name
	 *            属性名
	 * @return value
	 */
	public static String get(String name) {
		return getProperty(name);
	}

	/**
	 * 获取一个配制项，如果项没有被配制，则返回设置的默认值
	 *
	 * @param name
	 *            配制项名
	 * @param defaultValue
	 *            默认值
	 * @return 配制值
	 */
	public static String getString(String name, String defaultValue) {
		String ret = getProperty(name);
		return ret == null ? defaultValue : ret;
	}

	/**
	 * 从配制文件中获取一个整形的配制值，如果没有配制，则返回默认值
	 *
	 * @param item
	 *            属性名
	 * @param defaultValue
	 *            默认值
	 * @return int value
	 */
	public static int getInt(String item, int defaultValue) {
		String value = getProperty(item);
		if (value == null) {
			return defaultValue;
		}
		int ret = defaultValue;
		try {
			ret = Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static boolean getBoolean(String item, boolean defaultValue) {
		String value = getProperty(item);
		if (value == null) {
			return defaultValue;
		}
		boolean ret = defaultValue;
		try {
			ret = Boolean.parseBoolean(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
