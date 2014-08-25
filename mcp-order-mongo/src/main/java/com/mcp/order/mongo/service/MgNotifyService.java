/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.mongo.common.MgConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service("mgNotifyService")
public class MgNotifyService {

    public static Logger log = Logger.getLogger(MgNotifyService.class);
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	/**
	 * 新增需要通知的队列
	 */
	public void saveWait(MgNotify mgNotify)
	{
		this.mongoTemplate.save(mgNotify, MgConstants.NOTIFY_CHANNEL_ALL);
	}

	
	/**
	 * 删除一个渠道
	 * @param id
	 */
	public void deleteWaitById(String id)
	{
		this.mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), MgConstants.NOTIFY_CHANNEL_ALL);
	}

	/**
	 * 查找所有需要处理的渠道
	 * @return
	 */
	public List<MgNotify> findAllWait()
	{
        Query query = new Query(Criteria.where("status").is(ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode()));
        return this.mongoTemplate.find(query, MgNotify.class, MgConstants.NOTIFY_CHANNEL_ALL);
	}

    /**
     * 查找所有需要处理的渠道
     * @return
     */
    public List<MgNotify> findAll()
    {
        return this.mongoTemplate.findAll(MgNotify.class, MgConstants.NOTIFY_CHANNEL_ALL);
    }

    /**
     * 加入运行队列
     * @param id
     */
    public void run(String id)
    {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("status", ConstantValues.NOTIFY_CHANNEL_STATUS_RUNNING.getCode());
        this.mongoTemplate.findAndModify(query, update, MgNotify.class, MgConstants.NOTIFY_CHANNEL_ALL);
    }

    /**
     * 加入等待队列
     * @param id
     */
    public void wait(String id)
    {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("status", ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode());
        this.mongoTemplate.findAndModify(query, update, MgNotify.class, MgConstants.NOTIFY_CHANNEL_ALL);
    }

    /**
     * 加入停用队列
     * @param id
     */
    public void stop(String id)
    {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("status", ConstantValues.NOTIFY_CHANNEL_STATUS_UNUSED.getCode());
        this.mongoTemplate.findAndModify(query, update, MgNotify.class, MgConstants.NOTIFY_CHANNEL_ALL);
    }

	
	/**
	 * 等待的队列中查找
	 * @param id
	 * @return
	 */
	public MgNotify findWait(String id)
	{
		return this.mongoTemplate.findById(id, MgNotify.class, MgConstants.NOTIFY_CHANNEL_ALL);
	}

    /**
     * @param id
     * @return
     */
    public MgNotify findById(String id)
    {
        return this.mongoTemplate.findById(id, MgNotify.class, MgConstants.NOTIFY_CHANNEL_ALL);
    }

    /**
     * 对渠道进行清理
     */
    public void clear()
    {
        Query query = new Query(Criteria.where("status").gt(ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode()));
        Update update = new Update().set("status", ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode());
        this.mongoTemplate.updateMulti(query, update, MgNotify.class, MgConstants.NOTIFY_CHANNEL_ALL);
    }

}
