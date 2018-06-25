/******************************************************************************** 
 * Create Date     : Dec 2, 2009
 * File Name       : Version.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author jacky
 */
public final class JobUtil {

	
	/**
	 * The single instance.
	 */
	private static JobUtil instance = null;

	/**
	 * The version properties.
	 */
	private SafeProperties props;

	/**
	 * Do not allow direct public construction.
	 */
	private JobUtil() {
		props = loadProperties();
	}

	/**
	 * Get the single <tt>Version</tt> instance.
	 * 
	 * @return The single <tt>Version</tt> instance.
	 */
	public static JobUtil getInstance() {
		if (instance == null) {
			instance = new JobUtil();
		}
		return instance;
	}

	/**
	 * Returns an unmodifiable map of version properties.
	 * 
	 * @return An unmodifiable map of version properties.
	 */
	public Map getProperties() {
		return Collections.unmodifiableMap(props);
	}

	/**
	 * Returns the value for the given property name.
	 * 
	 * @param name - The name of the property.
	 * @return The property value or null if the property is not set.
	 */
	public String getProperty(final String name) {
		return props.getProperty(name);
	}
	

	/**
	 * 
	 * @param name - The name of the property.
	 * @return The property value or null if the property is not set.
	 */
	public void setProperty(String key, String newValue) {
		props.setProperty(key, newValue);
	}


	/**
	 * Returns a property value as an int.
	 * 
	 * @param name - The name of the property.
	 * @return The property value, or -1 if there was a problem converting it to an int.
	 */
	private int getIntProperty(final String name) {
		try {
			return Integer.valueOf(props.getProperty(name)).intValue();
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Load the version properties from a resource.
	 */
	public SafeProperties loadProperties() {
		props = new SafeProperties();
		try {
			//由于高级系统设置中可能会多次修改properties文件，故这里的InputStream从物理路径读出，确保每次读出的数据是最新的
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			InputStream in = new  FileInputStream(new File(path + "/job.properties")); 
			props.load(in);
			in.close();
		} catch (IOException e) {
			throw new Error("Missing res.properties");
		}
		return props;
	}

	/**
	 * 修改配置文件信息,主要用做高级系统设置模块
	 * add by xiaowei
	 * @param key 属性名
	 * @param value 属性值
	 * @return 状态值，1表示操作成功
	 */
	public int modifyProperty(Map<String, String> map) {
		try {
			// 获得properties对象
			SafeProperties props = this.loadProperties();
			for (String key : map.keySet()) {
				String value=map.get(key);
				value = new String(value.getBytes(CommonUtil.UTF8), CommonUtil.ISO88591);
				props.setProperty(key, value);
			}
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//			System.out.println(path);
			FileOutputStream fout = new FileOutputStream(path + "/job.properties");
			props.store(fout, "");// 保存文件
			fout.close();
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void main(String[] args) {
		System.out.println(System.getProperties());
	}

	/**
	 * 获取即时的属性信息，不缓存， 配置文件重新加载
	 * 
	 * @param name - The name of the property.
	 * @return The property value or null if the property is not set.
	 */
	public String getNewProperty(final String name) {
		Properties prop = new Properties();  
		String path = JobUtil.class.getClassLoader().getResource("job.properties").getPath();  
		InputStream is;
		try {
			is = new FileInputStream(path);
			prop.load(is); 
		} catch (Exception e1) {
			e1.printStackTrace();
		}  
		
		String company = prop.getProperty(name);
		String value = "";
		try {
			value = new String(company.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
}
