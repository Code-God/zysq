package dao.drug;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugDiseaseDict;
import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugResearchCenter;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;

/**
 * 疾病类型
 * @author Administrator
 *
 */
@Repository("drugDiseaseItemDao")
public class DrugDiseaseItemDao extends EnhancedHibernateDaoSupport<DrugDiseaseItem>{

	@Override
	protected String getEntityName() {
		return DrugDiseaseItem.class.getName();
	}
	
	public Page<DrugDiseaseItem> findPageForDisease(Page<DrugDiseaseItem> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from DrugDiseaseItem where 1=1");
		for(String key : paramap.keySet()){
			if("diseaseName".equals(key)){
				hql.append(" and (diseaseName like '%"+paramap.get(key)+"%' or searchKey like '%"+paramap.get(key)+"%')");
				continue;
			}			
		}
		hql.append(" order by  updateTime desc");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<DrugDiseaseItem> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());	
		page.setData(list);
		return page;
	}
	
	/**
	 * 通过疾病搜索中心统计信息
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page findPageForCenter(final Page page, Map<String,Object> paramap){
		String param1 = "";
		String param2 = "";
		String keyword = "";
		for(String key : paramap.keySet()){
		if("diseaseName".equals(key)){
		    keyword = paramap.get(key).toString();
			if(keyword!=null&&keyword.length()>0){
				param1 = " t1.adaptation like '%"+keyword+"%' or t1.searchKey like '%"+keyword+ "%'";
				param2 = " t1.disease_name like '%"+keyword+"%' or t1.searchKey like '%"+keyword+ "%'";
			}
			
			break;
		}			
	}
		String sqlCount = "";
		String sqlQuery = "";
		if(keyword.length()==0){
			sqlCount = "select count(1) from "
					+ "(select t1.cname,count(t1.cname) ccount FROM ( "
					+ "select institution_name cname from drug_research_center "
					+ "UNION ALL "
					+ "select organization_name cname from drug_project_conter ) t1 group by t1.cname) t";
			
			sqlQuery = "select t.cname,count(t.cname) ccount,'' sponsorinfo FROM( "
					+ "select institution_name cname from drug_research_center "
					+ "UNION ALL "
					+ "select organization_name cname from drug_project_conter) t "
					+ "group by t.cname order by ccount desc";
		}
		else{
			sqlCount = "select count(1) from (select cname,count(cname) ccount from "
					+ "(select t2.institution_name cname from drug_disease_dict t1 "
					+ "inner join drug_research_center t2 on t1.ctr_id=t2.ctr_id "
					+ "where  " + param1
					+ "union all select t2.organization_name cname from drug_disease_item t1 "
					+ "inner join drug_project_conter t2 on t1.id=t2.diseaseid "
					+ "where  "+ param2
					+ " ) t group by t.cname) tb";
			sqlQuery = "select cname,count(cname) ccount,left(group_concat(distinct sponsorinfo),65532) sponsorinfo from "
					+ "(select t2.institution_name cname,t1.sponsorinfo sponsorinfo from drug_disease_dict t1 "
					+ "inner join drug_research_center t2 on t1.ctr_id=t2.ctr_id "
					+ "where " + param1
					+ "union all select t2.organization_name cname,'' sponsorinfo from drug_disease_item t1 "
					+ "inner join drug_project_conter t2 on t1.id=t2.diseaseid "
					+ "where " + param2
					+ ") t group by t.cname ORDER BY ccount desc";
		}
		
		final String strCount = sqlCount;
		final String sql = sqlQuery;

		//hql.append(" order by  updateTime desc");
		
		List<Number> result = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(strCount);
				return q.list();
			}
		});	
	    int totalCount = Integer.parseInt(result.get(0).toString());
		page.setTotalCount(totalCount);
		
		List<Object> list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql);
				q.setFirstResult(page.getFirst()-1);
				q.setMaxResults(page.getPageSize());
				return q.list();
			}
		});	
		page.setData(list);
		return page;
	}
	
	/**
	 * 查看所有疾病信息
	 * @return
	 */
	public List<DrugDiseaseItem> getAllDisease(){
		String hql = "from DrugDiseaseItem order by id desc";
		return (List<DrugDiseaseItem>)this.getHibernateTemplate().find(hql);
	}
	
	
	/**
	 * 查看与用户有关的项目(推荐过的项目，或者报名的项目)
	 */
	public List getRelationDrugProjects(String userid){
		final String sql = " select  disease.id,disease.disease_Name,disease.disease_Profile  from drug_u_d_relation as relation "
					+" inner join drug_disease_item as disease on relation.disease_id=disease.id " 
 					 +" where relation.user_id="+userid+" or relation.referrer_id="+userid;
		
		final String sql2 ="select disease.id,disease.disease_Name,disease.disease_Profile "+		
			               " from drug_disease_item as disease where disease.isShow=1 ";
		//与个人相关项目
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql);
				q.setFirstResult(0);
				q.setMaxResults(2);
				return q.list();
			}
		});
		
		//与个人相关项目不存在，查询热门项目
		if(list==null||list.size()==0){
			list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws SQLException, HibernateException {
					Query q = session.createSQLQuery(sql2);
					q.setFirstResult(0);
					q.setMaxResults(2);
					return q.list();
				}
			});
		}
		return list;
	}

	
	/**
	 * 前台通过是否显示展示图片
	 */
	public List showDiseaseImg(){
		final StringBuffer sql = new StringBuffer("SELECT a.id,a.disease_name,a.medicine_name,a.imgpath,IFNULL(count(b.id),0) "
				+ " FROM drug_disease_item a LEFT JOIN drug_u_d_relation b ON a.id = b.disease_id"
				+ " WHERE isshow = 1 GROUP BY a.id,a.disease_name,a.medicine_name,a.imgpath ORDER BY a.update_time desc");
		System.out.println(sql);
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql.toString());
				return q.list();
			}
		});
		
		return list;
	}
	/**
	 * 查看项目下的疾病信息
	 * @param id 分类ID
	 * @return
	 */
	
	public  List<DrugDiseaseItem> getClassificationinDisease(String id){
		String hql="from DrugDiseaseItem where classificationId='"+id+"' order by updateTime desc" ;
		return this.getHibernateTemplate().find(hql);
		
		
	}
	
	/**
	 * 通过药品名称查询项目
	 */
	
	public List getDiseaseItemByName(String name){
		String hql="";
		
		/*
		if(name!=null)
		{
			if(name.trim()=="高血脂" || name.trim()=="高脂血" || name.trim().equals("高血脂") || name.trim().equals("高脂血") )
			{
				hql=" select item.id,item.diseaseName,item.diseaseProfile from DrugDiseaseItem item where item.diseaseName like '%高血脂%'  or item.diseaseProfile like '%高血脂%' or item.diseaseName like '%高脂血%'  or item.diseaseProfile like '%高脂血%'  order by update_time desc ";
			}
			else{
				if(name.length()>=3){
					
					String shortName = name.substring(0,2);
					System.out.println(shortName+"uuuuuuuuuuuuuuuuuuuu");
					hql =" select item.id,item.diseaseName,item.diseaseProfile  from DrugDiseaseItem item where (item.diseaseName like '%"+name+"%'  or item.diseaseProfile like '%"+name+"%') or (item.diseaseName like '%"+shortName+"%'  or item.diseaseProfile like '%"+shortName+"%' ) order by update_time desc";
					  
				}
				else{
					hql=" select item.id,item.diseaseName,item.diseaseProfile from DrugDiseaseItem item where item.diseaseName like '%"+name+"%'  or item.diseaseProfile like '%"+name+"%'  order by update_time desc";
				}
			}
			
			//hql = " select item.id,item.diseaseName,item.diseaseProfile from DrugDiseaseItem item where item.diseaseName like '%"+name+"%'  or item.searchKey like '%"+name+"%'  order by update_time desc";
			
		}
		*/
		
		if(name!=null)
		{
			hql = " select item.id,item.diseaseName,item.diseaseProfile from DrugDiseaseItem item where item.diseaseName like '%"+name+"%'  or item.searchKey like '%"+name+"%'  order by update_time desc";	
		}
		return this.getHibernateTemplate().find(hql);
	}
	/**
	 * 通过药品名称查询捷信项目
	 */
	
	public List<DrugDiseaseDict> getDrugDiseaseDictByName(String key,int pageIndex){
	/*	String hql="";
		if (name!=null && !name.equals("")) {
			hql="from DrugDiseaseDict where dicDiseaseName like '%"+name+"%' or dicMedicineName like '%"+name+"%' or dicDiseaseProfile like '%"+name+"%'  order by id desc";
		}*/
		
		String hql="";
		if(key!=null)
		{
			String[] words = key.trim().split("\\s+");
			
			String sql = "";
			
			for(String word : words)
			{
				sql = sql + " dict.ctrId like '%"+word+"%' or dict.dicDiseaseName like '%"+word+"%' or dict.generalTitle like '%"+word+"%'  or dict.dicDiseaseProfile like '%"+word+"%' or dict.adaptation like '%"+word+"%' or dict.designPurpose like '%"+word+"%' or dict.searchKey like '%"+word+"%' or ";
			}
			int lastIndex = sql.lastIndexOf("or");
			sql = sql.substring(0, lastIndex);
			System.out.println(pageIndex);
			
			hql=String.format(" select dict.id,dict.ctrId,dict.dicDiseaseName,dict.generalTitle ,dict.state from DrugDiseaseDict dict where %s order by ctrId desc ",sql);
			
		}
		
//		key = key.trim();
//		hql = " select dict.id,dict.ctrId,dict.dicDiseaseName,dict.generalTitle ,dict.state from DrugDiseaseDict dict where dict.dicDiseaseName like '%"+key+"%' or dict.searchKey like '%"+key+"'% order by ctrId desc ";
		
		return this.findList4Page(hql.toString(), (pageIndex - 1)*10, 10);
	}
	/**
	 * 通过id查询捷信项目详细
	 */
	
	public DrugDiseaseDict findDiseaseDictById(Long id){
		
		return  this.getHibernateTemplate().get(DrugDiseaseDict.class, id);
	}
	/**
	 * 通过登记号查询机构
	 */
	public List<DrugResearchCenter>getResearchCenterByCtrId(String ctrId){
		String hql="from DrugResearchCenter where ctrId='"+ctrId+"'";
		return this.getHibernateTemplate().find(hql);
		
	}

}
