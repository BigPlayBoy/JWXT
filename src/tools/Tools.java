package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Bean.Grade;
import Bean.IdAndPasswd;
import Bean.Student;

public class Tools {

    static Logger log = LoggerFactory.getLogger(Tools.class);

    static void PrintMap(Map<Integer, String> map) {
        System.out.println("���Map�е�����");
        Set<Integer> set = map.keySet(); // keyװ��set��
        Iterator<Integer> it = set.iterator(); // ����set�ĵ����� װ��keyֵ
        while (it.hasNext()) {
            Integer key = it.next();
            String value = (String) map.get(key);
            System.out.println(key + " " + value);
        }
        System.out.println("������");
    }

    // 获得网址所在的匹配区域部分
    // 个人信息在第三个table里
    // 成绩在第四个table里
    // 从指定的字符串中 匹配需要的范围 只需要输入匹配的开始 和结尾
    // 这个可以制定开头的大概位置
    static String getContentArea(String urlContent, String strAreaBegin, String strAreaEnd, int fromIndex) {
        int pos1 = 0, pos2 = 0;
        int i;
        for (i = 1; i < fromIndex; i++) {
            pos1 = urlContent.indexOf(strAreaBegin, pos1) + strAreaBegin.length();
            // System.out.println("在循环里面 pos1的值为："+pos1);
        }

        pos1 = urlContent.indexOf(strAreaBegin, pos1) + strAreaBegin.length();
        pos2 = urlContent.indexOf(strAreaEnd, pos1);
        // System.out.println("pos1:" + pos1 + "pos2" + pos2);
        String ContentArea = urlContent.substring(pos1, pos2).replaceAll("\t|\n", "").replaceAll(" ", "");
        // System.out.println(ContentArea);
        return ContentArea;
    }

    // 这个会从第一个匹配的开始
    static String getContentArea(String urlContent, String strAreaBegin, String strAreaEnd) {
        int pos1 = 0, pos2 = 0;
        int i;
        for (i = 0; i < 2; i++) {
            pos1 = urlContent.indexOf(strAreaBegin, pos1) + strAreaBegin.length();
            System.out.println("在循环里面 pos1的值为：" + pos1);
        }

        pos1 = urlContent.indexOf(strAreaBegin, pos1) + strAreaBegin.length();
        pos2 = urlContent.indexOf(strAreaEnd, pos1);
        System.out.println("pos1:" + pos1 + "pos2" + pos2);
        String ContentArea = urlContent.substring(pos1, pos2).replaceAll("\t|\n", "").replaceAll(" ", "");
        // System.out.println(ContentArea);
        return ContentArea;
    }

    public static void main(String[] args) {
//		String GradePage=null;
//		try {
//			 GradePage=Tools.readPage("1330090002.html");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(GradePage);
//		Student student = Tools.getStuInfo(GradePage);// 返回的是一个学生的所有成绩
//		
//		// 存入数据库
//		log.Bean("更新数据库");
//		// 这里应当返回一个包含了新增成绩的栈 然后 发送出去
//		// 额 又需要在数据库中 添加邮箱了。。。
//		JDBCTools.saveStudent(student);// 返回的是该学生的所有新增成绩
//		System.out.println(Tools.getGradePage("1330090002", "cuiminghui"));
        deleteFiles();
    }

    public static Student getStuInfo(String string) {
        Student stu = new Student();
        String strStuInfo = new String();
        int stunum = 0;
        // 将标签简化 为后面提取打下基础
        strStuInfo = getContentArea(string, "<table", "/table>", 3).replace("&nbsp;", "");
        // System.out.println("提取出的string：\n" + strStuInfo);
        strStuInfo = replacettt(strStuInfo);
        // 下面要提取学生信息了
        Pattern pattern = Pattern.compile("<td>(.*?)</td>");
        Matcher matcher = pattern.matcher(strStuInfo);
        int i = 1;// 因为：本段文本是隔一个才有一个需要的信息，所有使用i计数
        while (matcher.find()) {
            switch (i) {// 姓名,学号,性别,学制 ,院系,专业,班级,入学日期
                case 2:
                    stu.setName(matcher.group(1));
                    break;
                case 4:
                    stu.setNumber(stunum = Integer.parseInt(matcher.group(1)));
                    break;
                case 6:
                    stu.setSex(replaceSpan(matcher.group(1)).replace("<span>", "").replace("</span>", ""));
                    break;
                case 8:
                    stu.setXuezhi(matcher.group(1));
                    break;
                case 10:
                    stu.setYuanxi(matcher.group(1));
                    break;
                case 12:
                    stu.setZhuanye(matcher.group(1));
                    break;
                case 14:
                    stu.setBanji(matcher.group(1));
                    break;
                case 16:
                    stu.setRuxueriqi(matcher.group(1));
                    break;
            }
            i++;
        }
        log.info("学生信息已获取 正在获取成绩" + stu.getNumber());
        stu.setGrade(getGrade(string, stunum));
        //log.Bean("学生当前成绩的科目数量:" + stu.grade.size());
        stu.setGradeNUmber(stu.grade.size());// 设置学生当前成绩的科目数量
        return stu;
    }

    // 提取各科成绩方法
    // 方案二 1 .先数出所有tr标签的个数 因为一个tr是一科成绩（包括标题 所以提取之后 第一组要去掉）
    // 写个函数 求tr的个数 gettrnum();已完成
    // 2.提取出所有的tr 存放到数组中 然后把第一个删除掉-_-|
    // 写个函数 先匹配一个tr 放入stack 然后往后走 再匹配一个 trstackt()
    // 3.使用循环每次提取一个tr的数据 存放到grade里面 然后塞入另一个stack 函数名regexGrade(stack)
    // 4.成绩全部提取出来

    static String RegexStr(String targetStr, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(targetStr);
        // 需要保存数据
        int i = 1;
        while (matcher.find()) {
            //Grade grade = new Grade();
            System.out.print("匹配结果" + i++);
            System.out.println(matcher.group());// .replaceAll("<td>",
            // "").replaceAll("&nbsp;", "")
            // System.out.println("tr的个数为"+matcher.groupCount());
        }
        System.out.println("lalalala");
        if (matcher.find()) {
            return matcher.group();
        }
        return "nothing";
    }

    // 写一个清除网页属性的函数
    // 将<table....> <tr......> <td......>分别替换成<table><tr><td>
    static String replacettt(String string) {
        return string.replaceAll("[\\<]td.*?[\\>]", "<td>").replaceAll("[\\<]tr.*?[\\>]", "<tr>")
                .replaceAll("[\\<]table.*?[\\>]", "<table>");
        // return string;
    }

    /**
     * 将<table....>替换成
     * <table>
     *
     * @param string
     * @return
     */
    static String replaceTable(String string) {
        return string.replaceAll("[\\<]table.*?[\\>]", "<table>");
    }

    /**
     * 将<td......>替换成
     * <td>
     *
     * @param string
     * @return
     */
    static String replaceTd(String string) {
        return string.replaceAll("[\\<]td.*?[\\>]", "<td>");
    }

    /**
     * 将 <tr......>分别替换成
     * <tr>
     *
     * @param string
     * @return
     */
    static String replaceTr(String string) {
        return string.replaceAll("[\\<]tr.*?[\\>]", "<tr>");
    }

    /**
     * @param string
     * @return
     */
    static String replaceSpan(String string) {
        return string.replaceAll("[\\<]span.*?[\\>]", "<span>");
    }

    /**
     * 保存成网页
     *
     * @param result
     * @return
     * @throws IOException
     */
    public static boolean savepage(String result, String xuehao) {
        File fp = new File(xuehao + ".html");
        String str = result;
        PrintWriter pfp = null;
        try {
            pfp = new PrintWriter(fp);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        pfp.print(str);
        pfp.close();
        return true;
    }

    static String readPage(String pagename) throws IOException {
        String string = null;
        @SuppressWarnings("resource")
        //网页的编码是gb2312  但文本的编码是utf-8......
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(pagename), "utf-8"));
        String line = null;
        while ((line = in.readLine()) != null) {
            string += line;
        }
        return string;

    }

    /**
     * 本函数求得是一段字符串中 tr的个数 额 tr也需要输入才行 所以 其他的个数 也可以求到
     *
     * @param targetStr  需匹配的字符串
     * @param patternStr 目标字符串
     * @return
     */
    static int getTrNum(String targetStr, String patternStr) {
        int n = 0;
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(targetStr);
        while (matcher.find()) {
            n++;
            // System.out.println("tr的个数为"+matcher.groupCount());
            // return matcher.groupCount();
        }
        // System.out.println("tr的个数为："+n);
        return n;
    }

    /**
     * 构造一个stack 存放数据 因为 取着方便
     * <p>
     * 此函数已可用
     *
     * @param string
     * @return
     */
    static Stack<String> trStack(String string) {
        // 提取到的效果
        // <td>&nbsp;军训&nbsp;</td><td>1</td><td>85</td><!--<td>3.7</td>--><td>&nbsp;必修&nbsp;</td><td>&nbsp;2013-2014(1)&nbsp;</td>
        // String string = null;
        // System.out.println("当前位置 trSet()");
        int first = 0, second = 0;
        // HashSet<String> trstring = new HashSet<>();
        Stack<String> stack = new Stack<>();
        // 第一行 提取的是标题栏 需要踢出去
        for (int i = 0; i < getTrNum(string, "/tr"); i++) {
            // System.out.println("匹配前状态\n" + "first=" + first + "second=" +
            // second);
            first = string.indexOf("<tr>", first) + "<tr>".length();// 求得tr的位置
            second = string.indexOf("</tr>", first);// 从first开始 匹配段落
            // System.out.println("匹配后状态\n" + "first=" + first + "second=" +
            // second);
            String tr = string.substring(first, second);// 提取两个tr之间的内容
            // System.out.println("trStack()得到的结果是：\n" + tr);
            // trstring.add(tr);
            if (i == 0) {
                //这一句 不应该在循环最开始吗
                continue;
            }
            stack.push(tr);
            // first=second;
        }
        return stack;
    }

    static Stack<Grade> regexGrade(Stack<String> stack, int stunum) {
        Stack<Grade> stackgrade = new Stack<>();
        // Grade grade = new Grade();不能写在这里,因为是同一个空间,后者会重写前者
        int i = 0;
        stack.pop();// 第一个是标题什么的 不要
        while (!stack.empty()) {
            Grade grade = new Grade();
            grade.setNumber(stunum);
            i = 0;
            // System.out.println("从stack里取到的数据为：" + stack.peek());
            Pattern pattern = Pattern.compile("<td>(.*?)</td>");// 正则表达式
            // 提取td中间的东西
            Matcher matcher = pattern.matcher(stack.pop().replace("&nbsp;", ""));// 将文本中的空格去除
            // 当然这一步可以在之前就解决
            while (matcher.find()) {
                i++;
                // System.out.println("匹配的内容为：" + matcher.group(1));
                switch (i) {// 一行文本中 有很多内容 根据不同的位置 放到不同的位置里
                    case 1:
                        grade.setKecheng(matcher.group(1));
                        break;
                    case 2:// 因为学分，成绩，绩点有小数的情况，自己写了个函数匹配 浮点数
                        grade.setXuefen(Strtofloat(matcher.group(1)));
                        break;
                    case 3:
                        grade.setChengji(Strtofloat(matcher.group(1)));
                        break;
                    case 4:
                        grade.setJidian(Strtofloat(matcher.group(1)));
                        break;
                    case 5:
                        grade.setShuxing(matcher.group(1));
                        break;
                    case 6:
                        grade.setTime(matcher.group(1));
                        break;
                    default:
                        log.info("提取的数据出错啦");
                }

            }
            // System.out.println("获取到的成绩为：" + grade);
            stackgrade.push(grade);
        }
        return stackgrade;
    }

    /**
     * //学分 成绩 绩点 有小数点 不能直接转换成数字 所有用了个麻烦的方法
     *
     * @param string
     * @return
     */
    static int StrtoInt(String string) {
        int result = 0;
        try {
            result = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            String[] strings = string.split("\\D+");
            result = Integer.parseInt(strings[0]) + Integer.parseInt(strings[1]) / 10;
        }
        return result;
    }

    /**
     * //学分 成绩 绩点 有小数点 不能直接转换成数字 所有用了个麻烦的方法
     *
     * @param string
     * @return
     */
    static float Strtofloat(String string) {
        //System.out.println("string的内容是：" + string+"长度"+string.length());
        float result = 0;
        if (string.length() == 0) {
            result = 0;
            //log.Bean("此科成绩为空 设置为0");
        } else {
            try {
                result = Integer.parseInt(string);
            } catch (NumberFormatException e) {
                //System.out.println("有小数点的数字转换");
                String[] strings = string.split("\\D+");
                result = (float) (Float.parseFloat(strings[0]) + Float.parseFloat(strings[1]) / 10.0);
            }
        }
        return result;
    }

    /**
     * 想写一个函数 直接获取成绩
     * <p>
     * 输入的是获取的成绩网页的文本格式 直接在这里加工成成绩输出
     *
     * @return stack
     */
    static Stack<Grade> getGrade(String string, int stunum) {
        string = replacettt(string);
        // 将标签简化 为后面提取打下基础
        // string = replaceTd(replaceTd(replaceTable(string)));
        // System.out.println("去除属性\n输出。。。" + string);
        // 获得成绩的文本区域 第4个table包含的是成绩
        string = getContentArea(string, "<table", "/table>", 4);
        Stack<String> stack = new Stack<>();
        stack = trStack(string);// 这个函数将上面的文本提取成一个一个的tr文本 方便后来提取成绩
        // System.out.println("以下测试的是获取成绩的函数");
        return regexGrade(stack, stunum);// 直接将存放成绩的stack返回
    }

    /**
     * 从数据库中获取学号和密码 筛选时按优先级排序 将优先级高的放在首位
     *
     * @return
     */
    public static Stack<IdAndPasswd> getStuIdandPasswd() {
        String sql = "select StuID,Passwd,Priority from Student order by Priority desc";
        Stack<IdAndPasswd> StuIdandPasswd = new Stack<>();

        Connection connection = null;
        Statement statement = null;
        try {
            connection = JDBCTools.getConnection();
            statement = connection.createStatement();
            // String sql="insert into Student(stunumber,name,sex,xuezhi,yuanxi)
            // values('1330090003','胡永涛','男','4','计信学院')";
            ResultSet rs = statement.executeQuery(sql);// 执行查询语句
            while (rs.next()) {
                IdAndPasswd id = new IdAndPasswd();
                id.setStuId(rs.getString(1).trim());// 由于数据库中密码长度为20位，实际密码没有这么长
                // 所有需要除去空白
                id.setPassswd(rs.getString(2).trim());
                // System.out.println("输出学号"+rs.getString(1)+"\t长度"+rs.getString(1).length());
                // System.out.println("输出密码"+rs.getString(2)+"\t长度"+rs.getString(2).trim().length());
                StuIdandPasswd.push(id);
                System.out.println(id);
            }
            System.out.println("获取学号和密码Success!");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } finally {
            JDBCTools.releaseDB(null, statement, connection);
        }
        return StuIdandPasswd;
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String cookie = GetCookie("http://jw.tjnu.edu.cn/").replace("; path=/", "");
        String postInfo = "uname=" + xuehao + "&upass=" + upass + "&submitgo=GO";
        // System.out.println("拼接的登陆信息为："+postInfo);
        sendPost("http://jw.tjnu.edu.cn/index.php", postInfo, cookie);
        // 进入成绩页面
        String gradePage = sendGet("http://jw.tjnu.edu.cn/jwgl/cjgl/bbdy/bottom.php?id=&page=", cookie);
        Logout("http://jw.tjnu.edu.cn/jwgl/logout.php", cookie);
        //System.out.println("获取网页信息 等待加工....");
        return gradePage;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
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
            log.info("GET something wrong！" + e);
//			System.out.println("发送GET请求出现异常！" + e);
//			e.printStackTrace();
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
//			System.out.println("发送GET请求出现异常！" + e);
            log.info("get cookie something wrong！" + e);
            //e.printStackTrace();
        }
        return cookie;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
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
            //System.out.println("POST提交的cookie为：\n" + cookie);
            //System.out.println(conn);
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
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info(" POST wrong！" + e);
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
            // Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new
            // InetSocketAddress("127.0.0.1", 8888));
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
            // String status = connection.getHeaderField("");
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gb2312"));
            // in = new BufferedReader(new InputStreamReader((InputStream)
            // connection.getContent(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            //System.out.println("发送GET请求出现异常！" + e);
            log.info("logout wrong！" + e);
            //e.printStackTrace();
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

    public static boolean deleteFiles() {
        String reg = ".*ilqs.*";
        //第一步  获得当前项目的路径
        String path = System.getProperty("user.dir");
        System.out.println(path);
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (File file1 : tempList) {
            if (file1.isFile()) {
                {
                    if (file1.toString().matches(reg)) {
                        System.out.println("Delete:"+file1);
                        file1.delete();
                    }
                }
            }
        }
        return true;

    }
}
