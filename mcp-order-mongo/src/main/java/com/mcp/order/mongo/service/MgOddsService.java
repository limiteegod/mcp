/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.order.model.jc.JOdds;
import com.mcp.order.mongo.common.MgConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author ming.li
 *
 */
@Service("mgOddsService")
public class MgOddsService {
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	public JOdds findById(String id)
	{
		return this.mongoTemplate.findById(id, JOdds.class, MgConstants.TERM_JODDS);
	}
	
	public void save(JOdds jOdds)
	{
		String key = jOdds.getGameCode() + jOdds.getMatchCode() + jOdds.getOddsCode() + jOdds.getPlayTypeCode();
		jOdds.setId(key);
		this.mongoTemplate.save(jOdds, MgConstants.TERM_JODDS);
	}
	
	public void deleteById(String id)
	{
		this.mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), MgConstants.TERM_JODDS);
	}
	
	/**
	 * 查找id中所有的赔率信息
	 * @param idList
	 */
	public List<JOdds> findByIdList(List<String> idList)
	{
		return this.mongoTemplate.find(new Query(Criteria.where("_id").in(idList)), JOdds.class, MgConstants.TERM_JODDS);
	}
}
