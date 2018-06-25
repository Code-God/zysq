package com.base;

import org.apache.log4j.Logger;

import com.base.log.LogUtil;


/**
 * 服务器关闭时, 将内存中的索引flush到硬盘中
 * 
 * @author OwenChen
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
public class ShutdownHookService extends Thread {

	private static Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	public void run() {
		try {
//			IssueRealtimeIndexWriterService.getInstance().close();
//			NoticeRealtimeIndexWriterService.getInstance().close();
//			CiRealtimeIndexWriterService.getInstance().close();
//			KnowbaseRealtimeIndexWriterService.getInstance().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Flush lucene cache successfully.");
	}
}