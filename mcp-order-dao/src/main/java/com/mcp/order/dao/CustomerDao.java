package com.mcp.order.dao;


import com.mcp.order.model.ts.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerDao extends PagingAndSortingRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
	
	public Customer findOneByIdentityId(String identityId);
	
	public Customer findOneByNameAndChannelCode(String name, String channelCode);
	
	public Customer findOneByPhoneAndChannelCode(String phone, String channelCode);
	
	public Customer findOneByEmail(String email);
	
	@Modifying
    @Query("update Customer cs set cs.nickyName=?1,cs.phone=?2,cs.email=?3,cs.realName=?4,cs.identityId=?5 where cs.id=?6")
	public int updateInfoById(String nickyName, String phone, String email, String realName, String identityId, String id);

    @Modifying
    @Query("update Customer cs set cs.password=?1 where cs.id=?2")
    public int updatePasswordById(String password, String id);

	public List<Customer> findAll();
    
	public List<Customer> findAllByNameLike(String userName);
    
	public List<Customer> findAllByPhoneLike(String phone);
	
	@Modifying
    @Query("update Customer cs set cs.recharge=cs.recharge+?1 where cs.id=?2")
	public int incrRechageById(long money, String id);
	
	@Modifying
    @Query("update Customer cs set cs.recharge=cs.recharge-?1 where cs.id=?2")
	public int decrRechageById(long money, String id);
}
