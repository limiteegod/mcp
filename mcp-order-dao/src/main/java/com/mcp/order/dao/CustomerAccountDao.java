package com.mcp.order.dao;

import com.mcp.order.model.ts.CustomerAccount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerAccountDao extends PagingAndSortingRepository<CustomerAccount, String> {
	
	public List<CustomerAccount> findAllByCustomerId(String customerId);
	
	public CustomerAccount findOneByCustomerIdAndStationId(String customerId, String stationId);
	
	@Modifying
    @Query("update CustomerAccount cs set cs.recharge=cs.recharge+?1 where cs.id=?2")
	public int incrRechageById(long money, String id);
	
	@Modifying
    @Query("update CustomerAccount cs set cs.prize=cs.prize+?1 where cs.id=?2")
	public int incrPrizeById(long money, String id);
	
	@Modifying
    @Query("update CustomerAccount cs set cs.recharge=cs.recharge-?1 where cs.id=?2")
	public int decrRechageById(long money, String id);
	
	@Modifying
    @Query("update CustomerAccount cs set cs.prize=cs.prize-?1 where cs.id=?2")
	public int decrPrizeById(long money, String id);
	
	@Modifying
    @Query("update CustomerAccount cs set cs.recharge=cs.recharge+?1 where cs.customerId=?2 and cs.stationId=?3")
	public int incrRechageByCustomerIdAndStationId(long money, String customerId, String stationId);
	
	@Modifying
    @Query("update CustomerAccount cs set cs.recharge=cs.recharge-?1 where cs.customerId=?2 and cs.stationId=?3")
	public int decrRechageByCustomerIdAndStationId(long money, String customerId, String stationId);
	
	@Modifying
    @Query("update CustomerAccount cs set cs.recharge=?1 where cs.customerId=?2 and cs.stationId=?3")
	public int setRechargeByCustomerIdAndStationId(long money, String customerId, String stationId);
}
