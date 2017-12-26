package org.wltea.analyzer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC工具类
 * 
 * @author shouyilin
 *
 */
public class JDBCUtil {

	/**
	 * 获得连接
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static Connection getConnction(String driver, String url, String username, String password) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			LogUtil.IK.error("", e);
		}
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

	/**
	 * 关闭连接
	 * 
	 * @param rs
	 * @param state
	 * @param conn
	 */
	public static void closeConnection(ResultSet rs, Statement state, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LogUtil.IK.error("", e);
			}
		}
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				LogUtil.IK.error("", e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.IK.error("", e);
			}
		}
	}

	/**
	 * 获得PreparedStatement（不返回keys）
	 * 
	 * @param con
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public static PreparedStatement prepareStatement(Connection con, String sql, List<Object> parameters) {
		return prepareStatement(con, sql, parameters, false);
	}

	/**
	 * 获得PreparedStatement
	 * 
	 * @param con
	 * @param sql
	 * @param parameters
	 * @param return_keys
	 * @return
	 */
	public static PreparedStatement prepareStatement(Connection con, String sql, List<Object> parameters, boolean return_keys) {
		if (StringUtil.isEmpty(sql) || con == null) {
			return null;
		}
		try {
			PreparedStatement st = null;
			if (return_keys) {
				st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			} else {
				st = con.prepareStatement(sql);
			}
			if (parameters != null && !parameters.isEmpty()) {
				for (int i = 0; i < parameters.size(); i++) {
					st.setObject(i + 1, parameters.get(i));
				}
			}
			return st;
		} catch (SQLException e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

	/**
	 * 更新操作
	 * 
	 * @param st
	 * @return
	 */
	public static int update(PreparedStatement st) {
		if (st == null) {
			return 0;
		}
		try {
			return st.executeUpdate();
		} catch (SQLException e) {
			LogUtil.IK.error("", e);
		} finally {
			closeConnection(null, st, null);
		}
		return 0;
	}

	/**
	 * 查询操作（自动关闭connection）
	 * 
	 * @param con
	 * @param sql
	 * @param values
	 * @return
	 */
	public static List<Object[]> select2Array(Connection con, String sql, List<Object> values) {
		// TODO Auto-generated method stub
		try {
			return select2Array(prepareStatement(con, sql, values));
		} finally {
			closeConnection(null, null, con);
		}
	}

	/**
	 * 查询操作（不关闭connection）
	 * 
	 * @param con
	 * @param sql
	 * @param values
	 * @return
	 */
	public static List<Object[]> select2ArrayUnclose(Connection con, String sql, List<Object> values) {
		// TODO Auto-generated method stub
		return select2Array(prepareStatement(con, sql, values));
	}

	/**
	 * 更新操作（自动关闭connection）
	 * 
	 * @param con
	 * @param sql
	 * @param values
	 * @return
	 */
	public static int update(Connection con, String sql, List<Object> values) {
		try {
			return update(prepareStatement(con, sql, values));
		} finally {
			closeConnection(null, null, con);
		}
	}

	/**
	 * 更新操作（不关闭connection）
	 * 
	 * @param con
	 * @param sql
	 * @param values
	 * @return
	 */
	public static int updateUnclose(Connection con, String sql, List<Object> values) {
		return update(prepareStatement(con, sql, values));
	}

	/**
	 * 查询操作
	 * 
	 * @param st
	 * @return
	 */
	public static List<Object[]> select2Array(PreparedStatement st) {
		if (st == null) {
			return null;
		}
		List<Object[]> list = null;
		ResultSet rs = null;
		try {
			rs = st.executeQuery();
			String[] columnLabels = getColumnLabels(rs);
			if (columnLabels == null || columnLabels.length == 0) {
				return null;
			}
			while (rs.next()) {
				Object[] objects = new Object[columnLabels.length];
				for (int i = 0; i < columnLabels.length; i++) {
					String columnLabel = columnLabels[i];
					objects[i] = rs.getObject(columnLabel);
				}
				if (list == null) {
					list = new ArrayList<Object[]>();
				}
				list.add(objects);
			}
			return list;
		} catch (SQLException e) {
			LogUtil.IK.error("", e);
		} finally {
			closeConnection(rs, st, null);
		}
		return null;
	}

	/**
	 * 查询操作
	 * 
	 * @param st
	 * @return
	 */
	public static List<Map<String, Object>> select(PreparedStatement st) {
		// TODO Auto-generated method stub
		if (st == null) {
			return null;
		}
		List<Map<String, Object>> list = null;
		ResultSet rs = null;
		try {
			rs = st.executeQuery();
			String[] columnLabels = getColumnLabels(rs);
			if (columnLabels == null || columnLabels.length == 0) {
				return null;
			}
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String columnLabel : columnLabels) {
					map.put(columnLabel, rs.getObject(columnLabel));
				}
				if (map.isEmpty()) {
					continue;
				}
				if (list == null) {
					list = new ArrayList<Map<String, Object>>();
				}
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			LogUtil.IK.error("", e);
		} finally {
			closeConnection(rs, st, null);
		}
		return null;
	}

	/**
	 * 查询操作（自动关闭connection）
	 * 
	 * @param con
	 * @param sql
	 * @param values
	 * @return
	 */
	public static List<Map<String, Object>> select(Connection con, String sql, List<Object> values) {
		// TODO Auto-generated method stub
		try {
			return select(prepareStatement(con, sql, values));
		} finally {
			closeConnection(null, null, con);
		}
	}

	/**
	 * 查询操作（不关闭connection）
	 * 
	 * @param con
	 * @param sql
	 * @param values
	 * @return
	 */
	public static List<Map<String, Object>> selectUnclose(Connection con, String sql, List<Object> values) {
		// TODO Auto-generated method stub
		return select(prepareStatement(con, sql, values));
	}

	public static long getCount(Connection con, String tableName, String condition, List<Object> values) {
		String sql = SqlUtil.getSelectSQL(tableName, "count(*)", condition);
		List<Map<String, Object>> list = select(con, sql, values);
		if (list != null && !list.isEmpty()) {
			Map<String, Object> map = list.remove(0);
			Long count = (Long) map.get("count(*)");
			if (count != null) {
				return count;
			}
		}
		return 0;
	}

	/**
	 * 插入操作
	 * 
	 * @param st
	 * @return
	 */
	public static List<Integer> insert(PreparedStatement st) {
		if (st == null) {
			return null;
		}
		ResultSet rs = null;
		try {
			if (st != null) {
				int count = st.executeUpdate();
				if (count > 0) {
					List<Integer> ids = new ArrayList<Integer>();
					ResultSet generatedKeys = st.getGeneratedKeys();
					while (generatedKeys.next()) {
						ids.add(generatedKeys.getInt(1));
					}
					return ids;
				}
			}
		} catch (SQLException e) {
			LogUtil.IK.error("", e);
		} finally {
			closeConnection(rs, st, null);
		}
		return null;
	}

	/**
	 * 插入操作（自动关闭connection）
	 * 
	 * @param con
	 * @param sql
	 * @param values
	 * @return
	 */
	public static List<Integer> insert(Connection con, String sql, List<Object> values) {
		try {
			return insert(prepareStatement(con, sql, values, true));
		} finally {
			closeConnection(null, null, con);
		}
	}

	/**
	 * 插入操作（不关闭connection）
	 * 
	 * @param con
	 * @param sql
	 * @param values
	 * @return
	 */
	public static List<Integer> insertUnclose(Connection con, String sql, List<Object> values) {
		return insert(prepareStatement(con, sql, values, true));
	}

	/**
	 * 获得查询结果字段类型
	 * 
	 * @param rs
	 * @return
	 */
	public static int[] getColumnTypes(ResultSet rs) {
		if (rs == null) {
			return null;
		}
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int columCount = metaData.getColumnCount();
			int[] columnTypes = new int[columCount];
			for (int i = 0; i < columCount; i++) {
				columnTypes[i] = metaData.getColumnType(i + 1);
			}
			return columnTypes;
		} catch (SQLException e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

	/**
	 * 获得查询结果字段名称
	 * 
	 * @param rs
	 * @return
	 */
	public static String[] getColumnLabels(ResultSet rs) {
		if (rs == null) {
			return null;
		}
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int columCount = metaData.getColumnCount();
			String[] columnLabels = new String[columCount];
			for (int i = 0; i < columCount; i++) {
				columnLabels[i] = metaData.getColumnLabel(i + 1);
			}
			return columnLabels;
		} catch (SQLException e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}
}
