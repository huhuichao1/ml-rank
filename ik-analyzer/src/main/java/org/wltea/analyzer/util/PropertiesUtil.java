package org.wltea.analyzer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Properties工具类
 * 
 * @author shouyilin
 * 
 */
public class PropertiesUtil {

	public static Properties loadClasspath(String fileName) {
		if (StringUtil.isEmpty(fileName)) {
			return null;
		}
		InputStream inStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream(fileName);
		if (inStream != null) {
			try {
				Properties properties = new Properties();
				properties.load(inStream);
				return properties;
			} catch (IOException e) {
				LogUtil.IK.error("", e);
			} finally {
				try {
					inStream.close();
				} catch (IOException e) {
					LogUtil.IK.error("", e);
				}
			}
		}
		return null;
	}

	public static Properties load(File file) {
		if (file == null || !file.isFile()) {
			return null;
		}
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			return null;
		}
		try {
			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		} catch (IOException e) {
			LogUtil.IK.error("", e);
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
				LogUtil.IK.error("", e);
			}
		}
		return null;

	}


	public static void saveProperties(Properties properties, File file) {
		if (properties == null || file == null) {
			return;
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			properties.store(out, null);
		} catch (FileNotFoundException e) {
			LogUtil.IK.error("", e);
		} catch (IOException e) {
			LogUtil.IK.error("", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LogUtil.IK.error("", e);
				}
			}
		}
	}

	/**
	 * 获得int
	 * 
	 * @param properties
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInteger(Properties properties, String key,
			int defaultValue) {
		if (properties == null || StringUtil.isEmpty(key)) {
			return defaultValue;
		}
		Integer result = DataTypeUtil.getInteger(properties.getProperty(key));
		if (result == null) {
			return defaultValue;
		} else {
			return result;
		}
	}

	/**
	 * 获得long
	 * 
	 * @param properties
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(Properties properties, String key,
			long defaultValue) {
		if (properties == null || StringUtil.isEmpty(key)) {
			return defaultValue;
		}
		Long result = DataTypeUtil.getLong(properties.getProperty(key));
		if (result == null) {
			return defaultValue;
		} else {
			return result;
		}
	}

	/**
	 * 获得double
	 * 
	 * @param properties
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static double getDouble(Properties properties, String key,
			double defaultValue) {
		if (properties == null || StringUtil.isEmpty(key)) {
			return defaultValue;
		}
		Double result = DataTypeUtil.getDouble(properties.getProperty(key));
		if (result == null) {
			return defaultValue;
		} else {
			return result;
		}
	}

	/**
	 * 获得boolean
	 * 
	 * @param properties
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Properties properties, String key,
			boolean defaultValue) {
		if (properties == null || StringUtil.isEmpty(key)) {
			return defaultValue;
		}
		Boolean result = DataTypeUtil.getBoolean(properties.getProperty(key));
		if (result == null) {
			return defaultValue;
		} else {
			return result;
		}
	}

	/**
	 * 获得字符串
	 * 
	 * @param properties
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Properties properties, String key,
			String defaultValue) {
		if (properties == null || StringUtil.isEmpty(key)) {
			return defaultValue;
		}
		String result = properties.getProperty(key);
		if (result == null) {
			return defaultValue;
		} else {
			return StringUtil.trim(result);
		}
	}

}
