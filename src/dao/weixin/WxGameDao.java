package dao.weixin;

import model.bo.WxGame;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 * @author jacky.wang
 */
@Repository("wxGameDao")
public class WxGameDao extends EnhancedHibernateDaoSupport<WxGame> {

	@Override
	protected String getEntityName() {
		return WxGame.class.getName();
	}
}