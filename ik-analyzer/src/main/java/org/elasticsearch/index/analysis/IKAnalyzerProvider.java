package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.util.LogUtil;

/**
 * 
 * 
 * @author linshouyi
 *
 */
public class IKAnalyzerProvider extends AbstractIndexAnalyzerProvider<IKAnalyzer> {

	private final IKAnalyzer analyzer;

	@Inject
	public IKAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings, name, settings);
		String useSmart = settings.get("useSmart");
		if ("true".equals(useSmart)) {
			analyzer = new IKAnalyzer(true);
		} else {
			analyzer = new IKAnalyzer();
		}
		LogUtil.IK.info("创建分词器：" + analyzer + ",useSmar=" + useSmart);
	}

	@Override
	public IKAnalyzer get() {
		return this.analyzer;
	}

}
