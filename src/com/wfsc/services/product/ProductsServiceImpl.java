package com.wfsc.services.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.bo.fenxiao.OneProduct;
import model.bo.hotel.RoomTimeline;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.exception.CupidRuntimeException;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.product.ProductRecommend;
import com.wfsc.common.bo.product.Products;
import com.wfsc.daos.hotel.RoomTimeLineDao;
import com.wfsc.daos.product.ProductRecommendDao;
import com.wfsc.daos.product.ProductsDao;
import com.wfsc.util.DateUtil;

import dao.fenxiao.OneProductDao;

@Service("productsService")
public class ProductsServiceImpl implements IProductsService {

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);

	@Autowired
	private ProductsDao productDao;

	@Autowired
	private OneProductDao onePrdDao;

	@Autowired
	private RoomTimeLineDao roomTimelineDao;

	@Autowired
	private ProductRecommendDao productRecommendDao;

	@Override
	public void saveOrUpdateEntity(Products products) {
		boolean isadd = false;
		if (products.getId() == null || products.getId() < 1) {
			isadd = true;
		}
		products.setTstatus(1);// 默认设置为上架
		productDao.saveOrUpdateEntity(products);

		// 如果不是普通商品， 需要生成房间时间表
//		if (isadd) {// 新增
//			if (products.getNormal() == 0) {// 酒店行业专用
//				//这里比较耗时， 调用单独线程去做
//				RTLThread rtlThr = new RTLThread(products);
//				rtlThr.start();
//				return;
//			} else {
//				// 普通商品
//			}
//		}
	}

	class RTLThread extends Thread {

		private Products products;

		public RTLThread(Products products) {
			this.products = products;
		}

		@Override
		public void run() {
			long st1 = System.currentTimeMillis();
			logger.info("独立线程去完成roomTimeLine" + st1);
			try {
				// 生成库存数量的roomTimeLine
				Integer stock = products.getStock();
				long t1 = st1;
				List<RoomTimeline> saveList = null;
				List<String> dayList = null;
				// 针对每个房间，生成3年的时间记录
				for (int i = 0; i < stock.intValue(); i++) {
					// 3年记录, 2016, 2017, 2018
					int yearStart = Integer.valueOf(DateUtil
							.getShortCurrentDate().split("-")[0]);
					for (int j = 0; j < 5; j++) {
						saveList = new ArrayList<RoomTimeline>();
						// 生成当年12个月的记录
						for (int k = 0; k < 12; k++) {
							dayList = DateUtil.getDayList(yearStart, (k + 1));
							// logger.info(yearStart + "-" + (k+1) + "==" +
							// dayList);
							for (String day : dayList) {
								RoomTimeline rtl = new RoomTimeline();
								rtl.setPrdCode(products.getPrdCode());
								rtl.setRoomSn(i + 1);
								rtl.setPrdid(products.getId());
								rtl.setPrice(products.getPrdDisPrice());
								rtl.setRdate(day);
								rtl.setRstatus(0);
								saveList.add(rtl);
							}
						}
						long t2 = st1;
						// 批量保存
						logger.info("saving..." + yearStart);
						roomTimelineDao.saveOrUpdateAllEntities(saveList);
						long t3 = st1;
						logger.info("save finish--" + (t3 - t1) + "ms");
						logger.info(yearStart + "-yearStart=" + saveList);
						saveList.clear();
						yearStart++;
					}
				}
				logger.info("独立线程去完成roomTimeLine----finished---"
						+ (System.currentTimeMillis() - st1) + " ms");

			} catch (Throwable e) {
				e.printStackTrace();
				logger.info("单独生成roomTimeLine失败....");
			}
		}

	}

	@Override
	public void deleteByIds(List<Long> ids) {
		productDao.deletAllEntities(ids);
		//roomTimeLien也删除
		for (Long prdId : ids) {
			logger.info("删除timeLine" + prdId);
			roomTimelineDao.deleteEntityByProperty("prdId", prdId);
			logger.info("删除timeLine 结束...." + prdId);
		}
	}

	@Override
	public void deleteById(Long productsId) {
		productDao.deleteEntity(productsId);
	}

	@Override
	public Page<Products> findForPage(Page<Products> page,
			Map<String, Object> paramap) {
		return productDao.findForPage(page, paramap);
	}

	@Override
	public Page<Products> findPage(Page<Products> page,
			Map<String, Object> paramap) {
		return productDao.findPage(page, paramap);
	}

	@Override
	public List<Products> getAll() {
		return productDao.getAllEntities();
	}

	@Override
	public Products getById(Long productsId) {
		return productDao.getEntityById(productsId);
	}

	@Override
	public List<Products> findByCatCode(String catCode) {
		return productDao.findByCatCode(catCode);
	}

	@Override
	public List<Products> findByRecommend() {
		return productDao.findByRecommend();
	}

	@Override
	public List<ProductRecommend> queryAllProductRecommend() {
		return productRecommendDao.queryAll();
	}

	public String getMaxCodeByCatCode(String prdcatCode) {

		return productDao.getMaxCodeByCatCode(prdcatCode);
	}

	public Products getByPrdCode(String prdCode) {
		return productDao.getUniqueEntityByOneProperty("prdCode", prdCode);
	}

	@Override
	public Products findByCode(String code) {
		return productDao.findByCode(code);
	}

	@Override
	public List<String> findBySeachKey(String key) {
		return productDao.findBySeachKey(key);
	}

	@Override
	public Products getPrductById(Long prdId) {
		return productDao.getEntityById(prdId);
	}

	@Override
	public List<Products> findByCatCode(String catCode, int start, int limit,
			int sort) {
		String order = "createDate";
		String sorter = "desc";
		if (sort == 1) {
			order = "prdPrice";
			sorter = "asc";
		}
		return productDao.findByCatCode(catCode, start, limit, order, sorter);
	}

	@Override
	public List<Products> findByCatCode(String catCode, int start, int limit,
			String sorter, String order) {
		return productDao.findByCatCode(catCode, start, limit, order, sorter);
	}

	@Override
	public List<Products> getProductByKeyword(String keyword, int start,
			int limit) {
		return productDao.getProductByKeyword(keyword, start, limit);
	}

	@Override
	public List<ProductRecommend> queryRecommendByType(int type) {
		return productRecommendDao.queryRecommendByType(type);
	}

	@Override
	public void addProductRecommend(int type, String prdCode) {
		int count = productRecommendDao.countRecommendByType(type);
		if (type == 1) {
			if (count >= 6) {
				throw new CupidRuntimeException("最多设置6个新品推荐商品");
			}
		} else if (type == 2) {
			if (count >= 8) {
				throw new CupidRuntimeException("最多设置8个本周特惠商品");
			}
		}
		Products product = findByCode(prdCode);
		if (product == null) {
			throw new CupidRuntimeException("商品不存在或已被删除");
		}
		ProductRecommend recommend = new ProductRecommend();
		recommend.setType(type);
		recommend.setProduct(product);

		productRecommendDao.saveEntity(recommend);
	}

	@Override
	public void deleteRecommendById(long id) {
		productRecommendDao.deleteEntity(id);
	}

	@Override
	public ProductRecommend getRecommendByTypeAndPrdCode(int type,
			String prdCode) {
		return productRecommendDao.getRecommendByTypeAndPrdCode(type, prdCode);
	}

	@Override
	public int getRecommendCount(Integer recommend, String prdCatCode) {
		String topCode = prdCatCode.substring(0, 3);
		String hql = "from Products where recommend=" + recommend
				+ " and prdCatCode like '" + topCode + "%'";
		List list = productDao.getHibernateTemplate().find(hql);
		return list == null ? 0 : list.size();
	}

	@Override
	public OneProduct getOnePrdById(Long id) {
		return onePrdDao.getEntityById(id);
	}

	public void setOnePrdDao(OneProductDao onePrdDao) {
		this.onePrdDao = onePrdDao;
	}

	@Override
	public void saveOrUpdateOnePrd(OneProduct one) {
		onePrdDao.saveOrUpdateEntity(one);
	}

	@Override
	public Products getPrductByCode(String prdCode) {
		return productDao.getUniqueEntityByOneProperty("prdCode", prdCode);
	}

	@Override
	public void modSaleCount(String prdId, String saleCount) {
		String sql = "update Products set prdSaleCount="
				+ Long.valueOf(saleCount) + " where id=" + Long.valueOf(prdId);
		productDao.getHibernateTemplate().bulkUpdate(sql);
	}

	public RoomTimeLineDao getRoomTimelineDao() {
		return roomTimelineDao;
	}

	public void setRoomTimelineDao(RoomTimeLineDao roomTimelineDao) {
		this.roomTimelineDao = roomTimelineDao;
	}

}
