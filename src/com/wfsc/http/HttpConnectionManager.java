package com.wfsc.http;

/**
 * Created with IntelliJ IDEA.
 * User: jia
 * Date: 13-7-8
 * Time: 下午11:37
 * To change this template use File | Settings | File Templates.
 */

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpConnectionManager {

	public static HttpClient getHttpClient() {
		HttpParams params = new BasicHttpParams();
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, 10000);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		// Increase max connections for localhost:80 to 50
//		HttpHost localhost = new HttpHost("locahost", 80);
//		cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
//		// 自定义cookies*****************
//		CookieSpecFactory csf = new CookieSpecFactory() {
//
//			public CookieSpec newInstance(HttpParams params) {
//				return new BrowserCompatSpec() {
//
//					@Override
//					public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
//						// Oh, I am easy
//					}
//				};
//			}
//		};
//		httpClient.getCookieSpecs().register("easy", csf);
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		// 自定义cookies*****************
		return wrapClient(httpClient);
	}
	
	/**
     * 避免HttpClient的”SSLPeerUnverifiedException: peer not authenticated”异常
     * 不用导入SSL证书
     */
	public static HttpClient wrapClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			return new DefaultHttpClient(ccm, base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
