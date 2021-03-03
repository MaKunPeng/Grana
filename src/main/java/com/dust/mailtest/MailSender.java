package com.dust.mailtest;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender implements Runnable {
	/**
	 * 邮件服务器
	 */
	private static final String MAIL_HOST = "mail.smtp.host";
	private static final String MAIL_HOST_VALUE = "smtp.qq.com";
	/**
	 * 发件地址
	 */
	private static final String MAIL_FROM_VALUE = "1033928046@qq.com";
	/**
	 * 发送授权码
	 */
	private static final String MAIL_FROM_PASSWORD = "fwxokdimccksbcij"; //QQ
	/**
	 * 发送邮件的编码格式
	 */
	private static final String MAIL_ENCODING_LAYOUT = "text/html;charset=utf-8";
	/**
	 * 邮件认证
	 */
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	/**
	 * 调试模式
	 */
	private static final String MAIL_SMTP_DEBUG = "mail.debug";
	/**
	 * 确认
	 */
	private static final String CONFIRM = "true";
	
	/**
	 * 收件人地址
	 */
	private String receiverEmail;
	/**
	 * 后台根据帐号生成的唯一激活码
	 */
	private String activateCode;
	
	public MailSender(String receiverEmail, String activateCode) {
		this.receiverEmail = receiverEmail;
		this.activateCode = activateCode;
	}

	@Override
	public void run() {
		// 获取系统属性
		Properties props = System.getProperties();
		// 设置邮件服务器
		props.setProperty(MAIL_HOST, MAIL_HOST_VALUE);
		// 打开认证
		props.setProperty(MAIL_SMTP_AUTH, CONFIRM);
		// 打开调试输出
		props.setProperty(MAIL_SMTP_DEBUG, CONFIRM);
		
		try {
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// 发件人邮箱账号、授权码
	                return new PasswordAuthentication(MAIL_FROM_VALUE, MAIL_FROM_PASSWORD); 
				}
			});
			
			// 创建邮件对象
			Message message = new MimeMessage(session);
			// 设置发件人
			message.setFrom(new InternetAddress(MAIL_FROM_VALUE));
			// 设置收件人
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
			// 设置邮件主题
			message.setSubject("Dust - 帐号激活");
			// 设置邮件内容
			String content = "<html><head><title>Dust - 帐号激活</title></head><body><h1>这是一封激活邮件,激活请点击以下链接。该链接五分钟内有效。</h1>"
					+ "<h3>"
					+ "<a href='http://localhost:8080/RegisterDemo?code=" + activateCode + "'>"
							+ "http://localhost:8080/RegisterDemo?code=" + activateCode
                    + "</href>"
                    + "</h3></body></html>";
			message.setContent(content, MAIL_ENCODING_LAYOUT);
			
			// 保存文件
			// 由于Transport.send()中已经调用了保存方法故可省略
			// message.saveChanges();
			
			// 发送邮件
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
