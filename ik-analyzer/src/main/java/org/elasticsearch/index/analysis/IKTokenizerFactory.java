package org.elasticsearch.index.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;
import org.wltea.analyzer.dic.Dict;
import org.wltea.analyzer.dic.loader.FileDictLoader;
import org.wltea.analyzer.dic.loader.JdbcDictLoader;
import org.wltea.analyzer.lucene.IKTokenizer;
import org.wltea.analyzer.util.LogUtil;

/**
 * 
 * @author linshouyi
 *
 */
public class IKTokenizerFactory extends AbstractTokenizerFactory {

	static {
		FileDictLoader fdl = new FileDictLoader();
		Dict.load(fdl);// 加载本地文件词库
		JdbcDictLoader jdl = new JdbcDictLoader();
		Dict.load(jdl);// 加载数据库词库
		Dict.update(jdl);// 更新数据库词库
	}

	private boolean useSmart;

	@Inject
	public IKTokenizerFactory(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings, name, settings);
		String useSmart = settings.get("useSmart");
		if ("true".equals(useSmart)) {
			this.useSmart = true;
		} else {
			this.useSmart = false;
		}
		LogUtil.IK.info("==创建" + this + "," + useSmart);
	}

	/**
	 * 每次创建一个新实例
	 */
	@Override
	public Tokenizer create(Reader reader) {
		Tokenizer tokenizer = new IKTokenizer(reader, useSmart);
		LogUtil.IK.info("==" + this + "创建：" + tokenizer + "," + useSmart);
		return tokenizer;
	}

}
