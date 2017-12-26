package org.wltea.analyzer.util;


public class CharacterUtil {

	/**
	 * 全角转半角
	 * 
	 * 1.全角空格为12288，半角空格为32 2.其他字符半角(33-126)与全角(65281-65374)的对应关系是：相差65248
	 * 
	 * @param c
	 * @return
	 */
	public static char full2half(char c) {
		if (c == 12288) {// 全角空格
			c = 32;
		} else if (c > 65280 && c < 65375) {// 其他全角字符
			c = (char) (c - 65248);
		}
		return c;
	}

	public static void main(String[] args) {
		char c = '　';
		LogUtil.IK.info((int) c);
		LogUtil.IK.info((int) full2half(c));
	}

}
