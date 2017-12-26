package org.wltea.analyzer.lucene.synonym.loader;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.wltea.analyzer.lucene.synonym.SynonymDict;
import org.wltea.analyzer.util.JDBCUtil;
import org.wltea.analyzer.util.LogUtil;
import org.wltea.analyzer.util.PropertiesUtil;
import org.wltea.analyzer.util.StringUtil;

public class JdbcSynonymDictLoader extends AbstractSynonymDictLoader {

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
		}
	}
	private String groupIds;// 同义词组id

	public JdbcSynonymDictLoader() {
	}

	public JdbcSynonymDictLoader(String groupIds) {
		this.groupIds = groupIds;
	}

	@Override
	public void load(SynonymDict dict) throws Exception {
		if (dict == null) {
			return;
		}
		try {
			if (StringUtil.isEmpty(driver, url, username, password)) {
				return;
			}
			Connection con = JDBCUtil.getConnction(driver, url, username, password);
			if (con == null) {
				return;
			}
			long start = System.currentTimeMillis();
			StringBuilder builder = new StringBuilder("select a.source, a.target, a.status, b.status from dic_synonym a,dic_group b where a.group_id = b.id");
			if (!StringUtil.isEmpty(groupIds)) {
				builder.append(" and a.group_id in (").append(groupIds).append(")");
			}
			List<Object[]> list = JDBCUtil.select2Array(con, builder.toString(), null);
			int size = list != null ? list.size() : 0;
			int add = 0;
			int del = 0;
			if (size > 0) {
				for (Object[] objects : list) {
					String source = (String) objects[0];
					String target = (String) objects[1];
					Integer status = (Integer) objects[2];
					Integer status1 = (Integer) objects[3];
					boolean enable = false;
					if (status != null && status == 1 && status1 != null && status1 == 1) {
						enable = true;
					}
					int total = load(dict, source, target, enable);
					if (enable) {
						add += total;
					} else {
						del += total;
					}
				}
			}
			LogUtil.IK.info("加入词数：" + add + "，失效词数：" + del + "，耗时(ms):" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			LogUtil.IK.error("", e);
		}
	}

}
