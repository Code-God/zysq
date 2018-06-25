/******************************************************************************** 
 * Create Author   : Administrator
 * Create Date     : Aug 17, 2009
 * File Name       : EmailServiceImpl.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产品的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.services.system;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.ServerBeanFactory;
import com.base.mail.EmailDispatcher;
import com.wfsc.common.bo.system.EmailServerConfig;
import com.wfsc.daos.email.EmailServerDao;


/**
 * @author Administrator
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
@Service("emailService")
public class EmailServiceImpl implements IEmailService {
	@Autowired
	private EmailServerDao emailServerDao;

	private String initEmailServerStatus = "unknow";// 测试邮件服务器的初始状态为“未知”

	public EmailServerDao getEmailServerDao() {
		return emailServerDao;
	}

	public void setEmailServerDao(EmailServerDao emailServerDao) {
		this.emailServerDao = emailServerDao;
	}

	@Override
	public EmailServerConfig getEmailServerConfig() {
//		EmailServerConfig cfg = new EmailServerConfig();
//		cfg.setFromAddress("admin@amylove.cn");
//		cfg.setPassword("only1flower");
//		cfg.setPort(465);
//		cfg.setSubjectPrefix("AmyLove");
//		cfg.setSmtpAddress("smtp.exmail.qq.com");
//		cfg.setUserName("admin@amylove.cn");
//		return cfg;
		List<EmailServerConfig> list = emailServerDao.getAllEntities();
		if (CollectionUtils.isEmpty(list))
			return null;
		else
			return list.get(0);
	}

	@Override
	public boolean isConfigRight(EmailServerConfig config) {
		if (config.getFromAddress() == null || config.getFromAddress().equals(""))
			return false;
		if (config.getPassword() == null || config.getPassword().equals(""))
			return false;
		if (config.getSmtpAddress() == null || config.getSmtpAddress().equals(""))
			return false;
		if (config.getUserName() == null || config.getUserName().equals(""))
			return false;
		return true;
	}

	@Override
	public boolean saveEmailServerConfig(EmailServerConfig config) {
		try {
			//1. 保存到数据库
			List<EmailServerConfig> originalEmailServers = emailServerDao.getAllEntities();
			if (!CollectionUtils.isEmpty(originalEmailServers))
				emailServerDao.deleteAllEntities(originalEmailServers);
			emailServerDao.saveEntity(config);
			
			//2. 更新到内存
			EmailDispatcher emailDispatcher = (EmailDispatcher) ServerBeanFactory.getBean("mailDispather");
			emailDispatcher.updateMailSetting(config);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean testEmailServer(EmailServerConfig config) {
		RealTestEmaiServer realTestThread = new RealTestEmaiServer(config);
		realTestThread.start();
		for (int i = 0; i < 10; i++) {// 如果十秒还没有检测成功，则认定邮件服务器是不通的
			try {
				Thread.sleep(1000);
				if (initEmailServerStatus.equals("success")) {
					initEmailServerStatus = "unknow";// 将邮件服务器的状态还原
					return true;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		initEmailServerStatus = "unknow";
		return false;
	}

	class RealTestEmaiServer extends Thread {

		private EmailServerConfig mailSender;

		public RealTestEmaiServer(EmailServerConfig mailSender) {
			this.mailSender = mailSender;
		}

		public void run() {
			Properties props = new Properties();
			props.put("mail.smtp.host", mailSender.getSmtpAddress());
			props.put("mail.smtp.port", mailSender.getPort());
			props.put("mail.smtp.auth", "true"); // 允许smtp校验
			Session session = Session.getInstance(props, null);
			Transport transport = null;
			try {
				transport = session.getTransport("smtp");
				String passWord = new String(mailSender.getPassword().toCharArray());
				transport.connect(mailSender.getSmtpAddress(), mailSender.getUserName(), passWord);
				Message msg = new MimeMessage(session);
				msg.setSubject("测试邮件服务器");
				msg.setFrom(new InternetAddress(mailSender.getFromAddress()));// 设置发件人地址
				Address[] addressTo = { new InternetAddress(mailSender.getFromAddress()) };
				msg.setRecipients(Message.RecipientType.TO, addressTo);// 设置收件人地址
				msg.setSentDate(new java.util.Date());// 设置邮件发送时间
				msg.setText("这封邮件是系统用来测试Email服务器是否可用，请不要回复！");
				msg.saveChanges(); // 保存发送信息
				transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO)); // 发送邮件
				initEmailServerStatus = "success";
			} catch (Exception ex) {
				initEmailServerStatus = "failed";
			} finally {
				if (transport != null)
					try {
						transport.close();
					} catch (javax.mail.MessagingException e1) {
						e1.printStackTrace();
					}
			}
		}
	}
}
