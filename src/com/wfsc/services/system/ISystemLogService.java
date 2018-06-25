package com.wfsc.services.system;

import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.system.SystemLog;

public interface ISystemLogService {
	
	/**
	 * 保存系统日志
	 * @param systemLog
	 */
	public void saveSystemLog(SystemLog systemLog);
	
	/**
	 * 查询系统日志
	 * @param page
	 * @param params
	 * @return
	 */
	public Page<SystemLog> getSystemLog4Page(Page<SystemLog> page, Map<String, Object> params);
	
}
