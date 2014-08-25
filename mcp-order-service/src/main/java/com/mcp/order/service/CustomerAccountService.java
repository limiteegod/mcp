/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.CustomerAccountDao;
import com.mcp.order.model.ts.CustomerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author ming.li
 */
@Service("customerAccountService")
public class CustomerAccountService {
	
	@Autowired
	private CustomerAccountDao customerAccountDao;
	
	public List<CustomerAccount> findAllByCustomerId(String customerId)
	{
		return this.customerAccountDao.findAllByCustomerId(customerId);
	}
	
	public CustomerAccount findOne(String id)
	{
		return this.customerAccountDao.findOne(id);
	}
	
	public CustomerAccount save(CustomerAccount customerAccount)
	{
		return this.customerAccountDao.save(customerAccount);
	}
	
	public CustomerAccount findOneByCustomerIdAndStationId(String customerId, String stationId)
	{
		return this.customerAccountDao.findOneByCustomerIdAndStationId(customerId, stationId);
	}
	
	@Transactional
	public int setRechargeByCustomerIdAndStationId(long money, String customerId, String stationId)
	{
		return this.customerAccountDao.setRechargeByCustomerIdAndStationId(money, customerId, stationId);
	}
	
	@Transactional
	public int incrRechageByCustomerIdAndStationId(long money, String customerId, String stationId)
	{
		return this.customerAccountDao.incrRechageByCustomerIdAndStationId(money, customerId, stationId);
	}
	
	@Transactional
	public int decrRechageByCustomerIdAndStationId(long money, String customerId, String stationId)
	{
		return this.customerAccountDao.decrRechageByCustomerIdAndStationId(money, customerId, stationId);
	}
	
	@Transactional
	public int incrPrizeById(long money, String id)
	{
		return this.customerAccountDao.incrPrizeById(money, id);
	}
}
