/**
 * 
 */
package com.mcp.scheme.mongo.service;

import com.mcp.scheme.model.SchemeZh;
import com.mcp.scheme.mongo.common.MgSchemeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("mgZhService")
public class MgZhService {
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	/**
	 * 保存追号方案
	 * @param scheme
	 */
	public void save(SchemeZh scheme)
	{
		String colName = MgSchemeConstants.MG_SCHEME_ZH_POOL + "_" + scheme.getGameCode();
		this.mongoTemplate.save(scheme, colName);
	}
	
	/**
	 * 期次开奖后，查找需要处理的追号方案
	 * @param gameCode
	 * @param termCode
	 * @param p
	 * @return
	 */
	public List<SchemeZh> findToDraw(String gameCode, String termCode, Pageable p)
	{
		String colName = MgSchemeConstants.MG_SCHEME_ZH_POOL + "_" + gameCode;
		List<SchemeZh> oList = this.mongoTemplate.find(new Query(Criteria.where("curTermCode").is(termCode)).with(p), SchemeZh.class, colName);
		return oList;
	}
	
	/**
	 * 删除一条记录
	 * @param gameCode
	 * @param id
	 */
	public void delete(String gameCode, String id)
	{
		String colName = MgSchemeConstants.MG_SCHEME_ZH_POOL + "_" + gameCode;
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), colName);
	}
	
	/**
	 * 新增了一个订单
	 * @param gameCode
	 * @param id
	 */
	public void incrNewOrder(String gameCode, String nextTermCode, String id)
	{
		String colName = MgSchemeConstants.MG_SCHEME_ZH_POOL + "_" + gameCode;
		Update up = new Update().inc("finishedOrderCount", Long.valueOf(1)).set("curTermCode", nextTermCode);
		this.mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), up, colName);
	}
}
