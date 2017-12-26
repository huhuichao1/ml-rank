package org.wltea.analyzer.lucene.synonym;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.wltea.analyzer.lucene.synonym.loader.JdbcSynonymDictLoader;
import org.wltea.analyzer.lucene.synonym.loader.SynonymDictLoader;

public class SynonymFilterFactory extends TokenFilterFactory {

	private static SynonymDict dict;
	static {
		dict = new SynonymDict();// 创建同义词库
		SynonymDictLoader loader = new JdbcSynonymDictLoader("7");// 创建jdbc词库加载器
		// SynonymDictLoader loader = new FileSynonymDictLoader();// 创建文本词库加载器
		dict.load(loader);
		dict.update(loader);// 定时更新词库
	}

	/**
	 * @param map
	 *            format:词库格式: jdbc为JdbcSynonym词库,groupIds:组id，默认为空 eg:7,8
	 * 
	 *            ignoreCase: 是否忽略大小写，默认true
	 * 
	 *            update:是否更新词库，默认true
	 * 
	 *            updateDelay:更新延迟时间，单位毫秒，默认300000
	 * 
	 *            updatePeriod:更新周期，单位毫秒，默认300000
	 * 
	 */
	public SynonymFilterFactory(Map<String, String> map) {
		super(map);
		// LogUtil.IK.info("SynonymFilterFactory参数：" + map);
		// SynonymDictLoader loader = null;
		// String format = map.get("format");
		// if ("jdbc".equalsIgnoreCase(format)) {
		// String groupIds = map.get("groupIds");
		// loader = new JdbcSynonymDictLoader(groupIds);// 创建jdbc词库加载器
		// } else if ("file".equalsIgnoreCase(format)) {
		// String file = map.get("file");
		// loader = new FileSynonymDictLoader(file);
		// }
		// Boolean ignoreCase = DataTypeUtil.getBoolean(map.get("ignoreCase"));
		// dict = new SynonymDict(ignoreCase);// 创建同义词库
		// dict.load(loader);
		// Boolean update = DataTypeUtil.getBoolean(map.get("update"));
		// if (update == null || update) {
		// Long updateDelay = DataTypeUtil.getLong(map.get("updateDelay"));
		// Long updatePeriod = DataTypeUtil.getLong(map.get("updatePeriod"));
		// dict.update(updateDelay, updatePeriod, loader);// 定时更新词库
		// }
	}

	@Override
	public TokenStream create(TokenStream input) {
		return new SynonymFilter(input, dict);
	}
}
