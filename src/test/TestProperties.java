package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {
public static void main(String[] args) throws IOException {
	Properties pro=new Properties();
	//InputStream inStream=getClass().getResourceAsStream("mail.properties");
	//InputStream inStream=new BufferedInputStream(new FileInputStream("src/mail.properties"));
	pro.load(new BufferedInputStream(new FileInputStream("src/mail.properties")));
	System.out.println(pro);
	String value=pro.getProperty("mail.smtp.auth");
	System.out.println(value);
}
}
