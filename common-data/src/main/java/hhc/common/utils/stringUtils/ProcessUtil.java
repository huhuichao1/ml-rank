/*---------------------------------------------------------------------------*/
/**
 * @(#)$Id: ProcessUtil.java 7720 2013-07-10 02:46:00Z shiyongping $ 
 * @(#) Implementation file of class ProcessUtil.
 * @author sjyao
 * (c)  PIONEER SUNTEC CORPORATION  2013
 * All Rights Reserved.
 */
/*---------------------------------------------------------------------------*/
package hhc.common.utils.stringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author sjyao
 * 
 */
public class ProcessUtil {
	private static List<Pattern> patterns;

	/**
	 * 初始读取包含去除语义信息的模板文件
	 * 
	 * @param file
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void init(String file) throws IOException {
		patterns = new ArrayList<Pattern>();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "UTF-8"));
		String line = null;
		while ((line = in.readLine()) != null) {
			if (line.matches("^$") || line.startsWith("#"))
				continue;
			String toks = line.trim();
			Pattern patt = Pattern.compile(toks);
			patterns.add(patt);
		}
		in.close();
	}

	/**
	 * 全角转半角(此函数在CharUtils中已存在)
	 * 
	 * @param line
	 * @return line半角转换后的字符串
	 */
	public static String convertToHalfWidth(String line) {
		char[] c = line.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		String ret = new String(c);
		return ret.replace("*", "");
	}

	private static char[] NUM_BIG = { '一', '二', '三', '四', '五', '六', '七', '八',
			'九', '零' };
	private static char[] NUM_SMALL = { '1', '2', '3', '4', '5', '6', '7', '8',
			'9', '0' };

	public static String numConvert(String str) {
		for (int i = 0; i < NUM_SMALL.length; i++) {
			str = str.replace(NUM_SMALL[i], NUM_BIG[i]);
		}
		return str;
	}

	private static char[] SINGS = { '+', '-', '!', ':', '?', '*', '~', '^',
			'"', '\\', '(', ')', '[', ']', '{', '}', ' ' };

	/**
	 * 判断字符是否需要转换
	 * 
	 * @param c
	 * @return
	 */
	private static boolean needToConvert(char c) {
		for (int i = 0; i < SINGS.length; i++) {
			if (SINGS[i] == c)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param user_query
	 * @return
	 */
	public static String normalizat(String user_query) {
		String regex = " +";
		user_query = user_query.replaceAll(regex, " ").trim();
		char[] a = user_query.toCharArray();
		char[] b = new char[500];
		int i = 0;
		for (char c : a) {
			if (!needToConvert(c)) {
				b[i++] = c;
			}
		}
		String c = new String(b, 0, i);
		return c;
	}

	/**
	 * 
	 * @param user_query
	 * @return
	 */
	public static String preProcess(String user_query) {
		String regex = " +";
		user_query = user_query.replaceAll(regex, " ").trim();
		user_query = user_query.replace("\\*|\\;|\\[|\\]", "");
		char[] a = user_query.toCharArray();
		char[] b = new char[500];
		int i = 0;
		for (char c : a) {
			if (!needToConvert(c)) {
				b[i++] = c;
			} else {
				b[i++] = '\\';
				b[i++] = c;
			}
		}
		String c = new String(b, 0, i);
		return c;
	}

	/**
	 * 
	 * @param user_query
	 * @return
	 */
	public static String preProcess(String user_query, String special_char) {
		String regex = " +";
		user_query = user_query.replaceAll(regex, " ").trim();
		user_query = user_query.replace("\\*|\\;|\\[|\\]", "");
		char[] a = user_query.toCharArray();
		char[] b = new char[500];
		int i = 0;
		for (char c : a) {
			if (!needToConvert(c) || special_char.contains(String.valueOf(c))) {
				b[i++] = c;
			} else {
				b[i++] = '\\';
				b[i++] = c;
			}
		}
		String c = new String(b, 0, i);
		return c;
	}

	public static String process(String query) {
		if (null != query) {
			if (!query.startsWith("(") && !query.endsWith(")")
					&& query.contains(")")) {
				query = query.replaceAll("\\(|\\)|（|）", " ").trim();
			} else {
				query = query.replaceAll("\\(|\\)|（|）", " ").trim();
			}

			query = query.replaceAll("\"|'|“|”|‘|’|\\[|\\]|\\;|\\*", "");

		}
		return query;
	}

	/**
	 * 计算query中最后一个字符是右括号时，是否为多余字符
	 * 
	 * @param s
	 * @param c
	 * @return 字符串s中字符c的个数
	 */
	public static int counter(String s, char c) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 去除语义信息
	 * 
	 * @param query
	 * @return 返回去除语义信息后的query 注：在与模板中最后一个模板是用于去除query的最后一个不符合要求的字符的
	 *         对于最后一个字符是右括号并且前面没有与之匹配的左括号的情况做了单独处理
	 */
	public static String doDrop(String query) {
		for (int j = 0; j < patterns.size(); j++) {
			Pattern patt = patterns.get(j);
			Matcher mat = patt.matcher(query);
			if (mat.matches()) {
				query = mat.group(1);
			}
		}
		int leftParenthesesCount = counter(query, '(');
		int rightParenthesesCount = counter(query, ')');
		if (rightParenthesesCount == leftParenthesesCount + 1) {
			int length = query.length();
			if (query.charAt(length - 1) == ')')
				query = query.substring(0, length - 1);
		}
		return query;
	}

	/**
	 * 判断first_string数组中是否包含second_string
	 * 
	 * @param first_string
	 * @param second_string
	 * @return boolean
	 */
	public static boolean isHave(String[] first_string, String second_string) {
		for (int i = 0; i < first_string.length; i++) {
			if (first_string[i].equals(second_string)) {
				return true;
			}
		}
		return false;
	}
}
