package com.base.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.openapi.model.LoginResult;

/****************************************************************************************************************************
 * File Name : HttpUtils.java
 ***************************************************************************************************************************/
public class HttpUtils {

	// 日志
	private static Logger logger = Logger.getLogger(HttpUtils.class);

	// 令牌参数
	public final static String ACCESS_TOKEN_PARAMETER = "accessToken";

	/**
	 * 获取用户令牌
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String getUserAccessToken(String userName, String password) {
		// 调用服务，返回结果输出到控制台
		HttpClient httpclient = new DefaultHttpClient();
		String url = "http://localhost:81/itsm/mobile/v1/securityservice/login";
		// String url = "http://180.169.30.194:8888/itsm/mobile/v1/securityservice/login";
		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userName", userName));
		nvps.add(new BasicNameValuePair("password", password));
		UrlEncodedFormEntity reqEntity;
		try {
			reqEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HttpStatus.SC_OK) {
				logger.error(status.getReasonPhrase());
			} else {
				HttpEntity resEntity = response.getEntity();
				String responseText = EntityUtils.toString(response.getEntity(), "UTF-8");
				// logger.info(responseText);
				EntityUtils.consume(resEntity);
				LoginResult loginResult = (LoginResult) JSONObject.toBean(JSONObject.fromObject(responseText),
						LoginResult.class);
				if (loginResult != null && loginResult.isSuccess()) {
					logger.info("当前登录用户:" + loginResult.getRealName() + " 令牌:" + loginResult.getAccess_token());
					return loginResult.getAccess_token(); // 访问令牌
				} else {
					logger.info(loginResult.getMsg());
				}
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
			Assert.fail();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
		return null;
	}

	/**
	 * 调用HttpGet方法
	 * 
	 * @param httpclient
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String doGet(String url, List<NameValuePair> nvps, String accessToken) {
		HttpClient httpclient = null;
		String responseText = "";
		try {
			httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader(ACCESS_TOKEN_PARAMETER, accessToken);// 传入令牌使用该值
			if (CollectionUtils.isNotEmpty(nvps)) {
				for (NameValuePair nvp : nvps) {
					httpget.getParams().setParameter(nvp.getName(), nvp.getValue());
				}
			}
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			/*
			 * logger.info("----------------------------------------"); logger.info(response.getStatusLine());
			 * 
			 * if (resEntity != null) { logger.info("Response content length: " + resEntity.getContentLength()); }
			 */
			responseText = EntityUtils.toString(resEntity, "UTF-8");
			logger.info("--------------返回结果---------------");
			logger.info(responseText);
			EntityUtils.consume(resEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return responseText;
	}

	/**
	 * 调用HttpPost方法
	 * 
	 * @param httpclient
	 * @param url
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String doPost(String url, List<NameValuePair> nvps, String accessToken) {
		HttpClient httpclient = null;
		String responseText = "";
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			if (CollectionUtils.isNotEmpty(nvps)) {
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
				httppost.setEntity(reqEntity);
			}
			httppost.setHeader(ACCESS_TOKEN_PARAMETER, accessToken);// 传入令牌使用该值
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			/*
			 * logger.info("----------------------------------------"); logger.info(response.getStatusLine());
			 * 
			 * if (resEntity != null) { logger.info("Response content length: " + resEntity.getContentLength()); }
			 */
			responseText = EntityUtils.toString(resEntity, "UTF-8");
			logger.info("--------------返回结果---------------");
			// 输出返回的结果
			logger.info(responseText);
			EntityUtils.consume(resEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return responseText;
	}

	/**
	 * 传入指定apikey，调用HttpGet方法
	 * 
	 * @param httpclient
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String executeHttpGetForApiKey(HttpClient httpclient, String url, List<NameValuePair> nvps) {
		String responseText = "";
		// enableSSL(httpclient);
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("apiKey", "itsm2im@api_key");
			if (CollectionUtils.isNotEmpty(nvps)) {
				for (NameValuePair nvp : nvps) {
					httpget.getParams().setParameter(nvp.getName(), nvp.getValue());
				}
			}
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				System.out.println("Response content length: " + resEntity.getContentLength());
			}
			responseText = EntityUtils.toString(resEntity, "UTF-8");
			System.out.println("result=" + responseText);
			EntityUtils.consume(resEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return responseText;
	}

	/**
	 * 传入指定apikey，调用HttpPost方法
	 * 
	 * @param httpclient
	 * @param url
	 * @return
	 */
	public static String executeHttpPostForApiKey(HttpClient httpclient, String url, List<NameValuePair> nvps) {
		String responseText = "";
		// enableSSL(httpclient);
		try {
			HttpPost httppost = new HttpPost(url);
			if (CollectionUtils.isNotEmpty(nvps)) {
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
				httppost.setEntity(reqEntity);
			}
			httppost.setHeader("apiKey", "itsm2im@api_key");
			System.out.println("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				System.out.println("Response content length: " + resEntity.getContentLength());
			}
			responseText = EntityUtils.toString(resEntity, "UTF-8");
			System.out.println("result=" + responseText);
			EntityUtils.consume(resEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return responseText;
	}
}
