/*---------------------------------------------------------------------------*/
/**
 * @(#)$Id: StringMatch.java 7733 2013-07-10 11:32:47Z shiyongping $ 
 * @(#) Implementation file of class StringMatch.
 * (c)  PIONEER SUNTEC CORPORATION  2013
 * All Rights Reserved.
 */
/*---------------------------------------------------------------------------*/

package hhc.common.utils.stringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMatch {

	static Pattern road = Pattern.compile("(.+)(路|道|街|巷)");
	static Pattern namePattern = Pattern.compile("(?=《).*?(?=》)");

	public static boolean isRoadSuffix(String string) {
		boolean ret = false;
		if (string == null || string.trim().length() < 2) {
			return ret;
		}

		Matcher mat = road.matcher(string);
		ret = mat.find();
		return ret;
	}

	static Map<String, String> abb_map = new HashMap<String, String>();

	/**
	 * 初始化 需要写成文件
	 */
	public static void init() {
		abb_map.put("德庄火锅", "重庆德庄火锅");
		abb_map.put("移动营业厅", "移动通信");
		abb_map.put("联通营业厅", "中国联通");
		abb_map.put("电信营业厅", "中国电信");
	}

	/**
	 * Similarity字符串处理
	 * 
	 * @param target
	 * @param query
	 * @param queryList
	 * @return
	 */

	public static int[] similarity(String target, String query) {
		if (target == null || target.length() == 0)
			return null;
		int min = target.length() + query.length() + 100;
		int[] minsim = null;
		int len = 0;
		int[] sim = (new Scorer()).Similarity(target, query);
		if (sim == null)
			return sim;
		if (sim[0] < min || (sim[0] == min && (sim[2] - sim[1]) > len)) {
			min = sim[0];
			len = sim[2] - sim[1];
			minsim = sim;
		}
		return minsim;

	}

	/**
	 * 是否符合指定模式
	 * 
	 * @param pattern
	 * @param curPattern
	 * @return
	 */
	public static boolean findPattern(Pattern pattern, String curPattern) {
		if (null == pattern)
			return false;
		Matcher mat = pattern.matcher(curPattern);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 匹配出的字符是第一个字符串中的子串，如：输入为“上海大世界” “上海世界” 匹配得到“上海大世界”。验证
	 * 
	 * @param target
	 * @param retrivalResult
	 * @param type
	 * @throws Exception
	 */
	public static String removeCompareWords(String firstString, String secondString) {

		int[] sim = similarity(secondString, firstString);
		String tmp = abb_map.get(firstString);
		boolean eq = false;

		if (null != tmp) {
			eq = secondString.equals(tmp);
		}
		if (!eq && (sim == null || (sim[2] != firstString.length() && sim[1] != 0) || sim[2] - sim[1] < 2)) {
			return firstString;
		}
		return firstString.substring(0, sim[1]) + firstString.substring(sim[2], firstString.length());
	}

	/**
	 * 匹配出的字符是第一个字符串中的子串，如：输入为“上海大世界” “上海世界” 匹配得到“上海大世界”。验证
	 * 
	 * @param target
	 * @param retrivalResult
	 * @param type
	 * @throws Exception
	 */
	public static boolean isContain(String firstString, String secondString) {

		secondString = secondString.replace("\\", "").replace(" ", "");
		firstString = firstString.replace("\\", "");
		int[] sim = similarity(firstString, secondString);
		String tmp = abb_map.get(firstString);
		boolean eq = false;

		if (null != tmp) {
			eq = secondString.equals(tmp);
		}
		if (!eq && (sim == null || sim[2] - sim[1] != secondString.length())) {
			return false;
		}
		return true;
	}

	// public static void main(String[] arg) {
	// isContain("重庆德庄大火", "重庆德庄火");
	// }

	/**
	 * getComplateString
	 * 
	 * @param abb
	 * @return
	 */
	public static String getComplateString(String abb) {
		return abb_map.get(abb);
	}

	/**
	 * 判断Query是否以name为后缀
	 * 
	 * @param name
	 * @param Query
	 * @return
	 */
	public static int isSuffix(String name, String Query) {
		if ((Query != null) && (Query.endsWith(name))) {
			return 1;
		} else
			return -1;
	}

	public static ArrayList<String> getNumberList(String name) {
		ArrayList<String> result_num_list = new ArrayList<String>();
		if (name == null) {
			return result_num_list;
		}
		String a_temp = ProcessUtil.convertToHalfWidth(name);
		String a_temp_m = a_temp.replaceAll("[^0-9|１｜２｜３｜４｜５｜６｜７｜８｜９｜０｜零|一|二|三|四|五|六|七|八|九|十|百]+", "*");
		String[] number_list = a_temp_m.split("\\*");
		for (int i = 0; i < number_list.length; i++) {
			number_list[i] = toDigital(number_list[i]);
			if ((number_list[i] != null) && (number_list[i].length() > 0)
					&& (!result_num_list.contains(number_list[i]))) {
				result_num_list.add(number_list[i]);
			}
		}
		return result_num_list;
	}

	public static int compareNameNumber(String a_name, String b_name, ArrayList<String> q_num_list) {
		if (a_name == null || b_name == null || q_num_list == null) {
			return 0;
		}
		ArrayList<String> a_num_list = getNumberList(a_name);
		ArrayList<String> b_num_list = getNumberList(b_name);
		double a_score = getNumberTokenScore(a_num_list, q_num_list);
		double b_score = getNumberTokenScore(b_num_list, q_num_list);
		if (a_score > b_score) {
			return -1;
		} else if (a_score < b_score) {
			return 1;
		}
		return 0;
	}

	/**
	 * 衡量两个字符换中包含数字序列的相似程度
	 * 
	 * @param tokens_list1
	 * @param tokens_list2
	 * @return
	 */
	public static double getNumberTokenScore(ArrayList<String> tokens_list1, ArrayList<String> tokens_list2) {
		int list1_count = tokens_list1.size();
		int list2_count = tokens_list2.size();
		int inter_count = 0;
		double score = 0.0;
		if (list1_count == 0 || list2_count == 0) {
			return score;
		}
		for (int i = 0; i < list2_count; i++) {
			if (tokens_list1.contains(tokens_list2.get(i))) {
				inter_count++;
			}
		}
		if ((list1_count != 0) && (list2_count != 0)) {
			score = (double) (inter_count * 1.0) / (double) (list2_count);
		}
		return score;
	}

	/**
	 * 用于全角数字、中文数字到半角阿拉伯数字转换（限整型）
	 */
	public static String toDigital(String number_text) {
		String result = number_text;
		number_text = ProcessUtil.convertToHalfWidth(number_text);
		if (number_text.matches("^[0-9]+$")) {
			result = number_text;
		} else// 中文数字
		{
			if (number_text.matches("^[零|一|二|三|四|五|六|七|八|九|十|百]+$")) {
				String temp = "";
				if (number_text.startsWith("十")) {
					number_text = "一" + number_text.substring(1);
					if (number_text.length() == 1) {
						number_text = number_text + "零";
					}
				}
				if (number_text.endsWith("十")) {
					number_text = number_text.substring(0, number_text.length() - 1) + "零";
				}
				if (number_text.endsWith("百")) {
					number_text = number_text.substring(0, number_text.length() - 1) + "零零";
				}
				number_text = number_text.replace("十|百", "");
				char[] c = number_text.toCharArray();
				for (int i = 0; i < c.length; i++) {
					switch (c[i]) {
					case '零': {
						temp = temp + "0";
						break;
					}
					case '一': {
						temp = temp + "1";
						break;
					}
					case '二': {
						temp = temp + "2";
						break;
					}
					case '三': {
						temp = temp + "3";
						break;
					}
					case '四': {
						temp = temp + "4";
						break;
					}
					case '五': {
						temp = temp + "5";
						break;
					}
					case '六': {
						temp = temp + "6";
						break;
					}
					case '七': {
						temp = temp + "7";
						break;
					}
					case '八': {
						temp = temp + "8";
						break;
					}
					case '九': {
						temp = temp + "9";
						break;
					}
					default:
						break;
					}
				}
				result = temp;
			}
		}
		return result;
	}

	// 计算相似度
	public static int min(int one, int two, int three) {
		int min = one;
		if (two < min) {
			min = two;
		}
		if (three < min) {
			min = three;
		}
		return min;
	}

	// 计算编辑距离相似度
	public static int calculateEditDistance(String str1, String str2) {
		int d[][]; // 矩阵
		int n = str1.length();
		int m = str2.length();
		int i; // 遍历str1的
		int j; // 遍历str2的
		char ch1; // str1的
		char ch2; // str2的
		int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) { // 初始化第一列
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) { // 初始化第一行
			d[0][j] = j;
		}
		for (i = 1; i <= n; i++) { // 遍历str1
			ch1 = str1.charAt(i - 1);
			// 去匹配str2
			for (j = 1; j <= m; j++) {
				ch2 = str2.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}
				// 左边+1,上边+1, 左上角+temp取最小
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
			}
		}
		return d[n][m];
	}

	public static double calculateSimilarity(String str1, String str2) {
		// StringCompare c = new StringCompare();

		String max = StringCompare.getMaxLengthCommonString(str1, str2);
		// int ld = calculateEditDistance(str1, str2);
		// System.out.println(ld+" "+str1.length());
		return (double) max.length() / (0.8 * str1.length() + 0.2 * str2.length());
	}

	public static void main(String[] arg) {
		// int[] sim = similarity("上海shh", "AAA上海ssa");
		// System.out.println("AAA上海ssa".substring(sim[1],sim[2]));
		listQuoteName("《万达广场1》123《万达广场》123123《万达广场2》");
		System.out.println(calculateSimilarity("万达", "万达广场"));
		System.out.println(calculateSimilarity("万达广", "万达广场"));
		System.out.println(calculateSimilarity("万达山", "万达广"));
		System.out.println(calculateSimilarity("万达广场", "万达"));
	}

	/**
	 * public static void main(String[] arg) { String ret = ""; int[] sim =
	 * similarity("上海shh", "AAA上海ssa"); System.out.println(ret); }
	 */
	public static boolean isChinese(String str) {
		String china = "[\u4e00-\u9fa5]";// 汉字的字符集
		Pattern pattern = Pattern.compile(china);
		Matcher matcher = pattern.matcher(str);
		// 是否需要编码
		if (matcher.find()) { // 含有汉字时为ture
			return true;
		}
		return false;
	}

	public static boolean isContainNum(String str) {
		String china = ".*\\d+.*";
		Pattern pattern = Pattern.compile(china);
		Matcher matcher = pattern.matcher(str);
		// 是否需要编码
		if (matcher.find()) { // 含有汉字时为ture
			return true;
		}
		return false;
	}

	public static class QuoteName {
		public String name;
		public int count;
	}

	public static List<QuoteName> listQuoteName(String text) {
		Map<String, Integer> ret = new HashMap<String, Integer>();
		Matcher matcher = namePattern.matcher(text);
		while (matcher.find()) {
			// group(0)或group()将会返回整个匹配的字符串（完全匹配）；group(i)则会返回与分组i匹配的字符
			// 这个例子只有一个分组
			String str = matcher.group(0).replace("《", "");
			if(str.length()>20){
				continue;
			}
			Integer count = ret.get(str);
			if (count == null) {
				count = 1;
			} else {
				count++;
			}
			ret.put(str, count);

		}
		List<QuoteName> returns = new ArrayList<QuoteName>();
		for (String key : ret.keySet()) {
			QuoteName name = new QuoteName();
			name.name = key;
			name.count = ret.get(key);
			returns.add(name);
		}
		// matcher.
		// MatchResult result = matcher.toMatchResult();
		return returns;
		// result.wait();

	}
}
