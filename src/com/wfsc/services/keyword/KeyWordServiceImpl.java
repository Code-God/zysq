package com.wfsc.services.keyword;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfsc.common.bo.keyword.KeyWord;
import com.wfsc.daos.keyword.KeyWordDao;

@Service("keywordService")
public class KeyWordServiceImpl implements IKeyWordService {
	
	@Autowired
	private KeyWordDao keywordDao;

	@Override
	public void updateKeywordCounter(String keyword) {
		KeyWord kw = keywordDao.getKeyWord(keyword);
		if(kw == null){
			kw = new KeyWord();
			kw.setSearchCount(1);
			kw.setActiveDate(new Date());
			kw.setKeyword(keyword);
			int count = keywordDao.count();
			
			//如果满了1000条删除最老的一条记录
			if(count >= 1000){
				keywordDao.deleteOldestKeyWord();
			}else{
				keywordDao.saveEntity(kw);
			}
		}else{
			kw.setSearchCount(kw.getSearchCount() + 1);
			kw.setActiveDate(new Date());
			keywordDao.updateEntity(kw);
		}
	}
	
	@Override
	public List<KeyWord> findAllKeyWord(String key) {
		return keywordDao.findAllKeyWord(key);
	}

}
