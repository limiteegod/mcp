package com.mcp.order.dao;

import com.mcp.order.model.ts.CustomerPresent;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerPresentDao extends PagingAndSortingRepository<CustomerPresent, String> {
	
	public List<CustomerPresent> findAllByCustomerIdAndProviderIdAndStatus(String customerId, String providerId, int status);
	
}
