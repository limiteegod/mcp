/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.PromotionDao;
import com.mcp.order.model.ts.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("promotionService")
public class PromotionService {
	
	@Autowired
	private PromotionDao promotionDao;
	
	/**
	 * 获取系统所有可用活动
	 * @return
	 */
	public List<Promotion> getAllAvailablePromotions()
	{
		return this.promotionDao.findAllByExpiredTimeAfterOrderByCreateTimeDesc(new Date());
	}
}
