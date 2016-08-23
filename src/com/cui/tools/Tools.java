package com.cui.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Tools {
    static Logger log = LoggerFactory.getLogger(Tools.class);

    public static void main(String[] args) {
        deleteFiles();
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
        String line;
        while ((line = in.readLine()) != null) {
            string += line;
        }
        return string;

    }


    /**
     * 获取成绩页面，输入是学号，密码，输出成绩页面(String类型)
     *
     * @param xuehao
     * @param upass
     * @return
     */
    public static String getGradePage(String xuehao, String upass) {
        // 从主页获取cookie
        String cookie = GetCookie("http://jw.tjnu.edu.cn/").replace("; path=/", "");
        String postInfo = "uname=" + xuehao + "&upass=" + upass + "&submitgo=GO";
        sendPost("http://jw.tjnu.edu.cn/index.php", postInfo, cookie);
        // 进入成绩页面
        String gradePage = sendGet("http://jw.tjnu.edu.cn/jwgl/cjgl/bbdy/bottom.php?id=&page=", cookie);
        Logout("http://jw.tjnu.edu.cn/jwgl/logout.php", cookie);
        return gradePage;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url    发送请求的URL
     * @param cookie 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    private static String sendGet(String url, String cookie) {
        String result = "";
        BufferedReader in = null;
        try {
            // String urlNameString = url + "?" + param;
            URL realUrl;
            realUrl = new URL(url);
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
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gb2312"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("GET something wrong！" + e);
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

    private static String GetCookie(String url) {
        String cookie = null;
        try {
            URL realUrl;
            realUrl = new URL(url);
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
    private static String sendPost(String url, String param, String cookie) {
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
    private static String Logout(String url, String cookie) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
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
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gb2312"));
        } catch (Exception e) {
            log.info("logout wrong！" + e);
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

    private static boolean deleteFiles() {
        String reg = ".*ilqs.*";
        int i = 0;
        //第一步  获得当前项目的路径
        String path = System.getProperty("user.dir");
        System.out.println(path);
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (File file1 : tempList) {
            if (file1.isFile()) {
                {
                    if (file1.toString().matches(reg)) {
                        System.out.println("Delete:" + file1);
                        file1.delete();
                        i++;
                    }
                }
            }
        }
        System.out.println("一共删除了" + i + "个文件");
        return true;
    }
}
