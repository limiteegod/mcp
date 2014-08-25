/**
 * 
 */
package com.mcp.scheme.mongo.service;

import com.mcp.order.model.mongo.MgAutoIncrId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author ming.li
 *
 */
@Service("mgAutoIncrIdService")
public class MgAutoIncrIdService {
	
	private String colName = "mcp_id";
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	/**
	 * 获得自增id，并+1
	 * @param name
	 * @return
	 */
	public MgAutoIncrId getAutoIdAndIncrByName(String name)
	{
		MgAutoIncrId ai = mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(name)), new Update().inc("value", Long.valueOf(1)), MgAutoIncrId.class, colName);
		return ai;
	}
	
	/**
	 * 获得自增id
	 * @param name
	 * @return
	 */
	public MgAutoIncrId getAutoIdByName(String name)
	{
		MgAutoIncrId ai = mongoTemplate.findOne(new Query(Criteria.where("_id").is(name)), MgAutoIncrId.class, colName);
		return ai;
	}
	
	/**
	 * 增加一个键
	 * @param mgAutoIncrId
	 */
	public void save(MgAutoIncrId mgAutoIncrId)
	{
		mongoTemplate.save(mgAutoIncrId, colName);
	}
}
