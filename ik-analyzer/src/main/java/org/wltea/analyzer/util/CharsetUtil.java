package org.wltea.analyzer.util;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

/**
 * 字符工具类
 * 
 * @author shouyilin
 *
 */
public class CharsetUtil {

	public static boolean isSupported(String charsetName) {
		if (StringUtil.isEmpty(charsetName)) {
			return false;
		}
		try {
			return Charset.isSupported(charsetName);
		} catch (IllegalCharsetNameException e) {
		}
		return false;
	}

	public static Charset forName(String charsetName) {
		if (StringUtil.isEmpty(charsetName)) {
			return null;
		}
		try {
			return Charset.forName(charsetName);
		} catch (Exception e) {
		}
		return null;
	}

}
