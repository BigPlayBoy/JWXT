package tools;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		System.out.println("开始");
		int i = 1;
		Element table = getTable(3);//匹配到成绩信息
		Elements tr=table.getElementsByTag("tr");//获得每一行的信息
		for (Iterator iterator = tr.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
//			System.out.println(element);
			
			System.out.println("Node name="+element.children().text());
		}
		System.out.println("结束");
	}

	public static void addUrl(Stack<String> urls) {
		while (!urls.isEmpty()) {
			String url = urls.pop();
			System.out.println(url);
			try {
//				PageDao.addUrl(url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Element getTable(int index) {
		File input = new File("E://1330090002.html");
		Document doc;
		Element table = null;
		try {
			doc = Jsoup.parse(input, "gb2312", "");
			Elements tables = doc.getElementsByTag("table");
			//System.out.println(tables);
//			dSystem.out.println("tables的儿子数"+tables.size());
			table = tables.get(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return table;
	}
	/**
	 * 通过链接获得
	 * @param url
	 * @param index
	 * @return
	 */
	public static Element getTable(String url,int index) {
		//File input = new File("net.html");
		Document doc;
		Element table = null;
		doc = Jsoup.parse(url, "gb2312");
		Elements tables = doc.getElementsByTag("table");
		table = tables.get(index);
		return table;
	}

	public static Elements getTrs(Element table) {
		Elements trs = null;
		// File input = new File("net.html");
		// Document doc;
		// doc = Jsoup.parse(input, "gb2312", "");
		trs = table.getElementsByTag("tr");
		return trs;
	}

	public static Element getTd(Elements tr) {
		return tr.get(1);// 获得帖子的入口
	}

	public static Elements getTds() {
		Elements tds = null;
		File input = new File("net.html");
		Document doc;
		try {
			doc = Jsoup.parse(input, "gb2312", "");
			tds = doc.getElementsByTag("td");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tds;
	}

	/**
	 * 匹配出所有的网址 放到堆栈中
	 * 
	 * @param tdstack
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Stack<String> getPhotoUrl(Stack<Element> tdstack) {
		Stack<String> photoUrl = new Stack<String>();
		int i = 0;
		while (!tdstack.isEmpty()) {
			Element td = tdstack.pop();
			String url = "http://dc.ididcl.co/";
			try {
				url = url+td.child(0).attributes().get("href");
//				System.out.println(i++ + url);
			} catch (Exception e) {
				System.out.println("Index: 0, Size: 0");
			}
			if (!"http://dc.ididcl.co/".equals(url)) {
				photoUrl.push(url);
			}
		}
		return photoUrl;
	}

	/**
	 * 解析出包含链接的td标签
	 * 
	 * @param table
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Stack<Element> getTdStack(Element table) {
		Elements trs = getTrs(table);
		Stack<Element> tdstack = new Stack<Element>();
		for (Iterator iterator = trs.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			try {
				tdstack.push(element.child(0));
			} catch (Exception e) {
				System.out.println("没有孩子");
			}
		}
		return tdstack;
	}

	public static String getNextPage() {
		Element table = getTable(6);
		System.out.println(table);
		return null;

	}

	
	public static Elements getInput(String page){
		Document doc=Jsoup.parse(page);
		Elements inputs=doc.children();
		System.out.println(inputs);
		
		return inputs;
	}
	
}
