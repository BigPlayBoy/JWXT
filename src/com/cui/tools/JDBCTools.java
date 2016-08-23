package com.cui.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import com.cui.Bean.Grade;
import com.cui.Bean.IdAndPasswd;
import com.cui.Bean.Student;

/**
 * JDBC 的工具类
 * 
 * 其中包含: 获取数据库连接, 关闭数据库资源等方法.
 */
public class JDBCTools {
	static Logger log = LoggerFactory.getLogger(JDBCTools.class);

	// 处理数据库事务的
	// 提交事务
	public static void commit(Connection connection) {
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 回滚事务
	public static void rollback(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 开始事务
	public static void beginTx(Connection connection) {
		if (connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static DataSource dataSource = null;

	// 数据库连接池应只被初始化一次.
	static {
		String c3p0="TJNUSQlite";
		if("Windows_NT".equals(System.getenv("OS"))){
			c3p0="TJNU";
		}
		dataSource = new ComboPooledDataSource(c3p0);
	}

	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	public static void releaseDB(ResultSet resultSet, Statement statement, Connection connection) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				// 数据库连接池的 Connection 对象进行 close 时
				// 并不是真的进行关闭, 而是把该数据库连接会归还到数据库连接池中.
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 函数工厂 一定要学会！！！！！！！！！！
	 * 
	 * @param queryEmail
	 * @return
	 */
	public static String QueryEmail(String queryEmail) {
		String email = null;
		// String queryEmail = "select Email from Student where StuID=" + StuID;
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(queryEmail);// 执行查询语句
			log.info("exect select Success!！！！");
			log.info("at executeQuery rs=" + rs);
			while (rs.next()) {
				email = rs.getString(1).trim();
			}
			log.info(email);
		} catch (Exception e1) {
			log.info("select password happens something wrong！！！"+e1);
		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		return email.trim();
	}


	public static void main(String[] args) {

	}
}
