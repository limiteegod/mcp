package com.mcp.order.dao;

import com.mcp.order.model.ts.Promotion;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface PromotionDao extends PagingAndSortingRepository<Promotion, String> {
	
	public List<Promotion> findAllByExpiredTimeAfterOrderByCreateTimeDesc(Date expiredTime);
}
