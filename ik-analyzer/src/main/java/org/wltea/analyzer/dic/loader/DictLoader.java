package org.wltea.analyzer.dic.loader;

import java.util.Map;

import org.wltea.analyzer.dic.DictWord;

/**
 * 词典加载器接口
 * 
 * @author linshouyi
 *
 */
public interface DictLoader {

	/**
	 * 加载主词词典
	 * 
	 * @return
	 */
	public DictWord[] loadMainDict() throws Exception;

	/**
	 * 加载停止词词典
	 * 
	 * @return
	 */
	public DictWord[] loadStopwordDict() throws Exception;

	/**
	 * 加载量词词典
	 * 
	 * @return
	 */
	public DictWord[] loadQuantifierDict() throws Exception;

	/**
	 * 加载映射词典（简繁体，全半角等）
	 * 
	 * @return
	 */
	public Map<Character, Character> loadMappingDict() throws Exception;

}
