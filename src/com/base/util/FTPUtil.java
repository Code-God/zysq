package com.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.base.log.LogUtil;
import com.base.tools.Version;

/**
 * FTP上传工具类
 * 
 * @author jacky
 * @version 1.0
 * @since Resint 1.0
 */
public class FTPUtil {

	private static Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String filename, InputStream input) {
		// * @param host FTP服务器hostname
		// * @param port FTP服务器端口
		// * @param username FTP登录账号
		// * @param password FTP登录密码
		// * @param ftpDir FTP服务器保存目录
		String host = Version.getInstance().getNewProperty("image.server.ip");
		int ftpport = Integer.valueOf(Version.getInstance().getNewProperty("image.server.ftpport"));
		String username = Version.getInstance().getNewProperty("image.server.name");
		String password = Version.getInstance().getNewProperty("image.server.pwd");
		String ftpDir = Version.getInstance().getNewProperty("image.server.workdir");
		long t1 = System.currentTimeMillis();
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, ftpport);// 连接FTP服务器
			ftp.enterLocalPassiveMode();
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.changeWorkingDirectory(ftpDir);
			ftp.storeFile(filename, input);
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		logger.info("图片上传耗时：" + (System.currentTimeMillis() - t1));
		return success;
	}

	@Test
	public void testUpLoadFromDisk() throws IOException {
		FileInputStream input = null;
		try {
			input = new FileInputStream(new File("D:/Tulips.jpg"));
			boolean flag = uploadFile("Tulips.jpg", input);
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			input.close();
		}
	}
}
