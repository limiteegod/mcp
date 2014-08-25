/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.order.model.mongo.MgCurTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储普通游戏和高频游戏的当前期
 * @author ming.li
 */
@Service("mgCurTermService")
public class MgCurTermService {
	
	private String colName = "term_cur";
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	public List<MgCurTerm> findAll()
	{
		return this.mongoTemplate.findAll(MgCurTerm.class, colName);
	}
	
	/**
	 * 根据id获得当前期次信息
	 * @param id
	 * @return
	 */
	public MgCurTerm getById(String id)
	{
		MgCurTerm ct = this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), MgCurTerm.class, colName);
		return ct;
	}
	
	/**
	 * 跟新当前期次号
	 * @param id
	 * @return
	 */
	public void updateTermCodeById(String curTermCode, String id)
	{
		this.mongoTemplate.upsert(new Query(Criteria.where("_id").is(id)), new Update().set("curTermCode", curTermCode), colName);
	}
	
	/**
	 * 跟新上一期的期次号
	 * @param id
	 * @return
	 */
	public void updateLastTermCodeById(String lastTermCode, String id)
	{
		this.mongoTemplate.upsert(new Query(Criteria.where("_id").is(id)), new Update().set("lastTermCode", lastTermCode), colName);
	}
}
