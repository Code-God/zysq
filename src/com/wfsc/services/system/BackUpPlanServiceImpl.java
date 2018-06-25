package com.wfsc.services.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfsc.common.bo.system.BackUpPlan;
import com.wfsc.daos.system.BackUpPlanDao;
@Service("backUpPlanService")
public class BackUpPlanServiceImpl implements IBackUpPlanService {
	@Autowired
	private BackUpPlanDao backUpPlanDao;

	@Override
	public BackUpPlan getBackUpPlan() {
		return backUpPlanDao.getEntityById(1L);
	}

	@Override
	public void saveOrUpdateBackUpPlan(BackUpPlan backUpPlan) {
		backUpPlanDao.saveOrUpdateEntity(backUpPlan);

	}

	public BackUpPlanDao getBackUpPlanDao() {
		return backUpPlanDao;
	}

	public void setBackUpPlanDao(BackUpPlanDao backUpPlanDao) {
		this.backUpPlanDao = backUpPlanDao;
	}
	
	

}
