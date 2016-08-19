package com.cui.mail;

/**
 * 接收邮件
 */
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class RevMail {

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props);
		//Store store = session.getStore("imap");
		// 取得pop3协议的邮件服务器
		Store store = session.getStore("pop3");
		// 连接pop.qq.com邮件服务器
		store.connect("pop.163.com", "cuilovexing@163.com", "1994cuiminghui");
		// 返回文件夹对象
		Folder folder = store.getFolder("INBOX");
		// 设置仅读
		folder.open(Folder.READ_ONLY);
		// 获取信息
		Message message[] = folder.getMessages();
		for (int i = message.length - 1; i > 0; i--) {
			// 打印主题
			System.out.println(message[i].getSubject());
			System.out.println("-----------------------------------------");
		}
		folder.close(true);
		store.close();
	}
}