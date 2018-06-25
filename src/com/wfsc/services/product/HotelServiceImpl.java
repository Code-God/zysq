package com.wfsc.services.product;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Paging;
import model.bo.act.HongBao;
import model.bo.act.UserHongBao;
import model.bo.hotel.BookRecord;
import model.bo.hotel.RoomTimeline;
import model.bo.wxmall.Pj;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.wfsc.common.bo.product.Products;
import com.wfsc.daos.hotel.BookRecordDao;
import com.wfsc.daos.hotel.RoomTimeLineDao;
import com.wfsc.daos.product.ProductsDao;
import com.wfsc.util.DateUtil;

import dao.hongbao.HongBaoDao;
import dao.hongbao.UserHongBaoDao;

@Service("hotelService")
public class HotelServiceImpl implements IHotelService {
	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Autowired
	private RoomTimeLineDao roomTimelineDao;
	
	@Autowired
	private BookRecordDao bookRecordDao;
	
	@Autowired
	private UserHongBaoDao userHongBaoDao;
	
	@Autowired
	private HongBaoDao hongBaoDao;
	
	@Override
	public int bookRoom(Map<String, String> param) {
		//1-------------先检查当天是否有房间可以预定
		//将记录插入到预约单表
		Long prdId = Long.valueOf(param.get("prdId"));
		Long orgId = Long.valueOf(param.get("orgId"));
		String count = param.get("count");
		String openId = param.get("openId");
		String username = param.get("username");
		String tel = param.get("tel");
		String bookdate = param.get("bookdate");//预定日期
//		bookdate = "2016-02-04";
		String arriveTime = param.get("arriveTime");
		// 根据prdId， 日期，查询当天所有的房间
//		List<RoomTimeline> entitiesByPropNames = roomTimelineDao.getEntitiesByPropNames(new String[]{"prdid","rdate"}, new Object[]{prdId, bookdate});
		List find = roomTimelineDao.getHibernateTemplate().find("from RoomTimeline where prdid= ? and rdate= ? and rstatus = 0", prdId, bookdate);
		if(find.size() == 0){
			logger.info("[hotel]当日房间已经没有空闲的");
			return 0;
		}else{
			try{
				BookRecord br = new BookRecord();
				br.setBooknum(Integer.valueOf(count));
				br.setBookUser(username); 
				br.setBookDate(bookdate);
				br.setBstatus(1);//预约中
				br.setOpenId(openId);
				br.setOrgId(orgId);
				br.setBooktype(0);//保留字段
				br.setPrdid(prdId);
				br.setSubmitDate(DateUtil.getLongCurrentDate());
				br.setTelephone(tel);
				//设置最迟到店的时间
				Calendar cal = Calendar.getInstance();
				Date date = DateUtil.getDateFromShort(bookdate);
				cal.setTime(date);
				cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(arriveTime));
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				br.setArriveTime(DateUtil.getLongDate(cal.getTime()));
				Long bookId = bookRecordDao.saveEntity(br);
				logger.info("[hotel]预定记录保存完毕...." + bookId);
				//2...根据预定日期，设置预定状态，并插入预订单记录
				//因为对于同一个房型的房间来说，任意一条记录都可以，所以取find.get(0)
				RoomTimeline rtl = (RoomTimeline) find.get(0);
				rtl.setRstatus(RoomTimeline.RSTATUS_BOOKED);//房间资源状态
				rtl.setPrdid(prdId);//房型产品ID
				rtl.setOrderId(bookId);//预约单ID
				roomTimelineDao.updateEntity(rtl);
				logger.info("[hotel]房间时间记录更新完毕....");
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		return 1;
	}

	public RoomTimeLineDao getRoomTimelineDao() {
		return roomTimelineDao;
	}

	public void setRoomTimelineDao(RoomTimeLineDao roomTimelineDao) {
		this.roomTimelineDao = roomTimelineDao;
	}

	public BookRecordDao getBookRecordDao() {
		return bookRecordDao;
	}

	public void setBookRecordDao(BookRecordDao bookRecordDao) {
		this.bookRecordDao = bookRecordDao;
	}

	@Override
	public void checkAndUpdateBookRoom() {
		String shortCurrentDate = DateUtil.getShortCurrentDate();
		List<RoomTimeline> find = roomTimelineDao.getHibernateTemplate().find("from RoomTimeLine where rdate like '"+ shortCurrentDate +"%' and rstatus=" + RoomTimeline.RSTATUS_BOOKED);
		if(find.isEmpty()){
			return;
		}
		//检查是否有房间的保留时间已经过期，重置状态
		for (RoomTimeline rtl : find) {
			//根据预约单号，找预约单
			BookRecord br = bookRecordDao.getUniqueEntityByOneProperty("id", rtl.getOrderId());
			if(System.currentTimeMillis() > DateUtil.convertStringDate2Long(br.getArriveTime())){
				logger.info("房间预订时间已经过期....");
				br.setBstatus(0);
				bookRecordDao.updateEntity(br);
				
				//修改timeLine中的状态
				rtl.setRstatus(RoomTimeline.RSTATUS_IDLE);
				roomTimelineDao.updateEntity(rtl);
			}
		}
		logger.info("房间预订过期检查结束.....");
	}

	@Override
	public Paging getMyBookedRecords(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		BookRecordDao dao = (BookRecordDao) ServerBeanFactory.getBean("bookRecordDao");
		ProductsDao prdDao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = dao.queryRecord(start, limit, paramMap);
			// 分页对象
			int total = Integer.valueOf(map.get("total").toString());
			
			List<BookRecord> dataList = (List<BookRecord>) map.get("list");
			
			
			// 填充非持久属性
			for (BookRecord br : dataList) {
				Products prd = prdDao.getEntityById(br.getPrdid());
				if (prd != null) {
					br.setPrdName(prd.getName() + "("+ prd.getPrdCode() +")");
				} else {
					br.setPrdName("{该商品[id:" + br.getPrdid() + "]已删除}");
				}
			}
			
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!dataList.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public void updateBookRecord(Long id, int newStatus) {
		BookRecordDao dao = (BookRecordDao) ServerBeanFactory.getBean("bookRecordDao");
		BookRecord br = dao.getEntityById(id);
		br.setBstatus(newStatus);
		dao.updateEntity(br);
	}

	@Override
	public void checkHongbaoExpire() {
		//扫描userHongBao表,把未使用的红包查出来，判断是否过期
		String hql = "from UserHongBao where status = 0";
		int count = userHongBaoDao.countByHql(hql);
		logger.info("当前未过期的红包有：" + count + "个");
		int start = 0;
		int limit = 500;//每次查询500条
		//循环的次数
		int loop = 100/limit + 1;
		logger.info("循环检查次数：" + loop);
		List findList4Page = null;
		for(int i=0; i< loop; i++){
			findList4Page = userHongBaoDao.findList4Page(hql, loop * 500, 500);
			for (Object object : findList4Page) {
				UserHongBao uhb = (UserHongBao) object;
				//比较过期
				HongBao hb = hongBaoDao.getUniqueEntityByOneProperty("uuid", uhb.getHbuuid());
				if(System.currentTimeMillis() > DateUtil.convertStringDate2Long(hb.getExpireDate())){
					//过期
					uhb.setStatus(2);//设置过期
					userHongBaoDao.updateEntity(uhb);
					logger.info("用户："+uhb.getUserId() + "的红包："+uhb.getHbuuid() + "已过期...");
				}
			}
		}
		
		
	}

	
	public UserHongBaoDao getUserHongBaoDao() {
		return userHongBaoDao;
	}

	
	public void setUserHongBaoDao(UserHongBaoDao userHongBaoDao) {
		this.userHongBaoDao = userHongBaoDao;
	}

	
	public HongBaoDao getHongBaoDao() {
		return hongBaoDao;
	}

	
	public void setHongBaoDao(HongBaoDao hongBaoDao) {
		this.hongBaoDao = hongBaoDao;
	}
	 
	public static void main(String[] args){
		int start = 0;
		int limit = 500;//每次查询500条
		int loop = 600/limit + 1;
//		System.out.println(loop);
//		2016-01-29 00:00:00
		System.out.println(System.currentTimeMillis() - DateUtil.convertStringDate2Long("2016-01-28 00:00:00"));
		
		
	}

}
