package org.wltea.analyzer.lucene.synonym;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.wltea.analyzer.lucene.synonym.loader.SynonymDictLoader;
import org.wltea.analyzer.util.LogUtil;
import org.wltea.analyzer.util.StringUtil;

/**
 * 同义词典，线程安全
 * 
 * @author linshouyi
 *
 */
public class SynonymDict {

	private Map<String, Set<String>> synonyms = new ConcurrentHashMap<String, Set<String>>();// 同义词库
	private boolean ignoreCase;// 是否忽略大小写

	public SynonymDict() {
		this(null);
	}

	public SynonymDict(Boolean ignoreCase) {
		this.ignoreCase = (ignoreCase != null ? ignoreCase : true);
	}

	public void load(SynonymDictLoader... loaders) {
		if (loaders == null || loaders.length == 0) {
			return;
		}
		for (SynonymDictLoader loader : loaders) {
			try {
				long start = System.currentTimeMillis();
				LogUtil.IK.info("===" + loader + "开始加载同义词...");
				loader.load(this);
				LogUtil.IK.info("===" + loader + "同义词加载完毕，耗时(ms)：" + (System.currentTimeMillis() - start));
			} catch (Exception e) {
				LogUtil.IK.error("", e);
			}
		}
		for (Entry<String, Set<String>> entry : synonyms.entrySet()) {
			LogUtil.IK.info(entry.getKey() + ":" + entry.getValue());
		}
	}

	public void update(SynonymDictLoader... loaders) {
		update(null, null, loaders);
	}

	public void update(Long delay, Long period, final SynonymDictLoader... loaders) {
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

	public boolean add(String term, String synonym) {
		if (StringUtil.isEmpty(term, synonym)) {
			return false;
		}
		if (ignoreCase) {
			term = term.toLowerCase();
			synonym = synonym.toLowerCase();
		}
		if (term.equals(synonym)) {// 同一个词不是同义词
			return false;
		}
		Set<String> set = null;
		synchronized (synonyms) {// 保证map get,put同步
			set = synonyms.get(term);
			if (set == null) {
				synonyms.put(term, set = new LinkedHashSet<String>());
			}
		}
		synchronized (set) {
			return set.add(synonym);
		}
	}

	public boolean remove(String term, String synonym) {
		if (StringUtil.isEmpty(term, synonym)) {
			return false;
		}
		if (ignoreCase) {
			term = term.toLowerCase();
			synonym = synonym.toLowerCase();
		}
		Set<String> set = synonyms.get(term);
		if (set != null) {
			synchronized (set) {
				return set.remove(synonym);
			}
		}
		return false;
	}

	public String[] get(String term) {
		if (StringUtil.isEmpty(term)) {
			return null;
		}
		if (ignoreCase) {
			term = term.toLowerCase();
		}
		Set<String> set = synonyms.get(term);
		if (set != null) {
			LogUtil.IK.info(term + "=>" + set);
			synchronized (set) {
				return set.toArray(new String[set.size()]);
			}
		}
		return null;
	}
}
