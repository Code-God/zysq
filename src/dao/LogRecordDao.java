package dao;

import java.util.Calendar;
import java.util.List;

import model.bo.LogRecord;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.wfsc.util.DateUtil;
@Repository("logRecordDao")
public class LogRecordDao extends EnhancedHibernateDaoSupport<LogRecord> {

	Logger logger = LogUtil.getLogger(LogRecordDao.class.getName());
	
	@Override
	protected String getEntityName() {
		return LogRecord.class.getName();
	}
	/**
	 * 插入沙发记录，然后返回
	 * 抢沙发名次 
	 * @param loginId - 登录ID
	 * @param userName - 用户名
	 */
	public int getSofaAndReturnRank(String loginId, String userName) {
		
		//时间限制：12点00到12点05之间
		int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int m = Calendar.getInstance().get(Calendar.MINUTE);
		int startHour = Integer.valueOf(Version.getInstance().getNewProperty("sofaStartHour"));
		int startMin = Integer.valueOf(Version.getInstance().getNewProperty("sofaStartMinue"));
		int endHour = Integer.valueOf(Version.getInstance().getNewProperty("sofaEndHour"));
		int endMin = Integer.valueOf(Version.getInstance().getNewProperty("sofaEndMinue"));
		
		if((i >= startHour && i <= endHour) && (m >= startMin && m <= endMin)){
//			
		}else{
			return 9999;
		}
		
		LogRecord lr = new LogRecord();
		lr.setActType(LogRecord.ACT_SOFA);
		lr.setActTime(DateUtil.getLongCurrentDate());
		lr.setLoginId(loginId);
		lr.setUserName(userName);
		this.saveEntity(lr);
		logger.info("抢沙发记录插入...."+loginId);
		int r = 0;
		String shortCurrentDate = DateUtil.getShortCurrentDate();
		List find = this.getHibernateTemplate().find("from LogRecord where actTime > '"+shortCurrentDate+" "+ startHour +":"+ startMin +":00' order by actTime asc");
		for (Object object : find) {
			LogRecord l = (LogRecord) object;
			if(l.getLoginId().equals(loginId)){
				r++;
				break;
			}
			r++;
		}
		if(r <=10){//前5名有效
			return 1;
		}
		return r;
	}
}
