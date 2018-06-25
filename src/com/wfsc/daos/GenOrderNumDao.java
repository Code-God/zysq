/******************************************************************************** 
 * Create Author   : JONIM.XIA
 * Create Date     : Oct 23, 2009
 * File Name       : GenOrderNumDAO.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.daos;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.system.GenOrderNum;
@SuppressWarnings("unchecked")
@Repository("genOrdernumDao")
public class GenOrderNumDao extends EnhancedHibernateDaoSupport<GenOrderNum> {
	
	/**各种单据号缓存*/
	private static ConcurrentMap<String, GenOrderNum> cacheMap = new ConcurrentHashMap<String, GenOrderNum>();
	
	/**
	 * 未保存的序号对象队列，如果队列没有待保存的对象，保存序号对象的线程就阻塞
	 */
	private BlockingQueue<GenOrderNum> genOrderNumQueue;
	
	
	private GenOrderNumDao(){
		genOrderNumQueue = new ArrayBlockingQueue<GenOrderNum>(1000);
		new Thread(new saveGenOrderNumThread()).start();//启动保存序号对象的线程
	}
	
	/**
	 * 生成各种单据号
	 */
	@SuppressWarnings("unchecked")
	public synchronized Long getOrderNum(String orderType) {
		if (orderType == null) return 1L;
		Long currOrderNum = null;
		GenOrderNum genOrderNum = cacheMap.get(orderType);
		if(genOrderNum == null){
			List<GenOrderNum> list = getHibernateTemplate().find("from GenOrderNum where type=? and enabledFlag=?", new Object[] { orderType, true });
			if(null !=list && !list.isEmpty()){
				genOrderNum = list.get(0);
				cacheMap.put(orderType, genOrderNum);
			}else{
				genOrderNum = new GenOrderNum();
				genOrderNum.setNum(Long.valueOf(0));
				genOrderNum.setEnabledFlag(true);
				genOrderNum.setType(orderType);
				cacheMap.put(orderType, genOrderNum);
			}
		}
		currOrderNum = genOrderNum.getNum();
		currOrderNum +=1;
		genOrderNum.setNum(currOrderNum);
		try {
			genOrderNumQueue.put(genOrderNum);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return currOrderNum;
	}
	
	/**
	 * 
	 * 专门用于保存序号对象的线程
	 * 该线程从阻塞对象取一个序号对象，如果没有需要保存的序号对象
	 * 此线程一直阻塞，直至系统中有需要保存的序号对象
	 *
	 * @author LevyLiu
	 * @version 1.0
	 * @since Apex OssWorks 5.5
	 */
	class saveGenOrderNumThread implements Runnable{
		@Override
		public void run() {
			while(true){
				try {
					GenOrderNum genOrderNum = genOrderNumQueue.take();
					getHibernateTemplate().saveOrUpdate(genOrderNum);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return GenOrderNum.class.getName();
	}
}
