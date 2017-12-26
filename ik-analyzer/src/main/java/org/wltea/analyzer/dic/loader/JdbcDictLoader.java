package org.wltea.analyzer.dic.loader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.wltea.analyzer.dic.DictWord;
import org.wltea.analyzer.util.JDBCUtil;
import org.wltea.analyzer.util.LogUtil;
import org.wltea.analyzer.util.PropertiesUtil;
import org.wltea.analyzer.util.StringUtil;

/**
 * 数据库词典加载器
 * 
 * @author linshouyi
 *
 */
public class JdbcDictLoader implements DictLoader {

	private static String config = "jdbc.properties";// 配置文件
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	static {
		Properties properties = PropertiesUtil.loadClasspath(config);
		if (properties != null) {
			driver = properties.getProperty("dicLoader.driver");
			url = properties.getProperty("dicLoader.url");
			username = properties.getProperty("dicLoader.username");
			password = properties.getProperty("dicLoader.password");
			LogUtil.IK.info("数据库连接地址: " + url);
		}
	}

	@Override
	public DictWord[] loadMainDict() {
		Connection con = JDBCUtil.getConnction(driver, url, username, password);
		if (con == null) {
			return null;
		}
		String sql = "select a.name, a.status, b.status from dic a,dic_group b where a.group_id = b.id and a.group_id in (1,4,5)";
		return addDict(JDBCUtil.select2Array(con, sql, null));
	}

	@Override
	public DictWord[] loadStopwordDict() {
		Connection con = JDBCUtil.getConnction(driver, url, username, password);
		if (con == null) {
			return null;
		}
		String sql = "select a.name,a.status,b.status from dic a,dic_group b where a.group_id = b.id and a.group_id=2";
		return addDict(JDBCUtil.select2Array(con, sql, null));
	}

	@Override
	public DictWord[] loadQuantifierDict() {
		Connection con = JDBCUtil.getConnction(driver, url, username, password);
		if (con == null) {
			return null;
		}
		String sql = "select a.name,a.status,b.status from dic a,dic_group b where a.group_id = b.id and a.group_id=3";
		return addDict(JDBCUtil.select2Array(con, sql, null));
	}

	@Override
	public Map<Character, Character> loadMappingDict() {
		Connection con = JDBCUtil.getConnction(driver, url, username, password);
		if (con == null) {
			return null;
		}
		String sql = "select source,target,status from dic_mapping";
		return addMappingDict(JDBCUtil.select2Array(con, sql, null));
	}

	private Map<Character, Character> addMappingDict(List<Object[]> dics) {
		if (dics == null || dics.size() == 0) {
			return null;
		}
		try {
			Map<Character, Character> map = new HashMap<Character, Character>();
			for (Object[] dic : dics) {
				if (dic == null || dic.length != 3) {
					continue;
				}
				String source = StringUtil.trim((String) dic[0]);
				String target = StringUtil.trim((String) dic[1]);
				Integer status = (Integer) dic[2];
				Character c = (!StringUtil.isEmpty(source) && source.length() >= 1) ? source.charAt(0) : null;
				Character c1 = (!StringUtil.isEmpty(target) && target.length() >= 1) ? target.charAt(0) : null;
				if (c == null) {
					continue;
				}
				if (status != null && status == 1) {// 有效
				} else {
					c1 = null;
				}
				map.put(c, c1);
			}
			return map;
		} catch (Exception e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

	private DictWord[] addDict(List<Object[]> dics) {
		if (dics == null || dics.size() == 0) {
			return null;
		}
		try {
			List<DictWord> words = new ArrayList<DictWord>();
			for (Object[] dic : dics) {
				if (dic == null || dic.length != 3) {
					continue;
				}
				String name = StringUtil.trim((String) dic[0]);
				if (StringUtil.isEmpty(name)) {
					continue;
				}
				Integer status = (Integer) dic[1];
				Integer status1 = (Integer) dic[2];
				boolean enable = false;
				if (status != null && status == 1 && status1 != null && status1 == 1) {
					enable = true;
				}
				words.add(new DictWord(name, enable));
			}
			return words.toArray(new DictWord[words.size()]);
		} catch (Exception e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

}
