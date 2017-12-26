package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;
import org.wltea.analyzer.lucene.synonym.SynonymDict;
import org.wltea.analyzer.lucene.synonym.SynonymFilter;
import org.wltea.analyzer.lucene.synonym.loader.JdbcSynonymDictLoader;
import org.wltea.analyzer.lucene.synonym.loader.SynonymDictLoader;
import org.wltea.analyzer.util.LogUtil;

/**
 * 
 * 每个index启动会单独创建
 * 
 * @author linshouyi
 *
 */
public class SynonymsTokenFilterFactory extends AbstractTokenFilterFactory {

	private static SynonymDict dict;

	static {
		dict = new SynonymDict();// 创建同义词库
		SynonymDictLoader loader = new JdbcSynonymDictLoader("7");// 创建jdbc词库加载器
		// SynonymDictLoader loader = new FileSynonymDictLoader();// 创建文本词库加载器
		dict.load(loader);
		dict.update(loader);// 定时更新词库
	}

	@Inject
	public SynonymsTokenFilterFactory(Index index, @IndexSettings Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings, name, settings);
		LogUtil.IK.info("==创建" + this);
		// SynonymDictLoader loader = null;
		// String format = settings.get("format");
		// if ("jdbc".equalsIgnoreCase(format)) {
		// String groupIds = settings.get("groupIds");
		// loader = new JdbcSynonymDictLoader(groupIds);// 创建jdbc词库加载器
		// } else if ("file".equalsIgnoreCase(format)) {
		// loader = new FileSynonymDictLoader(settings.get("file"));
		// }
		// Boolean ignoreCase = settings.getAsBoolean("ignoreCase", null);
		// dict = new SynonymDict(ignoreCase);// 创建同义词库
		// dict.load(loader);
		//
		// Boolean update = settings.getAsBoolean("update", null);
		// if (update == null || update) {
		// Long updateDelay = settings.getAsLong("updateDelay", null);
		// Long updatePeriod = settings.getAsLong("updatePeriod", null);
		// dict.update(updateDelay, updatePeriod, loader);// 定时更新词库
		// }
	}

	@Override
	public TokenStream create(TokenStream tokenStream) {
		tokenStream = new SynonymFilter(tokenStream, dict);
		LogUtil.IK.info("==" + this + "创建" + tokenStream);
		return tokenStream;
	}

}
