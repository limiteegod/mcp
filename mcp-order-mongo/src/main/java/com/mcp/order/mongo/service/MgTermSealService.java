package com.mcp.order.mongo.service;

import com.mcp.order.model.mongo.MgTermSeal;
import com.mcp.order.model.mongo.MgWaitPay;
import com.mcp.order.mongo.common.MgConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("mgTermSealService")
public class MgTermSealService {
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	public void save(MgTermSeal mgTermSeal)
    {
        this.mongoTemplate.save(mgTermSeal, MgConstants.TERM_SEAL_INFO);
    }

    /**
     * 查找封存期次
     * @param gameCode 游戏代码
     * @param termCode 期次代码
     * @return
     */
    public MgTermSeal findOne(String gameCode, String termCode)
    {
        return this.mongoTemplate.findOne(new Query(Criteria.where("gameCode").is(gameCode).and("termCode").is(termCode)), MgTermSeal.class, MgConstants.TERM_SEAL_INFO);
    }

    /**
     * 获得上一天封存的所有的期次
     * @param start
     * @param end
     * @return
     */
    public List<MgTermSeal> findLastDaySealList(Date start, Date end)
    {
        Query query = new Query(Criteria.where("sTime").gte(start).lt(end));
        return this.mongoTemplate.find(query, MgTermSeal.class, MgConstants.TERM_SEAL_INFO);
    }

}
