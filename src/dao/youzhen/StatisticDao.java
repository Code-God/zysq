package dao.youzhen;

import model.bo.StaticData;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 * 栏目表DAO 
 *
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
@Repository("statisticDao")
public class StatisticDao extends EnhancedHibernateDaoSupport<StaticData> {

	@Override
	protected String getEntityName() {
		return StaticData.class.getName();
	}
}
