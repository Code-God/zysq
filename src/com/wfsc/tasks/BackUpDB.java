/******************************************************************************** 
 * Create Author   :timy
 * Create Date     : Jan 15, 2010
 * File Name       : BackUpDB.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.base.log.LogUtil;
/**
 * 系统备份附件之------备份数据库。windows环境下的mysql数据库备份已经实现，其他情况有待后面改进
 * 
 * @author timy
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
public class BackUpDB {

	/**
	 * 备份数据库同时备份附件
	 * 
	 * @param BackupDbUrl 备份命令
	 * @param BackupDbName备份后存放的名称，绝对路劲
	 * @param rawAttachmentURL 本地储存工单的地址
	 * @param StoreAttachmentURL 工单备份后存储地址
	 * @param URLDefinedBySelf 用户自定义路径
	 */
	// 备份数据库是否备份成功
	boolean isSuccess = false;

	String zipPackageName;

	String separator = File.separator;
	
	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	public boolean backUpDbByOperatorSystemAndDbType(String operatorSystem, String DBType,
			String StoreURL, int storeFileNum) {
		// ｘｐ操作系统，mysql数据库
		if (operatorSystem.equals("windows")) {
			String tempStoreUrl = "";
			if (!StoreURL.endsWith("\\") && !StoreURL.endsWith("/")) {
				tempStoreUrl = StoreURL + "\\tempDirectory\\";
				StoreURL = StoreURL + "\\";
			} else {
				tempStoreUrl = StoreURL + "tempDirectory\\";
			}
			// 备份mysql
			if (DBType.equals("mysqlDB")) {
				File storeFlie = new File(StoreURL);
				if (storeFlie.exists() && storeFlie.isFile()) {
					return isSuccess;
				} else if (!storeFlie.exists()) {
					boolean bool = storeFlie.mkdirs();
					if (!bool) {
						return isSuccess;
					}
				}
				isSuccess = windowsBackUpMysqlDB("root", "windows#2013", "wfsc", tempStoreUrl);
				if (isSuccess) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
					}
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
						zipPackageName = sdf.format(new Date());
						String inputFileName = tempStoreUrl;
						String storeDirectory = StoreURL;
						String zipFileName = storeDirectory + zipPackageName + ".zip";
						zip(inputFileName, storeDirectory, zipFileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String deletePath = tempStoreUrl;
				deleteBackUpFile(deletePath, StoreURL, storeFileNum);
			}
		}
		// linux操作系统
		else if (operatorSystem.equals("linux")) {
			String tempStoreUrl = "";
			if (!StoreURL.endsWith("/")) {
				tempStoreUrl = StoreURL + "/tempDirectory/";
				StoreURL = StoreURL + "/";
			} else {
				tempStoreUrl = StoreURL + "tempDirectory/";
			}
			// 备份mysql
			if (DBType.equals("mysqlDB")) {
				File storeFlie = new File(StoreURL);
				if (storeFlie.exists() && storeFlie.isFile()) {
					return isSuccess;
				} else if (!storeFlie.exists()) {
					boolean bool = storeFlie.mkdirs();
					if (!bool) {
						return isSuccess;
					}
				}
				isSuccess = linuxBackUpMysqlDB("root", "windows#2013", "wfsc", tempStoreUrl);
				if (isSuccess) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
					}
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
						zipPackageName = sdf.format(new Date());
						String inputFileName = tempStoreUrl;
						String storeDirectory = StoreURL;
						String zipFileName = StoreURL + zipPackageName + ".zip";
						zip(inputFileName, storeDirectory, zipFileName);
					} catch (Exception e) {
					}
				}
				String deletePath = tempStoreUrl;
				deleteBackUpFile(deletePath, StoreURL, storeFileNum);
			}
		}
		return isSuccess;
	}


	/**
	 * 
	 * 在windows环境下备份mysql
	 * 
	 * @param loginDBUserName 登陆数据库的用户名
	 * @param loginDBPWD 登陆数据库的用户密码
	 * @param DBName 数据库实例名
	 * @param StorUrl 存储备份文件的地址 例如：d:\\sql.dmp
	 */
	public boolean windowsBackUpMysqlDB(String loginDBUserName, String loginDBPWD, String DBName, String StorUrl) {
		boolean isSuccess = false;
		try {
			File storeFolder = new File(StorUrl);
			if (!storeFolder.exists()) {
				boolean bool = storeFolder.mkdirs();
				if (!bool) {
					return isSuccess;
				}
			}
			// 得到系统运行的当前路径,
			File tempFile = new File("." + separator);
			String currentPath = tempFile.getAbsolutePath();
			String beforePathJboss = currentPath.substring(0, currentPath.indexOf("jboss"));
			File beforeDirectoryJboss = new File(beforePathJboss);
			String mysqlName = "";
			if (beforeDirectoryJboss.isDirectory()) {
				File[] fileList = beforeDirectoryJboss.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].getName().startsWith("mysql")) {
						mysqlName = fileList[i].getName();
						break;
					} else {
						// 一直到最后都没有找到mysql目录则失败退出
						if (i == (fileList.length - 1) && !fileList[i].getName().startsWith("mysql")) {
							return isSuccess;
						}
					}
				}
			}
			// 得到系统运行的当前路径,
			String currentDisk = tempFile.getAbsolutePath().substring(0, 2);
			String jbossBinDirectory = "jboss-4.2.2.GA\\bin\\.";
			currentPath = currentPath.substring(0, currentPath.length() - jbossBinDirectory.length());
			String startBatCmd = "cmd /c start  " + currentPath + mysqlName + "\\backup_db_manual.bat";
			String binStr = currentPath + mysqlName + "\\bin";
			Runtime.getRuntime().exec(startBatCmd + " " + currentDisk + " " + binStr + " " + StorUrl); // 执行backup.bat文件进行备份数据库
			isSuccess = true;
		} catch (Exception e) {
		}
		return isSuccess;
	}


	/**
	 * 
	 * 在linux环境下备份mysql
	 * 
	 * @param loginDBUserName 登陆数据库的用户名
	 * @param loginDBPWD 登陆数据库的用户密码
	 * @param DBName 数据库实例名
	 * @param StorUrl 存储备份文件的地址 例如：d:\\sql.dmp
	 */
	public boolean linuxBackUpMysqlDB(String loginDBUserName, String loginDBPWD, String DBName, String StorUrl) {
		boolean isSuccess = false;
		try {
			File storeFolder = new File(StorUrl);
			if (!storeFolder.exists()) {
				boolean bool = storeFolder.mkdirs();
				if (!bool) {
					return isSuccess;
				}
			}
			// 得到系统运行的当前路径,
			File tempFile = new File("." + separator);
			String currentPath = tempFile.getAbsolutePath();
			String beforePathJboss = currentPath.substring(0, currentPath.indexOf("jboss"));
			File beforeDirectoryJboss = new File(beforePathJboss);
			String mysqlName = "";
			if (beforeDirectoryJboss.isDirectory()) {
				File[] fileList = beforeDirectoryJboss.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].getName().startsWith("mysql")) {
						mysqlName = fileList[i].getName();
						break;
					} else {
						if (i == (fileList.length - 1) && !fileList[i].getName().startsWith("mysql")) {
						}
					}
				}
			}
			String jbossBinPath = "jboss-4.2.2.GA/bin/.";
			currentPath = currentPath.substring(0, currentPath.length() - jbossBinPath.length());
			String shFilePath = currentPath + mysqlName + "/BackupDb.sh";
			String binStr = currentPath + mysqlName + "/bin/";
			String[] commands = new String[] { "/bin/sh", "-c", "sh " + shFilePath + " " + binStr + " " + StorUrl };
			java.lang.Process myproc = Runtime.getRuntime().exec(commands); // 执行SH程序
			myproc.waitFor();// 等待脚本执行完毕
			isSuccess = true;
		} catch (Exception e) {
		}
		return isSuccess;
	}

	/**
	 * 
	 * 根据要保存的文件数目把之前备份的文件删除。。。。 删除备份的文件夹，只保留zip包，在zip包中有个数限制
	 * 
	 * @param storeFileNum
	 * @param deletePath要删除的文件夹路径
	 * @param limitNumPath有个数限制的文件夹
	 * @return
	 */
	public void deleteBackUpFile(String deletePath, String limitNumPath, int storeFileNum) {
		// 删除文件夹
		if (deletePath.length() > 0) {
			deleteFile(deletePath);
		}
		// 个数限制
		File limitNumFolder = new File(limitNumPath);
		if (limitNumFolder.exists()) {
			File[] zipFileList = limitNumFolder.listFiles();
			if (null != zipFileList && zipFileList.length > 0) {
				if (zipFileList.length > storeFileNum) {
					// 个数多余要保存的数目，删除多余的文件
					// 得到所有的文件名字，由于是以时间为名，逐个比较各个名字的大小（后面创建的时间数值一定大于前面的时间数值），删除最小的文件，直到现存文件个数小于等于要保存的文件个数
					List<String> FileNameList = new ArrayList<String>();
					for (int i = 0; i < zipFileList.length; i++) {
						if (zipFileList[i].isFile()) {
							String regex = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}\\-[0-9]{2}\\-[0-9]{2}\\-[0-9]{2}.zip$";
							Pattern pattern = Pattern.compile(regex);
							String name = zipFileList[i].getName();
							Matcher m = pattern.matcher(name);
							if (m.matches()) {
								String tempName = name.substring(0, name.length() - 4);
								FileNameList.add(tempName);
							} else {
								// 名字不匹配就delete
								boolean bool = zipFileList[i].delete();
							}
						} else {
						}
					}
					for (int fileNameLength = FileNameList.size(); storeFileNum < fileNameLength; fileNameLength--) {
						// 得到所有名字中最小的一个，并删除名字和相应文件
						String minName = FileNameList.get(0);
						for (int j = 0; j < FileNameList.size(); j++) {
							int compareResult = minName.compareTo(FileNameList.get(j));
							minName = compareResult < 0 ? minName : FileNameList.get(j);
						}
						// 得到最小的后，删除name和文件
						FileNameList.remove(minName);
						File earliestFile = new File(limitNumPath + separator + minName + ".zip");
						earliestFile.delete();
					}
					return;
				} else {
					// 个数少于要保存数目，退出
					return;
				}
			} else {
				// 文件夹下没有文件
				return;
			}
		} else {
			// 文件夹不存在
			return;
		}
	}

	/**
	 * 
	 * @param inputFileName 输入一个文件夹 "D:\\temp\\test"
	 * @param zipFileName 输出一个压缩文件夹，打包后文件名字 "D:\\temp\\test.zip"
	 * @throws Exception,压缩中文文件时会出乱码现象，用apache的ant.jar包可以解决
	 */
	public void zip(String inputFilePath, String storeDirectory, String zipFileName) throws Exception {
		File storeFolder = new File(storeDirectory);
		if (!storeFolder.exists()) {
			boolean bool = storeFolder.mkdirs();
			if (!bool) {
				return;
			}
		}
		File inFile = new File(inputFilePath);
		if (null == inFile) {
			return;
		} else {
			zip(zipFileName, inFile);
		}
	}

	private void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		zip(out, inputFile, zipPackageName);
		out.close();
	}

	private void zip(ZipOutputStream out, File inputFile, String base) throws Exception {
		if (inputFile.isDirectory()) { // 判断是否为目录
			File[] file = inputFile.listFiles();
			out.putNextEntry(new ZipEntry(base + separator));
			base = base + separator;
			for (int i = 0; i < file.length; i++) {
				zip(out, file[i], base + file[i].getName());
			}
		} else { // 压缩目录中的所有文件
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(inputFile);
			int bytesRead = 0;
			byte[] buffer = new byte[20480];
			while ((bytesRead = in.read(buffer, 0, 20480)) != -1) {
				out.write(buffer, 0, bytesRead);
				out.flush();
			}
			in.close();
		}
	}

	/**
	 * 
	 * 删除文件夹
	 * 
	 * @param deletePath
	 */
	public void deleteFile(String deletePath) {
		// 文件正在copy中时，不能delete
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		File deleteFolder = new File(deletePath);
		if (deleteFolder.exists() && deleteFolder.isFile()) {
			deleteFolder.delete();
		} else if (deleteFolder.exists() && !deleteFolder.isFile()) {
			// 文件夹
			File[] fileList = deleteFolder.listFiles();
			int length = fileList.length;
			if (null != fileList && length > 0) {
				for (int i = 0; i < length; i++) {
					deleteFile(fileList[i].getAbsolutePath());
					// 最后一次把以前含有子文件，但是子文件已经被删除了的父文件删除
					if (i == (length - 1)) {
						deleteFolder.delete();
					}
				}
			} else {
				// 没有子文件的文件夹
				deleteFolder.delete();
			}
		}
	}
}
