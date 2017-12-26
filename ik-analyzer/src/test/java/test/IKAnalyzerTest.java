package test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;
import org.wltea.analyzer.dic.Dict;
import org.wltea.analyzer.dic.loader.FileDictLoader;
import org.wltea.analyzer.dic.loader.JdbcDictLoader;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKTokenizer;
import org.wltea.analyzer.lucene.synonym.SynonymDict;
import org.wltea.analyzer.lucene.synonym.SynonymFilter;
import org.wltea.analyzer.lucene.synonym.loader.JdbcSynonymDictLoader;
import org.wltea.analyzer.lucene.synonym.loader.SynonymDictLoader;

public class IKAnalyzerTest {

	static {
		FileDictLoader fdl = new FileDictLoader();
		// Dict.load(fdl);// 加载本地文件词库
		JdbcDictLoader jdl = new JdbcDictLoader();
		Dict.load(jdl);// 加载数据库词库
		// Dict.update(jdl);// 更新数据库词库
	}

	public static TokenStream getIKTokenizer(String input, boolean useSmart) {
		return new IKTokenizer(new StringReader(input), useSmart);
	}

	public static TokenStream getSynonymFilter(TokenStream input) {
		SynonymDict dict = new SynonymDict();// 创建同义词库
		SynonymDictLoader loader = new JdbcSynonymDictLoader("7");// 创建jdbc词库加载器
		// SynonymDictLoader loader = new FileSynonymDictLoader();// 创建文本词库加载器
		dict.load(loader);
		dict.update(loader);// 定时更新词库
		return new SynonymFilter(input, dict);// 同义词过滤器
	}

	public static void tokenStream(TokenStream ts) {
		try {
			OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			TypeAttribute type = ts.addAttribute(TypeAttribute.class);
			PositionIncrementAttribute position = ts.addAttribute(PositionIncrementAttribute.class);
			ts.reset();
			while (ts.incrementToken()) {
				System.out.println(position.getPositionIncrement() + " " + offset.startOffset() + "-" + offset.endOffset() + ":" + term.toString() + " | " + type.type());
			}
			ts.end();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ts != null) {
				try {
					ts.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * x,y=>z,p(x=>z,p,y=>z,p)
	 * 
	 * x,y,z(x=>x,y,z,y=>x,y,z,z=>x,y,z)
	 * 
	 * @param analyzer
	 * @param text
	 */
	public static void synonym(String input) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("synonyms", "a.txt");
		SynonymFilterFactory factory = new SynonymFilterFactory(map);
		try {
			factory.inform(new FilesystemResourceLoader());
			TokenStream ts = factory.create(getIKTokenizer(input, true));
			tokenStream(ts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 同义词测试
	 * 
	 * expand 只对=>有效
	 * 
	 * false,只产生第1个同义词x,y,z(x=>x,y,z;y=>x,y,z;z=>x,y,z)
	 * 
	 * true,产生所有同义词x,y,z(x=>x;y=>x;z=>x)
	 */
	public static void testSynonym(String input) {
		// test.analyze(analzyer, input);
		try {
			// SolrSynonymParser parser = new SolrSynonymParser(true, true, ik);
			// parser.parse(new FileReader(new File("a.txt")));
			// WordnetSynonymParser parser = new WordnetSynonymParser(true,true,standard);
			// SynonymMap.Builder parser = new SynonymMap.Builder(true);
			// parser.add(new CharsRef("中国"), new CharsRef("china"), true);
			// parser.add(new CharsRef("中国"), new CharsRef("chinese"), true);
			// parser.add(new CharsRef("国人"), new CharsRef("guoren"), true);
			// parser.add(new CharsRef("日本"), new CharsRef("japan"), false);
			// parser.add(new CharsRef("mobile"), new CharsRef("移动"), true);
			// SynonymMap map = parser.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String input = "中國人快乐";
		// TokenStream ik = getIKTokenizer(input, false);
		try {
			tokenStream(getSynonymFilter(new IKAnalyzer(false).tokenStream("", input)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// test.synonym(input);
		// test.testSynonym(input);
		// test.analyze(input);
		// try {
		// Thread.sleep(2000000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

}
