package org.wltea.analyzer.lucene.synonym.loader;

import org.wltea.analyzer.lucene.synonym.SynonymDict;

public interface SynonymDictLoader {

	/**
	 * 加载同义词
	 * 
	 * @return
	 * @throws Exception
	 */
	public void load(SynonymDict dict) throws Exception;

}
