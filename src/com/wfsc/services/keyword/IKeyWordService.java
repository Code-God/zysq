package com.wfsc.services.keyword;

import java.util.List;

import com.wfsc.common.bo.keyword.KeyWord;

/**
 * 搜索历史管理
 * @author Xiaojiapeng
 *
 */
public interface IKeyWordService {
	
	/**
	 * 更新keyword关键字
	 * @param keyword
	 */
	public void updateKeywordCounter(String keyword);
	
	/**
	 * 查询最热门的10个关键字
	 * @param key TODO
	 * @return
	 */
	public List<KeyWord> findAllKeyWord(String key);

}
