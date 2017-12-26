package org.wltea.analyzer.dic.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wltea.analyzer.dic.DictWord;
import org.wltea.analyzer.util.FileUtil;
import org.wltea.analyzer.util.LogUtil;
import org.wltea.analyzer.util.StringUtil;

/**
 * 文件词典加载器（默认词库加载器）
 * 
 * @author linshouyi
 *
 */
public class FileDictLoader implements DictLoader {

	private static String mainDict = "dic/main.dic";// 主词文件
	private static String stopwordDict = "dic/stopword.dic";// 停止词文件
	private static String quantifierDict = "dic/quantifier.dic";// 量词文件
	private static String mappingDict = "dic/mapping.dic";// 映射词典文件
	private static String charsetName = "UTF-8";// 词库文件编码

	public DictWord[] loadMainDict() {
		return loadDict(mainDict, charsetName);
	}

	public DictWord[] loadStopwordDict() {
		return loadDict(stopwordDict, charsetName);
	}

	public DictWord[] loadQuantifierDict() {
		return loadDict(quantifierDict, charsetName);
	}

	private DictWord[] loadDict(String fileName, String charsetName) {
		try {
			InputStream is = FileDictLoader.class.getClassLoader().getResourceAsStream(fileName);
			if (is == null) {
				return null;
			}
			List<DictWord> list = new ArrayList<DictWord>();
			String[] lines = FileUtil.readLine(is, charsetName);
			if (lines != null) {
				for (String line : lines) {
					if (StringUtil.isEmpty(line)) {
						continue;
					}
					list.add(new DictWord(line, true));
				}
			}
			return list.toArray(new DictWord[list.size()]);
		} catch (Exception e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

	@Override
	public Map<Character, Character> loadMappingDict() {
		try {
			InputStream is = FileDictLoader.class.getClassLoader().getResourceAsStream(mappingDict);
			if (is == null) {
				return null;
			}
			Map<Character, Character> map = new HashMap<Character, Character>();
			String[] lines = FileUtil.readLine(is, charsetName);
			if (lines != null) {
				for (String line : lines) {
					if (StringUtil.isEmpty(line)) {
						continue;
					}
					boolean comment = false;
					if (line.startsWith("#")) {// 注释无效
						comment = true;
						line = line.replaceAll("^#+", "");
					}
					int index = line.indexOf("=");
					if (index > 0) {
						String source = StringUtil.trim(line.substring(0, index));
						String target = StringUtil.trim(line.substring(index + 1));
						Character c = (!StringUtil.isEmpty(source) && source.length() >= 1) ? source.charAt(source.length() - 1) : null;
						Character c1 = (!StringUtil.isEmpty(target) && target.length() >= 1) ? target.charAt(0) : null;
						if (c == null || c == '#') {
							continue;
						}
						if (comment) {
							c1 = null;
						}
						map.put(c, c1);
					}
				}
			}
			return map;
		} catch (Exception e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

}
