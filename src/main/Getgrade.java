package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Stack;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.IdAndPasswd;
import info.Student;
import tools.SQLTools;
import tools.Tools;

public class Getgrade implements Job {
	private static Logger _log = LoggerFactory.getLogger(Getgrade.class);

	public static void main(String[] args) {
		// 第一步从数据中获取学号和密码，返回值类型是Stack<IdAndPasswd>
		Stack<IdAndPasswd> passwd = Tools.getStuIdandPasswd();// 得到学号和密码
		System.out.println("获取学号和密码成功");
		while (!passwd.isEmpty()) {
			String xuehao = passwd.peek().getStuId();
			String upass = passwd.peek().getPassswd();
			passwd.pop();
			// 获取成绩页面
			System.out.println("获取网页信息");
			String GradePage = getGradePage(xuehao, upass);
			// 拿到成绩页面，提取出学生信息和成绩
			System.out.println("已获取网页信息，正获取学生信息");
			// System.out.println("获取的网页为："+GradePage);
			Student student = Tools.getStuInfo(GradePage);
			// 存入数据库
			// System.out.println(student);
			System.out.println("存入数据库");
			SQLTools.saveStudent(student);// 已经考虑是否有重复了
		}
	}

	/**
	 * 获取成绩页面，输入是学号，密码，输出成绩页面(String类型)
	 * 
	 * @param uname
	 * @param upass
	 * @return
	 */
	public static String getGradePage(String xuehao, String upass) {
		// 从主页获取cookie
		String cookie = GetCookie("http://jw.tjnu.edu.cn/").replace("; path=/", "");
		// System.out.println("拿到的cookie为：" + cookie);
		// System.out.println("\n正在登录...\n");
		// xuehao = "1330090005";
		// upass = "rzy003502";
		String postInfo = "uname=" + xuehao + "&upass=" + upass + "&submitgo=GO";
		// System.out.println("拼接的登陆信息为："+postInfo);
		sendPost("http://jw.tjnu.edu.cn/index.php", postInfo, cookie);
		// 进入成绩页面
		String gradePage = sendGet("http://jw.tjnu.edu.cn/jwgl/cjgl/bbdy/bottom.php?id=&page=", cookie);

		// System.out.println("打印成绩页面");
		// System.out.println(Tools.getStuInfo(grade));// 将提取的信息打印出来
		// System.out.println(grade);// 获得了成绩页面
		// System.out.println(grade.replaceAll("\t|\n", "").replaceAll(" ",
		// ""));// 获得了成绩页面
		// String staus =
		Logout("http://jw.tjnu.edu.cn/jwgl/logout.php", cookie);
		// System.out.println("退出");
		System.out.println("获取网页信息 等待加工....");
		return gradePage;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String cookie) {
		String result = "";
		BufferedReader in = null;
		try {
			// String urlNameString = url + "?" + param;
			String urlNameString = url;

			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Host", "jw.tjnu.edu.cn");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
			connection.setRequestProperty("DNT", "1");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			connection.setRequestProperty("Referer", "http://jw.tjnu.edu.cn/jwgl/cjgl/bbdy/print.php");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			connection.setRequestProperty("Cookie", cookie);
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gb2312"));
			// in = new BufferedReader(new InputStreamReader((InputStream)
			// connection.getContent(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	static String GetCookie(String url) {
		String cookie = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Host", "jw.tjnu.edu.cn");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
			connection.setRequestProperty("DNT", "1");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			// 建立实际的连接
			connection.connect();
			cookie = connection.getHeaderField("Set-Cookie");
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		return cookie;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, String cookie) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Host", "jw.tjnu.edu.cn");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Content-Length", "45");//
			conn.setRequestProperty("Cache-Control", "max-age=0");//
			conn.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");//
			conn.setRequestProperty("Origin", "http://jw.tjnu.edu.cn");//
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");//
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");//
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//
			conn.setRequestProperty("DNT", "1");//
			conn.setRequestProperty("Referer", "http://jw.tjnu.edu.cn/");//
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");//
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");//
			conn.setRequestProperty("Cookie", cookie);
			System.out.println("POST提交的cookie为：\n" + cookie);
			System.out.println(conn);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			// 这里成功拿到cookie
			//String head = conn.getHeaderField("Set-Cookie");
			// System.out.println("返回的Cookie is \n\t" + head);
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	// 退出登录
	public static String Logout(String url, String cookie) {
		String result = "";
		BufferedReader in = null;
		try {
			// String urlNameString = url + "?" + param;
			String urlNameString = url;
			//Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Host", "jw.tjnu.edu.cn");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
			connection.setRequestProperty("DNT", "1");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			connection.setRequestProperty("Referer", "http://jw.tjnu.edu.cn/jwgl/cjgl/bbdy/print.php");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			connection.setRequestProperty("Cookie", cookie);
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			//String status = connection.getHeaderField("");
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gb2312"));
			// in = new BufferedReader(new InputStreamReader((InputStream)
			// connection.getContent(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobKey jobKey = context.getJobDetail().getKey();
		// 第一步从数据中获取学号和密码，返回值类型是Stack<IdAndPasswd>
		Stack<IdAndPasswd> passwd = Tools.getStuIdandPasswd();// 得到学号和密码
		_log.info("获取学号和密码成功");
		_log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
		while (!passwd.isEmpty()) {
			String xuehao = passwd.peek().getStuId();
			String upass = passwd.peek().getPassswd();
			passwd.pop();
			// 获取成绩页面
			_log.info("获取网页信息");
			String GradePage = getGradePage(xuehao, upass);
			// 拿到成绩页面，提取出学生信息和成绩
			_log.info("已获取网页信息，正获取学生信息");
			// System.out.println("获取的网页为："+GradePage);
			Student student = Tools.getStuInfo(GradePage);
			// 存入数据库
			// System.out.println(student);
			_log.info("存入数据库");
			SQLTools.saveStudent(student);// 已经考虑是否有重复了
		}
	}

}