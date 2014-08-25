/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.mongo.MgJcCheckTicket;
import com.mcp.order.model.mongo.MgNotifyMsg;
import com.mcp.order.mongo.common.MgConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
@Service("mgNotifyMsgService")
public class MgNotifyMsgService {
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	/**
	 * 插入一条通知到队列中
	 * @param channelCode
	 * @param mgNotifyMsg
	 */
	public void save(String channelCode, MgNotifyMsg mgNotifyMsg)
	{
		String colName = MgConstants.NOTIFY_QUEEN + channelCode;
		this.mongoTemplate.save(mgNotifyMsg, colName);
	}
	
	/**
	 * 查询未处理的
	 * @param channelCode
	 * @param page
	 * @return
	 */
	public Page<MgNotifyMsg> findUnused(String channelCode, Pageable page)
	{
		String colName = MgConstants.NOTIFY_QUEEN + channelCode;
		Criteria c = Criteria.where("status").is(ConstantValues.NOTIFY_STATUS_UNUSED.getCode());
		List<MgNotifyMsg> oList = mongoTemplate.find(new Query(c).with(page), 
				MgNotifyMsg.class, colName);
        return new PageImpl<MgNotifyMsg>(oList, page, this.mongoTemplate.count(new Query(c), colName));
	}

    /**
     * 查询已经处理的
     * @param channelCode
     * @param page
     * @return
     */
    public Page<MgNotifyMsg> findUsed(String channelCode, Pageable page)
    {
        String colName = MgConstants.NOTIFY_QUEEN + channelCode;
        Criteria c = Criteria.where("status").is(ConstantValues.NOTIFY_STATUS_USED.getCode());
        List<MgNotifyMsg> oList = mongoTemplate.find(new Query(c).with(page),
                MgNotifyMsg.class, colName);
        return new PageImpl<MgNotifyMsg>(oList, page, this.mongoTemplate.count(new Query(c), colName));
    }

    /**
     * 分页查找比minId要大的消息
     * @param minId
     * @param channelCode
     * @param page
     * @return
     */
    public Page<MgNotifyMsg> find(long minId, String channelCode, Pageable page)
    {
        String colName = MgConstants.NOTIFY_QUEEN + channelCode;
        Criteria c = Criteria.where("_id").gt(minId);
        Query query = new Query(c).with(page);
        List<MgNotifyMsg> oList = mongoTemplate.find(query,
                MgNotifyMsg.class, colName);
        return new PageImpl<MgNotifyMsg>(oList, page, this.mongoTemplate.count(query, colName));
    }

    /**
     * 查询渠道所有的
     * @param channelCode
     * @param page
     * @return
     */
    public Page<MgNotifyMsg> findAll(String channelCode, Pageable page)
    {
        String colName = MgConstants.NOTIFY_QUEEN + channelCode;
        List<MgNotifyMsg> oList = mongoTemplate.find(new Query().with(page),
                MgNotifyMsg.class, colName);
        return new PageImpl<MgNotifyMsg>(oList, page, this.mongoTemplate.count(new Query(), colName));
    }
	
	/**
	 * 消息已经发送，更新消息的状态
	 * @param channelCode
	 * @param id
	 */
	public void msgSended(String channelCode, long id)
	{
		String colName = MgConstants.NOTIFY_QUEEN + channelCode;
		Criteria c = Criteria.where("_id").is(id);
		Update update = new Update().set("status", ConstantValues.NOTIFY_STATUS_USED.getCode());
		this.mongoTemplate.findAndModify(new Query(c), update, MgNotifyMsg.class, colName);
	}

    /**
     * 重新发送消息
     * @param channelCode
     * @param id
     */
    public void resendMsg(String channelCode, long id)
    {
        String colName = MgConstants.NOTIFY_QUEEN + channelCode;
        Criteria c = Criteria.where("_id").is(id);
        Update update = new Update().set("status", ConstantValues.NOTIFY_STATUS_UNUSED.getCode());
        this.mongoTemplate.findAndModify(new Query(c), update, MgNotifyMsg.class, colName);
    }

}
