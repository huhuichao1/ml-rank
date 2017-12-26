package hhc.common.utils.stringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharUtils {
	// 区号表
	public static HashMap<String, String> AREA_CODE_TABLE = null;

	// 拼音表
	public static HashSet<String> PINYIN_SET = null;
	public static HashSet<String> NOT_TAIL_SET = null;

	public static HashSet<String> COMBINATOR_SET = null;
	public static String[] NOT_TAILS = new String[] { "b", "c", "d", "f", "h", "j", "k", "l", "m", "p", "q", "r", "s",
			"t", "w", "x", "y", "z" };

	public static String[] COMBINATORS = new String[] { "a", "ai", "an", "ang", "ao", "e", "ei", "en", "eng", "o",
			"ou" };

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            全角字符串
	 * @return 半角字符串
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 半角转全角
	 * 
	 * @param input
	 *            半角字符串
	 * @return 全角字符串
	 */
	public static String toSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	/**
	 * 
	 * @param dst
	 * @param org
	 * @param split
	 * @return
	 */
	public static boolean replaceContain(String dst, String org, char split) {
		if (dst == null || org == null || dst.length() != org.length()) {
			return false;
		}
		int count = dst.length();
		for (int i = 0; i < count; i++) {
			if (dst.charAt(i) == split || dst.charAt(i) == org.charAt(i)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public static String getSuffix(String org, String dst, int len) {
		if (dst == null || org == null || len == 0) {
			return null;
		}
		int idx = org.indexOf(dst);
		if (idx < 0) {
			return null;
		}
		if (idx + dst.length() + len > org.length()) {
			len = org.length() - (idx + dst.length());
		}
		return org.substring(idx + dst.length(), idx + dst.length() + len);
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String replaceBrackets(String input) {
		// Bracket's count
		if (input.contains("(") || input.contains(")")) {
			int bracket = input.indexOf('(');
			int end_bracket = input.indexOf(')');
			if (end_bracket >= bracket) {
				String temp = "";
				if (bracket != -1)
					temp = input.substring(0, bracket);
				if (end_bracket < input.length() && end_bracket != -1) {
					temp += input.substring(end_bracket + 1, input.length());
				}
				input = replaceBrackets(temp);
			} else {
				String temp = "";
				if (end_bracket != -1)
					temp = input.substring(0, end_bracket);
				if (bracket < input.length() && bracket != -1) {
					temp += input.substring(bracket + 1, input.length());
				}
				input = replaceBrackets(temp);
			}

		}
		return input;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String replaceBrackets2(String input) {
		// Bracket's count
		if (input.contains("[") || input.contains("]")) {
			int bracket = input.indexOf('[');
			int end_bracket = input.indexOf(']');
			if (end_bracket >= bracket) {
				String temp = "";
				if (bracket != -1)
					temp = input.substring(0, bracket);
				if (end_bracket < input.length() && end_bracket != -1) {
					temp += input.substring(end_bracket + 1, input.length());
				}
				input = replaceBrackets(temp);
			} else {
				String temp = "";
				if (end_bracket != -1)
					temp = input.substring(0, end_bracket);
				if (bracket < input.length() && bracket != -1) {
					temp += input.substring(bracket + 1, input.length());
				}
				input = replaceBrackets(temp);
			}

		}
		return input;
	}

	/**
	 * @param c
	 * @return
	 */
	public static boolean isEnglishChar(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			return true;
		else
			return false;
	}

	public static void regxChinese(String source) {
		// 要匹配的字符串
		// 将上面要匹配的字符串转换成小写
		// source = source.toLowerCase();
		// www.111cn.net 匹配的字符串的正则表达式
		String reg_charset = "([0-9]*[\\s|\\S]*[u4E00-u9FA5]*)";

		Pattern p = Pattern.compile(reg_charset);
		Matcher m = p.matcher(source);
		while (m.find()) {
			System.out.println(m.group(1));
		}
	}

	public static void main(String[] args) {
		String str = "……^1dsf  の  adS   DFASFSADF阿德斯防守对方asdfsadf37《？：？@%#￥%#￥%@#$%#@$%^><?1234";  
		System.out.println(norm( str));
	}

	// public static void regxChinese(){
	// // 要匹配的字符串
	// String source = "<span title='5 星级酒店' class='dx dx5'>";
	// // 将上面要匹配的字符串转换成小写
	// // source = source.toLowerCase();
	// // 匹配的字符串的正则表达式
	// String reg_charset =
	// "<span[^>]*?title='([0-9]*[\s|\S]*[u4E00-u9FA5]*)'[\s|\S]
	//
	// *class='[a-z]*[\s|\S]*[a-z]*[0-9]*'";
	//
	// Pattern p = Pattern.compile(reg_charset);
	// Matcher m = p.matcher(source);
	// while (m.find()) {
	// System.out.println(m.group(1));
	// }
	// }

	public static String norm(String str) {
		String regEx = "[a-zA-Z0-9\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			sb.append(m.group());
			sb.append("");
		}
		return sb.toString();

	}
}
