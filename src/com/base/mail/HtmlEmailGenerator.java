/******************************************************************************** 
 * Create Author   : Andy Cui
 * Create Date     : Oct 19, 2009
 * File Name       : ProcessEmailHtmllGenerator.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.base.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.ObjectFindingVisitor;

import com.base.log.LogUtil;
import com.base.tools.Version;
import com.wfsc.daos.user.UserDao;


/**
 * <code>HtmlEmailGenerator</code>代表一个HTML邮件内容产生器，Apex-OssWorks系统中需要发送HTML格式的富文本邮件，
 * 这里采取的方案是从JVM内部发起HTTP请求，直接请求JBOSS服务器将相关HTML页面源文件抓取下来，做相应的处理后（去掉上面的菜单）。
 * <p>
 * 当前系统中共有下列几处需要发送邮件的地方：
 * <ul>
 * <li> 
 * <li> 
 * <li> 
 * </ul>
 * 
 * @author Jacky
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
public abstract class HtmlEmailGenerator {
	
	protected static Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	protected UserDao userDao;

	protected Email email;

	protected String httpServerBasePath;

	private final static String START_LINE = "<!-- main menu start -->";
	
	private final static String END_LINE = "<!-- main menu end -->";
	
	public HtmlEmailGenerator(Email email) {
		this.email = email;
		String serverIpAddr = System.getProperty("jboss.bind.address");
		logger.info("jboss.bind.address---------------"+serverIpAddr);
		if (StringUtils.isEmpty(serverIpAddr) || "0.0.0.0".equals(serverIpAddr) || "127.0.0.1".equals(serverIpAddr)) {// 取不到服务器IP地址
			try {
				serverIpAddr = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			if(serverIpAddr == null)  serverIpAddr = "81";
		}
		httpServerBasePath = "http://" + Version.getInstance().getProperty("server.ip") + ":"+ Version.getInstance().getProperty("server.port") +"/fenxiao";
		logger.info("httpServerBasePath---------------"+httpServerBasePath);
//		noticeDao = (NoticeDao) ServerBeanFactory.getBean("noticeDao");
//		userDao = (UserDao) ServerBeanFactory.getBean("userDao");
//		knowledgeDao = (KnowledgeDao) ServerBeanFactory.getBean("knowledgeItemDao");
//		workflowBaseDao = (WorkflowBaseDao) ServerBeanFactory.getBean("workflowBaseDao");
	}

	/**
	 * 发起HTTP请求，获取HTML页面源文件
	 * 
	 * @return 待发送的邮件正文，如果请求http服务器错误（比如工单已经被删除）则返回null
	 */
	public String getMailContent() {
		String httpUrl = getHttpUrl();
		logger.info(httpUrl);
		HttpClient http = new HttpClient();
		GetMethod getMethod = new GetMethod(httpUrl);// 编程方式发起HTTP请求，请求httpUrl来获取具体页面的HTML源代码
		String mailContent = null;
		try {
			getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			int statusCode = http.executeMethod(getMethod);//发起http请求
			if(statusCode == HttpStatus.SC_OK) {
				InputStream input  = getMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
				String line = null;
				StringBuilder builder = new StringBuilder();
				boolean flag = true;//标记某行html源码是否需要被发送的标记位
				while((line = reader.readLine()) != null) {//逐行匹配
					if(START_LINE.equals(line.trim())) {//发现开始标记，下面的html源码不需要被发送出去
						flag = false;
					}
					if(flag) {
						builder.append(line);
						if(line.trim().startsWith("<head>")) {//追加一个<base href="http://localhost/itsm/"/>进去以便正确的获取CSS，否则发出来的邮件缺少CSS，布局是乱的
							builder.append("<base href=\"" + httpServerBasePath + "/\"/>\n");
						}
					}
					if(END_LINE.equals(line.trim())) {//发现结束标记，下面的html源码需要被发送出去
						flag = true;
					}
				}
				mailContent = builder.toString();
			} else {
				logger.error("Request for url[" + httpUrl + "] failed, the http status code is: " + statusCode);
			}
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("Http error occured when request for url[" + httpUrl + "], mail will not be sent.");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Http error occured when request for url[" + httpUrl + "], mail will not be sent.");
		} finally {
			getMethod.releaseConnection();
		}
		return mailContent;
	}

	/**
	 * 获取请求的HTTP URL地址
	 * 
	 * @return 请求的HTTP URL地址
	 */
	protected abstract String getHttpUrl();

	/**
	 * 获取需要接受该邮件的人员邮件地址列表
	 * 
	 * @return 需要接受该邮件的人员邮件地址列表
	 */
	protected abstract List<String> getMailReceivers();

	/**
	 * 获取待发送邮件的邮件标题
	 * 
	 * @return 待发送邮件的邮件标题
	 */
	protected abstract String getMailTitle();
	
	public static void main(String[] args){

		String httpUrl = "http://www.cz88.net/baidu/id.php";
		HttpClient http = new HttpClient();
		PostMethod postMethod = new PostMethod(httpUrl);// 编程方式发起HTTP请求，请求httpUrl来获取具体页面的HTML源代码
		postMethod.setParameter("in_id", "340703197909130016");
		String html = null;
		try {
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			int statusCode = http.executeMethod(postMethod);//发起http请求
			if(statusCode == HttpStatus.SC_OK) {
				InputStream input  = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input, "GBK"));
				String line = null;
				StringBuilder builder = new StringBuilder();
				boolean flag = true;//标记某行html源码是否需要被发送的标记位
				while((line = reader.readLine()) != null) {//逐行匹配
					builder.append(line);
				}
				html = builder.toString();
				
				Parser cp = Parser.createParser(html, "GBK");
				//查找DIV节点
				ObjectFindingVisitor visitor = new ObjectFindingVisitor(Div.class);
				cp.visitAllNodesWith(visitor);
				
				Node[] tags = visitor.getTags();
				List<Node> boxNodes = new ArrayList<Node>();
				int i=0;
				for (Node node : tags) {
					Div div = (Div) node;
					String classname = div.getAttribute("class");
					if("box".equals(classname)){
						if(i > 0){ //从第二个开始
							//获得class="box"的div的子节点
							boxNodes.add(div);
						}
						i++;
					}
				}
				
				//显示搜索结果:
				for (Node boxNode : boxNodes) {
					NodeList extractAllNodesThatMatch = boxNode.getChildren().extractAllNodesThatMatch(new NodeClassFilter(Div.class));
					Node[] nodeArray = extractAllNodesThatMatch.toNodeArray();
					System.out.println(nodeArray[0].toPlainTextString() + "" + nodeArray[1].toPlainTextString());
				}
				
			} else {
				logger.error("Request for url[" + httpUrl + "] failed, the http status code is: " + statusCode);
			}
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("Http error occured when request for url[" + httpUrl + "], mail will not be sent.");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Http error occured when request for url[" + httpUrl + "], mail will not be sent.");
		} catch (ParserException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
	
}
