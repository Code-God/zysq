package com.wfsc.services.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Page;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.daos.system.SystemLogDao;

@Service("systemLogService")
public class SystemLogServiceImpl implements ISystemLogService {
	
	@Autowired
	private SystemLogDao systemLogDao;

	@Override
	public Page<SystemLog> getSystemLog4Page(Page<SystemLog> page, Map<String, Object> params) {
		return systemLogDao.getSystemLogForPage(page, params);
	}

	@Override
	public void saveSystemLog(SystemLog systemLog) {
		systemLogDao.saveEntity(systemLog);
	}

}
