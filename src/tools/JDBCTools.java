package tools;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Stack;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import info.IdAndPasswd;


/**
 * JDBC 的工具类
 * 
 * 其中包含: 获取数据库连接, 关闭数据库资源等方法.
 */
public class JDBCTools {

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
		dataSource = new ComboPooledDataSource("c3p0");
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
	 * 下面是我写的函数
	 */
	public static boolean updateSql(String sql) {
		int status = 0;//设置标志状态位,当数据库更新成功时,返回1
		Connection connection = null;
		//CallableStatement callableStatement = null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			// String sql="insert into Student(stunumber,name,sex,xuezhi,yuanxi)
			// values('1330090003','胡永涛','男','4','计信学院')";
			statement.executeUpdate(sql);
			System.out.println("Success!");
			// return true;
			status = 1;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("发生异常！！！");
		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		if (status == 1) {
			return true;
		} else
			return false;
	}
	//这个函数 木有卵用
	public static Stack<IdAndPasswd> executeQuery(String sql){
		Stack<IdAndPasswd> xuehao=new Stack<>();
		Connection connection=null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			// String sql="insert into Student(stunumber,name,sex,xuezhi,yuanxi)
			// values('1330090003','胡永涛','男','4','计信学院')";
			ResultSet rs=statement.executeQuery(sql);//执行查询语句
			while(rs.next()){
				IdAndPasswd id=new IdAndPasswd();
				id.setStuId(rs.getString(1));
				id.setPassswd(rs.getString(2));
				xuehao.push(id);
			}
			System.out.println("Success!");
			// return true;
			//status = 1;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		return xuehao;
	}
}
