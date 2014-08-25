package com.mcp.order.dao;

import com.mcp.order.model.ts.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface PaymentDao extends PagingAndSortingRepository<Payment, String> {
	
	public List<Payment> findAllByCustomerId(String customerId);
	
	@Query("from Payment t where t.customerId=?1")
	public Payment findOneByCustomerId(String customerId);
	
}
