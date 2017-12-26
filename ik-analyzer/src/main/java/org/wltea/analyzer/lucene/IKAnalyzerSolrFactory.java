package org.wltea.analyzer.lucene;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.dic.Dict;
import org.wltea.analyzer.dic.loader.FileDictLoader;
import org.wltea.analyzer.dic.loader.JdbcDictLoader;
import org.wltea.analyzer.util.LogUtil;

public class IKAnalyzerSolrFactory extends TokenizerFactory {

	private boolean useSmart = false;

	static {
		FileDictLoader fdl = new FileDictLoader();
		JdbcDictLoader jdl = new JdbcDictLoader();
		Dict.load(fdl, jdl);// 加载一次本地文件和数据库词库
		Dict.update(jdl);// 以后只更新数据库词库
	}

	public boolean useSmart() {
		return useSmart;
	}

	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}

	/**
	 * userSmart:是否使用智能分词;
	 * 
	 * @param map
	 */
	public IKAnalyzerSolrFactory(Map<String, String> map) {
		super(map);
		LogUtil.IK.info("IKAnalyzerSolrFactory参数：" + map);
		String userSmart = map.get("useSmart");
		if (userSmart != null && userSmart.equalsIgnoreCase("true")) {
			this.useSmart = true;
		}
		// Boolean update = DataTypeUtil.getBoolean(map.get("update"));
		// if (update == null || update) {
		// Long period = DataTypeUtil.getLong(map.get("updatePeriod"));// 加载延迟
		// Long delay = DataTypeUtil.getLong(map.get("updateDelay"));// 加载周期
		// Dict.update(period, delay);// 启动自动更新
		// }
	}

	@Override
	public Tokenizer create(AttributeFactory arg0, Reader reader) {
		Tokenizer tokenizer = new IKTokenizer(reader, this.useSmart);
		return tokenizer;
	}

}
