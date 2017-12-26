/*---------------------------------------------------------------------------*/
/**
 * @(#)$Id: AbstractScorer.java 7720 2013-07-10 02:46:00Z shiyongping $ 
 * @(#) Implementation file of class AbstractScorer.
 * (c)  PIONEER SUNTEC CORPORATION  2013
 * All Rights Reserved.
 */
/*---------------------------------------------------------------------------*/

package hhc.common.utils.stringUtils;


/**
 * 打分器抽象类，子类必须实现isCharEqual方法 进行字符串打分以及各种字符串信息获得
 * 
 * @author Ling Cao
 * 
 */
public abstract class AbstractScorer {

	private static final long serialVersionUID = 2986174831352822621L;

	private final double unigram = 0.4;
	private final double bigram = 0.6;

	// 可替换字列表
	protected final String[] substituteList = { "路街", "村镇", "aA", "bB", "cC",
			"dD", "eE", "fF", "gG", "hH", "iI", "jJ", "kK", "lL", "mM", "nN",
			"oO", "pP", "qQ", "rR", "sS", "tT", "uU", "vV", "wW", "xX", "yY",
			"zZ" };

	/**
	 * 获得两个字符串的ngram打分
	 * 
	 * @param gram1
	 *            字符串1的ngram
	 * @param gram2
	 *            字符串2的ngram
	 * @return
	 */
	public double getNGramScore(String[] gram1, String[] gram2) {
		double[] num = { 0.0, 0.0 };

		if (gram1 == null || gram2 == null)
			return 0.0;

		for (int i = 0; i < gram1.length; i++)
			for (int j = 0; j < gram2.length; j++)
				if (isStringEqual(gram1[i], gram2[j])) {
					num[gram1[i].length() - 1] = num[gram1[i].length() - 1] + 1;
					// break;
				}

		double length1 = stringLength(gram1.length);
		double length2 = stringLength(gram2.length);

		double cscore = 0.0;

		if (num[0] != 0)
			cscore += unigram * num[0] / Math.sqrt(length1 * length2);
		if (num[1] != 0)
			cscore += bigram * num[1]
					/ Math.sqrt((length1 - 1) * (length2 - 1));

		double modify = length1 / length2;
		if (modify > 1)
			modify = 1 / modify;
		double sqrt = Math.cbrt((length1 + length2) / 2);
		modify = 1 - Math.pow(1 - modify, sqrt);

		cscore *= modify;

		if (cscore > 0.99)
			cscore = 1;

		return cscore;
	}

	/**
	 * 将字符串转化为ngram形式
	 * 
	 * @param s
	 * @return
	 */
//	public String[] toNGram(String s) {
//		if (s == null || s.length() == 0) {
//			return null;
//		}
//
//		int size = 2 * s.length() - 1;
//
//		String[] ngram = new String[size];
//
//		for (int i = 0; i < ngram.length; i++)
//			ngram[i] = null;
//
//		try {
//			StringReader sReader = new StringReader(s);
//
//			TokenStream result = new NGramTokenizer(sReader, 1, 2);
//			CharTermAttribute termAtt = (CharTermAttribute) result
//					.getAttribute(CharTermAttribute.class);
//			while (result.incrementToken()) {
//				String token = new String(termAtt.buffer(), 0, termAtt.length());
//				int index;
//				for (index = 0; index < ngram.length; index++)
//					if (ngram[index] == null)
//						break;
//				assert (index != ngram.length);
//				ngram[index] = token;
//
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return ngram;
//	}

	public double getHZPinYinNGramScore(String[] gram1, String[] gram2) {
		double[] num = { 0.0 };

		if (gram1 == null || gram2 == null)
			return 0.0;

		for (int i = 0; i < gram2.length; i++)
			for (int j = 0; j < gram1.length; j++)
				if ((gram1[j] != null) && (gram2[i] != null)) {
					if (isStringEqual(gram1[j], gram2[i])) {
						num[0] = num[0] + 1;
						break;
					}
				}
		double length1 = gram1.length;
		double length2 = gram2.length;

		double cscore = 0.0;

		if (num[0] != 0)
			cscore += num[0] / Math.sqrt(length1 * length2);

		double modify = length1 / length2;
		if (modify > 1)
			modify = 1 / modify;
		double sqrt = Math.cbrt((length1 + length2) / 2);
		modify = 1 - Math.pow(1 - modify, sqrt);

		cscore *= modify;

		if (cscore > 0.99)
			cscore = 1;

		return cscore;
	}

	/**
	 * 根据边际距离方法在target中找到与query最大相似匹配的子串
	 * 
	 * @param target
	 * @param query
	 * @return int[3] 返回长度为3的int型数组 int[0] 边际距离打分 int[1] target中最大匹配子串的起始位置
	 *         int[2] target中最大匹配子串的终止位置+1
	 */
	public int[] Similarity(String target, String query) {
		if (target == null || query == null || target.length() == 0
				|| query.length() == 0)
			return null;
		int[] result = new int[3];
		int begin = 0;
		;
		int end = query.length();
		int min = target.length() + query.length() + 100;
		int len = 0;
		// 找出最大的匹配字符串的其实位置和长度
		for (int i = 0; i < query.length(); i++) {
			// 分别用query中子串与之匹配
			int[][] D = Distance(target, query.substring(i));
			for (int j = i; j < query.length(); j++) {
				// 如果距离最小说明找到了最优匹配度的结尾，如果距离相同说明这个第j个字符也能匹配上
				if (D[target.length() - 1][j - i] < min
						|| (D[target.length() - 1][j - i] == min && (j - i + 1) > len)) {
					min = D[target.length() - 1][j - i];
					len = (j - i + 1);
					begin = i;
					end = j + 1;
				}
			}
		}
		// 计算目标在query的始末位置
		char[] dbchar = target.toCharArray();
		while (begin < query.length() && begin >= 0) {
			boolean isBreak = false;
			char c = query.charAt(begin);
			for (int i = 0; i < dbchar.length; i++) {
				boolean flag = (isCharEqual(dbchar[i], c));
				if (flag)
					isBreak = true;
				if (isBreak)
					break;
			}
			if (isBreak)
				break;
			begin++;
		}

		while (end > 0 && end <= query.length()) {
			boolean isBreak = false;
			char c = query.charAt(end - 1);
			for (int i = 0; i < dbchar.length; i++) {
				boolean flag = (isCharEqual(dbchar[i], c));
				if (flag)
					isBreak = true;
				if (isBreak)
					break;
			}
			if (isBreak)
				break;
			end--;
		}

		if (begin >= end) {
			return null;
		}
		// 计算query中起始位置的前一个字符与结果相关且query的起始位置的字符和结果无关需要将begin--
		while (begin > 0 && begin < query.length()) {
			boolean isBreak = false;
			// 验证前一个字符是否相关
			char c = query.charAt(begin - 1);
			int i;
			for (i = 0; i < dbchar.length; i++) {
				boolean flag = (isCharEqual(dbchar[i], c));
				if (flag)
					break;
			}

			if (i == dbchar.length)
				break;

			char[] qchar = query.substring(begin, end).toCharArray();
			for (i = 0; i < qchar.length; i++) {
				boolean flag = (isCharEqual(qchar[i], c));
				if (flag)
					isBreak = true;
				if (isBreak)
					break;
			}
			if (isBreak)
				break;

			begin--;
		}
		// 计算query中结束位置的后一个字符与结果相关且query的结束位置的字符和结果无关需要将end--
		while (end > 0 && end < query.length()) {
			boolean isBreak = false;
			char c = query.charAt(end);
			int i;
			for (i = 0; i < dbchar.length; i++) {
				boolean flag = (isCharEqual(dbchar[i], c));
				if (flag)
					break;
			}
			if (i == dbchar.length)
				break;

			char[] qchar = query.substring(begin, end).toCharArray();
			for (i = 0; i < qchar.length; i++) {
				boolean flag = (isCharEqual(qchar[i], c));
				if (flag)
					isBreak = true;
				if (isBreak)
					break;
			}
			if (isBreak)
				break;
			end++;
		}

		if (begin >= end) {
			return null;
		}

		result[1] = begin;
		result[2] = end;
		result[0] = min;

		return result;
	}

	/**
	 * 判断两个字符是否相等的方法，该方法由子类提供
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public abstract boolean isCharEqual(char c1, char c2);

	/**
	 * 根据isCharEqual方法判断两字符串是否相同
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public boolean isStringEqual(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() != s2.length())
			return false;

		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();

		boolean flag = true;
		for (int i = 0; i < c1.length; i++)
			flag = flag && isCharEqual(c1[i], c2[i]);

		return flag;
	}

	/**
	 * 根据isCharEqual方法实现String的indexOf方法
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public int indexOf(String s1, String s2) {
		if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0
				|| s1.length() > s2.length())
			return -1;
		int begin = 0;
		int end = s1.length();
		while (end <= s2.length()) {
			if (isStringEqual(s1, s2.substring(begin, end)))
				return begin;
			begin++;
			end++;
		}
		return -1;
	}

	/**
	 * 根据isCharEqual方法判断字符串s是否包含字符c
	 * 
	 * @param c
	 * @param s
	 * @return
	 */
	public boolean isContain(char c, String s) {
		int i;
		if (s == null || s.length() == 0)
			return false;
		char[] sc = s.toCharArray();

		for (i = 0; i < sc.length; i++)
			if (isCharEqual(c, sc[i]))
				break;
		if (i == sc.length)
			return false;
		else
			return true;

	}

	/**
	 * 根据isCharEqual方法判断字符串s2是否包含s1
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public boolean isContain(String s1, String s2) {
		if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0
				|| s1.length() > s2.length())
			return false;
		int begin = 0;
		int end = s1.length();
		while (end <= s2.length()) {
			if (isStringEqual(s1, s2.substring(begin, end)))
				return true;
			begin++;
			end++;
		}
		return false;
	}

	private int min(int x, int y, int z) {
		x = x < y ? x : y;
		x = x < z ? x : z;
		return x;
	}

	public int[][] Distance(String word, String word2) {

		char[] p = word.toCharArray();
		char[] t = word2.toCharArray();

		int[][] D = new int[p.length][t.length];

		for (int i = 0, k = 1; i < p.length; i++) {
			boolean flag = isCharEqual(t[0], p[i]);
			if (flag)
				k = 0;
			D[i][0] = i + k;
		}
		for (int j = 0, k = 1; j < t.length; j++) {
			boolean flag = isCharEqual(t[j], p[0]);
			if (flag)
				k = 0;
			D[0][j] = j + k;
		}
		for (int i = 1; i < p.length; i++)
			for (int j = 1; j < t.length; j++) {
				boolean flag = isCharEqual(t[j], p[i]);
				int x = D[i - 1][j - 1] + (flag ? 0 : 1);
				int y = D[i - 1][j] + 1;
				int z = D[i][j - 1] + 1;
				D[i][j] = min(x, y, z);
			}

		return D;
	}

	private double stringLength(int gramLength) {
		return (gramLength + 1) / 2;
	}

}
