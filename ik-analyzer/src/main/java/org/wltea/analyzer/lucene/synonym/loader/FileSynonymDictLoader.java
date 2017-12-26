package org.wltea.analyzer.lucene.synonym.loader;

import java.io.InputStream;

import org.wltea.analyzer.dic.loader.FileDictLoader;
import org.wltea.analyzer.lucene.synonym.SynonymDict;
import org.wltea.analyzer.util.FileUtil;
import org.wltea.analyzer.util.LogUtil;
import org.wltea.analyzer.util.StringUtil;

public class FileSynonymDictLoader extends AbstractSynonymDictLoader {

	private String file = "dic/synonym.dic";// 配置文件
	private String charsetName = "UTF-8";// 词库文件编码

	public FileSynonymDictLoader() {
	}

	public FileSynonymDictLoader(String file) {
		if (!StringUtil.isEmpty(file)) {
			this.file = file;
		}
	}

	@Override
	public void load(SynonymDict dict) throws Exception {
		if (dict == null) {
			return;
		}
		try {
			long start = System.currentTimeMillis();
			LogUtil.IK.info("读取同义词文件：" + file);
			InputStream is = FileDictLoader.class.getClassLoader().getResourceAsStream(file);
			if (is == null) {
				LogUtil.IK.error("同义词文件不存在：" + file);
				return;
			}
			String[] lines = FileUtil.readLine(is, charsetName);
			int size = lines != null ? lines.length : 0;
			int add = 0;
			int del = 0;
			if (size > 0) {
				for (String line : lines) {
					boolean enable = true;
					if (line.startsWith("#")) {
						line = line.replaceAll("^#+", "");
						enable = false;
					}
					String[] words = line.split("=>");
					String source = null;
					String target = null;
					if (words.length >= 1) {
						source = words[0];
					}
					if (words.length >= 2) {
						target = words[1];
					}
					int total = load(dict, source, target, enable);
					if (enable) {
						add += total;
					} else {
						del += total;
					}
				}
			}
			LogUtil.IK.info("==同义词：总数：" + (add + del) + "，加入词数：" + add + "，失效词数：" + del + "，耗时(ms):" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			LogUtil.IK.error("", e);
		}
	}

}
