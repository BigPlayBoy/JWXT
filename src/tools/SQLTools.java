package tools;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.Stack;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.*;

import info.Student;

/**
 * 此包里存放的是与数据库连接有关的函数
 * 
 * 想用c3p0啊
 * 
 * @author 明辉
 *
 */
public class SQLTools {
	public static void main(String[] args) {
		// Statement stmt=ConnectSql();
		// String sql="insert into Student(stunumber,name,sex,xuezhi,yuanxi)
		// values('1330090002','崔明辉','男','4','计信学院')";
		// try {
		// stmt.executeUpdate(sql);
		// System.out.println("Success");
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// System.err.println(" Something Wrong!");
		// }
		try {
			testc3p0();
			System.out.println("Success");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(" Something Wrong!");
	}

	static Statement ConnectSql() {
		Statement stmt = null;
		try {
			// 1.注册数据库引擎
			String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";// SQL数据库引擎
			Class.forName(JDriver);
			// 2.连接数据库
			String url = "jdbc:sqlserver://localhost:1433;DatabaseName=TJNU";
			Connection conn = DriverManager.getConnection(url, "sa", "cuiminghui");
			stmt = conn.createStatement();
			// Statement stmt1 = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}

	static void testc3p0() throws PropertyVetoException, SQLException {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // loads
																				// the
																				// jdbc
																				// driver
		cpds.setJdbcUrl("jdbc:sqlserver://localhost:1433;DatabaseName=TJNU");
		cpds.setUser("sa");
		cpds.setPassword("cuiminghui");
		cpds.setMaxStatements(6);
		cpds.setMinPoolSize(3);
		System.out.println(cpds.getConnection());
	}

	private static DataSource ds;

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	static {
		ds = new ComboPooledDataSource();// 直接使用即可，不用显示的配置，其会自动识别配置文件
	}

	public static DataSource getDataSource() {
		return ds;
	}

	public static Connection getConnection() {
		try {
			// 得到当前线程上绑定的连接
			Connection conn = tl.get();
			if (conn == null) { // 代表线程上没有绑定连接
				conn = ds.getConnection();
				tl.set(conn);
			}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void startTransaction() {
		try {
			// 得到当前线程上绑定连接开启事务
			Connection conn = getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void commitTransaction() {
		try {
			Connection conn = tl.get();
			if (conn != null) {
				conn.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void closeConnection() {
		try {
			Connection conn = tl.get();
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			tl.remove(); // 千万注意，解除当前线程上绑定的链接（从threadlocal容器中移除对应当前线程的链接）
		}
	}

	// 下面写存储数据的函数 传入参数是Student类型
	public static boolean saveStudent(Student student) {
		String sql = student.getSql();
		JDBCTools.updateSql(sql);
		Stack<String> existGrade = getExistGrade(student.number);
//		for (String string : existGrade) {
//			System.out.println("数据库中已存在的课程名：" + string+"长度："+string.length());
//		}
		for (int i = 0; i < student.gradeNUmber; i++) {
			// student.grade.
			// 存成绩的时候 要考虑成绩是否已经存在的情况
			//System.out.println("student中的课程名"+student.grade.peek().getKecheng().trim()+"长度："+student.grade.peek().getKecheng().trim().length());
				boolean status=existGrade.search(student.grade.peek().getKecheng().trim()) ==-1;
				//System.out.println("搜索的结果"+existGrade.search(student.grade.peek().getKecheng().trim()));
				//System.out.println("两个课程的比较"+status);
				//System.out.println(student.grade.peek().getKecheng().trim());s's's
			if (status) {
				// 只有在数据库中匹配不到的成绩 才可以保存到数据库中 已经存在的 不用保存
				
				sql = student.grade.pop().getsql();
				JDBCTools.updateSql(sql);
				System.out.println("！！！这是一个新成绩！！！");
				System.out.println(sql);
				// 下面欠一个函数 新增的成绩 应当进行推送 推送的内容为姓名 学号 课程名 学分 成绩
				// fun()
			} else {
				student.grade.pop();// 如果已经存在 就不需要再写入数据库了
			}

		}
		System.out.println("保存学生信息成功");
		return false;
	}

	// 我只需要获得已存在的考试科目即可 这样的话 可以放到map里面了
	public static Stack<String> getExistGrade(int StuID) {
		Stack<String> existGrade = new Stack<>();
		String sql = "select kecheng from Grade where StuID='" + StuID + "'";
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			// String sql="insert into Student(stunumber,name,sex,xuezhi,yuanxi)
			// values('1330090003','胡永涛','男','4','计信学院')";
			ResultSet rs = statement.executeQuery(sql);// 执行查询语句
			while (rs.next()) {
				existGrade.push(rs.getString(1).trim());// 将获得的成绩装入栈中
			}
			// for (String string : existGrade) {
			// System.out.println("从数据库中取到的考试课程" + string);
			// }
			// System.out.println(existGrade);
			// return true;
			// status = 1;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		return existGrade;
	}
}
