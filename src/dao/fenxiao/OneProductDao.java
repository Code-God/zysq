package dao.fenxiao;

import model.bo.fenxiao.OneProduct;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("onePrdDao")
public class OneProductDao extends EnhancedHibernateDaoSupport<OneProduct> {

	@Override
	protected String getEntityName() {
		return OneProduct.class.getName();
	}
}
