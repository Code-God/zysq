/******************************************************************************** 
 * Create Author   : Andy Cui
 * Create Date     : Oct 14, 2009
 * File Name       : EmailDispatcherCenter.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.base.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.wfsc.common.bo.system.EmailServerConfig;
import com.wfsc.mail.BindCodeEmailGenerator;
import com.wfsc.mail.CommonEmailGenerator;
import com.wfsc.mail.RegisterEmailGenerator;
import com.wfsc.services.system.IEmailService;
import com.wfsc.util.CipherUtil;


/**
 * <code>EmailDispatcherCenter</code>是一个邮件派发线程，系统启动时由Spring负责初始化，串行的从队列中取出
 * 需要发送邮件的数据，发送给相关邮件接收人。
 * <p>性能出现问题时，可以优化，多线程来发送邮件，需要保证任何需要发送的邮件能够在5分钟内发送出去，最大延迟时间不得超过5分钟。
 * 
 * @author Andy Cui
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
@Service("mailDispather")
public class EmailDispatcher extends Thread {

	private static Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);

	/**
	 * 阻塞队列，用来保存需要发送出去的邮件对象，当队列为空时，线程将阻塞直到有新的邮件被加入到队列中来。
	 */
	private BlockingQueue<Email> mailQueue = new LinkedBlockingQueue<Email>();

	/**
	 * 真正的邮件发送器，我们采用Spring框架提供的工具类来发送，比较简单和方便
	 */
	private JavaMailSenderImpl sender;

	/**
	 * 系统邮件服务接口，用来获取邮件配置
	 */
	@Autowired
	private IEmailService mailService;

	/**
	 * 系统配置的邮件服务器各项参数，一直保存在内存中，当用户重新改变了邮件服务器参数时，需要更新这个值，如果不更新将导致邮件发送失败或错误
	 */
	private EmailServerConfig mailConfiguration;

	/**
	 * 异步发送一封邮件，需要发送的模块不要管具体逻辑只需要调用该方法丢进来即可，方法会迅速返回而不会等到邮件发送结束才返回。
	 * 
	 * @param email	要发送的邮件对象
	 */
	public void dispatchMail(Email email) {
		if (email == null) {
			throw new NullPointerException("Email object can not be null.");
		}
		if(mailConfiguration == null) {//系统没有配置邮件服务器参数，不能发送邮件，直接跳过，不放到队列中去
			mailConfiguration = mailService.getEmailServerConfig();
			if (mailConfiguration == null) {
				logger.warn("No mail server setting found, mail system will not work.");
				return;
			}
		}
		mailQueue.offer(email);
	}

	/**
	 * 做一些初始化的工作，从数据库读出邮件服务器配置参数
	 */
	public void initMailSetting() {
		sender = new JavaMailSenderImpl();
		mailConfiguration = mailService.getEmailServerConfig();
		if (mailConfiguration == null) {
			logger.warn("No mail server setting found, mail system will not work.");
			return;
		}
		sender.setHost(mailConfiguration.getSmtpAddress());
		sender.setUsername(mailConfiguration.getUserName());
		sender.setPassword(mailConfiguration.getPassword());
//		sender.setPort(mailConfiguration.getPort());
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		sender.setJavaMailProperties(props);
		
		if(logger.isInfoEnabled()) {
			logger.info("Initialize mail system successfully.");
		}
	}

	/**
	 * 用户更改了邮件服务器配置，因此需要重新设定发送邮件服务器的相关参数，否则会导致邮件发送出现问题。
	 */
	public void updateMailSetting(EmailServerConfig newConfiguration) {
		if(newConfiguration == null) {
			throw new IllegalArgumentException("Mail configuration can not be null.");
		}
		mailConfiguration = newConfiguration;
		sender.setHost(mailConfiguration.getSmtpAddress());
		sender.setUsername(mailConfiguration.getUserName());
		sender.setPassword(CipherUtil.decodeBase64(mailConfiguration.getPassword()));
		sender.setPort(mailConfiguration.getPort());
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		sender.setJavaMailProperties(props);

		if(logger.isInfoEnabled()) {
			logger.info("Re-config mail server setting now.");
		}
	}

	@SuppressWarnings("unchecked")
	public void run() {
		Email email = null;
		try {
			while ((email = mailQueue.take()) != null) {
				try {//留时间给更新数据的线程提交事务
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					String code = email.getCode();
					HtmlEmailGenerator generator = null;
					if(code.equals("register")){
						generator = new RegisterEmailGenerator(email);
					}else if(code.equals("info")){
						generator = new CommonEmailGenerator(email);
					}else if(code.equals("bind")){//重新生成绑定码的邮件
						generator = new BindCodeEmailGenerator(email);
					}
					sendEmail(generator.getMailTitle(), generator.getMailContent(), true, generator.getMailReceivers());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Send mail: " + email.getCode() + " failed.");
				} 
			}
		} catch (InterruptedException e) {
			logger.fatal("Email dispatcher thread has been interrupted, mail system will not work now, error is:\n" + e);
		}
	}

	/**
	 * 发送邮件给指定的接收者
	 * TODO: 在短时间内连接邮件服务器发送大量邮件，可能被邮件服务器认为非法，邮件服务器可能暂停提供邮件服务，这里需要做个处理，不要短时间发
	 * 大量邮件出去。
	 * 
	 * @param title 邮件标题
	 * @param content 邮件正文
	 * @param receiver 邮件接收者地址
	 * @param asHtml True以HTML格式发送邮件，false以纯文本格式发送邮件
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	private void sendEmail(String title, String content, boolean asHtml, List<String> mailReceivers)
			throws MessagingException, UnsupportedEncodingException {
		if(StringUtils.isEmpty(title)) {
			throw new IllegalArgumentException("Mail title can not be empty.");
		}
		if(StringUtils.isEmpty(content)) {
			throw new IllegalArgumentException("Mail content can not be empty.");
		}
		if(CollectionUtils.isEmpty(mailReceivers)) {
			throw new IllegalArgumentException("Mail receivers can not be empty.");
		}
		if (!mailReceivers.isEmpty()) {
			Set<String> receivers = new HashSet<String>(mailReceivers);
			List<String> ems = new ArrayList<String>();
			//对邮件列表进行过滤，有空的就去掉
			for (String str : receivers) {
				if(!StringUtils.isBlank(str)){
					ems.add(str);
				}
			}
			logger.info("ems===" + ems.toString());
			if(!ems.isEmpty()){
				try{
					MimeMessage mm = sender.createMimeMessage();
					MimeMessageHelper sendMail = new MimeMessageHelper(mm, true, "UTF-8");
					sendMail.setSubject(title);
					sendMail.setFrom(mailConfiguration.getFromAddress());
					sendMail.setTo(ems.toArray(new String[0]));//同时发送给多个人
					sendMail.setText(content, asHtml);
					sender.send(mm);
					logger.info("邮件已发送......");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				logger.info("没有可发送的邮件列表，不发送邮件....");
			}
		}
	}
	
	/**
	 * 
	 * 发送带附件的邮件
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param attachPath 附件路径列表
	 * @param receiverAddress 收件人列表
	 */
	public void sendAttachmentEmail(String title,String content,List<String>attachPaths,List<String>receiverAddress)throws MessagingException, UnsupportedEncodingException{
		if(!receiverAddress.isEmpty()){
			sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
			MimeMessage mm = sender.createMimeMessage();
			MimeMessageHelper sendMail = new MimeMessageHelper(mm, true, "GBK");
			sendMail.setSubject(title);
			sendMail.setTo(receiverAddress.toArray(new String[0]));
			sendMail.setFrom(mailConfiguration.getFromAddress());
			sendMail.setText(content);
			if (!CollectionUtils.isEmpty(attachPaths)) {// 如果附件，则上传附件
				for (String filePath : attachPaths) {
					File file = new File(filePath);
					if (file.exists()) {
						String fileName = file.getName();
						FileSystemResource attachFile = new FileSystemResource(new File(filePath));
						sendMail.addAttachment("=?GBK?B?"+enc.encode(fileName.getBytes())+"?=", attachFile);
					}
				}
			}
			sender.send(mm);
		}
		
	}

	/**
	 * @param mailService the mailService to set
	 */
	public void setMailService(IEmailService mailService) {
		this.mailService = mailService;
	}
}