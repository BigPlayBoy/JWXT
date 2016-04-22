package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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

import info.Grade;
import info.IdAndPasswd;
import info.Student;

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
		// 测试获取学生信息
		//Student stu = getStuInfo("123");
		// System.out.println(stu);
		// SQLTools.saveStudent(stu);
		//Stack<IdAndPasswd> idandpasswd = getStuIdandPasswd();

		// Stack<Grade> stack = getGrade("123");
		// for (Grade grade : stack) {
		// System.out.println(grade);
		// }
		// for (Iterator iterator = stack.iterator(); iterator.hasNext();) {
		// Grade grade = (Grade) iterator.next();
		// System.out.println(grade);
		// }
		// while (!stack.empty()) {
		// System.out.println(stack.pop());
		// }
		// Tools tool = new Tools();
		// String html =
		// "<metahttp-equiv=Content-Typecontent=text/html;charset=gb2312><html><head><title>信息</title><metahttp-equiv=Content-Typecontent=text/html;charset=gb2312><linkhref=../../css.cssrel=stylesheettype=text/css><scripttype=text/javascriptsrc=../../js/jquery.js></script></head><bodyleftmargin=0topmargin=0marginwidth=0marginheight=0><!--学分汇总表--><style>.bold{font-weight:bold;}</style><divid=datapostalign=center><tablewidth=100%border=0cellspacing=0cellpadding=0><tr><tdwidth=100%height=48align=center><spanclass=STYLE6>天津师范大学学生历年学习成绩列表</span></td></tr></table><tablewidth=100%border=0cellpadding=0cellspacing=0bgcolor=#000000><tr><tdcolspan=2bgcolor=#000000><tablewidth=100%border=0align=centercellpadding=0cellspacing=1bordercolorlight=#999999bordercolordark=#ffffffclass=font><tr><tdwidth=11%height=29align=centerbgcolor=#FFFFFF>姓名</td><tdwidth=26%align=centerbgcolor=#FFFFFF>&nbsp;任重远&nbsp;</td><tdwidth=9%align=centerbgcolor=#FFFFFF>学号</td><tdalign=centerbgcolor=#FFFFFF>&nbsp;1330090005&nbsp;</td><tdwidth=7%align=centerbgcolor=#FFFFFF>性别</td><tdwidth=14%align=centerbgcolor=#FFFFFF><spanclass=STYLE13>&nbsp;男性&nbsp;</span></td><tdwidth=7%align=centerbgcolor=#FFFFFF>学制</td><tdwidth=15%align=centerbgcolor=#FFFFFF>&nbsp;4&nbsp;年</td></tr><tr><tdheight=26align=centerbgcolor=#FFFFFF>院系</td><tdheight=26colspan=2align=centerbgcolor=#FFFFFF>&nbsp;计算机与信息工程学院&nbsp;</td><tdwidth=11%align=centerbgcolor=#FFFFFF>专业</td><tdheight=26colspan=4align=centerbgcolor=#FFFFFF>&nbsp;计算机科学与技术&nbsp;</td></tr><tr><tdheight=28align=centerbgcolor=#FFFFFF>班级</td><tdheight=28colspan=2align=centerbgcolor=#FFFFFF>&nbsp;计算机1301&nbsp;</td><tdheight=28align=centerbgcolor=#FFFFFF>入学日期</td><tdheight=28colspan=4align=centerbgcolor=#FFFFFF>&nbsp;2013-09-06&nbsp;</td></tr></table></td></tr><tr><tdheight=21colspan=2valign=top><tablewidth=100%border=0cellspacing=1cellpadding=0><trclass=font><tdalign=centervalign=middlebgcolor=#FFFFFF>课程名称</td><tdalign=centervalign=middlebgcolor=#FFFFFF>学分</td><tdalign=centervalign=middlebgcolor=#FFFFFF>成绩</td><!--<tdalign=centervalign=middlebgcolor=#FFFFFF>绩点</td>--><tdalign=centervalign=middlebgcolor=#FFFFFF>属性</td><tdalign=centervalign=middlebgcolor=#FFFFFF>考试时间</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;高等数学2-1&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.5</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>77</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;线性代数&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>67</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;信息科学导论&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>68</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;体育4-1&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>92</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;大学英语2-1&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>5</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>75</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;心理健康&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>79</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;军训&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>85</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;C语言程序设计&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>6</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>66</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;高等数学2-2&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>5</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>78</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;离散数学&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>78</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;网页设计基础&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>80</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;电路原理&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>79</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;思想道德修养与法律基础&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>78</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;大学生职业发展与就业指导&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>84</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;任选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;体育4-2&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>93</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;大学英语2-2&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>5</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>79</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;军事理论&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>82</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2013-2014(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;大众传媒与流行文化&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>65</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;任选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;数字电路&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>83</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;数据结构&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>73</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;数据结构实验&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>94</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;概率论与数理统计&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>94</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;《信息科学专题系列》讲座&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>85</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;任选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;多媒体软件开发&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>73</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;计算机维修与维护&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>80</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;算法设计与分析&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>73</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;中国近现代史纲要&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>69</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;各类讲座&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>90</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;任选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;体育4-3&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>94</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;外国小说选读&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>79</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;生活健康与生殖健康&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>77.6</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>0</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;人工智能导论&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>79</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;机器人设计I&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>91</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;操作系统实验&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>72</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;马克思主义基本原理&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>73</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;计算机网络&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>91</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;C++与面向对象技术&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>92</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;操作系统&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>83</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;体育4-4&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>89</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;计算机网络实验&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>91</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2014-2015(2)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;欧洲博物馆美术作品赏析&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>87</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;任选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2015-2016(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;数据库原理&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>90</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2015-2016(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;计算机组成原理&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>86</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.7</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2015-2016(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;Java程序设计&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>95</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2015-2016(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;计算机组成原理实验&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>1</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>70</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;必修&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2015-2016(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;电子商务&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>84</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>3.3</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2015-2016(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;机器人设计II&nbsp;</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>2</td><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>93</td><!--<tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>4</td>--><tdalign=centervalign=middlenowrapbgcolor=#FFFFFF>&nbsp;限选&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;2015-2016(1)&nbsp;</td></tr><trclass=font><tdalign=leftvalign=middlenowrapbgcolor=#FFFFFF>&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;</td><!--<tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;</td>--><tdalign=centervalign=middlebgcolor=#FFFFFF>&nbsp;</td><tdalign=centervalign=middlebgcolor=#FFFFFF>――</td></tr></table></td></tr><tr><tdwidth=51%><tablewidth=100%height=75border=0cellpadding=0cellspacing=1class=font><tr><tdwidth=21%height=31align=centerbgcolor=#FFFFFF>已获总学分</td><tdalign=centerbgcolor=#FFFFFF>&nbsp;120.5&nbsp;</td></tr><tr><tdheight=33align=centerbgcolor=#FFFFFF>备注</td><tdbgcolor=#FFFFFF><!--凡是“学分”一栏中学分数为零的课程，不计入平均学分绩点。--></td></tr></table></td><tdwidth=49%><tablewidth=100%height=75border=0cellpadding=0cellspacing=1class=font><tr><tdwidth=36%height=27valign=topbgcolor=#FFFFFF>&nbsp;教学院长签字：</td><tdwidth=64%valign=topbgcolor=#FFFFFF>&nbsp;<!--报表：--><!--国家学生体质健康标准：--><br>&nbsp;<br>&nbsp;&nbsp;<br>&nbsp;</td></tr></table></td></tr></table><tablewidth=100%border=0cellspacing=0cellpadding=0><tr><tdheight=21class=font>注：重-重修课程辅-辅修课程缓-缓考缺-缺考弊-作弊或作弊后重修</td></tr></table><tablewidth=100%border=0cellspacing=0cellpadding=0><tr><tdwidth=38%height=21class=font>制表人：</td><tdwidth=38%class=font>制表日期：2016-03-10</td><tdwidth=24%align=rightvalign=bottomclass=font>&nbsp;&nbsp;&nbsp;</td></tr></table></div></body></html>";
		// String str =
		// tool.getContentArea(html.replaceAll("[\\<]table.*?[\\>]", "<table>"),
		// "<table", "/table>", 3);
		// System.out.println(str);
		// str = str.replaceAll("[\\<]td.*?[\\>]", "<td>");// [\<]td.*?[\>]将 <td
		// // .....>转换为<td>
		// System.out.println(str);
		// str = str.replaceAll("[\\<]tr.*?[\\>]", "<tr>");// 整理tr
		// System.out.println(str);
		/// (?<=<td>).*?(?=</td>)
		// str = str.replaceAll("(?<=<tr>).*?(?=</tr>)", "</tr>");
		// str = RegexStr(str, "<tr>(.*?)</td>");
		// System.out.println(str);
		// String string = null;
		//
		// try {// 读取所需要的文本
		// string = readPage("1.html");
		// System.out.println("文件读取成功！\n正在输出" + string);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// string = replacettt(string);
		// System.out.println("去除属性\n输出。。。" + string);
		// string = getContentArea(string, "<table", "/table>", 3);
		// System.out.println("提取出的string：\n" + string);
		// getTrNum(string, "/tr");
		// RegexStr(string, "/tr");

		// Stack<String> stack = new Stack<>();
		// stack = trStack(string);
		// for (String string2 : stack) {
		// System.out.println("stack里面放的是" + string2);
		// }
		// System.out.println("以下测试的是获取成绩的函数");
		// regexGrade(stack);
		// System.out.println("打印stack：");
		// System.out.println(stack);
		// System.out.println("stack"+stack.size());
		// System.out.println(replacettt(string));
		// try {
		// savepage(string);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
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
		log.info("学生信息已获取 正在获取成绩"+stu.getNumber());
		stu.setGrade(getGrade(string, stunum));
		//log.info("学生当前成绩的科目数量:" + stu.grade.size());
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
	 * 
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
	static boolean savepage(String result) throws IOException {
		File fp = new File("b.html");
		String str = result;
		PrintWriter pfp = new PrintWriter(fp);
		pfp.print(str);
		pfp.close();
		return false;
	}

	static String readPage(String pagename) throws IOException {
		String string = null;
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(pagename), "GBK"));
		String line = null;
		while ((line = in.readLine()) != null) {
			string += line;
		}
		return string;

	}

	/**
	 * 本函数求得是一段字符串中 tr的个数 额 tr也需要输入才行 所以 其他的个数 也可以求到
	 * 
	 * @param targetStr
	 *            需匹配的字符串
	 * @param patternStr
	 *            目标字符串
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
	 * 
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
		if(string.length()==0){
			result=0;
			//log.info("此科成绩为空 设置为0");
		}else{
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
	 * 
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
			log.info("发送GET请求出现异常！" + e);
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
			log.info("get cookie出现异常！" + e);
			//e.printStackTrace();
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
			// 这里成功拿到cookie
			// String head = conn.getHeaderField("Set-Cookie");
			// System.out.println("返回的Cookie is \n\t" + head);
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.info("发送 POST 请求出现异常！" + e);
//			System.out.println("发送 POST 请求出现异常！" + e);
//			e.printStackTrace();
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
			log.info("退出发生异常！" + e);
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

}
