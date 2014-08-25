package com.mcp.order.dao;

import com.mcp.order.model.admin.BetShop;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface BetShopDao extends PagingAndSortingRepository<BetShop, String>, JpaSpecificationExecutor<BetShop> {

}
