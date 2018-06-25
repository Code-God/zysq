package com.wfsc.util;

import java.util.List;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import actions.integ.weixin.WeiXinUtil;

import com.base.tools.Version;

public class HttpUtils {

	// 日志
	private static Logger logger = Logger.getLogger(HttpUtils.class);

	// 令牌参数
	public final static String ACCESS_TOKEN_PARAMETER = "accessToken";

	/**
	 * 调用HttpGet方法
	 * 
	 * @param httpclient
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String doGet(String url, List<NameValuePair> nvps) {
		HttpClient httpclient = null;
		String responseText = "";
		try {
			httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			if (nvps != null && !nvps.isEmpty()) {
				for (NameValuePair nvp : nvps) {
					httpget.getParams().setParameter(nvp.getName(),
							nvp.getValue());
				}
			}
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			/*
			 * logger.info("----------------------------------------");
			 * logger.info(response.getStatusLine());
			 * 
			 * if (resEntity != null) { logger.info("Response content length: "
			 * + resEntity.getContentLength()); }
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
	 * 调用HttpGet方法(數據中心接口)
	 * 
	 * @param httpclient
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String doDataCenterGet(String url, List<NameValuePair> nvps) {
		HttpClient httpclient = null;
		String responseText = "";
		try {
			httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			if (nvps != null && !nvps.isEmpty()) {
				for (NameValuePair nvp : nvps) {
					httpget.getParams().setParameter(nvp.getName(),
							nvp.getValue());
				}
			}
			String orgCode = Version.getInstance().getNewProperty(
					"datacenter_orgCode");
			String token = WeiXinUtil.getDataCenterToken(orgCode);
			System.out.println(token);
			httpget.addHeader("Authorization", "Bearer " + token);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			/*
			 * logger.info("----------------------------------------");
			 * logger.info(response.getStatusLine());
			 * 
			 * if (resEntity != null) { logger.info("Response content length: "
			 * + resEntity.getContentLength()); }
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
	 * 调用HttpPost方法(数据中心接口)
	 * 
	 * @param httpclient
	 * @param url
	 * @return
	 */
	public static String doDataCenterPost(String url, List<NameValuePair> nvps,
			String json) {
		HttpClient httpclient = null;
		String responseText = "";
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			// 设置post编码

			httppost.addHeader("Content-Type",
					"application/x-www-form-urlencoded");
			if (json != null) {
				StringEntity entity1 = new StringEntity(json, "UTF-8");
				entity1.setContentType("text/xml;charset=UTF-8");
				entity1.setContentEncoding("UTF-8");
				httppost.setEntity(entity1);
			}
			if (nvps != null && !nvps.isEmpty()) {
				System.out.println(nvps);
				httppost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			}
			String orgCode = Version.getInstance().getNewProperty(
					"datacenter_orgCode");
			String token = WeiXinUtil.getDataCenterToken(orgCode);
			System.out.println(token);
			httppost.addHeader("Authorization", "Bearer " + token);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			responseText = EntityUtils.toString(resEntity, "UTF-8");
			logger.info("--------------返回结果---------------");
			// 输出返回的结果
			logger.info("输出返回的结果:"+responseText);
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
	public static String doPost(String url, List<NameValuePair> nvps,
			String json) {
		HttpClient httpclient = null;
		String responseText = "";
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			// 设置post编码
			httppost.addHeader("Content-Type",
					"application/x-www-form-urlencoded");
			StringEntity entity1 = new StringEntity(json, "UTF-8");
			entity1.setContentType("text/xml;charset=UTF-8");
			entity1.setContentEncoding("UTF-8");
			httppost.setEntity(entity1);
			if (nvps != null && !nvps.isEmpty()) {
				httppost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			}
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
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
	public static String executeHttpGetForApiKey(String url,
			List<NameValuePair> nvps, String apiKey, String token) {
		String responseText = "";
		HttpClient httpclient = null;
		try {
			httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("apiKey", apiKey);
			httpget.setHeader("accessToken", token);
			if (CollectionUtils.isNotEmpty(nvps)) {
				for (NameValuePair nvp : nvps) {
					httpget.getParams().setParameter(nvp.getName(),
							nvp.getValue());
				}
			}
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				System.out.println("Response content length: "
						+ resEntity.getContentLength());
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
	public static String executeHttpPostForApiKey(String url,
			List<NameValuePair> nvps, String apiKey, String token) {
		String responseText = "";
		HttpClient httpclient = null;
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			if (CollectionUtils.isNotEmpty(nvps)) {
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(nvps,
						HTTP.UTF_8);
				httppost.setEntity(reqEntity);
			}
			httppost.setHeader("apiKey", apiKey);
			httppost.setHeader("accessToken", token);
			System.out
					.println("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				System.out.println("Response content length: "
						+ resEntity.getContentLength());
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

	public static void main(String[] args) {
		String s = "{'a':'123','b':'222'}";
		try {
			JSONObject fromObject = JSONObject.fromObject(s);
			// Object bean = JSONObject.toBean(fromObject, AA.class);
			// System.out.println(bean);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
