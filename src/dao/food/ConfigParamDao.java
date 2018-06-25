package dao.food;

import model.bo.food.ConfigParam;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

@Repository("configParamDao")
public class ConfigParamDao extends EnhancedHibernateDaoSupport<ConfigParam> {

	@Override
	protected String getEntityName() {
		return ConfigParam.class.getName();
	}
}
