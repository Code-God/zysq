package service;

import java.util.Map;

import model.bo.drug.DrugScoreLog;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.drug.IDrugScoreLogService;

import com.base.log.LogUtil;
import com.base.util.Page;

import dao.drug.DrugScoreLogDao;

/**
 * 积分
 * 
 * @author Administrator
 * 
 */

@Service("drugScoreLogService")
public class DrugScoreLogServiceImpl implements IDrugScoreLogService {

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	// 积分dao
	@Autowired
	private DrugScoreLogDao drugScoreLogDao;

	/**
	 * 查看该积分类型的积分是否已经存在
	 * @return
	 */
	@Override
	public boolean isExsit(String openid,String source){
		return drugScoreLogDao.isExsit(openid, source);
	}
	/**
	 * 通过openid查看积分总数
	 * @param openid
	 * @return
	 */
	public Integer getTotalScoreByOpenid(String openid){
		return drugScoreLogDao.getTotalScoreByOpenid(openid);
	}
	
	
	/**
	 * 保存或者修改积分
	 */

	@Override
	public void saveOrUpdateDrugScore(DrugScoreLog drugScoreLog) {
		drugScoreLogDao.saveOrUpdateEntity(drugScoreLog);

	}
	/**
	 * 通过openid 查看用户获得积分记录列表分页
	 */

	@Override
	public Page<DrugScoreLog> findPageObtainForDrugScoreLog(Page<DrugScoreLog> page, Map<String, Object> paramap) {
		return  drugScoreLogDao.findPageObtainForDrugScoreLog(page, paramap);
		
	}
	
	/**
	 * 通过openid 查看用户兑换积分记录列表分页
	 */

	@Override
	public Page<DrugScoreLog> findPageExchangeForDrugScoreLog(Page<DrugScoreLog> page, Map<String, Object> paramap) {
		return drugScoreLogDao.findPageExchangeForDrugScoreLog(page, paramap);
	}


	public DrugScoreLogDao getDrugScoreLogDao() {
		return drugScoreLogDao;
	}

	public void setDrugScoreLogDao(DrugScoreLogDao drugScoreLogDao) {
		this.drugScoreLogDao = drugScoreLogDao;
	}

}
