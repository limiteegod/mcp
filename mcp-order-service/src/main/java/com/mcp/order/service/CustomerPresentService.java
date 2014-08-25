/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.CustomerPresentDao;
import com.mcp.order.model.ts.CustomerPresent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("customerPresentService")
public class CustomerPresentService {
	
	@Autowired
	private CustomerPresentDao customerPresentDao;
	
	public CustomerPresent findOne(String id)
	{
		return this.customerPresentDao.findOne(id);
	}
	
	public List<CustomerPresent> findAllByCustomerIdAndProviderIdAndStatus(String customerId, String providerId, int status)
	{
		return this.customerPresentDao.findAllByCustomerIdAndProviderIdAndStatus(customerId, providerId, status);
	}
}
