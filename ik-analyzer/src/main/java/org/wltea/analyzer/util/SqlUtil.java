package org.wltea.analyzer.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

public class SqlUtil {

	/**
	 * 生成字段sql
	 * 
	 * @param fields
	 * @return 不符条件返回null
	 */
	public static String getSelectField(Collection<String> fields) {
		if (fields == null || fields.isEmpty()) {
			return null;
		}
		StringBuilder builder = null;
		for (String field : fields) {
			if (StringUtil.isEmpty(field)) {
				continue;
			}
			if (builder == null) {
				builder = new StringBuilder();
			} else {
				builder.append(",");
			}
			builder.append(field);
		}
		return builder != null ? builder.toString() : null;
	}

	/**
	 * 生成字段sql
	 * 
	 * @param fields
	 * @return 不符条件返回null
	 */
	public static String getSelectField(Map<String, String> fields) {
		if (fields == null || fields.isEmpty()) {
			return null;
		}
		StringBuilder builder = null;
		for (Entry<String, String> entry : fields.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtil.isEmpty(key)) {
				continue;
			}
			if (builder == null) {
				builder = new StringBuilder();
			} else {
				builder.append(",");
			}
			if (StringUtil.isEmpty(value)) {
				builder.append(key);
			} else {
				builder.append(key).append(" as ").append(value);
			}
		}
		return builder != null ? builder.toString() : null;
	}

	public static String getSelectSQL(String table, String fields, String condition, String order, Integer start, Integer limit) {
		if (StringUtil.isEmpty(table)) {
			return null;
		}
		if (StringUtil.isEmpty(fields)) {
			fields = "*";
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select ").append(fields).append(" from ").append(table);
		if (!StringUtil.isEmpty(condition)) {
			sql.append(" where ").append(condition);
		}
		if (!StringUtil.isEmpty(order)) {
			sql.append(" order by ").append(order);
		}
		if (limit != null) {
			if (start == null) {
				start = 0;
			}
			sql.append(" limit ").append(start).append(",").append(limit);
		}
		return sql.toString();
	}

	public static String getSelectSQL(String table, String fields, String condition) {
		return getSelectSQL(table, fields, condition, null, null, null);
	}

	public static String getSelectSQL(String table, Map<String, String> fields, String condition, String order, Integer start, Integer limit) {
		return getSelectSQL(table, getSelectField(fields), condition, order, start, limit);
	}

	public static String getSelectSQL(String table, Map<String, String> fields, String condition) {
		return getSelectSQL(table, fields, condition, null, null, null);
	}

	public static String getSelectSQL(String table, Collection<String> fields, String condition, String order, Integer start, Integer limit) {
		return getSelectSQL(table, getSelectField(fields), condition, order, start, limit);
	}

	public static String getSelectSQL(String table, Collection<String> fields, String condition) {
		return getSelectSQL(table, fields, condition, null, null, null);
	}

	public static String getInsertSQL(String table, String fields, String values) {
		if (StringUtil.isEmpty(table, fields, values)) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(table).append(" (").append(fields).append(") values (").append(values).append(")");
		return sql.toString();
	}

	public static String getInsertSQL(String table, Collection<String> fields) {
		if (fields == null || fields.isEmpty()) {
			return null;
		}
		StringBuilder fieldSql = null;
		StringBuilder valueSql = null;
		for (String field : fields) {
			if (StringUtil.isEmpty(field)) {
				continue;
			}
			if (fieldSql == null) {
				fieldSql = new StringBuilder();
				valueSql = new StringBuilder();
			} else {
				fieldSql.append(",");
				valueSql.append(",");
			}
			fieldSql.append(field);
			valueSql.append("?");
		}
		if (fieldSql == null || valueSql == null) {
			return null;
		}
		return getInsertSQL(table, fieldSql.toString(), valueSql.toString());
	}

	public static String getUpdateSQL(String table, String condition, String fields) {
		if (StringUtil.isEmpty(table, condition, fields)) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		return sql.append("update ").append(table).append(" set ").append(fields).append(" where ").append(condition).toString();
	}

	public static String getUpdateSQL(String table, Collection<String> conditions, Collection<String> fields) {
		if (conditions == null || conditions.isEmpty()) {
			return null;
		}
		StringBuilder conditionSql = null;
		for (String condtion : conditions) {
			if (StringUtil.isEmpty(condtion)) {
				continue;
			}
			if (conditionSql == null) {
				conditionSql = new StringBuilder();
			} else {
				conditionSql.append(",");
			}
			conditionSql.append(condtion).append("=?");
		}
		return getUpdateSQL(table, conditionSql != null ? conditionSql.toString() : null, fields);
	}

	public static String getUpdateSQL(String table, String condition, Collection<String> fields) {
		if (fields == null || fields.isEmpty()) {
			return null;
		}
		StringBuilder fieldSql = null;
		for (String field : fields) {
			if (StringUtil.isEmpty(field)) {
				continue;
			}
			if (fieldSql == null) {
				fieldSql = new StringBuilder();
			} else {
				fieldSql.append(",");
			}
			fieldSql.append(field).append("=?");
		}
		if (fieldSql == null) {
			return null;
		}
		return getUpdateSQL(table, condition, fieldSql.toString());
	}

}
