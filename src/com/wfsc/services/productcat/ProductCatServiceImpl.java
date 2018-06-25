package com.wfsc.services.productcat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.exception.CupidRuntimeException;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.daos.product.ProductCatDao;

@Service("productCatService")
@SuppressWarnings("unchecked")
public class ProductCatServiceImpl implements IProductCatService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private ProductCatDao productCatDao;

	@Override
	public List<ProductCat> findProductCatByCode(String code) {
		List<ProductCat> list = productCatDao.findByCode(code);
		return list;
	}

	@Override
	public List<ProductCat> getAllProductCat() {
		return productCatDao.queryAll();
	}
	
	@Override
	public Page<ProductCat> queryCatForPage(Page<ProductCat> page, Map<String, Object> paramap) {
		return productCatDao.findForPage(page, paramap);
	}

	@Override
	public ProductCat getProductCatById(Long id) {
		ProductCat productCar = productCatDao.getEntityById(id);
		return productCar;
	}

	@Override
	public void saveOrUpdateProductCat(ProductCat productCat) {
		//如果是一级分类，则需要限制一级分类的个数
		if(productCat.getId() == null && productCat.getLevel() == 1){
			int size = productCatDao.countTopPrdCat();
			if(size >= 11){
				throw new CupidRuntimeException("最多允许添加11个一级分类");
			}
		}
		productCatDao.saveOrUpdateEntity(productCat);
	}

	@Override
	public void deleteProductCatByIds(List<Long> ids) {
		if(CollectionUtils.isNotEmpty(ids)){
			for(Long id : ids){
				ProductCat cat = productCatDao.getEntityById(id);
				if(cat != null && cat.getParentId() != 0){
					int childCount = productCatDao.countChildCat(cat.getParentId());
					if(childCount > 0){
						throw new CupidRuntimeException(cat.getName() + "分类拥有子分类，无法删除！");
					}
				}
			}
		}
		productCatDao.deletAllEntities(ids);
	}
	
	@Override
	public List<ProductCat> findBySeachKey(String key) {
		return productCatDao.findBySeachKey(key);
	}

	@Override
	public ProductCat findByCode(String code) {
		return productCatDao.getEntitiesByOrCondition("code", code).get(0);
	}
	
	@Override
	public String generatorPrdCatCode(int level, long levelOneId, long levelTwoId) {
		List<ProductCat> prdCats = productCatDao.queryAll();
		ProductCat prdCat1 = getProductCatById(levelOneId);
		ProductCat prdCat2 = getProductCatById(levelTwoId);
		String code = "";
		if(level == 1){
			if(CollectionUtils.isEmpty(prdCats)){
				code = "001";
			}else{
				long id = 0;
				String currCode = "";
				for(ProductCat prdCat : prdCats){
					if(prdCat.getLevel() == 1 && prdCat.getId() > id){
						id = prdCat.getId();
						currCode = prdCat.getCode();
					}
				}
				int maxCode = Integer.parseInt(currCode);
				int newCode = maxCode + 1;
				code = String.format("%03d", newCode);
			}
		}else if(level == 2){
			if(CollectionUtils.isEmpty(prdCats)){
				throw new CupidRuntimeException("请先添加一级分类");
			}
			if(prdCat1 == null)
				throw new CupidRuntimeException("请选择一级分类");
			
			long id = 0;
			String currCode = "";
			for(ProductCat prdCat : prdCats){
				if(prdCat.getLevel() == 2 && prdCat.getId() > id){
					id = prdCat.getId();
					currCode = prdCat.getCode();
				}
			}
			if(id == 0){
				code = prdCat1.getCode() + "001";
			}else{
				String selfCode = currCode.substring(3);
				int maxCode = Integer.parseInt(selfCode);
				int newCode = maxCode + 1;
				code = prdCat1.getCode() + String.format("%03d", newCode);
			}
		}else if (level == 3){
			if(CollectionUtils.isEmpty(prdCats)){
				throw new CupidRuntimeException("请先添加一级和二级分类");
			}
			if(prdCat1 == null)
				throw new CupidRuntimeException("请选择一级分类");
			if(prdCat2 == null)
				throw new CupidRuntimeException("请选择二级分类");
			
			long id = 0;
			String currCode = "";
			for(ProductCat prdCat : prdCats){
				if(prdCat.getLevel() == 3 && prdCat.getId() > id){
					id = prdCat.getId();
					currCode = prdCat.getCode();
				}
			}
			if(id == 0){
				code = prdCat2.getCode() + "001";
			}else{
				String selfCode = currCode.substring(6);
				int maxCode = Integer.parseInt(selfCode);
				int newCode = maxCode + 1;
				code = prdCat2.getCode() + String.format("%03d", newCode);
			}
		}
		return code;
	}

	@Override
	public List<ProductCat> getPrdCatsByParentId(long parentId) {
		return productCatDao.getEntitiesByOneProperty("parentId", parentId);
	}

	@Override
	public List<ProductCat> getAllProductCatByOrg(String fxCode, String pid) {
		if(fxCode.length() > 3){
			fxCode = fxCode.substring(0, 3);
		}
		return productCatDao.getEntitiesByPropNames(new String[]{"fxCode", "parentId"}, new Object[]{fxCode, Long.valueOf(pid)});
	}

	@Override
	public List<ProductCat> getAllProductCat(String orgCode) {
		return productCatDao.getEntitiesByOneProperty("fxCode", orgCode);
	}
}
