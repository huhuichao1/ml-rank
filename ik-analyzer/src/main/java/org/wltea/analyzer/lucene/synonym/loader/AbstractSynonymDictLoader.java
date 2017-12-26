package org.wltea.analyzer.lucene.synonym.loader;

import org.wltea.analyzer.lucene.synonym.SynonymDict;
import org.wltea.analyzer.util.StringUtil;

public abstract class AbstractSynonymDictLoader implements SynonymDictLoader {

	private String splitPattern = "[\\s　]*[,;，；]+[\\s　]*";// 同义词分隔正则

	protected int load(SynonymDict dict, String source, String target, boolean enable) {
		return load(dict, source, splitPattern, target, splitPattern, enable);
	}

	protected int load(SynonymDict dict, String source, String sourceSplitPattern, String target, String targetSplitPattern, boolean enable) {
		if (StringUtil.isEmpty(source)) {
			return 0;
		}
		String[] sources = StringUtil.split(source, sourceSplitPattern);
		if (sources == null || sources.length == 0) {
			return 0;
		}
		String[] targets = StringUtil.split(target, targetSplitPattern);
		if (targets == null || targets.length == 0) {
			targets = sources;
		}
		return load(dict, sources, targets, enable);
	}

	protected int load(SynonymDict dict, String[] sources, String[] targets, boolean enable) {
		if (sources == null || sources.length == 0 || targets == null || targets.length == 0) {
			return 0;
		}
		int total = 0;
		for (String input : sources) {
			for (String output : targets) {
				if (StringUtil.isEmpty(input, output) || input.equals(output)) {
					continue;
				}
				if (enable) {
					if (dict.add(input, output)) {
						total++;
					}
				} else {
					if (dict.remove(input, output)) {
						total++;
					}
				}
			}
		}
		return total;
	}
}
