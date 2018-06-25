package dao;

import java.util.List;

import model.bo.wxmall.WxMenu;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("wxMenuDao")
public class WxMenuDao extends EnhancedHibernateDaoSupport<WxMenu> {

	@Override
	protected String getEntityName() {
		return WxMenu.class.getName();
	}

	public List<WxMenu> getByOrgCode(Long orgId) {
		return this.getEntitiesByOneProperty("orgId", orgId);
	}
}
