package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class TestProperties {
	public static void main(String[] args) throws IOException {
		Properties pro = new Properties();
		// InputStream
		// inStream=getClass().getResourceAsStream("com.cui.mail.properties");
		// InputStream inStream=new BufferedInputStream(new
		// FileInputStream("src/com.cui.mail.properties"));
//		pro.load(new BufferedInputStream(new FileInputStream("src/com.cui.mail.properties")));
//		System.out.println(pro);
//		String value = pro.getProperty("com.cui.mail.smtp.auth");
//		System.out.println(value);
		
		//获取操作系统类型
//		Map<String,String> map=System.getenv();
		String os=System.getenv("OS");
		String c3p0="TJNUSQLite";
		if("Windows_NT".equals(System.getenv("OS"))){
			c3p0="TJNU";
		}
		System.out.println(os);
	}
}
