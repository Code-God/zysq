package dao;

import org.springframework.stereotype.Repository;

import model.bo.auth.Org;

import com.base.EnhancedHibernateDaoSupport;
@Repository("orgDao")
public class OrgDao extends EnhancedHibernateDaoSupport<Org> {

	@Override
	protected String getEntityName() {
		return Org.class.getName();
	}

	public Org getByCode(String orgCode) {
		return this.getUniqueEntityByOneProperty("code", orgCode);
	}
}
