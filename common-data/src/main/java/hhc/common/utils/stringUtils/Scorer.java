/*---------------------------------------------------------------------------*/
/**
 * @(#)$Id: Scorer.java 7720 2013-07-10 02:46:00Z shiyongping $ 
 * @(#) Implementation file of class Scorer.
 * (c)  PIONEER SUNTEC CORPORATION  2013
 * All Rights Reserved.
 */
/*---------------------------------------------------------------------------*/

package hhc.common.utils.stringUtils;

import java.io.Serializable;

/**
 * 打分器。继承父类AbstractScorer并实现isCharEqual方法
 * 
 * @author Ling Cao
 * 
 */
public class Scorer extends AbstractScorer implements Serializable {
	public boolean isCharEqual(char c1, char c2) {
		boolean flag = c1 == c2;
		for (int m = 0; m < substituteList.length; m++)
			flag = flag
					|| (substituteList[m].indexOf(c1) != -1 && substituteList[m]
							.indexOf(c2) != -1);
		return flag;
	}

	public String getSimilarity(String target, String query) {
		int[] result = new int[3];
		result = Similarity(target, query);
		if (result[1] < 0 || result[2] < result[1]) {
			return "";
		} else {
			return query.substring(result[1], result[2]);
		}
	}

	public static void main(String[] args) {
		Scorer scorer = new Scorer();
		int[][] test = scorer.Distance("南京市", "南北京市");
		int[] testmost = scorer.Similarity("南京市", "南北必南南通市");
		System.out.println(test);
		/*
		 * System.out.println(scorer.isPinyinEqual('百','佰')); String s1 =
		 * "上海酒店"; String s2 = "夜上海大酒店中山门店";
		 * 
		 * int[] sim = scorer.Similarity(s1, s2); double ngram =
		 * scorer.getNGramScore(scorer.toNGram(s1), scorer.toNGram(s2));
		 * System.out.println(sim[0] + " " + sim[1] + " " + sim[2] + "\n" +
		 * ngram);
		 */
	}

}
