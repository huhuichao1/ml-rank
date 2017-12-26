package org.wltea.analyzer.util;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dict;
import org.wltea.analyzer.dic.loader.FileDictLoader;

public class TokenizerUtils {

	public static void init() {
		TokenizerUtils.getIKTokenizer(true);
	}

	public static abstract class MyTokenizer {
		public abstract List<String> analyzer(String str);
	}

	

	public static class MyIkTokenizer extends MyTokenizer {
		static MyIkTokenizer tokener;
		static String pathMainDic;
		static String pathMainQutify;

		public static MyIkTokenizer getInstance(boolean smart) {
			if (tokener == null) {
				tokener = new MyIkTokenizer();
			}
			tokener.smart = smart;
			return tokener;
		}

		IKSegmenter iks;
		IKSegmenter smartIks;
		boolean smart;

		private MyIkTokenizer() {
			// this.smart = smart;
			FileDictLoader fdl = new FileDictLoader();
			Dict.load(fdl);// 加载本地文件词库
			this.iks = new IKSegmenter(new StringReader(""), false);
			this.smartIks = new IKSegmenter(new StringReader(""), true);
		}

		@Override
		public List<String> analyzer(String str) {
			List<String> ret = new ArrayList<String>();
			StringReader re = new StringReader(str);
			// Configuration conf = new Configuration();
			// conf.
			// 独立Lucene实现
			// DefaultConfig.getInstance();
			IKSegmenter ikseg = this.iks;
			if (smart) {
				ikseg = this.smartIks;
			}

			ikseg.reset(re);
			Lexeme lexeme = null;
			Map<String, Integer> words = new HashMap<String, Integer>();
			try {
				while ((lexeme = ikseg.next()) != null) {
					if (words.containsKey(lexeme.getLexemeText())) {
						words.put(lexeme.getLexemeText(), words.get(lexeme.getLexemeText()) + 1);
					} else {
						words.put(lexeme.getLexemeText(), 1);
					}
					ret.add(lexeme.getLexemeText());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// return words;

			// Lexeme lex = null;
			// try {
			// while ((lex = ik.next()) != null) {
			// System.out.print(lex.getLexemeText() + "-");
			// ret.add(lex.getLexemeText());
			// }
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			return ret;
		}

	}


	public static MyTokenizer getIKTokenizer(boolean smart) {
		return MyIkTokenizer.getInstance(smart);
	}

	public static String listToString(List<String> list){
		StringBuilder sb=new StringBuilder();
		for(String s : list){
			sb.append(s);
			sb.append(" ");
		}
		return sb.toString();
	}
	public static void main(String[] str) {
		List<String> list =new ArrayList();;

		System.out.print("\n");
		list.clear();
		list = TokenizerUtils.getIKTokenizer(true).analyzer("巴黎春天一二八纪念路店");
		for (String s : list) {
			System.out.print(s + "\t");
		}
		System.out.print("\n");
		list.clear();
		list = TokenizerUtils.getIKTokenizer(false).analyzer("浦电路巴黎春天");
		for (String s : list) {
			System.out.print(s + "\t");
		}

		list.clear();
		long start = System.currentTimeMillis();
		list = TokenizerUtils.getIKTokenizer(true).analyzer("延边");
		System.out.println(System.currentTimeMillis() - start);
		for (String s : list) {
			System.out.print(s + "\t");
		}
		// IkTokenizerFactory.getIkSmartTokenizerFactory(indexSettings, env,
		// name, settings);
	}

}
