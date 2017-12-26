package org.wltea.analyzer.dic;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.wltea.analyzer.dic.loader.DictLoader;
import org.wltea.analyzer.util.LogUtil;
import org.wltea.analyzer.util.StringUtil;

/**
 * 词典
 * 
 * @author linshouyi
 *
 */
public class Dict {

	private static DictSegment mainDict = new DictSegment((char) 0);// 主词典
	private static DictSegment stopwordDict = new DictSegment((char) 0);// 停止词典
	private static DictSegment quantifierDict = new DictSegment((char) 0);// 量词词典
	private static Map<Character, Character> mappingDict = new ConcurrentHashMap<Character, Character>();// 映射词典

	// 不允许实例化
	private Dict() {
	}

	public static void load(DictLoader... loaders) {
		if (loaders == null || loaders.length == 0) {
			return;
		}
		for (DictLoader loader : loaders) {
			try {
				long start = System.currentTimeMillis();
				LogUtil.IK.info("===" + loader + "开始加载词典...");
				LogUtil.IK.info("==映射词：");
				addMappingDict(loader.loadMappingDict());
				LogUtil.IK.info("==主词：");
				addDict(mainDict, loader.loadMainDict());
				LogUtil.IK.info("==停止词：");
				addDict(stopwordDict, loader.loadStopwordDict());
				LogUtil.IK.info("==量词：");
				addDict(quantifierDict, loader.loadQuantifierDict());
				LogUtil.IK.info("===" + loader + "词典加载完毕，耗时(ms)：" + (System.currentTimeMillis() - start));
			} catch (Exception e) {
				LogUtil.IK.error("", e);
			}
		}
	}

	/**
	 * 更新词典
	 */
	public static void update(DictLoader... loaders) {
		update(null, null, loaders);
	}

	/**
	 * 更新词典
	 * 
	 * @param delay
	 *            延迟时间，单位毫秒，默认300000
	 * @param period
	 *            更新周期，单位毫秒，默认300000
	 */
	public static void update(Long delay, Long period, final DictLoader... loaders) {
		if (loaders == null || loaders.length == 0) {
			return;
		}
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					load(loaders);
				} catch (Exception e) {
					LogUtil.IK.error("", e);
				}
			}
		}, delay != null ? delay : 300000, period != null ? period : 300000);// 定时加载
	}

	/**
	 * 添加映射词，value=null无效
	 * 
	 * @param map
	 */
	private static void addMappingDict(Map<Character, Character> map) {
		if (map == null || map.isEmpty()) {
			return;
		}
		int enable = 0;
		int disable = 0;
		long start = System.currentTimeMillis();
		for (Entry<Character, Character> entry : map.entrySet()) {
			Character key = entry.getKey();
			Character value = entry.getValue();
			if (key == null) {
				continue;
			}
			if (value == null) {
				mappingDict.remove(key);
				disable++;
			} else {
				mappingDict.put(key, value);
				enable++;
			}
		}
		LogUtil.IK.info("总数：" + map.size() + "，加入词数：" + enable + "，失效词数：" + disable + "，耗时(ms):" + (System.currentTimeMillis() - start));
	}

	/**
	 * 添加词典
	 * 
	 * @param dictSegment
	 * @param dics
	 */
	private static void addDict(DictSegment dictSegment, DictWord[] dics) {
		if (dics == null || dics.length == 0) {
			return;
		}
		try {
			if (dictSegment == null) {
				dictSegment = new DictSegment((char) 0);
			}
			int fill = 0;
			int disable = 0;
			long start = System.currentTimeMillis();
			for (DictWord word : dics) {
				if (word == null) {
					continue;
				}
				String name = StringUtil.trim(word.getName());
				Boolean enable = word.getEnable();
				if (StringUtil.isEmpty(name)) {
					continue;
				}
				char[] chars = name.toCharArray();
				if (enable != null && enable) {
					dictSegment.fillSegment(chars);
					fill++;
				} else {
					dictSegment.disableSegment(chars);
					disable++;
				}
			}
			LogUtil.IK.info("总数：" + dics.length + "，加入词数：" + fill + "，失效词数：" + disable + "，耗时(ms):" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			LogUtil.IK.error("", e);
		}
	}

	/**
	 * 检索匹配主词典
	 * 
	 * @param charArray
	 * @return
	 */
	public static Hit matchInMainDict(char[] charArray) {
		return mainDict.match(charArray);
	}

	/**
	 * 检索匹配主词典
	 * 
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return
	 */
	public static Hit matchInMainDict(char[] charArray, int begin, int length) {
		return mainDict.match(charArray, begin, length);
	}

	/**
	 * 检索匹配量词词典
	 * 
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return
	 */
	public static Hit matchInQuantifierDict(char[] charArray, int begin, int length) {
		return quantifierDict.match(charArray, begin, length);
	}

	/**
	 * 从已匹配的Hit中直接取出DictSegment，继续向下匹配
	 * 
	 * @param charArray
	 * @param currentIndex
	 * @param matchedHit
	 * @return
	 */
	public static Hit matchWithHit(char[] charArray, int currentIndex, Hit matchedHit) {
		DictSegment ds = matchedHit.getMatchedDictSegment();
		return ds.match(charArray, currentIndex, 1, matchedHit);
	}

	/**
	 * 判断是否是停止词
	 * 
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return
	 */
	public static boolean isStopword(char[] charArray, int begin, int length) {
		return stopwordDict.match(charArray, begin, length).isMatch();
	}

	/**
	 * 是否有映射词
	 * 
	 * @return
	 */
	public static Character mapping(Character c) {
		if (c == null) {
			return null;
		}
		Character c1 = mappingDict.get(c);
		return c1 != null ? c1 : c;
	}

}
