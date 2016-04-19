package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import info.IdAndPasswd;
import info.Student;

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
		dataSource = new ComboPooledDataSource();
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
		int status = 0;// 设置标志状态位,当数据库更新成功时,返回1
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			// log.info("更新数据库成功Success!");
			status = 1;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			log.error("发生异常！！！");
		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		if (status == 1) {
			return true;
		} else
			return false;
	}

	public static ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);// 执行查询语句
			System.out.println("执行查询Success!！！！");
			System.out.println("在executeQuery中输出rs" + rs);

			return rs;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
			System.out.println("执行查询发生异常！！！");

		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		return rs;
	}

	// 这个函数 现在有用了
	public static Stack<IdAndPasswd> QueryPasswd(String sql) {
		Stack<IdAndPasswd> xuehao = new Stack<>();
		sql = "select StuID,Passwd,Priority from Student order by Priority desc";
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);// 执行查询语句
			while (rs.next()) {
				IdAndPasswd id = new IdAndPasswd();
				id.setStuId(rs.getString(1));
				id.setPassswd(rs.getString(2));
				xuehao.push(id);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("执行查询发生异常！！！");

		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		return xuehao;
	}

	/**
	 * 函数工厂 一定要学会！！！！！！！！！！
	 * 
	 * @param sql
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
			System.out.println("执行查询Success!！！！");
			System.out.println("在executeQuery中输出rs" + rs);
			while (rs.next()) {
				email = rs.getString(1).trim();
			}
			System.out.println(email);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
			System.out.println("执行查询发生异常！！！");
		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		return email.trim();
	}

	// 下面写存储数据的函数 传入参数是Student类型
	// 返回一个包含更新的新成绩的栈
	// 4.17 返回的应当是一个学生对象，包含了该学生的所有新增加成绩
	public static Student saveStudent(Student student) {
		String sqlStudent = student.getSql();
		if (JDBCTools.updateSql(sqlStudent)) {
			log.info("学生信息更新成功");
		}
		Stack<String> existGrade = getExistGrade(student.number);
		Student stu = null;
		stu = (Student) student.cloneStudent();
		// 本变量存放该学生新增加的成绩
		// 重写一个克隆函数
		// stu.grade.clear();// 清空学生的成绩--我是想克隆的时候就清空的 但是还没有学会 已经写出来了
		for (int i = 0; i < student.gradeNUmber; i++) {
			// 存成绩的时候 要考虑成绩是否已经存在的情况
			// System.out.println("student中的课程名"+student.grade.peek().getKecheng().trim()+"长度："+student.grade.peek().getKecheng().trim().length());
			boolean status = existGrade.search(student.grade.peek().getKecheng().trim()) == -1;
			if (status) {
				// 只有在数据库中匹配不到的成绩 才可以保存到数据库中 已经存在的 不用保存
				sqlStudent = student.grade.pop().getsql();
				if (!JDBCTools.updateSql(sqlStudent)) {
					log.error("学生成绩存储失败");
				}
				// System.out.println("！！！这是一个新成绩！！！");
				// log.info("新成绩");
				// 将新增加的成绩压入stu栈中
				stu.grade.push(student.grade.peek());
			} else {
				student.grade.pop();// 如果已经存在 就不需要再写入数据库了
			}
		}
		log.info("保存学生信息成功");
		return stu;// 返回的是包含增加的新成绩的栈
	}

	// 我只需要获得已存在的考试科目即可 这样的话 可以放到map里面了
	public static Stack<String> getExistGrade(int StuID) {
		Stack<String> existGrade = new Stack<>();
		String sql = "select kecheng from Grade where StuID='" + StuID + "'";
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);// 执行查询语句
			while (rs.next()) {
				existGrade.push(rs.getString(1).trim());// 将获得的成绩名装入栈中
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("执行查询发生异常！！！");
		} finally {
			JDBCTools.releaseDB(null, statement, connection);
		}
		return existGrade;
	}

	public static void main(String[] args) {
		// Stack<IdAndPasswd>
		// try {
		// System.out.println(getConnection());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// QueryPasswd(null);
		String email = QueryEmail("1330090010");
		System.out.println(email + email.length());
	}
}
