package com.wfsc.services.favourite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfsc.common.bo.account.Favourite;
import com.wfsc.daos.account.FavouriteDao;

@Service("favouriteService")
public class FavouriteServiceImpl implements IFavouriteService {
	
	@Autowired
	private FavouriteDao favouriteDao;

	@Override
	public List<Favourite> getFavouritesByUserId(long userId, int start, int limit) {
		return favouriteDao.getFavouriteByUserId(userId, start, limit);
	}

	@Override
	public Long add(Favourite favourite){
		return favouriteDao.saveEntity(favourite);
	}
	
	public void deleteByUserIdAndProId(Long userId, Long proId){
		favouriteDao.deleteByUserIdAndProId(userId, proId);
	}

	@Override
	public Favourite getFavouriteByUserIdAndPrdId(long userId, long prdId) {
		return favouriteDao.getFavouriteByUserIdAndPrdId(userId, prdId);
	}

	@Override
	public List<Favourite> getFavouritesByUserId(long userId) {
		return favouriteDao.getFavouriteByUserId(userId);
	}
}
