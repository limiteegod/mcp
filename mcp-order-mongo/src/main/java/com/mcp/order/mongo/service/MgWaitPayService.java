package com.mcp.order.mongo.service;

import com.mcp.order.model.mongo.MgWaitPay;
import com.mcp.order.mongo.common.MgConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service("mgWaitPayService")
public class MgWaitPayService {
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	public void save(MgWaitPay mgWaitPay)
	{
		this.mongoTemplate.save(mgWaitPay, MgConstants.WAIT_PAY);
	}
	
	public MgWaitPay findOne(String id)
	{
		return this.mongoTemplate.findById(id, MgWaitPay.class, MgConstants.WAIT_PAY);
	}
}
