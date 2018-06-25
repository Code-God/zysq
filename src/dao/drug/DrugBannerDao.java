package dao.drug;
import java.util.List;
import model.bo.drug.DrugBanner;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

@Repository("drugBannerDao")
public class DrugBannerDao extends EnhancedHibernateDaoSupport<DrugBanner> {

	@Override
	protected String getEntityName() {
		return DrugBanner.class.getName();
	
	}
	/**
	 * 保存
	 */
	public void saveBanner(DrugBanner drugBanner) {

		getHibernateTemplate().save(drugBanner);

	}

	/**
	 * 查询详细
	 */
	public DrugBanner getBannerById(Long id) {

		return getHibernateTemplate().get(DrugBanner.class, id);
	}
	
	/**
	 * 查询列表
	 */
	
	public List<DrugBanner> getAll(){
		String hql=" from DrugBanner order by id desc";
		return getHibernateTemplate().find(hql);
	}
	/**
	 * 删除
	 */
	
	public void delete(DrugBanner drugBanner){
		getHibernateTemplate().delete(drugBanner);
	}
	
	/**
	 * 修改
	 */
	
	public void modify(DrugBanner drugBanner){
		getHibernateTemplate().update(drugBanner);
	}

}
